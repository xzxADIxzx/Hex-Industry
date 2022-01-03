package hex.content;

import hex.types.*;
import mindustry.content.*;

public class Fractions {

	public static Fraction 
			horde,
			engineer,
			militant;

	public static void load() {
		horde = new Fraction(){{
			unit = UnitTypes.alpha;
			dmgfix();

			damage = .8f;
			people = 2f;
		}};

		engineer = new Fraction(){{
			unit = UnitTypes.beta;
			dmgfix();

			production = 2f;
			people = .8f;
		}};

		militant = new Fraction(){{
			unit = UnitTypes.gamma;
			dmgfix();

			damage = 2f;
			production = .8f;
		}};
	}
}
