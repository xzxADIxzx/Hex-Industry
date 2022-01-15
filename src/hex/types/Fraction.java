package hex.types;

import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.game.*;
import mindustry.content.*;

public class Fraction {

	public UnitType unit;
	public int damage;
	public int production;
	public int people;
	public int distance;

	public void unit(UnitType unit) {
		unit.weapons.each(weapon -> weapon.bullet.damage = 0f);
		unit.buildSpeed = 0f;
		this.unit = unit;
	}

	public Unit spawn(Team team, Position pos) {
		Unit unit = this.unit.spawn(team, pos);
		unit.apply(StatusEffects.boss);
		return unit;
	}
}
