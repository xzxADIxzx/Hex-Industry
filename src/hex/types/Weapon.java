package hex.types;

import static hex.Politics.attacked;
import static hex.components.Bundle.get;
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
        return format(name + ".desc", human.locale, damage(human));
    }

    public void attack(Human human) {
        if (!attackable(human)) return;
        Hex hex = attacked.get(human);
        if (cons.sour.enough(human.production)) {
            cons.sour.consume(human.production);
            if (hex.damage(damage(human))) hex.lose(human.player.coloredName());
        } else human.enough();
    }

    public static boolean attackable(Human human) {
        Hex hex = attacked.get(human);
        boolean zone = !hex.isCaptured(human), busy = hex.isEmpty() || hex.busy;
        if (hex.owner == human.leader) return false;
        else if (zone) human.player.sendMessage(get("hex.toofar", human.locale));
        else if (busy) human.player.sendMessage(get("hex.attack", human.locale));
        return !zone && !busy;
    }
}