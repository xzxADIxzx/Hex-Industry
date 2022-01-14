package hex.components;

import hex.*;
import hex.types.*;
import hex.content.*;
import mindustry.ui.*;
import mindustry.game.*;

public class MenuListener {

	public static int fractionChooseMenu;

	public static void load() {
		fractionChooseMenu = Menus.registerMenu((player, option) -> {
			Fraction fract = Fractions.from(option);
			if (fract != Fractions.spectator) Main.humans.add(new Human(player, fract));
			else player.team(Team.derelict);
		});
	}
}
