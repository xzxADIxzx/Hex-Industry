package hex.content;

import hex.types.Human;
import hex.types.Package;
import useful.Bundle;
import arc.struct.Seq;

public class Packages {

    public static Package crawler, unit, ai, atomic, free;

    public static void load() {
        crawler = new Package() {{
            name = "shop.pack.crawler";
            cost = 150;

            cont = human -> human.production.crawler(human.shops());
            desc = human -> Bundle.format(name + ".desc", human.locale, (int) (human.shops() * human.fraction.creature), cost);
            cons = human -> human.production.spore(human, cost);
        }};

        unit = new Package() {{
            name = "shop.pack.unit";
            cost = 1;

            cont = human -> human.production.unit(human.cities());
            desc = human -> Bundle.format(name + ".desc", human.locale, (int) (human.cities() * human.fraction.creature), cost);
            pred = human -> human.cities() > 0;
        }};

        ai = new Package() {{
            name = "shop.pack.ai";
            cost = 6;

            post = human -> human.leader.stats.ai = true;
            cont = human -> human.unlock(Weapons.crawler.id);
            desc = human -> Bundle.format(name + ".desc", human.locale, Weapons.crawler.desc(human), cost);
            pred = human -> !human.leader.stats.ai;
        }};

        atomic = new Package() {{
            name = "shop.pack.atomic";
            cost = 10;

            post = human -> human.leader.stats.atomic = true;
            cont = human -> human.unlock(Weapons.atomic.id);
            desc = human -> Bundle.format(name + ".desc", human.locale, Weapons.atomic.desc(human), cost);
            pred = human -> !human.leader.stats.atomic;
        }};

        free = new Package() {{
            name = "shop.pack.free";
            cost = 0;

            cont = human -> human.production.unit(20);
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
        for (int i = 0; i < names.length; i++) names[i][0] = Bundle.get(packages.get(i).name + ".name", human.locale);
        return names;
    }
}
