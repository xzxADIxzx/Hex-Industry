package hex.types;

import arc.math.geom.Position;
import mindustry.content.StatusEffects;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class Fraction {

	public UnitType unit;
	public int damage;
	public int production;
	public int people;
	public int distance;

	public void unit(UnitType unit) {
		this.unit = unit;
	}

	public Unit spawn(Team team, Position pos) {
		Unit unit = this.unit.spawn(team, pos);
		unit.apply(StatusEffects.boss);

		unit.buildSpeedMultiplier(0f);
		unit.damageMultiplier(0f);
		return unit;
	}
}
