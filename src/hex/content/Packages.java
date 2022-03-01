package hex.content;

import hex.types.Human;
import hex.types.Package;
import arc.struct.Seq;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Packages {

    public static Package crawler, unit, ai, atomic;

    public static void load() {
        crawler = new Package(){{
            name = "shop.pack.crawler";
            cost = 20;

            cont = human -> human.production.crawler(human.shops());
            desc = human -> format(name + ".desc", human.locale, human.shops() * human.fraction.creature, cost);
            cons = human -> human.production.spore(human, cost);
        }};

        unit = new Package(){{
            name = "shop.pack.unit";
            cost = 10;

            cont = human -> human.production.unit(human.cities());
            desc = human -> format(name + ".desc", human.locale, human.cities() * human.fraction.creature, cost);
            pred = human -> human.cities() > 0;
        }};

        ai = new Package(){{
            name = "shop.pack.ai";
            cost = 10;

            cont = human -> human.unlock(Weapons.crawler.id);
            desc = human -> format(name + ".desc", human.locale, Weapons.crawler.desc(human), cost);
            pred = human -> (Weapons.crawler.id & human.weapons) != Weapons.crawler.id;
        }};

        atomic = new Package(){{
            name = "shop.pack.atomic";
            cost = 10;

            cont = human -> human.unlock(Weapons.atomic.id);
            desc = human -> format(name + ".desc", human.locale, Weapons.atomic.desc(human), cost);
            pred = human -> (Weapons.atomic.id & human.weapons) != Weapons.atomic.id;
        }};
    }

    public static Package from(Human human, int id) {
        return from(human).get(id);
    }

    public static Seq<Package> from(Human human) {
        return Seq.with(crawler, unit, ai, atomic).filter(p -> p.pred.get(human));
    }

    public static String[][] names(Human human) {
        Seq<Package> packages = from(human);
        String[][] names = new String[packages.size][1];
        for (int i = 0; i < names.length; i++) names[i][0] = get(packages.get(i).name + ".name", human.locale);
        return names;
    }
}
