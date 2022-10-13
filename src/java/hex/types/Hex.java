package hex.types;

import arc.func.Cons;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import hex.Generator;
import hex.content.Buttons;
import hex.content.HexBuilds;
import hex.types.buttons.BuildButton;
import hex.types.buttons.Button;
import hex.types.buttons.OpenButton;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Call;
import mindustry.graphics.Pal;
import mindustry.world.blocks.environment.Floor;
import useful.Bundle;

import static hex.Main.*;
import static hex.content.HexSchematics.*;
import static mindustry.Vars.*;

public class Hex implements Position {

    /** Radius in which the base hex allows you to do things to other hexes. */
    public static final float basedst = 600f;

    public static final int width = 27;
    public static final int height = 25;
    public static final int radius = 52;

    private static int idprov;
    public final int id;

    public int x, y;
    public int cx, cy;
    public float fx, fy;

    public Human owner;
    public HexBuild build;

    public HexEnv env;
    public Seq<Button> buttons = new Seq<>();

    public boolean open;
    public boolean busy;
    public boolean base;
    public byte door;

    private int health;
    private float step;
    private Color color;
    private int instdeg;

    public Hex(Point2 pos) {
        id = idprov++;

        x = pos.x;
        y = pos.y;

        cx = x + width / 2;
        cy = y + height / 2;

        fx = cx * tilesize;
        fy = cy * tilesize;

        base = (cx - 13) % 57 == 0 && (cy - ((cx - 13) % 114 == 0 ? 12 : 45)) % 66 == 0; // no one knows how it works
        door = (byte) Mathf.rand.nextLong();

        env = base ? HexEnv.base : HexEnv.standard();
        closed.floor(x, y); // door's terrain
    }

    public void update(Human human) {
        if (busy) for (float i = 0; i < 60; i += 10f) Time.run(i, () -> smoke(human));
        else for (int deg = 0; deg < health; deg++) health(human, deg);

        if (owner == null || owner == human.leader) buttons.each(b -> b.update(human));
        if (base && open) for (float i = 0; i < 60; i += 2f) Time.run(i, () -> inst(human));
    }

    public void open() {
        door(door).airNet(x, y);
        clear();
        open = true;

        Generator.onEmpty(() -> openedNeighbours().each(bour -> {
            if (bour.isClosed()) bour.buttons.add(new OpenButton(bour));
        }));
    }

    public void build(HexBuild build) {
        if (build == null) return; // this happens sometimes

        build.build(this);
        this.build = build;

        health = build.health;
        step = 360f / health;
        damage(0); // update color and run cooldown

        if (base) Generator.onEmpty(() -> Generator.setc(cx, cy, isCitadel() ? Blocks.coreNucleus : Blocks.coreShard, owner.player.team()));
    }

    public boolean damage(int damage) {
        health = Math.max(health - damage, 0);
        color = Color.valueOf("38d667").lerp(Pal.health, 1 - (float) health / build.health);

        cooldown(damage == 0 ? 600f : Time.toMinutes);
        return health <= 0;
    }

    public void cooldown(float time) {
        busy = true;
        Time.run(time, () -> busy = false);
    }

    public void kill(Human killer) {
        if (owner == null) return; // this happens sometimes

        owner.production.check(owner);
        killer.stats.destroyed++;

        kill();
    }

    public void kill() {
        if (owner == null) return; // this happens sometimes

        build.destroy(owner.production);
        clear();

        Human human = Human.find(this);
        if (human != null) human.lose(); // kill human if this hex is a citadel
    }

    public void clear() {
        clearBuild();
        env.buttons(this);

        owner = null;
        build = null;
        health = 0;
    }

    public void clearBuild() {
        if (build != null) build.explode(this);
        env.terrain(this);

        buttons.each(Buttons::unregister);
        buttons.clear();
    }

    // region getters

    public String health(Human human) {
        return health == 0 ? Bundle.get("hex.zerohp", human) : Bundle.format("hex.health", human, color, health, build.health);
    }

    public Seq<Hex> neighbours() {
        return hexes.select(hex -> within(hex, 210f));
    }

    public Seq<Hex> openedNeighbours() {
        return neighbours().select(hex -> world.tile((hex.cx + cx) / 2, (hex.cy + cy) / 2).block().isAir() && hex != this);
    }

    public Vec2 vec() {
        return new Vec2(fx, fy);
    }

    public Point2 point() {
        return new Point2(cx, cy);
    }

    public boolean isClosed() {
        return !open;
    }

