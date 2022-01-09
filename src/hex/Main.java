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

		Timer.schedule(() -> humans.each(h -> Call.setHudText(h.player.con, "[gray]hex #" + h.location().id + "\n[green]" + h.production.human())), 0f, .01f);

		Events.on(PlayerJoin.class, event -> handle(event.player));
	}

	public void handle(Player player) {
		Call.menu(player.con, fractionChooseMenu, "Заголовок", "Текст", new String[][] {{"Horde"}, {"Engineer"}, {"Militant"}});
	}

	@Override
	public void registerClientCommands(CommandHandler handler) {}

	@Override
	public void registerServerCommands(CommandHandler handler) {
		handler.removeCommand("host");
		handler.register("host", "Initialize new game", args -> {
			// generate hex-map
			Generator.generate();

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
