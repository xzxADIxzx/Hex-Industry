package hex.types;

import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.content.*;

// TODO: go through all the hexes when the amount of liquid changes and turn on/off plants that require liquid
public class Production {

	public Resource sour;

	private final CoreBuild core;
	private final Fraction fract;

	// can be 1 or -1
	private int base;

	// production per sec
	protected int titanium;
	protected int thorium;
	protected int spore;

	// presence / absence
	protected int oil;
	protected int water;
	protected int cryo;

	// little people
	protected int human;

	public Production() {
		core = null;
		fract = null;

		sour = new Resource();
	}

	public Production(Human human) {
		core = human.player.team().core();
		fract = human.fraction;

		// makes core invisible for 5 hours
		core.iframes = 1000000;

		// sets base to 1
		reverse();
	}

	public void update() {
		float speed = fract.production + (water() ? .2f : 0f) + (cryo() ? .4f : 0f);

		core.items.add(Items.titanium, (int) (titanium * speed));
		core.items.add(Items.thorium, (int) (thorium * speed));
		core.items.add(Items.sporePod, (int) (spore * speed));
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

	public int spore() {
		return core.items.get(Items.sporePod);
	}

	public void spore(int amount) {
		core.items.add(Items.sporePod, base * amount);
	}

	public int sporeProd() {
		return spore;
	}

	public void sporeProd(int amount) {
		spore += base * amount;
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

	public int human() {
		return human;
	}

	public void human(int amount) {
		human += base * amount * (fract == null ? 1 : fract.people);
	}

	public class Resource {

		public void produce(Production prod) {
			prod.titaniumProd(titanium);
			prod.thoriumProd(thorium);
			prod.sporeProd(spore);

			if (oil()) prod.oilProd();
			if (water()) prod.waterProd();
			if (cryo()) prod.cryoProd();

			prod.human(human);
		}

		public void consume(Production prod) {
			prod.titanium(-titanium);
			prod.thorium(-thorium);
			prod.spore(-spore);

			if (oil()) prod.oilProd();
			if (water()) prod.waterProd();
			if (cryo()) prod.cryoProd();

			prod.human(-human);
		}

		public String format(Fraction fract) {
			return "[green]+10 titanium";
		}

		public String format() {
			return "[scarlet]-1 people";
		}
	}
}
