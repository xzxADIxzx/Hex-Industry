package hex;

import arc.struct.ObjectMap;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.gen.Sounds;

import java.util.Locale;

import static hex.components.Bundle.*;
import static hex.components.MenuListener.guide;

public class Guide {

    private static final int max = 8;
    private static final ObjectMap<Player, Integer> pages = new ObjectMap<>();

    public static void show(Player player) {
        if (!pages.containsKey(player)) pages.put(player, 0);
        int page = pages.get(player);

        Locale loc = findLocale(player);
        Call.menu(player.con, guide, format("guide.name", loc, page), get("guide.page." + page, loc), new String[][] {
                {get("guide.prev", loc), get("guide.next", loc)},
                {get("guide.exit", loc)}
        });
    }

    public static void choose(Player player, int option) {
        Call.sound(player.con, Sounds.click, 100f, 1f, 0f);
        if (option != -1 && option != 2) {
            option = pages.get(player) + (option == 0 ? -1 : 1);
            pages.put(player, option > max ? max : Math.max(option, 0));
            show(player);
        }
    }

}
