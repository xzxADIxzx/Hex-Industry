package hex.components;

import hex.Politics;
import hex.content.Fractions;
import hex.types.Fraction;
import hex.types.Human;
import mindustry.ui.Menus;

import static hex.Main.humans;

public class MenuListener {

    public static int fractionChooseMenu, weaponChooseMenu;

    public static void load() {
        fractionChooseMenu = Menus.registerMenu((player, option) -> {
            Fraction fract = Fractions.from(option);
            if (fract != Fractions.spectator) humans.add(new Human(player, fract));
        });

        weaponChooseMenu = Menus.registerMenu((player, option) -> {
            if (option > 0) Politics.attack(player, option);
        });
    }
}
