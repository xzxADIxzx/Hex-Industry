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
import mindustry.game.EventType.*;

import static hex.components.MenuListener.*;
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
		Buttons.load();
		MenuListener.load();

		netServer.admins.actionFilters.clear();
		netServer.admins.addActionFilter(action -> false);

		Administration.Config.strict.set(false);

		Timer.schedule(() -> {
			if (initialized) humans.each(h -> h.production.update());
			Buttons.update();
		}, 0f, 1f);

		Timer.schedule(() -> humans.each(h -> Call.setHudText(h.player.con, "[gray]hex #" + h.location().id + "[]\n" + h.production.human())), 0f, .02f);

		Events.on(PlayerJoin.class, event -> handle(event.player));
	}

	public void handle(Player player) {
		Call.menu(player.con, fractionChooseMenu, "Заголовок", "Текст", new String[][] { { "Horde" }, { "Engineer" }, { "Militant" } });
	}

	@Override
	public void registerClientCommands(CommandHandler handler) {
		handler.<Player>register("peace", "<player>", "Offer the player a peace", (args, player) -> {
			Human human = Human.from(args[0]);
			if (human == null || human.player == player) player.sendMessage("[scarlet]Player not found");
			else {
				player.sendMessage("[green]Offer sent");
				human.player.sendMessage(player.coloredName() + " [white]offered you a [green]peace[]... do /peace if you agree");
			}
		});

		handler.<Player>register("join", "<player>", "Offer the player to team up", (args, player) -> {
			Human human = Human.from(args[0]);
			if (human == null) player.sendMessage("[scarlet]Player not found");
			else {
				player.sendMessage("[green]Offer sent");
				human.player.sendMessage(player.coloredName() + " [white]offered you to [green]team up[]... do /join if you agree");
				human.team(Generator.team());
			}
		});

		handler.<Player>register("spectate", "Watching the game is fun too", (args, player) -> {
			Human human = Human.from(player);
			if (human == null) handle(player);
			else {
				human.lose();
				humans.remove(human);
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

			// synchronize the world
			Call.worldDataBegin();
			Groups.player.each(netServer::sendWorldData);

			// handle all players
			Groups.player.each(this::handle);

			logic.play();
			netServer.openServer();
			initialized = true;
		});
	}
}
