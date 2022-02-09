package hex.types.ai;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import hex.types.Hex;
import mindustry.ai.Pathfinder;
import mindustry.ai.Pathfinder.PositionTarget;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.units.AIController;
import mindustry.gen.Call;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;

import static arc.Core.app;
import static hex.Main.hexes;
import static mindustry.Vars.world;
import static mindustry.Vars.pathfinder;

public class HexSuicideAI extends AIController {

    public int state;

    public Hex hex;
    public PositionTarget cultivator;

    public HexSuicideAI() {
        app.post(() -> {
            hex = hexes.min(h -> h.pos().dst(unit));
            cultivator = new PositionTarget(new Vec2(hex.fx + 16f, hex.fy + 8f));
            state = 1;
        });
    }

    @Override
    public void updateMovement() {
        if (state == 1) {
            Tile tile = pathfinder.getTargetTile(unit.tileOn(), cultivator);
            unit.movePref(vec.trns(unit.angleTo(tile.worldx(), tile.worldy()), unit.speed()));

            if (unit.within(tile, 10f)) state = 2;
        } else if (state == 2) {
            Call.takeItems(world.build(hex.cx, hex.cy + 1), Items.sporePod, 30, unit);
            if (unit.hasItem()) state = 3;
        } else if (state == 3) {
            pathfind(Pathfinder.fieldRally);

            if (unit.within(targetFlag(unit.x, unit.y, BlockFlag.rally, false), 10f)) {
                unit.controlWeapons(true);
                Call.effect(Fx.spawn, unit.x, unit.y, 0, Color.white);
            }
        }
    }
}
