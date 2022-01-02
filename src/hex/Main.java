package hex;

import arc.util.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class Main extends Plugin {

	public static Seq<Hex> hexes = new Seq<>();

	@Override
	public void init() {
		Schems.load();

		Timer.schedule(() -> Log.info("main update resources & etc"), 0f, 60f);

		// Timer.schedule(main, 0f, 60f);
		// Timer.schedule(todo, 0f, .01f);
	}

	@Override
	public void registerServerCommands(CommandHandler handler) {
		handler.register("init", "initialize new game", args -> {
			// generate hex-map
			Vec2 pos = new Vec2();
			hexes.add(new Hex((int)pos.x, (int)pos.y));

			// boolean up = false;
			// while (true) {
			// 	hexes.add(new Hex((int)pos.x, (int)pos.y));

			// 	up = !up;
			// 	pos.add(20f * (up ? 1f : -1f), 12f);
			// }

			// synchronize the world
			Call.worldDataBegin();
			Groups.player.each(ppl -> {
				if(ppl != player) netServer.sendWorldData(ppl);
			});

			// ask unit type & abilities

			// spawn a citadel in a random hex
		});
	}

	@Override
	public void registerClientCommands(CommandHandler handler) {}
}
