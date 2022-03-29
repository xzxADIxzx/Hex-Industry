package hex.types.ai;

import static hex.Generator.onEmpty;

public class HexBuilderAI extends HexAI {

    public boolean start;

    public HexBuilderAI() {
        onEmpty(() -> start = true);
    }

    @Override
    public void updateMovement() {
        unit.updateBuilding = start;
        if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 180f);
        else despawn();
    }
}
