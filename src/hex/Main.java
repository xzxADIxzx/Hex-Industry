package hex;

import arc.Events;
import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Strings;
import arc.util.Timer;
import hex.components.Icons;
import hex.components.MenuListener;
import hex.content.*;
import hex.types.Hex;
import hex.types.Human;
import hex.types.ai.HexBuilderAI;
import hex.types.ai.HexMinerAI;
import mindustry.game.EventType.PlayerJoin;
import mindustry.game.EventType.PlayerLeave;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Administration;
import mindustry.world.blocks.distribution.MassDriver;

import static hex.components.Bundle.findLocale;
import static hex.components.Bundle.get;
import static mindustry.Vars.*;

public class Main extends Plugin {

    public static Seq<Hex> hexes = new Seq<>();
    public static Seq<Human> humans = new Seq<>();
    public static Rules rules = new Rules();

    @Override
    public void init() {
        HexSchematics.load();
        Fractions.load();
        HexBuilds.load();
        Weapons.load();
        Buttons.load();
        MenuListener.load();
        Icons.load();

        Administration.Config.strict.set(false);

        netServer.admins.actionFilters.clear();
        netServer.admins.addActionFilter(action -> false);
        netServer.assigner = (player, players) -> Team.derelict;

        UnitTypes.mono.defaultController = HexMinerAI::new;
        UnitTypes.poly.defaultController = HexBuilderAI::new;
        UnitTypes.poly.weapons.clear();

        ((MassDriver) Blocks.massDriver).bullet.damage = 0f;

        rules.enemyCoreBuildRadius = 0f;
        rules.unitCap = 16;
        rules.infiniteResources = true;
        rules.waves = false;
        rules.canGameOver = false;
        rules.modeName = "Hex Industry";

        Timer.schedule(() -> {
            humans.each(Human::update);
            Buttons.update();
        }, 0f, 1f);

        Events.on(PlayerJoin.class, event -> Politics.join(event.player));
        Events.on(PlayerLeave.class, event -> Politics.leave(event.player));
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("guide", "Manual with info about mechanics", (args, player) -> Guide.show(player));

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
            state.rules = rules;

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
