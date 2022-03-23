package hex.types;

import arc.math.geom.Position;
import mindustry.content.StatusEffects;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

import java.util.Locale;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Fraction {

    public String name;
    public UnitType unit;

    public float damage;
    public float production;
    public float creature;

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

    public String desc(Locale loc) {
        String desc = get(name + ".desc", loc);
        if (unit != null) desc += format("fract.stats", loc, damage, production, creature);
        return desc;
    }
}
