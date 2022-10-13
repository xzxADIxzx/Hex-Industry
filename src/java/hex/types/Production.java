package hex.types;

import arc.func.Boolf;
import arc.math.Mathf;
import arc.util.Time;
import hex.content.Packages;
import mindustry.content.Items;
import mindustry.core.UI;
import mindustry.gen.Iconc;
import mindustry.world.modules.ItemModule;
import useful.Bundle;

import java.util.Locale;

public class Production {

    public ItemModule items = null;
    public Fraction fract = null;

    public Resource resource;
    public boolean sending;

    // production per sec
    protected int plastanium;
    protected int titanium;
    protected int thorium;
    protected int spore;

    // presence / absence
    protected int oil;
    protected int water;
    protected int cryo;
    protected int arkycite;

    // little creatures
    protected int unit;
    protected int crawler;

    public Production() {
        resource = new Resource();
    }

    public Production(Human human) {
        items = new ItemModule();
        fract = human.fraction;
    }

    public void update(Human human) {
        float speed = fract.production + (water > 0 ? .1f : 0f) + (cryo > 0 ? .2f : 0f);

        items.add(Items.plastanium, (int) (plastanium * speed));
        items.add(Items.titanium, (int) (titanium * speed));
        items.add(Items.thorium, (int) (thorium * speed));
        items.add(Items.sporePod, (int) (spore * speed));

        if(unit <= 5 && !sending) { // free units without sms and registration
            sending = true;
            Time.run(3f * Time.toMinutes, () -> sending = false);
            Packages.free.send(human);
        }
    }

    public void all(int amount) {
        titanium = plastanium = thorium = spore = oil = water = cryo = arkycite = unit = crawler = amount;
    }

    public int plastanium() {
        return items.get(Items.plastanium);
    }

    public void plastanium(int amount) {
        items.add(Items.plastanium, amount);
    }

    public int titanium() {
        return items.get(Items.titanium);
    }

    public void titanium(int amount) {
        items.add(Items.titanium, amount);
    }

    public int thorium() {
        return items.get(Items.thorium);
    }

    public void thorium(int amount) {
        items.add(Items.thorium, amount);
    }

    public int spore() {
        return items.get(Items.sporePod);
    }

    public void spore(int amount) {
        items.add(Items.sporePod, amount);
    }

    public void unit(int amount) {
        unit += amount * fract.creature;
    }

    public void crawler(int amount) {
        crawler += amount * fract.creature;
    }

    public void unlock(Human human, Weapon weapon) {
        if (crawler >= weapon.cost) {
            crawler -= weapon.cost;
            human.weaponry.unlock(weapon);
        } else human.enough();
    }

    // I'am sorry ,_,
    public boolean unit(Human human, int amount) {
        if (unit >= amount) {
            unit -= amount;
            return true;
        } else human.enough();
        return false;
    }

    public boolean crawler(Human human, int amount) {
        if (crawler >= amount) {
            crawler -= amount;
            return true;
        } else human.enough();
        return false;
    }

    public boolean spore(Human human, int amount) {
        if (spore() >= amount) {
            spore(-amount);
            return true;
        } else human.enough();
        return false;
    }

    public void check(Human human) {
        if (oil <= 0) check(human, prod -> prod.oil == 1);
        if (water <= 0) check(human, prod -> prod.water == 1);
        if (cryo <= 0) check(human, prod -> prod.cryo == 1);
        if (arkycite <= 0) check(human, prod -> prod.arkycite == 1);
    }

    public void check(Human human, Boolf<Production> pred) {
        human.captured().each(hex -> pred.get(hex.build.cons), Hex::kill);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        item(builder.append("\uF832"), titanium());
        item(builder.append("\uF831"), thorium());
        item(builder.append("\uF82E"), plastanium());
        item(builder.append("\uF82B"), spore());

        builder.append("\n");

        creature(builder, unit).append("\uE86D[] ");
        creature(builder, crawler).append("[]\uF7FA ");

        liquid(builder, oil).append("\uF826 ");
        liquid(builder, water).append("\uF828 ");
        liquid(builder, cryo).append("\uF825 ");
        liquid(builder, arkycite).append("\uF705 ");

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

    public class Resource {

        private String format;

        public void produce(Production prod, boolean add) {
            int base = Mathf.sign(add);

            prod.plastanium += plastanium * base;
            prod.titanium += titanium * base;
            prod.thorium += thorium * base;
            prod.spore += spore * base;

            prod.oil += oil * base;
            prod.water += water * base;
            prod.cryo += cryo * base;
            prod.arkycite += arkycite * base;

            prod.unit(unit * base);
        }

        public void consume(Production prod) {
            prod.plastanium(-plastanium);
            prod.titanium(-titanium);
            prod.thorium(-thorium);
            prod.spore(-spore);

            prod.unit -= unit;
        }

        public void unit(Production prod, boolean add) {
            prod.unit += unit * Mathf.sign(add);
        }

        public boolean enough(Production prod) {
            return (plastanium <= 0 || prod.plastanium() >= plastanium) &&
                    (titanium <= 0 || prod.titanium() >= titanium) &&
                    (thorium <= 0 || prod.thorium() >= thorium) &&
                    (spore <= 0 || prod.spore() >= spore) &&
                    (oil <= 0 || prod.oil >= oil) &&
                    (water <= 0 || prod.water >= water) &&
                    (cryo <= 0 || prod.cryo >= cryo) &&
                    (arkycite <= 0 || prod.arkycite >= arkycite) &&
                    (unit <= 0 || prod.unit >= unit);
        }

        public String formatP(Human human) {
            return formatB(human.locale, new String[] { "prod.unit", "prod.item", "prod.liquid" }, human.fraction.creature, human.fraction.production);
        }

        public String formatC(Human human) {
            return formatB(human.locale, new String[] { "cons.unit", "cons.item", "cons.liquid" }, 1, 1);
        }

        private String formatB(Locale loc, String[] base, float c, float i) {
            format = "";
            add(base[0], loc, unit * c, ' ');
            add(base[1], loc, titanium * i, Iconc.itemTitanium);
            add(base[1], loc, thorium * i, Iconc.itemThorium);
            add(base[1], loc, plastanium * i, Iconc.itemPlastanium);
            add(base[1], loc, spore * i, Iconc.itemSporePod);
            if (oil > 0 || water > 0 || cryo > 0) format += "\n";
            add(base[2], loc, oil, Iconc.liquidOil);
            add(base[2], loc, water, Iconc.liquidWater);
            add(base[2], loc, cryo, Iconc.liquidCryofluid);
            add(base[2], loc, arkycite, Iconc.liquidArkycite);

            return format;
        }

        private void add(String key, Locale loc, float amount, char icon) {
            if (amount != 0) format += Bundle.format(key, loc, (int) amount, icon);
        }
    }
}
