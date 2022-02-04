package hex.types.ai;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.units.AIController;
import mindustry.gen.Call;
import mindustry.world.meta.BlockFlag;

public class HexMinerAI extends AIController {

    @Override
    public void updateMovement() {
        moveTo(targetFlag(unit.x, unit.y, BlockFlag.rally, false), 0f);

        if (unit.within(target, 10f)) {
            Call.unitDespawn(unit);
            Call.effect(Fx.spawn, unit.x, unit.y, 0, Color.white);
        }
    }
}
