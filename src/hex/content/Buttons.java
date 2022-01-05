package hex.content;

import hex.*;
import hex.types.*;
import arc.*;
import arc.struct.*;
import mindustry.game.EventType.*;

public class Buttons {

	public static ObjectMap<Integer, Seq<Button>> buttons = new ObjectMap<>();

	public static void load() {
		Events.on(TapEvent.class, event -> {
			Human human = Main.humans.find(h -> h.player == event.player);
			buttons.keys().forEach(y -> {
				if (bounds(y, event.tile.y)) buttons.get(y).each(button -> button.check(event.tile.x, human));
			});
		});
	}

	public static void register(Button button, int y) {
		if (buttons.containsKey(y))
			buttons.get(y).add(button);
		else {
			buttons.put(y, new Seq<Button>());
			register(button, y);
		}
	}

	public static void unregister(Button button, int y) {
		if (buttons.containsKey(y))
			buttons.get(y).remove(button);
		else throw new IllegalArgumentException();
	}

	public static boolean bounds(int y, int in) {
		return in >= y - 1 && in <= y + 1;
	}
}