    public boolean isCaptured(Human owner) {
        return hexes.contains(hex -> hex.owner == owner.leader && hex.base && within(hex, basedst));
    }

    public boolean isCitadel() {
        return owner != null && (owner.citadel == this || owner.slaves().contains(h -> h.citadel == this));
    }

    @Override
    public float getX() {
        return fx;
    }

    @Override
    public float getY() {
        return fy;
    }

    // endregion
    // region particles

    public void smoke(Human human) {
        particle(human, Fx.smokeCloud, Mathf.random(360f), Mathf.random(110f), 0f, Color.white);
    }

    public void health(Human human, float deg) {
        particle(human, Fx.mineSmall, deg * step, radius, 0f, color);
    }

    public void inst(Human human) {
        particle(human, Fx.instShoot, instdeg += 12f, basedst, instdeg, Color.white);
    }

    public void particle(Human human, Effect effect, float deg, float dst, float rotation, Color color) {
        Call.effect(human.player.con, effect,
                fx + Mathf.cosDeg(deg) * dst,
                fy + Mathf.sinDeg(deg) * dst, rotation, color);
    }

    // endregion
    // region other stuffs

    public void attacked(Human human, Weapon weapon) {
        attacked(owner, human, weapon);
        owner.slaves().each(slave -> attacked(slave, human, weapon));
    }

    private void attacked(Human to, Human from, Weapon weapon) {
        Bundle.bundled(to.player, "hex.attack",
                from.player.coloredName(), cx, cy,
                Bundle.get(build.name, to), health(to), Bundle.get(weapon.name + ".name", to));
    }

    public static boolean bounds(Point2 pos) {
        return pos.x + width > world.width() || pos.y + height > world.height();
    }

    // endregion

    public enum HexEnv {
        citadel(citadelLr1, citadelLr2), // there is nothing, because the citadel building will add the necessary buttons
        base(baseLr1, baseLr2, prov -> {
            prov.add(HexBuilds.base, 0, 0);
        }),
        titanium(titaniumLr1, titaniumLr2, prov -> {
            prov.add(HexBuilds.compressor, 4, 4);
            prov.add(HexBuilds.miner, -6, -3);
        }),
        thorium(thoriumLr1, thoriumLr2, prov -> {
            prov.add(HexBuilds.thory, 0, 0);
        }),
        oil(oilLr1, oilLr2, prov -> {
            prov.add(HexBuilds.oil, 7, 2);
        }),
        water(waterLr1, waterLr2, prov -> {
            prov.add(HexBuilds.water, 0, 0);
        }),
        cryo(cryoLr1, cryoLr2, prov -> {
            prov.add(HexBuilds.cryo, -3, -6);
        }),
        arkycite(arkyciteLr1, arkyciteLr2, prov -> {
            prov.add(HexBuilds.arkycite, 0, 0);
        }),
        forest(forestLr1, forestLr2, prov -> {
            prov.add(HexBuilds.city, -1, 1);
        }),
        spore(sporeLr1, sporeLr2, prov -> {
            prov.add(HexBuilds.cultivator, -7, -2);
            prov.add(HexBuilds.maze, 4, -6);
        }),
        canyon(canyonLr1, canyonLr2),
        gradient(gradientLr1, gradientLr2);

        private final HexSchematic lr1;
        private final HexSchematic lr2;
        private final Cons<ButtonProv> buttons;

        HexEnv(HexSchematic floor, HexSchematic block) {
            this(floor, block, prov -> {});
        }

        HexEnv(HexSchematic floor, HexSchematic block, Cons<ButtonProv> buttons) {
            this.lr1 = floor;
            this.lr2 = block;
            this.buttons = buttons;
        }

        public static HexEnv standard() {
            return Mathf.chance(.6f) ? titanium : thorium;
        }

        public void terrain(Hex hex) {
            lr1.floorNet(hex.x, hex.y);
            lr1.airNet(hex.x, hex.y);

            lr2.tiles.each(st -> {
                if (st.block instanceof Floor) Generator.set(st.x + hex.x, st.y + hex.y, null, st.block);
                else Generator.set(st.x + hex.x, st.y + hex.y, st.block);
            });
        }

        public void buttons(Hex hex) {
            buttons.get((build, x, y) -> hex.buttons.add(new BuildButton(build, hex, hex.cx + x, hex.cy + y)));
        }

        public interface ButtonProv {
            public void add(HexBuild build, int x, int y);
        }
    }
}
