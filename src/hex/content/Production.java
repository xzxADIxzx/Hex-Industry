package hex.content;

import hex.types.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Production {

	private CoreBuild core;
	private Fraction fract;

	public Resources resource;

	public int titanium = 10;
	public int thorium;
	public int spores;

	public Production(Human ppl) {
		core = world.build(ppl.citadel.cx, ppl.citadel.cy).core();
		fract = ppl.fraction;

		resource = new Resources();
		resource.humans(20);
	}

	public class Resources {

		public int humans;
		public int oil;
		public int water;
		public int cryo;

		public void update() {
			float speed = fract.production + (water() ? .2f : 0f) + (cryo() ? .4f : 0f);
			core.items.add(Items.titanium, (int)(titanium * speed));
			core.items.add(Items.thorium, (int)(thorium * speed));
			core.items.add(Items.sporePod, (int)(spores * speed));
		}

		public void humans(int amount) {
			humans += amount * fract.people;
		}

		public boolean oil() {
			return oil > 0;
		}

		public boolean water() {
			return water > 0;
		}

		public boolean cryo() {
			return cryo > 0;
		}

		public int titanium() {
			return core.items.get(Items.titanium);
		}

		public int thorium() {
			return core.items.get(Items.thorium);
		}

		public int spores() {
			return core.items.get(Items.sporePod);
		}
	}
}
