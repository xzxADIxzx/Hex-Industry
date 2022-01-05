package hex.types;

import mindustry.entities.units.*;

public class BuilderAI extends AIController{

	@Override
    public void updateMovement(){
        unit.updateBuilding = true;

        if(unit.buildPlan() != null) moveTo(unit.buildPlan().tile(), 200f);
        else unit.spawnedByCore(true);
	}
}
