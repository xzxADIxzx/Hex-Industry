package hex.types.ai;

import arc.math.geom.Position;
import arc.util.Time;

public class HexFlyingAI extends HexAI {

    public Position pos;
    public float lenght;

    public HexFlyingAI() {
        Time.run(600f, this::despawn);
        Time.run(540f, () -> lenght = 0);
        Time.run(60f, () -> {
            pos = hexOn();
            lenght = unit.dst(pos) * 2f;
        });
    }

    @Override
    public void updateMovement() {
        if (pos == null) return;

        circle(pos, lenght);
        unit.controlWeapons(true, true);
    }
}
