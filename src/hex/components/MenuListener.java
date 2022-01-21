package hex.components;

import hex.*;
import hex.types.*;
import hex.content.*;
import mindustry.ui.*;

import static hex.Main.*;

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
