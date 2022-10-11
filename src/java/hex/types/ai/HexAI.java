package hex.types.ai;

import arc.graphics.Color;
import hex.types.Hex;
import hex.types.Human;
import mindustry.content.Fx;
import mindustry.entities.units.AIController;
import mindustry.gen.Call;

import static hex.Main.hexes;

public class HexAI extends AIController {

    /** Blocks the updateUnit call. */
    public boolean withdrawn;

    @Override
    public void init() {
        if (Human.find(unit.team()) == null) withdrawn = true;
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

    protected void target() { // TODO command center removed
        // target = targetFlag(unit.x, unit.y, BlockFlag.rally, false);
    }

    protected Hex hexOn() {
        return hexes.min(hex -> hex.dst(unit));
    }
}
