package hex.content;

import arc.math.Mathf;
import hex.types.Fraction;
import mindustry.content.UnitTypes;

import java.util.Locale;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Fractions {

    public static Fraction horde, engineer, militant, spectator;

    public static void load() {
        horde = new Fraction() {{
            name = "fract.horde";
            unit(UnitTypes.alpha);

            damage = 2;
            production = 1;
            creature = 4;
            distance = 1;
        }};

        engineer = new Fraction() {{
            name = "fract.engineer";
            unit(UnitTypes.beta);

            damage = 1;
            production = 3;
            creature = 1;
            distance = 3;
        }};

        militant = new Fraction() {{
            name = "fract.militant";
            unit(UnitTypes.gamma);

            damage = 4;
            production = 1;
            creature = 1;
            distance = 2;
        }};

        spectator = new Fraction() {{
            name = "fract.spectator";
        }};
    }

    public static Fraction from(int id) {
        if (id == -1) id = Mathf.random(2);
        return new Fraction[] {horde, engineer, militant, spectator}[id];
    }

    public static String[][] names(Locale loc) {
        String[][] names = new String[4][1];
        for (int i = 0; i < 4; i++) names[i][0] = get(from(i).name + ".name", loc);
        return names;
    }

    public static String desc(Locale loc, int id) {
        Fraction fract = from(id);
        String desc = get(fract.name + ".desc", loc);
        if (fract != spectator) desc += format("fract.stats", loc, fract.damage, fract.production, fract.creature, fract.distance);
        return desc;
    }
}
