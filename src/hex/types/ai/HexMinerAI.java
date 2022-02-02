package hex.types.ai;

import arc.graphics.Color;
import mindustry.ai.Pathfinder;
import mindustry.content.Fx;
import mindustry.entities.units.AIController;
import mindustry.gen.Call;
import mindustry.gen.Teamc;
import mindustry.world.meta.BlockFlag;

public class HexMinerAI extends AIController {

    @Override
    public void updateMovement() {
        Teamc target = targetFlag(unit.x, unit.y, BlockFlag.rally, false);
        pathfind(Pathfinder.fieldRally);

        if (unit.within(target, 40f)) {
            Call.unitDespawn(unit);
            Call.effect(Fx.spawn, unit.x, unit.y, 0, Color.white);
        }
    }
}
