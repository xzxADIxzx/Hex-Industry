package hex.types;

import static hex.components.Bundle.format;

public class Weapon {

    protected static int _id;

    public int id;
    public String name;

    public int damage;
    public int cost;
    public Production cons;

    public Weapon() {
        id = 1 << _id++;
    }

    public int damage(Human human) {
        return damage * human.fraction.damage;
    }

    public String desc(Human human) {
        return format(name + ".name", human.locale, damage(human));
    }

    public void attack(Human human, Hex hex) {
        if (cons.sour.enough(human.production)) {
            cons.sour.consume(human.production);
            if (hex.damage(damage(human))) hex.lose(human.player.coloredName());
        } else human.enough();
    }
}