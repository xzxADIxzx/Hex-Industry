package hex.types.ai;

import static hex.Generator.onEmpty;

public class HexBuilderAI extends HexAI {

    public HexBuilderAI() {
        onEmpty(() -> unit.updateBuilding = true);
    }

    @Override
    public void updateMovement() {
        if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 180f);
        else if (unit.updateBuilding) despawn();
    }
}
