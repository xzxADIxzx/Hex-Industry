package hex.types;

import arc.func.Boolf;
import arc.math.Mathf;
import hex.components.Icons;
import mindustry.content.Items;
import mindustry.world.modules.ItemModule;

import java.util.Locale;

import static hex.components.Bundle.format;

public class Production {

    public final ItemModule items;
    public final Fraction fract;

    public Resource sour;

    // production per sec
    protected int plastanium;
    protected int titanium;
    protected int thorium;
    protected int spore;

    // presence / absence
    protected int oil;
    protected int water;
    protected int cryo;

    // little creatures
    protected int unit;
    protected int crawler;

    public Production() {
        items = null;
        fract = null;

        sour = new Resource();
    }

    public Production(Human human) {
        items = new ItemModule();
        fract = human.fraction;
    }

    public void update() {
        float speed = fract.production + (water > 0 ? .2f : 0f) + (cryo > 0 ? .4f : 0f);

        items.add(Items.plastanium, (int) (plastanium * speed));
        items.add(Items.titanium, (int) (titanium * speed));
        items.add(Items.thorium, (int) (thorium * speed));
        items.add(Items.sporePod, (int) (spore * speed));
    }

    public void all(int amount) {
        titanium = plastanium = thorium = spore = oil = water = cryo = unit = crawler = amount;
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

    public String liquids() {
        return (oil > 0 ? "[lime]\uE800[]" : "[scarlet]\uE815[]") + Icons.get("oil") +
                (water > 0 ? "[lime]\uE800[]" : "[scarlet]\uE815[]") + Icons.get("water") +
                (cryo > 0 ? "[lime]\uE800[]" : "[scarlet]\uE815[]") + Icons.get("cryofluid");
    }

    public String unit() {
        return (unit <= 5 ? "[scarlet]" : unit <= 10 ? "[orange]" : "[green]") + unit;
    }

    public void unit(int amount) {
        unit += amount * fract.creature;
    }

    public String crawler() {
        return (crawler <= 5 ? "[scarlet]" : crawler <= 10 ? "[orange]" : "[green]") + crawler;
    }

    public void crawler(int amount) {
        crawler += amount * fract.creature;
    }

    public void unlock(Human human, Weapon weapon) {
        if (crawler >= weapon.cost) {
            crawler -= weapon.cost;
            human.unlock(weapon.id);
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
            crawler(-amount);
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
    }

    public void check(Human human, Boolf<Production> pred) {
        human.captured().each(hex -> pred.get(hex.build.cons), hex -> hex.lose(null));
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
                    (unit <= 0 || prod.unit >= unit);
        }

        public String formatP(Human human) {
            return formatB(human.locale, new String[] {"prod.unit", "prod.item", "prod.liquid"}, human.fraction.creature, human.fraction.production);
        }
        
        public String formatC(Human human) {
            return formatB(human.locale, new String[] {"cons.unit", "cons.item", "cons.liquid"}, 1, 1);
        }

        private String formatB(Locale loc, String[] base, int c, int i) {
            format = "";
            add(base[0], loc, unit * c, "");
            add(base[1], loc, titanium * i, "titanium");
            add(base[1], loc, thorium * i, "thorium");
            add(base[1], loc, plastanium * i, "plastanium");
            add(base[1], loc, spore * i, "spore-pod");
            if (oil > 0 || water > 0 || cryo > 0) format += "\n";
            add(base[2], loc, oil, "oil");
            add(base[2], loc, water, "water");
            add(base[2], loc, cryo, "cryofluid");

            return format;
        }

        private void add(String key, Locale loc, int amount, String icon) {
            if (amount != 0) format += format(key, loc, amount, Icons.get(icon));
        }
    }
}
