package hex.content;

import arc.Events;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import hex.types.Button;
import mindustry.game.EventType.TapEvent;

public class Buttons {

    public static ObjectMap<Integer, Seq<Button>> buttons;

    static {
        Events.on(TapEvent.class, event -> buttons.keys().forEach(y -> {
			if (bounds(y, event.tile.y)) buttons.get(y).each(button -> button.check(event.tile.x));
		}));
    }

    public static boolean bounds(int y, int in) {
        return in > y - 1 && in < y + 1;
    }

    public void register(Button button, int y) {
        if (buttons.containsKey(y))
            buttons.get(y).add(button);
        else {
            buttons.put(y, new Seq<Button>());
            register(button, y);
        }
    }

    public void unregister(Button button, int y) {
        if (buttons.containsKey(y))
            buttons.get(y).remove(button);
        else throw new IllegalArgumentException();
    }
}
