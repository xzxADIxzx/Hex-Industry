package hex.types;

import mindustry.entities.units.AIController;
import mindustry.gen.Call;

public class HexBuilderAI extends AIController {

    public boolean start;

    public HexBuilderAI() {
        timer.get(3, 180f); // reset
    }

    @Override
    public void updateMovement() {
        if (!start) unit.updateBuilding = start = timer.get(3, 180f);

        if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 180f);
        else Call.unitDespawn(unit);
    }
}
