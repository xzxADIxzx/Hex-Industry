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
import mindustry.gen.Sounds;
import mindustry.ui.Menus;

import static hex.Main.humans;

public class MenuListener {

    private static final ObjectMap<Player, Integer> last = new ObjectMap<>();
    private static final ObjectMap<Player, MenuInfo> info = new ObjectMap<>();
    private static final ObjectMap<Integer, Cons2<Player, Integer>> menus = new ObjectMap<>();

    public static int fractionChoose, weaponChoose, shop, statistics, guide, base;

    public static void load() {
        menus.put(fractionChoose = 0, (player, option) -> {
            Fraction fract = Fractions.from(option);
            if (fract != Fractions.spectator) humans.add(new Human(player, fract));
        });

        menus.put(weaponChoose = 1, (player, option) -> {
            Human human = Human.from(player);
            if (option != -1) Weapons.from(human.weapons).get(option).attack(human);
        });

        menus.put(shop = 2, (player, option) -> {
            Human human = Human.from(player);
            if (option != -1) Packages.from(human, option).send(human);
        });

        menus.put(statistics = 3, (player, option) -> {});

        guide = Menus.registerMenu(Guide::choose);

        base = Menus.registerMenu((player, option) -> {
            MenuInfo menu = info.get(player);
            Call.sound(player.con, Sounds.click, 100f, 1f, 0f);
            if (last.get(player) == option || option == -1) menus.get(menu.id).get(player, option);
            else {
                Call.menu(player.con, base, menu.title, menu.text.get(option), menu.buttons);
                last.put(player, option);
            }
        });
    }

    public static void menu(Player player, int menu, String title, String text, String[][] buttons, Func<Integer, String> func) {
        info.put(player, new MenuInfo(menu, title, buttons, func));
        last.put(player, -1);
        Call.menu(player.con, base, title, text, buttons);
    }

    public record MenuInfo(int id, String title, String[][] buttons, Func<Integer, String> text) {}
}
