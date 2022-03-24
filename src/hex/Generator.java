package hex;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.struct.StringMap;
import hex.types.Hex;
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
import static hex.components.Bundle.defaultLocale;
import static hex.components.Bundle.get;
import static mindustry.Vars.*;

public class Generator {

    private static int last;
    private static Queue<Set> calls = new Queue<>();
    private static Seq<Runnable> tasks = new Seq<>();

    public static void play() {
        humans.each(human -> human.lose != null, human -> human.lose.cancel()); // cancel all lose tasks
        hexes.clear();
        humans.clear();
        last = 0;

        generate(MapSize.get()); // generate hex map
        Call.worldDataBegin(); // synchronize the world
        Groups.player.each(netServer::sendWorldData);
        Groups.player.each(Politics::join); // handle all players
    }

    public static void generate(MapSize s) {
        world.loadGenerator(s.width, s.height, tiles -> tiles.each((x, y) -> tiles.set(x, y, new Tile(x, y, Blocks.air, Blocks.air, Blocks.darkMetal))));

        Point2 start = new Point2();
        Point2 point = new Point2();

        while (true) {
            hexes.add(new Hex(point.x, point.y));
            point.add(38, 0);

            if (Hex.bounds(point.x, point.y)) {
                start.add(19 * (start.x == 0 ? 1 : -1), 11);
                point.set(start);

                if (Hex.bounds(start.x, start.y)) break;
            }
        }

        // Создаем карту с нужным названием
        state.map = new Map(StringMap.of("name", "Industry", "author", get("author", defaultLocale()), "description", "A special generated map for Hex-industry gamemode."));
    }

    public static Hex citadel(Player player) {
        Hex hex = citadel();

        hex.env = Hex.HexEnv.citadel;
        hex.door = (byte) 0x00FFFFFF;
        hex.open();
        hex.base = true;

        player.team(team());
        return hex;
    }

    public static Hex citadel() {
        Seq<Hex> closed = hexes.select(Hex::isClosed);
        return closed.sort(h -> humans.sumf(human -> {
            float dst = h.point().dst(human.citadel.point());
            return dst > 100f ? -dst : Mathf.sqr(100f - dst);
        })).get(Mathf.random(humans.isEmpty() ? closed.size - 1 : closed.size / (humans.size + 2)));
    }

    public static Team team() {
        return Team.all[++last];
    }

    // Queue functions
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

        public static MapSize get() {
            int amount = Groups.player.size();
            return amount < 3 ? small : amount < 5 ? medium : big;
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
