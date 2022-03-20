package hex.types.ai;

import arc.util.Time;
import hex.types.Hex;

public class HexFlyingAI extends HexAI {

    public Hex hex;
    public float lenght;

    public HexFlyingAI() {
        Time.run(600f, () -> despawn());
        Time.run(540f, () -> lenght = 0);
        Time.run(60f, () -> {
            hex = hexOn();
            lenght = unit.dst(hex.pos()) * 2f;
        });
    }

    @Override
    public void updateMovement() {
        if (hex == null) return;

        circle(hex.pos(), lenght);
        unit.controlWeapons(true, true);
    }
}
