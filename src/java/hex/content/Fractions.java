package hex.content;

import arc.math.Mathf;
import hex.types.Fraction;
import mindustry.content.UnitTypes;
import useful.Bundle;

import java.util.Locale;

public class Fractions {

    public static Fraction horde, engineer, militant, spectator;

    public static void load() {
        horde = new Fraction() {{
            name = "fract.horde";
            unit = UnitTypes.alpha;

            damage = 1f;
            production = 1.5f;
            creature = 1.9f;
        }};

        engineer = new Fraction() {{
            name = "fract.engineer";
            unit = UnitTypes.beta;

            damage = 1.5f;
            production = 2f;
            creature = 1.2f;
        }};

        militant = new Fraction() {{
            name = "fract.militant";
            unit = UnitTypes.gamma;

            damage = 2.1f;
            production = 1f;
            creature = 1.5f;
        }};

        spectator = new Fraction() {{
            name = "fract.spectator";
        }};
    }

    public static Fraction from(int id) {
        if (id == -1) id = Mathf.random(2);
        return new Fraction[] {horde, engineer, militant, spectator}[id];
    }

    public static String[][] names(Locale loc, boolean spectator) {
        int len = spectator ? 4 : 3;
        String[][] names = new String[len][1];
        for (int i = 0; i < len; i++) names[i][0] = Bundle.get(from(i).name + ".name", loc);
        return names;
    }
}
