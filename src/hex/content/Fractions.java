package hex.content;

import arc.math.Mathf;
import hex.types.Fraction;
import mindustry.content.UnitTypes;

import java.util.Locale;

import static hex.components.Bundle.get;

public class Fractions {

    public static Fraction horde, engineer, militant, spectator;

    public static void load() {
        horde = new Fraction() {{
            name = "fract.horde";
            unit(UnitTypes.alpha);

            damage = 2;
            production = 1;
            creature = 3;
        }};

        engineer = new Fraction() {{
            name = "fract.engineer";
            unit(UnitTypes.beta);

            damage = 2;
            production = 3;
            creature = 1;
        }};

        militant = new Fraction() {{
            name = "fract.militant";
            unit(UnitTypes.gamma);

            damage = 3;
            production = 1;
            creature = 2;
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
        for (int i = 0; i < len; i++) names[i][0] = get(from(i).name + ".name", loc);
        return names;
    }
}
