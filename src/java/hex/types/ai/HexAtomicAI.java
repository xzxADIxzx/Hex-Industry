package hex.types.ai;

import arc.math.geom.Vec2;
import arc.util.Time;

public class HexAtomicAI extends HexAI {

    public Vec2 dir;
    
    public HexAtomicAI(){
        Time.run(60f, () -> dir = new Vec2(200f, 200f).setLength(unit.speed()));
        Time.run(300f, () -> {
            unit.controlWeapons(true, true);
            unit.kill();
        });
    }

    @Override
    public void updateMovement() {
        if (dir != null) unit.movePref(dir);
    }
}
