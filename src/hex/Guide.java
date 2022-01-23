package hex;

import arc.struct.ObjectMap;
import mindustry.gen.Player;

public class Guide {

    // TODO (Дарк) гайд
    private static ObjectMap<Player, Integer> pages = new ObjectMap<>();

    public static void show(Player player) {
        if (!pages.containsKey(player)) pages.put(player, 1);
    }

}
