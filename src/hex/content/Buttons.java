package hex.content;

import arc.Events;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import hex.Politics;
import hex.types.Human;
import hex.types.buttons.Button;
import mindustry.game.EventType.TapEvent;
import mindustry.gen.Player;
import mindustry.world.Tile;

import static hex.Main.hexes;

public class Buttons {

    private static final Seq<Button> buttons = new Seq<>();
    private static final Seq<Button> awaiting = new Seq<>();

    private static final ObjectMap<Player, Click> clicks = new ObjectMap<>();

    public static void load() {
        Events.on(TapEvent.class, event -> {
            Human human = Human.from(event.player);
            if (human != null) {
                buttons.each(btn -> btn.check(event.tile, human));

                if (clicks.containsKey(event.player) && clicks.get(event.player).check(event)) {
                    Politics.attack(hexes.min(hex -> event.tile.dst(hex.pos())), human);
                    clicks.remove(event.player);
                } else clicks.put(event.player, new Click(Time.time, event.tile));
            }
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

    public static class Click {
        public float time;
        public Tile tile;

        public Click(float time, Tile tile) {
            this.time = time;
            this.tile = tile;
        }

        public boolean check(TapEvent event) {
            return Time.time - time < Time.toSeconds && tile.dst(event.tile) < 18f;
        }
    }
}
