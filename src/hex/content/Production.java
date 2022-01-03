package hex.content;

import hex.types.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Production {

	private CoreBuild core;
	private Fraction fract;

	// production per sec
	private int titanium = 10;
	private int thorium;
	private int spores;

	// presence / absence
	private int oil;
	private int water;
	private int cryo;

	// little people
	private int ppl = 20;
	private int pplPrd = 1;
	private int pplMax = 20;

	public Production(Human ppl) {
		core = world.build(ppl.citadel.cx, ppl.citadel.cy).core();
		fract = ppl.fraction;
	}

	public void update() {
		float speed = fract.production + (water() ? .2f : 0f) + (cryo() ? .4f : 0f);

		core.items.add(Items.titanium, (int)(titanium * speed));
		core.items.add(Items.thorium, (int)(thorium * speed));
		core.items.add(Items.sporePod, (int)(spores * speed));

		ppl += pplPrd * fract.people;
	}

	public int titanium() {
		return core.items.get(Items.titanium);
	}

	public void titanium(int amount) {
		titanium += amount;
	}

	public int thorium() {
		return core.items.get(Items.thorium);
	}

	public void thorium(int amount) {
		thorium += amount;
	}

	public int spores() {
		return core.items.get(Items.sporePod);
	}

	public void spores(int amount) {
		spores += amount;
	}

	public boolean oil() {
		return oil > 0;
	}

	public void oil(boolean amount) {
		oil += amount ? 1 : -1;
	}

	public boolean water() {
		return water > 0;
	}

	public void water(boolean amount) {
		water += amount ? 1 : -1;
	}

	public boolean cryo() {
		return cryo > 0;
	}

	public void cryo(boolean amount) {
		cryo += amount ? 1 : -1;
	}

	public int ppl() {
		return ppl;
	}

	public void ppl(int amount) {
		pplPrd += amount * fract.people;
	}

	public int pplMax() {
		return pplMax;
	}

	public void pplMax(int amount) {
		pplMax += amount * fract.people;
	}
}
