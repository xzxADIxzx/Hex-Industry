package hex;

import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Timer;
import hex.content.Fractions;
import hex.content.HexBuilds;
import hex.content.Schems;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.mod.Plugin;

import static mindustry.Vars.*;

public class Main extends Plugin {

    public static boolean initialized;

    public static Seq<Hex> hexes = new Seq<>();
    public static Seq<Human> humans = new Seq<>();

    @Override
    public void init() {
        Schems.load();
        Fractions.load();
        HexBuilds.load();

        netServer.admins.actionFilters.clear();
        netServer.admins.addActionFilter(action -> false);

        Timer.schedule(() -> {
            if (initialized)
                humans.each(p -> p.production.update());
        }, 0f, 1f);

        Timer.schedule(() -> humans.each(p -> p.player != player, p -> Call.setHudText(p.player.con, String.valueOf(p.location().id))), 0f, 0.01f);
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {}

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("init", "Initialize new game", args -> {
            // change rules
            state.rules.enemyCoreBuildRadius = 0f;
            state.rules.unitCap = 16;

            // generate hex-map
            Point2 start = new Point2();
            Point2 point = new Point2();

            while (true) {
                hexes.add(new Hex(point.x, point.y));
                point.add(38, 0);

                if (!Hex.bounds(point.x, point.y)) {
                    start.add(19 * (start.x == 0 ? 1 : -1), 11);
                    point.set(start);

                    if (!Hex.bounds(start.x, start.y)) break;
                }
            }

            // synchronize the world
            Call.worldDataBegin();
            Groups.player.each(p -> p != player, p -> netServer.sendWorldData(p));

            // ask unit type & abilities
            Groups.player.each(p -> humans.add(new Human(p, Fractions.horde)));

            // spawn a citadel in a random hex
            humans.each(p -> p.init(hexes.get(Mathf.random(hexes.size))));

            initialized = true;
        });
    }
}
