package hex;

import arc.Events;
import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Strings;
import arc.util.Timer;
import hex.components.Icons;
import hex.components.MenuListener;
import hex.content.Buttons;
import hex.content.Fractions;
import hex.content.HexBuilds;
import hex.content.HexSchematics;
import hex.types.Hex;
import hex.types.Human;
import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Administration;

import static hex.components.Bundle.findLocale;
import static hex.components.Bundle.get;
import static mindustry.Vars.*;

public class Main extends Plugin {

    public static Seq<Hex> hexes = new Seq<>();
    public static Seq<Human> humans = new Seq<>();

    @Override
    public void init() {
        HexSchematics.load();
        Fractions.load();
        HexBuilds.load();
        Buttons.load();
        MenuListener.load();
        Icons.load();

        netServer.admins.actionFilters.clear();
        netServer.admins.addActionFilter(action -> false);

        Administration.Config.strict.set(false);

        Timer.schedule(() -> {
            humans.each(h -> h.production.update());
            Buttons.update();
        }, 0f, 1f);

        Timer.schedule(() -> humans.each(Human::update), 0f, .05f);

        Events.on(PlayerJoin.class, event -> Politics.join(event.player));
        Events.on(PlayerLeave.class, event -> Politics.leave(event.player));
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("attack", "[hex]", "Attack a hex.", (args, player) -> Politics.attack(args.length > 0 ? args[0] : null, player));

        handler.<Player>register("peace", "<player>", "Offer the player a peace.", (args, player) -> Politics.peace(args[0], player));

        handler.<Player>register("join", "<player>", "Offer the player to team up.", (args, player) -> Politics.join(args[0], player));

        handler.<Player>register("spectate", "Watching the game is fun too.", (args, player) -> Politics.spectate(player));

        handler.<Player>register("author", "Plugin creators.", (args, player) -> player.sendMessage(get("author", findLocale(player))));
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("host", "[size]", "Initialize new game.", args -> {
            // generate hex-map
            Generator.generate(args.length > 0 ? Strings.parseInt(args[0], 0) : 0);

            // change rules
            state.rules.enemyCoreBuildRadius = 0f;
            state.rules.unitCap = 16;
            state.rules.infiniteResources = true;
            state.rules.waves = false;
            state.rules.canGameOver = false;
            state.rules.modeName = "Hex Industry";
            state.rules.defaultTeam = Team.derelict;

            for (Team team : Team.all) state.rules.teams.get(team).cheat = true;

            // synchronize the world
            Call.worldDataBegin();
            Groups.player.each(netServer::sendWorldData);

            // handle all players
            Groups.player.each(Politics::join);

            logic.play();
            netServer.openServer();
        });
    }
}
