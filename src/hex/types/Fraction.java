package hex.types;

import mindustry.type.*;

public class Fraction {

	public UnitType unit;
	public float damage;
	public float production;
	public float people;

	public void dmgfix() {
		unit.weapons.get(0).bullet.damage = 0;
	}
}
