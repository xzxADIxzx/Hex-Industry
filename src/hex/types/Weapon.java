package hex.types;

import arc.math.Mathf;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Weapon {

    public String name;
    public float chance;

    public Production cons;

    public float chance(Fraction fract, HexBuild build) {
        return chance;
    }

    public void chance(Human human, Hex hex) {
        if (!cons.sour.enough(human.production)) human.player.sendMessage(get("enough", human.locale));
        else if (Mathf.chance(chance(human.fraction, hex.build))) {
            hex.owner.player.sendMessage(format("hex.attacked", hex.owner.locale, human.player.coloredName(), hex.cx, hex.cy));
            hex.build.destroy(hex.owner.production);
            hex.clear();
        }
    }
}