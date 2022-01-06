package hex;

import hex.types.*;
import hex.content.*;
import arc.*;
import arc.util.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.game.EventType.*;

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

		netServer.admins.actionFilters.clear();
		netServer.admins.addActionFilter(action -> false);

		Timer.schedule(() -> {
			if (initialized) humans.each(ppl -> ppl.production.update());
		}, 0f, 1f);

		Timer.schedule(() -> {
			humans.each(ppl -> {
				Call.setHudText(ppl.player.con, "[gray]hex #" + String.valueOf(ppl.location().id) +
						"\n[green]" + ppl.production.ppl() + "[][]\\" + ppl.production.pplMax());
			});
		}, 0f, .01f);

		Events.on(PlayerJoin.class, event -> handle(event.player));
	}

	public void handle(Player player) {
		if (!initialized) return;
		Human human;

		// ask unit type & abilities
		humans.add(human = new Human(player, Fractions.horde));

		// spawn a citadel in a random hex
		human.init();
	}

	@Override
	public void registerClientCommands(CommandHandler handler) {}

	@Override
	public void registerServerCommands(CommandHandler handler) {
		handler.removeCommand("host");
		handler.register("host", "Initialize new game", args -> {
			// change rules
			state.rules.enemyCoreBuildRadius = 0f;
			state.rules.unitCap = 16;
			state.rules.infiniteResources = true;

			// generate hex-map
			Generator.generate();

			// synchronize the world
			Call.worldDataBegin();
			Groups.player.each(ppl -> netServer.sendWorldData(ppl));

			// handle all players
			Groups.player.each(ppl -> handle(ppl));

			// netServer.openServer();
			initialized = true;
		});
	}
}
