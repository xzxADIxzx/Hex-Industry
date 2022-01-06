package hex.types;

import hex.*;
import hex.content.*;
import arc.*;
import arc.util.*;
import arc.math.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.game.EventType.*;

public class Human {

	protected static int _id;

	static ObjectMap<Player, Unit> units = new ObjectMap<>();

	static {
		Events.on(UnitChangeEvent.class, event -> {
			if (!Main.initialized) return;

			Unit unit = units.get(event.player);
			if (event.unit != unit)
				event.player.unit(unit);
		});
	}

	public Player player;
	public Hex citadel;
	public Fraction fraction;
	public Production production;

	public Human(Player ppl, Fraction abilities) {
		player = ppl;
		fraction = abilities;
	}

	public void init() {
		citadel = Generator.citadel();
		production = new Production(this);

		citadel.owner = this;
		citadel.build(HexBuilds.citadel);

		player.team(Team.all[++_id]);
		player.unit(fraction.spawn(player.team(), citadel.pos()));

		// saves the player's unit
		units.put(player, player.unit());
	}

	public void cleanup() {
		Main.hexes.each(hex -> {
			if (hex.owner == this && hex.build != null)
				Time.runTask(Mathf.random(120f), () -> hex.build.explode(hex));
		});
	}

	public Hex location() {
		return Main.hexes.min(hex -> player.dst2(hex.pos()));
	}
}
