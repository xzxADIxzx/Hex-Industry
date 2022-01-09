package hex.components;

import hex.*;
import hex.types.*;
import hex.content.*;
import mindustry.ui.*;

public class MenuListener {

	public static int fractionChooseMenu;

	public static void load() {
		fractionChooseMenu = Menus.registerMenu((player, option) -> Main.humans.add(new Human(player, Fractions.from(option))));
	}
}
