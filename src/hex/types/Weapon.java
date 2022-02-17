package hex.types;

import arc.math.Mathf;

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
            if (Mathf.chance(chance(human.fraction, hex.build))) hex.lose(human.player.coloredName());
        } else human.enough();
    }
}