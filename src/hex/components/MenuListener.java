package hex.components;

import hex.*;
import hex.types.*;
import hex.content.*;
import mindustry.ui.*;

public class MenuListener {

	public static int fractionChooseMenu, weaponChooseMenu;

	public static void load() {
		fractionChooseMenu = Menus.registerMenu((player, option) -> {
			Fraction fract = Fractions.from(option);
			if (fract != Fractions.spectator) Main.humans.add(new Human(player, fract));
		});

		weaponChooseMenu = Menus.registerMenu((player, option) -> {
			if (option > 0) Main.attack(option / 3f);
		});
	}
}
