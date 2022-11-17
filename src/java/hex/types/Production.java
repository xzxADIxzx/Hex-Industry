package hex.types;

import arc.func.Boolf;
import arc.util.Time;
import hex.content.Packages;
import mindustry.core.UI;
import mindustry.gen.Iconc;
import useful.Bundle;

public class Production {

    protected final Resource resources = new Resource();
    protected final Resource production = new Resource();

    /** Free units package cooldown. */
    protected boolean sending;

    public void update(Human human) {
        float speed = human.fraction.production + (resources.water > 0 ? .1f : 0f) + (resources.cryo > 0 ? .2f : 0f);
        production.produce(resources, speed);

        if (resources.units <= 5 && !sending) { // free units without sms and registration
            sending = true;
            Time.run(3f * Time.toMinutes, () -> sending = false);
            Packages.free.send(human);
        }
    }

    public void builded(Resource prod, Resource cons) {
        prod.produce(production, 1f);
        cons.consume(resources);
        resources.units += prod.units;
    }

    public void destroyed(Resource prod, Resource cons) {
        prod.produce(production, -1f);
        resources.units += cons.units; // revert units because they are not produced
    }

    public void check(Human human) {
        if (resources.oil      <= 0) check(human, sour -> sour.oil == 1);
        if (resources.water    <= 0) check(human, sour -> sour.water == 1);
        if (resources.cryo     <= 0) check(human, sour -> sour.cryo == 1);
        if (resources.arkycite <= 0) check(human, sour -> sour.arkycite == 1);
    }

    public void check(Human human, Boolf<Resource> pred) {
        human.captured().each(hex -> pred.get(hex.build.cons), Hex::kill);
    }

    // region produce & consume

    public void prodUnits(Human human, int amount) {
        resources.units += amount * human.fraction.creature;
    }

    public void prodCrawlers(Human human, int amount) {
        resources.crawlers += amount * human.fraction.creature;
    }

    public boolean consSpores(Human human, int amount) {
        if (resources.spores >= amount) {
            resources.spores -= amount;
            return true;
        } else human.enough();
        return false;
    }

    public boolean consUnits(Human human, int amount) {
        if (resources.units >= amount) {
            resources.units -= amount;
            return true;
        } else human.enough();
        return false;
    }

    public boolean consCrawlers(Human human, int amount) {
        if (resources.crawlers >= amount) {
            resources.crawlers -= amount;
            return true;
        } else human.enough();
        return false;
    }

    // endregion
    // region to string

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        item(builder.append("\uF832"), resources.titanium);
        item(builder.append("\uF831"), resources.thorium);
        item(builder.append("\uF82E"), resources.plastanium);
        item(builder.append("\uF82B"), resources.spores);

        builder.append("\n");

        creature(builder, resources.units).append("\uE86D[] ");
        creature(builder, resources.crawlers).append("[]\uF7FA ");

        liquid(builder, resources.oil).append("\uF826 ");
        liquid(builder, resources.water).append("\uF828 ");
        liquid(builder, resources.cryo).append("\uF825 ");
        liquid(builder, resources.arkycite).append("\uF705 ");

        return builder.toString();
    }

    private StringBuilder item(StringBuilder builder, int amount) {
        builder.append(amount <= 500 ? "[scarlet]" : amount <= 1000 ? "[orange]" : "[green]").append(UI.formatAmount(amount)).append("[] ");
        return builder;
    }

    private StringBuilder creature(StringBuilder builder, int amount) {
        builder.append(amount <= 5 ? "[scarlet]" : amount <= 10 ? "[orange]" : "[green]").append(amount);
        return builder;
    }

    private StringBuilder liquid(StringBuilder builder, int amount) {
        builder.append(amount > 0 ? "[green]\uE800[]" : "[scarlet]\uE815[]");
        return builder;
    }

    // endregion

    public static class Resource {

        /** Production per second or amount. */
        public int titanium, thorium, plastanium, spores;

        /** Presence / absence or amount. */
        public int oil, water, cryo, arkycite;

        /** Little creatures. */
        public int units, crawlers;

        public void all(int amount) {
            titanium = plastanium = thorium = spores = oil = water = cryo = arkycite = units = crawlers = amount;
        }

        public void produce(Resource sour, float multiplier) {
            sour.titanium += titanium * multiplier;
            sour.thorium += thorium * multiplier;
            sour.plastanium += plastanium * multiplier;
            sour.spores += spores * multiplier;

            sour.oil += oil;
            sour.water += water;
            sour.cryo += cryo;
            sour.arkycite += arkycite;
        }

        public void consume(Production production) {
            consume(production.resources);
        }

        public void consume(Resource sour) {
            sour.titanium -= titanium;
            sour.thorium -= thorium;
            sour.plastanium -= plastanium;
            sour.spores -= spores;

            sour.units -= units;
            sour.crawlers -= crawlers;
        }

        public boolean enough(Production production) {
            return enough(production.resources);
        }

        public boolean enough(Resource sour) {
            return  (titanium   <= 0 || sour.titanium   >= titanium) &&
                    (thorium    <= 0 || sour.thorium    >= thorium) &&
                    (plastanium <= 0 || sour.plastanium >= plastanium) &&
                    (spores     <= 0 || sour.spores     >= spores) &&
                    (oil        <= 0 || sour.oil        >= oil) &&
                    (water      <= 0 || sour.water      >= water) &&
                    (cryo       <= 0 || sour.cryo       >= cryo) &&
                    (arkycite   <= 0 || sour.arkycite   >= arkycite) &&
                    (units      <= 0 || sour.units      >= units);
        } // there are no crawlers because they are not currently managed by the Resource

        // region formatting

        private String format;
        private Human provider;

        public String formatProd(Human human) {
            this.format = "";
            this.provider = human;

            formatB(new String[] { "prod.unit", "prod.item", "prod.liquid" }, human.fraction.creature, human.fraction.production);
            return format;
        }

        public String formatCons(Human human) {
            this.format = "";
            this.provider = human;

            formatB(new String[] { "cons.unit", "cons.item", "cons.liquid" }, 1, 1);
            return format;
        }

        private void formatB(String[] base, float c, float i) {
            add(base[0], units * c, ' ');
            add(base[1], titanium * i, Iconc.itemTitanium);
            add(base[1], thorium * i, Iconc.itemThorium);
            add(base[1], plastanium * i, Iconc.itemPlastanium);
            add(base[1], spores * i, Iconc.itemSporePod);

            if (oil > 0 || water > 0 || cryo > 0) format += "\n";

            add(base[2], oil, Iconc.liquidOil);
            add(base[2], water, Iconc.liquidWater);
            add(base[2], cryo, Iconc.liquidCryofluid);
            add(base[2], arkycite, Iconc.liquidArkycite);
        }

        private void add(String key, float amount, char icon) {
            if (amount != 0) format += Bundle.format(key, provider, (int) amount, icon);
        }

        // endregion
    }
}
