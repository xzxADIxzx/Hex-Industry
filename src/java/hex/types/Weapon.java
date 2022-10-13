package hex.types;

import arc.func.Cons3;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.type.UnitType;
import useful.Bundle;

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
        return Bundle.format(name + ".desc", human, damage(human), cons.resource.formatC(human));
    }

    public void attack(Human human) {
        if (!attackable(human)) return;
        if (cons.resource.enough(human.production)) {
            cons.resource.consume(human.production);
            todo.get(human, human.attacked, damage(human));
            human.attacked.attacked(human, this);
        } else human.enough();
    }

    public Cons3<Human, Hex, Integer> attack(UnitType unit, int min, int max) {
        return (human, hex, dmg) -> {
            if (hex.damage(dmg)) Time.run(600f, () -> hex.kill(human));
            for (int i = 0; i < Mathf.random(min, max); i++) // the amount depends on the unit size
                unit.spawn(human.player.team(), hex.fx + Mathf.random(-80f, 80f), hex.fy + Mathf.random(-80f, 80f));
        };
    }

    public static boolean attackable(Human human) {
        Hex hex = human.attacked;
        boolean zone = !hex.isCaptured(human), busy = hex.busy;

        if (hex.owner == human.leader || hex.build == null) return false;
        else if (zone) Bundle.bundled(human.player, "hex.toofar");
        else if (busy) Bundle.bundled(human.player, "hex.downed");

        return !zone && !busy;
    }
}