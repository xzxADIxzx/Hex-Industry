package hex;

import arc.func.Cons;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.struct.StringMap;
import arc.util.Time;
import hex.types.Hex;
import hex.types.Human;
import hex.types.Hex.HexEnv;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.maps.Map;
import mindustry.world.Block;
import mindustry.world.Tile;
import useful.Prefixes;

import static hex.Main.*;
import static hex.Politics.*;
import static hex.types.Human.units;
import static hex.content.Buttons.*;
import static mindustry.Vars.*;

public class Generator {

    private static int last;
    private static final Queue<Runnable> calls = new Queue<>();
    private static final Seq<Runnable> tasks = new Seq<>();

    public static void restart() {
        clear(); // someone could enter while the game was over
        last = 0; // sooner or later an OutOfBoundsException will pop up

        Groups.player.each(player -> player.team(Team.derelict)); // someone could spawn
        Groups.unit.each(Call::unitDespawn); // units and blocks can stay on the new map

        for (Team team : Team.all) team.data().plans.clear(); // ghost blocks looks bad
        play(MapSize.get(Groups.player.size())); // map size depends on players amount
    }

    public static void clear() {
        Prefixes.removeDisconnected(); // cleanup to prevent memory lose
        Prefixes.each(data -> data.remove(Human.prefix).apply());

        Time.clear(); // cancel tasks for safety
        units.clear(); // remove the dependence of the player on the unit

        buttons.each(button -> button.label.hide());
        humans.each(human -> {
            if (human.lose != null) human.lose.cancel(); // task can be executed after new game
        });

        buttons.clear();
        awaiting.clear();

        offers.clear();
        left.clear();

        hexes.clear();
        humans.clear(); // no one should stay alive
    }

    public static void play(MapSize size) {
        generate(size); // generate hex map
        Call.worldDataBegin(); // synchronize the world
        Groups.player.each(netServer::sendWorldData);
        Groups.player.each(Politics::join); // handle all players
    }

    public static void generate(MapSize s) {
        world.loadGenerator(s.width, s.height, tiles -> tiles.each((x, y) -> tiles.set(x, y, new Tile(x, y, Blocks.darkPanel3, Blocks.air, Blocks.darkMetal))));

        Point2 start = new Point2();
        Point2 point = new Point2();

        while (true) {
            hexes.add(new Hex(point));
            point.add(38, 0);

            if (Hex.bounds(point)) {
                start.add(19 * (start.x == 0 ? 1 : -1), 11);
                point.set(start);

                if (Hex.bounds(start)) break;
            }
        }

        spray(HexEnv.oil, .4f);
        spray(HexEnv.water, .25f);
        spray(HexEnv.cryo, .11f);
        spray(HexEnv.arkycite, .08f);
        spray(HexEnv.forest, .5f);
        spray(HexEnv.spore, .15f);
        pile(HexEnv.canyon, .08f, 2, 4);
        pile(HexEnv.gradient, .08f, 0, 1);
        pile(HexEnv.wasteland, .08f, 0, 1);

        // create a map with the necessary tags
        state.map = new Map(StringMap.of(
                "name", "Helindo",
                "author", "xzxADIxzx",
                "description", "A special generated map for Hex-Industry gamemode."));
    }

    public static Hex citadel(Player player) {
        Hex hex = citadel();

        hex.env = HexEnv.citadel;
        hex.door = (byte) 0x00FFFFFF;
        hex.open();
        hex.base = true;

        player.team(team());
        return hex;
    }

    private static Hex citadel() {
        Seq<Hex> closed = common().select(Hex::isClosed);
        if (closed.isEmpty()) closed = hexes.select(Hex::isClosed);

        return closed.sort(hex -> humans.sumf(human -> {
            float dst = hex.point().dst(human.citadel.point());
            return dst > 100f ? -dst : Mathf.sqr(100f - dst);
        })).get(Mathf.random(humans.isEmpty() ? closed.size - 1 : closed.size / (humans.size + 2)));
    }

    // region methods to generate

    private static void template(float amount, Cons<Hex> cons) {
        Seq<Hex> base = hexes.select(hex -> hex.base);
        for (float i = 0; i < base.size; i += 1 / amount + rand(1.3f))
            cons.get(closest(base.get((int) i).point().add((int) rand(70f), (int) rand(70f))));
    }

    private static void spray(HexEnv env, float amount) {
        template(amount, hex -> hex.env = env);
    }

    private static void pile(HexEnv env, float amount, int min, int max) {
        template(amount, hex -> {
            hex.env = env;
            for (int q = 0; q < Mathf.random(min, max); q++) {
                hex = closest(hex.point().add((int) rand(20f), (int) rand(20f)));
                hex.env = env;
            }
        });
    }

    private static float rand(float range) {
        return Mathf.random(range) - range / 2f;
    }

    private static Hex closest(Point2 pos) {
        return common().min(hex -> hex.point().dst(pos));
    }

    private static Seq<Hex> common() {
        return hexes.select(hex -> hex.env == HexEnv.titanium || hex.env == HexEnv.thorium);
    }

    private static Team team() {
        return Team.all[++last];
    }

    // endregion
    // region queue functions
    
    public static void update() {
        if (calls.isEmpty()) {
            tasks.each(Runnable::run);
            tasks.clear();
        } else for (int i = 0; i < Math.min(100, calls.size); i++) calls.removeFirst().run();
    }

    public static void set(int x, int y, Block block) {
        set(x, y, null, block, null);
    }

    public static void set(int x, int y, Block floor, Block overlay) {
        set(x, y, floor, null, overlay);
    }

    public static void set(int x, int y, Block floor, Block block, Block overlay) {
        Tile tile = world.tile(x, y);
        boolean f = floor != null, o = overlay != null;

        calls.addLast(() -> {
            if (f || o) Call.setFloor(tile, f ? floor : tile.floor(), o ? overlay : tile.overlay());
            if (block != null) Call.setTile(tile, block, Team.derelict, 0);
        });
    }

    /** Used to host cores. */
    public static void setc(int x, int y, Block block, Team team) {
        world.tile(x, y).setNet(block, team, 0);
        Call.effect(Fx.instBomb, x * tilesize, y * tilesize, 0, Color.white);
    }

    public static void sett(Building build, Team team) {
        calls.addLast(() -> Call.setTeam(build, team));
    }

    public static void onEmpty(Runnable todo) {
        tasks.add(todo);
    }

    // endregion

    public enum MapSize {
        small(198, 201), medium(369, 366), big(540, 542), wide(711, 542);

        public final int width;
        public final int height;

        MapSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public static MapSize get(String name) {
            return switch (name.toLowerCase()) {
                case "small", "1" -> small;
                case "medium", "2" -> medium;
                case "big", "3" -> big;
                case "wide", "4" -> wide;
                default -> null;
            };
        }

        public static MapSize get(int players) {
            return players <= 3 ? small : players <= 6 ? medium : players <= 9 ? big : wide;
        }
    }
}
