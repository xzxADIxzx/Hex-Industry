package hex.types.ai;

import arc.util.Time;

import static hex.Generator.onEmpty;

public class HexBuilderAI extends HexAI {

    public HexBuilderAI() {
        Time.run(180f, () -> onEmpty(() -> unit.updateBuilding = true));
    }

    @Override
    public void updateMovement() {
        if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 180f);
        else despawn();
    }
}
