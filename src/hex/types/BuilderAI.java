package hex.types;

import mindustry.entities.units.*;

public class BuilderAI extends AIController {

	public boolean start;

	@Override
	public void updateMovement() {
		if (!start) {
			start = timer.get(500);
			return;
		}

		unit.updateBuilding = true;

		if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 200f);
		else unit.spawnedByCore(true);
	}
}
