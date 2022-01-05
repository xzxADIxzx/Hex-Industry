package hex.types;

import mindustry.entities.units.*;

public class BuilderAI extends AIController {

	public BuilderAI() {
		unit.updateBuilding = false;
	}

	@Override
	public void updateMovement() {
		if (!unit.updateBuilding) unit.updateBuilding = timer.get(500);

		if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 200f);
		else unit.spawnedByCore(true);
	}
}
