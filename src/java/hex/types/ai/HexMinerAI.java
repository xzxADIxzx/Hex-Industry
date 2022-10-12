package hex.types.ai;

import arc.math.geom.Vec2;
import arc.struct.Seq;

public class HexMinerAI extends HexPathAI {

    @Override
    public Seq<Vec2> initMarks() {
        return unit.y > hex.fy
                ? Seq.with(from(0, 1), from(2, -1), from(7, -1)) /* top unit */
                : Seq.with(from(-3, -3), from(-1, -1), from(7, -1)); /* bottom unit */
    }
}
