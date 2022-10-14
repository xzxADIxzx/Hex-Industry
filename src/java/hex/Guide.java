package hex;

import arc.struct.ObjectMap;
import mindustry.gen.Player;
import useful.Bundle;

import static hex.components.MenuListener.*;

public class Guide {

    private static final int max = 9;
    private static final ObjectMap<Player, Integer> pages = new ObjectMap<>();

    public static void show(Player player) {
        if (!pages.containsKey(player)) pages.put(player, 0);
        int page = pages.get(player);

        menu(player, guide, Bundle.format("guide.name", player, page), "guide.page." + page,
                new String[][] { { "guide.prev", "guide.next" }, { "guide.exit" } });
    }

    public static void choose(Player player, int option) {
        if (option == -1 || option == 2) return; // escape or exit

        option = pages.get(player) + (option == 0 ? -1 : 1);
        pages.put(player, option > max ? max : Math.max(option, 0));
        show(player);
    }

}
