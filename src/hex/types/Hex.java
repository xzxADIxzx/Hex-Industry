package hex.types;

import arc.func.Cons3;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Point2;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import hex.Generator;
import hex.content.Buttons;
import hex.content.HexBuilds;
import hex.content.HexSchematics;
import hex.types.buttons.BuildButton;
import hex.types.buttons.Button;
import hex.types.buttons.OpenButton;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.gen.Call;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;

import static hex.Main.hexes;
import static hex.components.Bundle.format;
import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class Hex {

    public static final int width = 27;
    public static final int height = 25;
    public static final int radius = 52;
    public static final Rand random = new Rand();
    public static final float basedst = 600f;

    protected static int _id;

    public int x;
    public int y;
    public int cx;
    public int cy;

    public float fx;
    public float fy;

    public float health;
    public float step;
    public Color color;

    public Human owner;
    public int id;

    public Seq<Button> buttons = new Seq<>();
    public HexBuild build;

    public boolean open;
    public boolean busy;
    public boolean base;
    public HexEnv env;
    public byte door;

    public Hex(int x, int y) {
        this.x = x;
        this.y = y;

        cx = x + width / 2;
        cy = y + height / 2;

        fx = cx * tilesize;
        fy = cy * tilesize;

        base = (cx - 13) % 57 == 0 && (cy - ((cx - 13) % 114 == 0 ? 12 : 45)) % 66 == 0;
        env = base ? HexEnv.base : HexEnv.get();
        door = (byte) random.nextLong();
        id = _id++;

        HexSchematics.hex.floor(x, y);
        HexSchematics.closed.floor(x, y);
    }

    public void update(Human human) {
        buttons.each(b -> b.update(human));

        if (busy) for (int i = 0; i < 5; i++) Time.run(i * 12f, () -> smoke(human));
        else for (int deg = 0; deg < health; deg++) {
                float dx = fx + Mathf.cosDeg(deg * step) * radius;
                float dy = fy + Mathf.sinDeg(deg * step) * radius;
                Call.effect(human.player.con, Fx.mineSmall, dx, dy, 0, color);
            }
    }

    public void smoke(Human human) {
        float deg = Mathf.random(360f);
        float dst = Mathf.random(110f);
        float dx = fx + Mathf.cosDeg(deg) * dst;
        float dy = fy + Mathf.sinDeg(deg) * dst;
        Call.effect(human.player.con, Fx.smokeCloud, dx, dy, 0, color);
    }

    public static boolean bounds(int x, int y) {
        return x + width > world.width() || y + height > world.height();
    }

    public void build(HexBuild build) {
        build.build(this);
        this.build = build;

        health = build.health;
        step = 360f / health;
        damage(0); // update color

        if (base) Time.run(180f, () -> {
            Generator.setc(cx, cy, isCitadel() ? Blocks.coreNucleus : Blocks.coreShard, owner.player.team());
            owner.updateModule();
        });
    }

    public boolean damage(int damage) {
        color = Pal.health.cpy().lerp(Pal.plastanium, health / build.health);
        cooldown(damage == 0 ? 600f : 3600f);

        health -= damage;
        return health <= 0;
    }

    public void cooldown(float time) {
        busy = true;
        Time.run(time, () -> busy = false);
    }

    public void lose(Human attacker) {
        build.destroy(owner.production);
        if (attacker != null) {
            owner.player.sendMessage(format("hex.attacked", owner.locale, attacker.player.coloredName(), cx, cy));
            owner.production.check(owner);
            attacker.stats.destroyed++;
        }
        clear();

        Human human = Human.from(this);
        if (base && human != null) human.lose();
    }

    public void lose(Human attacker, UnitType unit, int amount) {
        Time.run(600f, () -> lose(attacker));
        for (int i = 0; i < amount; i++) {
            float deg = Mathf.random(360f);
            float dst = Mathf.random(20f, 80f);
            float dx = fx + Mathf.cosDeg(deg) * dst;
            float dy = fy + Mathf.sinDeg(deg) * dst;
            unit.spawn(attacker.player.team(), dx, dy);
        }
    }

    public void open() {
        HexSchematics.door(door).airNet(x, y);
        env.build(this);
        open = true;

        Time.run(60f, () -> openedNeighbours().each(bour -> {
            if (bour.isClosed()) bour.buttons.add(new OpenButton(bour));
        }));
    }

    public void clear() {
        build.explode(this);
        env.build(this);

        build = null;
        owner = null;
        health = 0;
    }

    public void clearButtons() {
        buttons.each(Buttons::unregister);
        buttons.clear();
        env.terrain(this);
    }

    public Seq<Hex> neighbours() {
        return hexes.select(hex -> pos().within(hex.pos(), 210f));
    }

    public Seq<Hex> openedNeighbours() {
        return neighbours().select(hex -> world.tile((hex.cx + cx) / 2, (hex.cy + cy) / 2).block() == Blocks.air && hex != this);
    }

    public Position pos() {
        return new Vec2(fx, fy);
    }

    public Point2 point() {
        return new Point2(cx, cy);
    }

    public boolean isClosed() {
        return !open;
    }

    public boolean isCaptured(Human owner) {
        return hexes.contains(hex -> hex.base && pos().within(hex.pos(), basedst) && hex.owner == owner.leader);
    }

    public boolean isCitadel() {
        return owner != null && (owner.citadel == this || owner.slaves().contains(h -> h.citadel == this));
    }

    public enum HexEnv {
        citadel(0f, HexSchematics.citadelLr1, HexSchematics.citadelLr2) {
            // there is nothing, because the citadel building will add the necessary buttons
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {}
        },
        base(0f, HexSchematics.baseLr1, HexSchematics.baseLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.base, 0, 0);
            }
        },
        titanium(.25f, HexSchematics.titaniumLr1, HexSchematics.titaniumLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.compressor, 4, 4);
                add.get(HexBuilds.miner, -6, -3);
            }
        },
        thorium(.35f, HexSchematics.thoriumLr1, HexSchematics.thoriumLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.thory, 0, 0);
            }
        },
        spore(.2f, HexSchematics.sporeLr1, HexSchematics.sporeLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.spore, -7, -2);
                add.get(HexBuilds.maze, 4, -6);
            }
        },
        oil(.35f, HexSchematics.oilLr1, HexSchematics.oilLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.oil, 7, 2);
            }
        },
        water(.4f, HexSchematics.waterLr1, HexSchematics.waterLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.water, 0, 0);
            }
        },
        cryo(.4f, HexSchematics.cryoLr1, HexSchematics.cryoLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.cryo, -3, -6);
            }
        },
        forest(1f, HexSchematics.forestLr1, HexSchematics.forestLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.city, -1, 1);
            }
        };

        protected final float frq;
        protected final HexSchematic Lr1;
        protected final HexSchematic Lr2;

        HexEnv(float chance, HexSchematic floor, HexSchematic block) {
            frq = chance;
            Lr1 = floor;
            Lr2 = block;
        }

        public static HexEnv get() {
            for (HexEnv env : values())
                if (Mathf.chance(env.frq)) return env;
            return null; // never happen because the last one has a 100% drop chance
        }

        public void build(Hex hex) {
            hex.clearButtons();
            addButtons((build, x, y) -> hex.buttons.add(new BuildButton(build, hex, hex.cx + x, hex.cy + y)));
        }

        public void terrain(Hex hex) {
            Lr1.floorNet(hex.x, hex.y);
            Lr1.airNet(hex.x, hex.y);

            Lr2.each(st -> {
                if (st.block instanceof Floor) Generator.set(st.x + hex.x, st.y + hex.y, null, st.block);
                else Generator.set(st.x + hex.x, st.y + hex.y, st.block);
            });
        }

        public abstract void addButtons(Cons3<HexBuild, Integer, Integer> add);
    }
}
