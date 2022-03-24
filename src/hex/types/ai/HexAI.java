package hex.types.ai;

import arc.graphics.Color;
import hex.types.Hex;
import mindustry.content.Fx;
import mindustry.entities.units.AIController;
import mindustry.gen.Call;
import mindustry.world.meta.BlockFlag;

import static hex.Main.hexes;
import static hex.Main.humans;

public class HexAI extends AIController {

    @Override
    public void init() {
        if (!humans.contains(h -> h.player.team() == unit.team())) despawn();
    }

    public void despawn() {
        Call.unitDespawn(unit);
        Call.effect(Fx.spawn, unit.x, unit.y, 0, Color.white);
    }

    protected void target() {
        target = targetFlag(unit.x, unit.y, BlockFlag.rally, false);
        if (target == null) despawn();
    }

    protected Hex hexOn() {
        return hexes.min(h -> h.pos().dst(unit));
    }
}
