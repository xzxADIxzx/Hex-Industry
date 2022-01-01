package hex;

import arc.util.*;
import mindustry.mod.*;

public class Main extends Plugin {

	@Override
	public void init() {
		Timer.schedule(() -> Log.info("main update resources & etc"), 0f, 60f);

		// Timer.schedule(main, 0f, 60f);
		// Timer.schedule(todo, 0f, .01f);
	}

	@Override
	public void registerServerCommands(CommandHandler handler) {
		handler.register("init", "initialize new game", args -> {
			// generate hex-map

			// synchronize the world

			// ask unit type & abilities

			// spawn a citadel in a random hex
		});
	}

	@Override
	public void registerClientCommands(CommandHandler handler) {}
}
