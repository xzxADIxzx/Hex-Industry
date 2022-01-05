package hex.content;

import hex.types.Fraction;
import mindustry.content.UnitTypes;

public class Fractions {

    public static Fraction
            horde,
            engineer,
            militant;

    public static void load() {
        horde = new Fraction() {{
            unit(UnitTypes.alpha);

            damage = .8f;
            shield = .8f;
            production = 1f;
            people = 2.4f;
        }};

        engineer = new Fraction() {{
            unit(UnitTypes.beta);

            damage = 1f;
            shield = 1.2f;
            production = 2f;
            people = .8f;
        }};

        militant = new Fraction() {{
            unit(UnitTypes.gamma);

            damage = 2.2f;
            shield = 1.2f;
            production = .6f;
            people = 1f;
        }};
    }
}
