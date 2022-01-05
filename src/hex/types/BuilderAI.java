package hex.types;

import mindustry.entities.units.*;

public class BuilderAI extends AIController {

	public boolean start;

	@Override
	public void updateMovement() {
		if (!start) unit.updateBuilding = start = timer.get(500);

		if (unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 200f);
		else unit.spawnedByCore(true);
	}
}
