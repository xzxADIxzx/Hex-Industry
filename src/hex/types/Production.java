package hex.types;

import hex.components.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.content.*;

import java.util.*;

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

	// little creatures
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
		return (human <= 5 ? "[scarlet]" : human <= 10 ? "[orange]" : "[green]") + human;
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

		public boolean enough(Production prod) {
			return (plastanium > 0 ? prod.plastanium() >= plastanium : true) &&
					(titanium > 0 ? prod.titanium() >= titanium : true) &&
					(thorium > 0 ? prod.thorium() >= thorium : true) &&
					(spore > 0 ? prod.spore() >= spore : true) &&
					(oil() ? prod.oil() : true) &&
					(water() ? prod.water() : true) &&
					(cryo() ? prod.cryo() : true) &&
					(human > 0 ? prod.human >= human : true);
		}

		public String format(Locale loc, Fraction fract) {
			return format(loc, new String[] { "prod.item", "prod.liquid", "prod.creature" }, fract.production, fract.people);
		}

		public String format(Locale loc) {
			return format(loc, new String[] { "cons.item", "cons.liquid", "cons.creature" }, 1, 1);
		}

		private String format(Locale loc, String[] base, int r, int h) {
			String result = "";

			if (plastanium != 0) result += Bundle.format(base[0], loc, plastanium * r, Icons.get("plastanium"));
			if (titanium != 0) result += Bundle.format(base[0], loc, titanium * r, Icons.get("titanium"));
			if (thorium != 0) result += Bundle.format(base[0], loc, thorium * r, Icons.get("thorium"));
			if (spore != 0) result += Bundle.format(base[0], loc, spore * r, Icons.get("spore"));

			if (oil != 0) result += Bundle.format(base[1], loc, Icons.get("oil"));
			if (water != 0) result += Bundle.format(base[1], loc, Icons.get("water"));
			if (cryo != 0) result += Bundle.format(base[1], loc, Icons.get("cryo"));

			if (human != 0) result += Bundle.format(base[2], loc, human * h);

			return result;
		}
	}
}
