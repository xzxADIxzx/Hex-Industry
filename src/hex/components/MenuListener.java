package hex.components;

import arc.util.Time;
import hex.Generator;
import hex.Guide;
import hex.Politics;
import hex.content.Fractions;
import hex.types.Fraction;
import hex.types.Human;
import hex.types.Production;
import mindustry.ui.Menus;

import static hex.Main.humans;

public class MenuListener {

    public static int fractionChooseMenu, weaponChooseMenu, leaderFractionChooseMenu, guideMenu;

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

                human.captured().each(hex -> hex.owner = leader);
                human.citadel.owner = human; // citadel must be assigned to a human
            });

            // recalculate production
            Time.runTask(300f, () -> {
                leader.production = new Production(leader);
                leader.captured().each(hex -> hex.build.create(leader.production));
                leader.slaves().each(human -> {
                    human.production = leader.production;
                    human.citadel.build.create(leader.production);
                });
            });

            Politics.offers.remove(of -> of.equals(leader, null, 2));
        });

        guideMenu = Menus.registerMenu(Guide::choose);
    }
}
