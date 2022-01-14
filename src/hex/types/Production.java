package hex.types;

import mindustry.gen.*;
import mindustry.game.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.content.*;

// TODO: go through all the hexes when the amount of liquid changes and turn on/off plants that require liquid
public class Production {

	public Resource sour;

	private final CoreBuild core;
	private final Fraction fract;

	// production per sec
	protected int plastanium;
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
	}

	public void update() {
		float speed = fract.production + (water() ? .2f : 0f) + (cryo() ? .4f : 0f);

		core.items.add(Items.plastanium, (int) (plastanium * speed));
		core.items.add(Items.titanium, (int) (titanium * speed));
		core.items.add(Items.thorium, (int) (thorium * speed));
		core.items.add(Items.sporePod, (int) (spore * speed));
	}

	public void team(Team team) {
		core.items.clear();
		Call.setTeam(core, team);

		titanium = plastanium = thorium = spore = oil = water = cryo = human = 0;
	}

	public int plastanium() {
		return core.items.get(Items.plastanium);
	}

	public void plastanium(int amount) {
		core.items.add(Items.plastanium, amount);
	}

	public int titanium() {
		return core.items.get(Items.titanium);
	}

	public void titanium(int amount) {
		core.items.add(Items.titanium, amount);
	}

	public int thorium() {
		return core.items.get(Items.thorium);
	}

	public void thorium(int amount) {
		core.items.add(Items.thorium, amount);
	}

	public int spore() {
		return core.items.get(Items.sporePod);
	}

	public void spore(int amount) {
		core.items.add(Items.sporePod, amount);
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

	public String human() {
		return (human <= 5 ? "[scarlet]" : human <= 10 ? "[orange]" : "[green]") + human + "[]";
	}

	public void human(int amount) {
		human += amount * (fract == null ? 1 : fract.people);
	}

	public class Resource {

		public void produce(Production prod) {
			prod.plastanium += plastanium;
			prod.titanium += titanium;
			prod.thorium += thorium;
			prod.spore += spore;

			if (oil()) prod.oil++;
			if (water()) prod.water++;
			if (cryo()) prod.cryo++;

			prod.human(human);
		}

		public void consume(Production prod) {
			prod.plastanium(-plastanium);
			prod.titanium(-titanium);
			prod.thorium(-thorium);
			prod.spore(-spore);

			prod.human -= human;
		}

		public String format(Fraction fract) {
			return format(new String[] { "[green]+%d[]%s[gray]/sec[]\n", "extracts %s\n", "gives %d" }, fract.production, fract.people);
		}

		public String format() {
			return format(new String[] { "[scarlet]-%d[]%s\n", "requires %s\n", "takes %d" }, 1, 1);
		}

		// TODO: bundle.format
		private String format(String[] base, int r, int h) {
			String result = "";

			if (plastanium != 0) result += base[0].formatted(plastanium * r, Items.plastanium.emoji());
			if (titanium != 0) result += base[0].formatted(titanium * r, Items.titanium.emoji());
			if (thorium != 0) result += base[0].formatted(thorium * r, Items.thorium.emoji());
			if (spore != 0) result += base[0].formatted(spore * r, Items.sporePod.emoji());

			if (oil != 0) result += base[1].formatted(Liquids.oil.emoji());
			if (water != 0) result += base[1].formatted(Liquids.water.emoji());
			if (cryo != 0) result += base[1].formatted(Liquids.cryofluid.emoji());

			if (human != 0) result += base[2].formatted(human * h);

			return result;
		}
	}
}
