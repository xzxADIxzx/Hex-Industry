package hex.content;

import hex.*;
import hex.types.*;
import arc.*;
import arc.struct.*;
import mindustry.game.EventType.*;

public class Buttons {

	private static final Seq<Button> buttons = new Seq<>();
	private static final Seq<Button> awaiting = new Seq<>();

	public static void load() {
		Events.on(TapEvent.class, event -> {
			Human human = Main.humans.find(h -> h.player == event.player);
			buttons.each(btn -> btn.check(event.tile, human));
		});

		Events.on(PlayerJoin.class, event -> buttons.each(btn -> btn.joined()));
	}

	public static void update() {
		buttons.addAll(awaiting);
		awaiting.clear();

		buttons.each(Button::update);
	}

	public static void register(Button button) {
		awaiting.add(button);
	}

	public static void unregister(Button button) {
		buttons.remove(button);
	}
}
