package hex;

import java.util.Locale;

import arc.struct.ObjectMap;
import mindustry.gen.Call;
import mindustry.gen.Player;

import static hex.components.Bundle.*;
import static hex.components.MenuListener.guideMenu;

public class Guide {

    // TODO (Дарк) гайд
    private static ObjectMap<Player, Integer> pages = new ObjectMap<>();

    public static void show(Player player) {
        if (!pages.containsKey(player)) pages.put(player, 1);
        int page = pages.get(player);

        Locale loc = findLocale(player);
        Call.menu(player.con, guideMenu, format("guine.name", loc, page), get("guide.page." + page, loc), new String[][] {
                { get("guide.prev", loc), get("guide.next", loc) },
                { get("guide.exit", loc) }
        });
    }

    public static void choose(Player player, int option) {
        if (option != -1 && option != 2) pages.put(player, pages.get(player) + (option < 0 ? 1 : -1));
    }

}
