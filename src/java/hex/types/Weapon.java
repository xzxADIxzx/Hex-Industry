package hex.types;

import arc.func.Cons3;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.type.UnitType;

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
    public Cons3<Human, Hex, Integer> todo;

    public Weapon() {
        id = 1 << _id++;
    }

    public int damage(Human human) {
        return (int) (damage * human.fraction.damage);
    }

    public String desc(Human human) {
        return format(name + ".desc", human.locale, damage(human), cons.sour.formatC(human));
    }

    public void attack(Human human) {
        if (!attackable(human)) return;
        Hex hex = attacked.get(human);
        if (cons.sour.enough(human.production)) {
            cons.sour.consume(human.production);
            todo.get(human, hex, damage(human));
            hex.attacked(human, this);
        } else human.enough();
    }

    public Cons3<Human, Hex, Integer> attack(UnitType unit, int min, int max) {
        return (human, hex, dmg) -> {
            if (hex.damage(dmg)) Time.run(600f, () -> hex.lose(human));
            for (int i = 0; i < Mathf.random(min, max); i++) // the amount depends on the unit size
                unit.spawn(human.player.team(), hex.fx + Mathf.random(-80f, 80f), hex.fy + Mathf.random(-80f, 80f));
        };
    }

    public static boolean attackable(Human human) {
        Hex hex = attacked.get(human);
        boolean zone = !hex.isCaptured(human), busy = hex.busy;
        if (hex.owner == human.leader || hex.build == null) return false;
        else if (zone) human.player.sendMessage(get("hex.toofar", human.locale));
        else if (busy) human.player.sendMessage(get("hex.downed", human.locale));
        return !zone && !busy;
    }
}