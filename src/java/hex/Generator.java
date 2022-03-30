package hex;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.struct.StringMap;
import arc.util.Time;
import hex.content.Buttons;
import hex.types.Hex;
import hex.types.Hex.HexEnv;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.maps.Map;
import mindustry.world.Block;
import mindustry.world.Tile;

import static hex.Main.hexes;
import static hex.Main.humans;
import static hex.types.Human.units;
import static hex.components.Bundle.defaultLocale;
import static hex.components.Bundle.get;
import static mindustry.Vars.*;

public class Generator {

    private static int last;
    private static final Queue<Set> calls = new Queue<>();
    private static final Seq<Runnable> tasks = new Seq<>();

    public static void restart() {
        play(MapSize.get(Groups.player.size()));
    }

    public static void play(MapSize size) {
        Politics.clear();
        Buttons.clear();
        Time.clear();
        units.clear();
        hexes.clear();
        humans.clear(); // someone could enter while the game was over
        last = 0;

        // it's looks bad
        for (Team team : Team.all) team.data().blocks.clear();

        generate(size); // generate hex map
        Call.worldDataBegin(); // synchronize the world
        Groups.unit.each(Call::unitDespawn);
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

        Generator.spray(HexEnv.oil, .4f);
        Generator.spray(HexEnv.water, .25f);
        Generator.spray(HexEnv.cryo, .1f);
        Generator.spray(HexEnv.forest, .5f);
        Generator.spray(HexEnv.spore, .15f);

        // create a map with the necessary tags
        state.map = new Map(StringMap.of("name", "Industry", "author", get("author", defaultLocale()), "description", "A special generated map for Hex-industry gamemode."));
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

    public static Hex citadel() {
        Seq<Hex> closed = hexes.select(Hex::isClosed);
        return closed.sort(hex -> humans.sumf(human -> {
            float dst = hex.point().dst(human.citadel.point());
            return dst > 100f ? -dst : Mathf.sqr(100f - dst);
        })).get(Mathf.random(humans.isEmpty() ? closed.size - 1 : closed.size / (humans.size + 2)));
    }

    // methods to generate
    private static void spray(HexEnv env, float amount) {
        Seq<Hex> base = hexes.select(hex -> hex.base);
        for (float i = 0; i < base.size; i += 1 / amount + rand(1f))
            closest(base.get((int) i).point().add((int) rand(70f), (int) rand(70f))).env = env;
    }

    private static float rand(float range) {
        return Mathf.random(range) - range / 2f;
    }

    private static Hex closest(Point2 pos) {
        return hexes.select(hex -> hex.env == HexEnv.titanium || hex.env == HexEnv.thorium).min(hex -> hex.point().dst(pos));
    }

    public static Team team() {
        return Team.all[++last];
    }

    // queue functions
    public static void update() {
        if (calls.isEmpty()) {
            tasks.each(Runnable::run);
            tasks.clear();
        } else for (int i = 0; i < Math.min(100, calls.size); i++) calls.removeFirst().set();
    }

    public static void set(int x, int y, Block block) {
        set(x, y, null, block, null);
    }

    public static void set(int x, int y, Block floor, Block overlay) {
        set(x, y, floor, null, overlay);
    }

    public static void set(int x, int y, Block floor, Block block, Block overlay) {
        calls.addLast(new Set(world.tile(x, y), floor, block, overlay));
    }

    /** Used to host cores */
    public static void setc(int x, int y, Block block, Team team) {
        world.tile(x, y).setNet(block, team, 0);
        Call.effect(Fx.instBomb, x * tilesize, y * tilesize, 0, Color.white);
    }

    public static void onEmpty(Runnable todo) {
        tasks.add(todo);
    }

    public enum MapSize {
        small(198, 201), medium(369, 366), big(540, 542);

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
                default -> null;
            };
        }

        public static MapSize get(int players) {
            return players < 4 ? small : players < 7 ? medium : big;
        }
    }

    public static class Set {
        public final Tile tile;

        public Block floor;
        public Block block;
        public Block overlay;

        public Set(Tile tile, Block floor, Block block, Block overlay) {
            this.tile = tile;
            this.floor = floor;
            this.block = block;
            this.overlay = overlay;
        }

        public void set() {
            boolean f = floor != null, o = overlay != null;
            if (f || o) Call.setFloor(tile, f ? floor : tile.floor(), o ? overlay : tile.overlay());
            if (block != null) Call.setTile(tile, block, Team.derelict, 0);
        }
    }
}
