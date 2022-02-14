package hex.types;

import arc.func.Boolf;
import arc.math.Mathf;
import hex.components.Bundle;
import hex.components.Icons;
import mindustry.content.Items;
import mindustry.world.blocks.storage.CoreBlock.CoreBuild;

import java.util.Locale;

import static hex.components.Bundle.get;

public class Production {

    private final CoreBuild core;
    private final Fraction fract;

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
    protected int human;
    protected int crawler;

    public Production() {
        core = null;
        fract = null;

        sour = new Resource();
    }

    public Production(Human human) {
        core = human.player.team().core();
        fract = human.fraction;

        // makes core invisible for 5 hours
        core.iframes = 1000000;
        core.items.clear();
    }

    public void update() {
        float speed = fract.production + (water > 0 ? .2f : 0f) + (cryo > 0 ? .4f : 0f);

        core.items.add(Items.plastanium, (int) (plastanium * speed));
        core.items.add(Items.titanium, (int) (titanium * speed));
        core.items.add(Items.thorium, (int) (thorium * speed));
        core.items.add(Items.sporePod, (int) (spore * speed));
    }

    public void all(int amount) {
        titanium = plastanium = thorium = spore = oil = water = cryo = human = crawler = amount;
    }

    public int plastanium() {
        return core.items.get(Items.plastanium);
    }

    public void plastanium(int amount) {
        core.items.add(Items.plastanium, amount);
    }

    public int titanium() {
        return core.items.get(Items.titanium);
    }

    public void titanium(int amount) {
        core.items.add(Items.titanium, amount);
    }

    public int thorium() {
        return core.items.get(Items.thorium);
    }

    public void thorium(int amount) {
        core.items.add(Items.thorium, amount);
    }

    public int spore() {
        return core.items.get(Items.sporePod);
    }

    public void spore(int amount) {
        core.items.add(Items.sporePod, amount);
    }

    public String liquids() {
        return (oil > 0 ? "[lime]\uE800[]" : "[scarlet]\uE815[]") + Icons.get("oil") +
                (water > 0 ? "[lime]\uE800[]" : "[scarlet]\uE815[]") + Icons.get("water") +
                (cryo > 0 ? "[lime]\uE800[]" : "[scarlet]\uE815[]") + Icons.get("cryofluid");
    }

    public String human() {
        return (human <= 5 ? "[scarlet]" : human <= 10 ? "[orange]" : "[green]") + human + "\uE86D[]";
    }

    public void human(int amount) {
        human += amount * fract.creature;
    }

    public String crawler() {
        return (crawler > 0 ? "[green]" : "[scarlet]") + crawler + "[]\uF7FA";
    }

    public void crawler(int amount) {
        crawler += amount * fract.creature;
    }

    public void unlock(Human human, Weapon weapon) {
        if (crawler >= weapon.cost) {
            crawler -= weapon.cost;
            human.unlock(weapon.id);
        } else human.player.sendMessage(get("enough", human.locale));
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

        public void produce(Production prod, boolean add) {
            int base = Mathf.sign(add);

            prod.plastanium += plastanium * base;
            prod.titanium += titanium * base;
            prod.thorium += thorium * base;
            prod.spore += spore * base;

            prod.oil += oil * base;
            prod.water += water * base;
            prod.cryo += cryo * base;

            prod.human(human * base);
        }

        public void consume(Production prod) {
            prod.plastanium(-plastanium);
            prod.titanium(-titanium);
            prod.thorium(-thorium);
            prod.spore(-spore);

            prod.human -= human;
        }

        public void human(Production prod, boolean add) {
            prod.human += human * Mathf.sign(add);
        }

        public boolean enough(Production prod) {
            return (plastanium <= 0 || prod.plastanium() >= plastanium) &&
                    (titanium <= 0 || prod.titanium() >= titanium) &&
                    (thorium <= 0 || prod.thorium() >= thorium) &&
                    (spore <= 0 || prod.spore() >= spore) &&
                    (oil <= 0 || prod.oil >= oil) &&
                    (water <= 0 || prod.water >= water) &&
                    (cryo <= 0 || prod.cryo >= cryo) &&
                    (human <= 0 || prod.human >= human);
        }

        public String format(Locale loc, Fraction fract) {
            return format(loc, new String[] {"prod.item", "prod.liquid", "prod.creature"}, fract.production, fract.creature);
        }

        public String format(Locale loc) {
            return format(loc, new String[] {"cons.item", "cons.liquid", "cons.creature"}, 1, 1);
        }

        private String format(Locale loc, String[] base, int r, int h) {
            String result = "";

            if (plastanium != 0) result += Bundle.format(base[0], loc, plastanium * r, Icons.get("plastanium"));
            if (titanium != 0) result += Bundle.format(base[0], loc, titanium * r, Icons.get("titanium"));
            if (thorium != 0) result += Bundle.format(base[0], loc, thorium * r, Icons.get("thorium"));
            if (spore != 0) result += Bundle.format(base[0], loc, spore * r, Icons.get("spore-pod"));

            if (oil != 0) result += Bundle.format(base[1], loc, Icons.get("oil"));
            if (water != 0) result += Bundle.format(base[1], loc, Icons.get("water"));
            if (cryo != 0) result += Bundle.format(base[1], loc, Icons.get("cryofluid"));

            if (human != 0) result += Bundle.format(base[2], loc, human * h);

            return result;
        }
    }
}
