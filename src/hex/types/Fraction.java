package hex.types;

import arc.math.geom.Position;
import mindustry.content.StatusEffects;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class Fraction {

    public String name;
    public UnitType unit;

    public int damage;
    public int production;
    public int creature;
    public int distance;

    public static void leader(Unit unit) {
        unit.apply(StatusEffects.overdrive);
    }

    public void unit(UnitType unit) {
        unit.weapons.clear();
        unit.buildSpeed = 0f;
        this.unit = unit;
    }

    public Unit spawn(Team team, Position pos) {
        Unit unit = this.unit.spawn(team, pos);
        unit.apply(StatusEffects.boss);
        return unit;
    }
}
