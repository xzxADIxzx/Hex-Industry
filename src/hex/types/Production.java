package hex.types;

import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.content.*;

import static mindustry.Vars.*;

// TODO: go through all the hexes when the amount of liquid changes and turn on/off plants that require liquid
public class Production {

	private final CoreBuild core;
	private final Fraction fract;

	// can be 1 or -1
	private int base;

	// production per sec
	private int titanium;
	private int thorium;
	private int spores;

	// presence / absence
	private int oil;
	private int water;
	private int cryo;

	// little people
	private int ppl;
	private int pplPrd;
	private int pplMax;

	public Production(Human ppl) {
		core = world.build(ppl.citadel.cx, ppl.citadel.cy).core();
		fract = ppl.fraction;

		// sets base to 1
		reverse();
	}

	public void update() {
		float speed = fract.production + (water() ? .2f : 0f) + (cryo() ? .4f : 0f);

		core.items.add(Items.titanium, (int) (titanium * speed));
		core.items.add(Items.thorium, (int) (thorium * speed));
		core.items.add(Items.sporePod, (int) (spores * speed));

		ppl += pplPrd;
		if (ppl > pplMax) ppl = pplMax;
	}

	public void reverse() {
		base = base == 1 ? -1 : 1;
	}

	public int titanium() {
		return core.items.get(Items.titanium);
	}

	public void titanium(int amount) {
		core.items.add(Items.titanium, base * amount);
	}

	public int titaniumProd() {
		return titanium;
	}

	public void titaniumProd(int amount) {
		titanium += base * amount;
	}

	public int thorium() {
		return core.items.get(Items.thorium);
	}

	public void thorium(int amount) {
		core.items.add(Items.thorium, base * amount);
	}

	public int thoriumProd() {
		return titanium;
	}

	public void thoriumProd(int amount) {
		thorium += base * amount;
	}

	public int spores() {
		return core.items.get(Items.sporePod);
	}

	public void spores(int amount) {
		core.items.add(Items.sporePod, base * amount);
	}

	public int sporesProd() {
		return spores;
	}

	public void sporesProd(int amount) {
		spores += base * amount;
	}

	public boolean oil() {
		return oil > 0;
	}

	public void oilProd() {
		oil += base;
	}

	public boolean water() {
		return water > 0;
	}

	public void waterProd() {
		water += base;
	}

	public boolean cryo() {
		return cryo > 0;
	}

	public void cryoProd() {
		cryo += base;
	}

	public int ppl() {
		return ppl;
	}

	public void ppl(int amount) {
		pplPrd += base * amount * fract.people;
	}

	public int pplMax() {
		return pplMax;
	}

	public void pplMax(int amount) {
		pplMax += base * amount * fract.people;
	}
}
