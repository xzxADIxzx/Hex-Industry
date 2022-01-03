package hex.types;

import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.game.*;
import mindustry.content.*;

public class Fraction {

	public UnitType unit;
	public float damage;
	public float shield;
	public float production;
	public float people;

	public UnitType unit(UnitType unit) {
		unit.weapons.each(weapon -> weapon.bullet.damage = 0f);
		unit.buildSpeed = 0f;
		return this.unit = unit;
	}

	public Unit spawn(Team team, Position pos) {
		Unit unit = this.unit.spawn(team, pos);
		unit.apply(StatusEffects.boss);
		return unit;
	}
}
