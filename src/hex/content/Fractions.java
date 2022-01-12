package hex.content;

import hex.types.*;
import arc.math.*;
import mindustry.content.*;

public class Fractions {

	public static Fraction horde, engineer, militant, spectator;

	public static void load() {
		horde = new Fraction() {{
			unit(UnitTypes.alpha);

			damage = 2;
			production = 1;
			people = 4;
			distance = 1;
		}};

		engineer = new Fraction() {{
			unit(UnitTypes.beta);

			damage = 1;
			production = 3;
			people = 1;
			distance = 3;
		}};

		militant = new Fraction() {{
			unit(UnitTypes.gamma);

			damage = 4;
			production = 1;
			people = 1;
			distance = 2;
		}};
	}

	public static Fraction from(int id) {
		if (id == -1) id = Mathf.random(2);
		return new Fraction[] { horde, engineer, militant, spectator }[id];
	}
}
