package hex;

import hex.types.*;
import hex.content.*;
import hex.components.*;
import arc.*;
import arc.util.*;
import arc.struct.*;
import mindustry.net.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.game.*;
import mindustry.game.EventType.*;

import static hex.components.Bundle.*;
import static mindustry.Vars.*;

public class Main extends Plugin {

	public static Seq<Hex> hexes = new Seq<>();
	public static Seq<Human> humans = new Seq<>();

	@Override
	public void init() {
		Schems.load();
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

	// TODO: check for player.team() == Team.derelict
	@Override
	public void registerClientCommands(CommandHandler handler) {
		handler.<Player>register("attack", "[hex]", "Attack a hex", (args, player) -> Politics.peace(args[0], player));

		handler.<Player>register("peace", "<player>", "Offer the player a peace", (args, player) -> Politics.peace(args[0], player));

		// TODO: in early development! add Human.Leader
		handler.<Player>register("join", "<player>", "Offer the player to team up", (args, player) -> Politics.join(args[0], player));

		handler.<Player>register("spectate", "Watching the game is fun too", (args, player) -> Politics.spectate(player));

		handler.<Player>register("author", "Plugin creators", (args, player) -> player.sendMessage(get("author", findLocale(player))));
	}

	@Override
	public void registerServerCommands(CommandHandler handler) {
		handler.removeCommand("host");
		handler.register("host", "[size]", "Initialize new game", args -> {
			// generate hex-map
			Generator.generate(args.length > 0 ? Integer.valueOf(args[0]) : 0);

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
