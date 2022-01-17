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

import java.util.Locale;

import static hex.components.Bundle.*;
import static hex.components.MenuListener.*;
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

		Timer.schedule(() -> humans.each(h -> {
			Hex look = h.lookAt();
			Call.setHudText(h.player.con, format("ui.hud", findLocale(h.player), h.location().id, h.production.human()));
			Call.label(h.player.con, format("ui.label", findLocale(h.player), look.id, look.owner == null ? "" : look.owner.name()), .05f, look.lx, look.ly);
		}), 0f, .05f);

		Events.on(PlayerJoin.class, event -> handle(event.player));
		Events.on(PlayerLeave.class, event -> {
			Human player = Human.from(event.player);
			if (player != null) player.lose();
		});
	}

	public void handle(Player player) {
        Locale loc = findLocale(player);
        Call.menu(player.con, fractionChooseMenu, get("fract.title", loc), get("fract.text", loc), new String[][] {
                { get("fract.horde", loc) },
                { get("fract.engineer", loc) },
                { get("fract.militant", loc) },
                { get("fract.spectator", loc) } });
    }

	@Override
	public void registerClientCommands(CommandHandler handler) {
		handler.<Player>register("peace", "<player>", "Offer the player a peace", (args, player) -> {
            Locale loc = findLocale(player);
			Human human = Human.from(args[0]);
			if (human == null || human.player == player) player.sendMessage(get("offer.notfound", loc));
			else {
				player.sendMessage(get("offer.sent", loc));
				human.player.sendMessage(player.coloredName() + " " + get("offer.peace", loc));
			}
		});

		// TODO: in early development! add Human.Leader & Politics
		handler.<Player>register("join", "<player>", "Offer the player to team up", (args, player) -> {
			Locale loc = findLocale(player);
			Human human = Human.from(args[0]);
			if (human == null) player.sendMessage(get("offer.notfound", loc));
			else {
				player.sendMessage(get("offer.sent", loc));
				human.player.sendMessage(player.coloredName() + " " + get("offer.join", loc));

				Human offerer = Human.from(player);
				human.team(Generator.team());
				offerer.team(human.player.team());
				offerer.production = human.production;
			}
		});

		handler.<Player>register("spectate", "Watching the game is fun too", (args, player) -> {
			Human human = Human.from(player);
			if (human == null) handle(player);
			else {
				human.lose();
				humans.remove(human);
				player.team(Team.derelict);
			}
		});
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

			for (Team team : Team.all) state.rules.teams.get(team).cheat = true;

			// synchronize the world
			Call.worldDataBegin();
			Groups.player.each(netServer::sendWorldData);

			// handle all players
			Groups.player.each(this::handle);

			logic.play();
			netServer.openServer();
		});
	}
}
