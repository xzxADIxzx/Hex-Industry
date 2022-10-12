package hex.types.ai;

import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.gen.Call;

import static mindustry.Vars.*;

public class HexSuicideAI extends HexPathAI {

    @Override
    public void updateMovement() {
        super.updateMovement();
        if (state == 3) Call.takeItems(world.build(hex.cx, hex.cy + 1), Items.sporePod, 10, unit);
    }

    @Override
    public Seq<Vec2> initMarks() {
        return Seq.with(
                from(7, -3), from(3, -3), from(3, 1), /* take spores */
                from(3, -3), from(7, -3), from(7, 5), /* go back */
                from(-5, 5), from(-5, 1), from(-9, 1), from(-9, -3), from(-5, -3), from(-5, -7), from(-1, -7));
    }
}
