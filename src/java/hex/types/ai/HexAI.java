package hex.types.ai;

import arc.graphics.Color;
import hex.types.Hex;
import hex.types.Human;
import mindustry.content.Fx;
import mindustry.entities.units.AIController;
import mindustry.gen.Call;
import mindustry.world.meta.BlockFlag;

import static hex.Main.hexes;

public class HexAI extends AIController {

    /** blocks the updateUnit call */
    public boolean withdrawn;

    @Override
    public void init() {
        if (Human.from(unit.team()) == null) withdrawn = true;
    }

    @Override
    public void updateUnit() {
        if (withdrawn) despawn();
        else super.updateUnit();
    }

    public void despawn() {
        Call.effect(Fx.spawn, unit.x, unit.y, 0, Color.white);
        Call.unitDespawn(unit);
    }

    protected void target() {
        target = targetFlag(unit.x, unit.y, BlockFlag.rally, false);
    }

    protected Hex hexOn() {
        return hexes.min(h -> h.pos().dst(unit));
    }
}
