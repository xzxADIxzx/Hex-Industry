package hex.types.ai;

public class HexMinerAI extends HexAI {

    @Override
    public void updateMovement() {
        target();
        if (target == null) return;

        moveTo(target, 0f);
        if (unit.within(target, 10f)) despawn();
    }
}
