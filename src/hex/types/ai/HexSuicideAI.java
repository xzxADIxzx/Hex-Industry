package hex.types.ai;

import mindustry.ai.Pathfinder.PositionTarget;
import mindustry.entities.units.AIController;
import mindustry.world.Tile;

import static hex.Main.hexes;
import static mindustry.Vars.pathfinder;

public class HexSuicideAI extends AIController {

    public int state;

    public PositionTarget cultivator;

    public HexSuicideAI() {
        cultivator = new PositionTarget(hexes.min(h -> h.pos().dst(unit)).pos());
    }

    @Override
    public void updateMovement() {
        // TEMP TODO: сделать сложный ии с самообучением для перетаскивания предметов
        if (state == 0) {
            Tile tile = pathfinder.getTargetTile(unit.tileOn(), cultivator);
            unit.movePref(vec.trns(unit.angleTo(tile.worldx(), tile.worldy()), unit.speed()));

            if (unit.within(tile, 10f)) unit.controlWeapons(true);
        }
    }
}
