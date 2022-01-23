package hex.content;

import arc.Events;
import arc.struct.Seq;
import hex.types.Button;
import hex.types.Human;
import mindustry.game.EventType.TapEvent;

public class Buttons {

    private static final Seq<Button> buttons = new Seq<>();
    private static final Seq<Button> awaiting = new Seq<>();

    public static void load() {
        Events.on(TapEvent.class, event -> {
            Human human = Human.from(event.player);
            if (human != null) buttons.each(btn -> btn.check(event.tile, human));
        });
    }

    public static void update() {
        buttons.addAll(awaiting);
        awaiting.clear();
    }

    public static void register(Button button) {
        awaiting.add(button);
    }

    public static void unregister(Button button) {
        buttons.remove(button);
    }
}
