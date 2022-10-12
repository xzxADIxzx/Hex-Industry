package hex.types.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import hex.types.Hex;

import static mindustry.Vars.*;

public abstract class HexPathAI extends HexAI {

    public Hex hex;
    public Seq<Vec2> marks;

    public int state;

    public HexPathAI() {
        Time.run(60f, () -> {
            hex = hexOn();
            marks = initMarks();
        });
    }

    @Override
    public void updateMovement() {
        if (marks == null) return; // 1 second

        if (state < marks.size) {
            Vec2 pos = marks.get(state);
            moveTo(pos, 0f);
            faceMovement(); // crawlers brrr

            if (unit.within(pos, 2f)) state++;
        } else despawn();
    }

    public Vec2 from(int x, int y) {
        return new Vec2(hex.fx + x * tilesize + Mathf.random(8f), hex.fy + y * tilesize + Mathf.random(8f));
    }

    public abstract Seq<Vec2> initMarks();
}
