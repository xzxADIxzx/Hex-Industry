package hex.types.ai;

public class HexBuilderAI extends HexAI {

    @Override
    public void updateMovement() {
        if (unit.plans.isEmpty()) despawn();
        else moveTo(unit.buildPlan().tile(), 180f);
    }
}
