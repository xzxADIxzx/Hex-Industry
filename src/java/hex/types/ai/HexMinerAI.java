package hex.types.ai;

public class HexMinerAI extends HexAI {

    @Override
    public void updateMovement() {
        target();
        moveTo(target, 0f);

        if (unit.within(target, 5f)) despawn();
    }
}
