package hex.types;

import arc.math.Mathf;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Weapon {

    protected static int _id;

    public byte id;
    public String name;

    public float chance;
    public Production cons;

    public Weapon() {
        id = (byte) _id++;
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
            }
        } else human.player.sendMessage(get("enough", human.locale));
    }
}