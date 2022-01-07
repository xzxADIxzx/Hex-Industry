package hex.content;

import hex.*;
import hex.types.*;
import arc.*;
import arc.struct.*;
import mindustry.game.EventType.*;

public class Buttons {

	public static Seq<Button> buttons = new Seq<>();

	public static void load() {
		Events.on(TapEvent.class, event -> {
			Human human = Main.humans.find(h -> h.player == event.player);
			buttons.each(btn -> btn.check(event.tile, human));
		});
	}

	public static void register(Button button) {
		buttons.add(button);
	}

	public static void unregister(Button button) {
		buttons.remove(button);
	}
}
