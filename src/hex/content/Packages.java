package hex.content;

import hex.types.Package;

import static hex.components.Bundle.format;

public class Packages {

    public static Package crawler, atomic;

    public static void load() {
        crawler = new Package(){{
            name = "shop.pack.crawler";
            cost = 20;

            cont = human -> human.production.crawler(human.shops());
            desc = human -> format(name + ".desc", human.locale, human.shops() * human.fraction.creature, cost);
        }};

        atomic = new Package(){{
            name = "shop.pack.atomic";
            cost = 10;

            cont = human -> human.unlock(Weapons.atomic.id);
            desc = human -> format(name + ".desc", human.locale, Weapons.atomic.desc(human), cost);
        }};
    }
}
