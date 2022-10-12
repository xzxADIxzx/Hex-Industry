package hex.components;

import arc.func.Cons2;
import arc.func.Func;
import arc.struct.ObjectMap;
import hex.Guide;
import hex.content.Fractions;
import hex.content.Packages;
import hex.content.Weapons;
import hex.types.Fraction;
import hex.types.Human;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.ui.Menus;
import useful.Bundle;

import static hex.Main.humans;

public class MenuListener {

    private static final ObjectMap<Player, Integer> last = new ObjectMap<>();
    private static final ObjectMap<Player, MenuInfo> info = new ObjectMap<>();
    private static final ObjectMap<Integer, Cons2<Player, Integer>> menus = new ObjectMap<>();

    public static int fractionChoose, weaponChoose, shop, over, welcome, guide, base;

    public static void load() {
        menus.put(fractionChoose = 0, (player, option) -> {
            Fraction fract = Fractions.from(option);
            Human human = Human.find(player); // if the stars converge in the sky, the player can have two faction selection menus
            if (fract != Fractions.spectator && human == null) humans.add(new Human(player, fract));
        });

        menus.put(weaponChoose = 1, (player, option) -> {
            Human human = Human.find(player);
            if (option != -1) Weapons.from(human).get(option).attack(human);
        });

        menus.put(shop = 2, (player, option) -> {
            Human human = Human.find(player);
            if (option != -1) Packages.from(human, option).send(human);
        });

        menus.put(over = 3, (player, option) -> {});

        welcome = Menus.registerMenu((player, option) -> {
            if (option != -1) Guide.show(player);
        });

        guide = Menus.registerMenu(Guide::choose);

        base = Menus.registerMenu((player, option) -> {
            MenuInfo menu = info.get(player);
            if (last.get(player) == option || option == -1) menus.get(menu.id).get(player, option);
            else {
                Call.menu(player.con, base, menu.title, menu.text.get(option), menu.buttons);
                last.put(player, option);
            }
        });
    }

    public static void menu(Player player, int menu, String title, String text, String[][] buttons, Func<Integer, String> func) {
        title = Bundle.get(title, player);
        text = Bundle.get(text, player);

        info.put(player, new MenuInfo(menu, title, buttons, func));
        last.put(player, -1);

        Call.menu(player.con, base, title, text, buttons);
    }

    public record MenuInfo(int id, String title, String[][] buttons, Func<Integer, String> text) {}
}
