package hex.types;

import arc.math.Mathf;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;
import static mindustry.Vars.world;

public class Weapon {

    protected static int _id;

    public int id;
    public String name;

    public float chance;
    public int cost;
    public Production cons;

    public Weapon() {
        id = 1 << _id++;
    }

    public float chance(Fraction fract, HexBuild build) {
        return chance;
    }

    public void attack(Human human, Hex hex) {
        if (cons.sour.enough(human.production)) {
            cons.sour.consume(human.production);
            if (Mathf.chance(chance(human.fraction, hex.build))) {
                hex.owner.player.sendMessage(format("hex.attacked", hex.owner.locale, human.player.coloredName(), hex.cx, hex.cy));
                hex.build.destroy(hex.owner.production);
                hex.clear();

                if (hex.base && !hex.isCitadel()) world.build(hex.cx, hex.cy).kill();
            }
        } else human.player.sendMessage(get("enough", human.locale));
    }
}