package hex;

import hex.types.*;
import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class Main extends Plugin {

	public static Seq<Hex> hexes = new Seq<>();
	public static Seq<Human> humans = new Seq<>();

	@Override
	public void init() {
		Schems.load();

		// Timer.schedule(main, 0f, 1f);
		// Timer.schedule(todo, 0f, .01f);
	}

	@Override
	public void registerServerCommands(CommandHandler handler) {}

	@Override
	public void registerClientCommands(CommandHandler handler) {
		handler.register("init", "Initialize new game", args -> {
			// generate hex-map
			Point2 start = new Point2();
			Point2 point = new Point2();

			while (true) {
				hexes.add(new Hex(point.x, point.y));
				point.add(38, 0);

				if (!Hex.bounds(point.x, point.y)) {
					start.add(19 * (start.x == 0 ? 1 : -1), 11);
					point.set(start);

					if (!Hex.bounds(start.x, start.y))
						break;
				}
			}

			// synchronize the world
			Call.worldDataBegin();
			Groups.player.each(ppl -> {
				if (ppl != player)
					netServer.sendWorldData(ppl);
			});

			// ask unit type & abilities
			Groups.player.each(ppl -> humans.add(new Human(ppl, new Fraction())));

			// spawn a citadel in a random hex
			humans.each(ppl -> ppl.init(hexes.get(Mathf.random(hexes.size))));
		});

		handler.<Player>register("tp", "<id>", "Teleport to hex", (args, ppl) -> ppl.set(hexes.get(Integer.valueOf(args[0])).pos()));
	}
}
