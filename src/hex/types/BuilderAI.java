package hex.types;

import mindustry.entities.units.*;

public class BuilderAI extends AIController {

	public boolean start;

	public BuilderAI() {
		timer.get(3, 180f); // reset
	}

	@Override
	public void updateMovement() {
		if (!start) unit.updateBuilding = start = timer.get(3, 180f);

		if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 180f);
		else unit.spawnedByCore(true);
	}
}
