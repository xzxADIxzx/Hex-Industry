package hex.components;

import hex.Politics;
import hex.Generator;
import hex.content.Fractions;
import hex.types.Fraction;
import hex.types.Human;
import mindustry.ui.Menus;

import static hex.Main.humans;

public class MenuListener {

    public static int fractionChooseMenu, weaponChooseMenu, leaderFractionChooseMenu;

    public static void load() {
        fractionChooseMenu = Menus.registerMenu((player, option) -> {
            Fraction fract = Fractions.from(option);
            if (fract != Fractions.spectator) humans.add(new Human(player, fract));
        });

        weaponChooseMenu = Menus.registerMenu((player, option) -> {
            if (option > 0) Politics.attack(player, option);
        });

        leaderFractionChooseMenu = Menus.registerMenu((player, option) -> {
            Human leader = Human.from(player);
            Fraction fract = Fractions.from(option);

            leader.team(Generator.team());
            leader.unit(fract, true);
            leader.slaves().each(human -> {
                human.team(leader.player.team());
                human.unit(fract, false);

                human.production = leader.production;
                human.captured().each(hex -> hex.owner = leader);
                human.citadel.owner = human; // citadel must be assigned to a human
            });

            Politics.offers.remove(of -> of.equals(leader, null, 2));
        });
    }
}
