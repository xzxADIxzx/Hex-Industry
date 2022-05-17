package hex.types.ai;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import hex.types.Hex;
import mindustry.ai.Pathfinder;
import mindustry.content.Items;
import mindustry.gen.Call;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class HexSuicideAI extends HexAI {

    public int state;

    public Hex hex;
    public Seq<Vec2> marks;

    public HexSuicideAI() {
        Time.run(60f, () -> {
            hex = hexOn();
            if (hex.owner.player.team() != unit.team()) return;
            marks = Seq.with(from(7, -3), from(3, -3), from(3, 1));
        });
    }

    @Override
    public void updateMovement() {
        if (marks == null) return;
        if (state < marks.size) {
            Vec2 pos = marks.get(state);
            moveTo(pos, 0f);

            if (unit.within(pos, 2f)) state++;
        } else if (state == 3) {
            Call.takeItems(world.build(hex.cx, hex.cy + 1), Items.sporePod, 10, unit);
            state = 4;
        } else if (state == 4) {
            pathfind(Pathfinder.fieldRally);
            target();

            if (unit.within(target, 20f)) despawn();
        }
    }

    public Vec2 from(int x, int y) {
        return new Vec2(hex.fx + x * tilesize + Mathf.random(8f), hex.fy + y * tilesize + Mathf.random(8f));
    }
}
