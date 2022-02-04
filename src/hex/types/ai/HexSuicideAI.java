package hex.types.ai;

import mindustry.entities.units.AIController;

public class HexSuicideAI extends AIController {

    @Override
    public void updateMovement() {
        // TEMP TODO: сделать сложный ии с самообучением для перетаскивания предметов
        unit.controlWeapons(true);
    }
}
