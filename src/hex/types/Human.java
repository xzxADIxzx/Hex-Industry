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
import mindustry.content.*;

import static mindustry.Vars.world;

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

	public void init(Hex hex) {
		player.team(Team.all[++_id]);
		player.unit(fraction.spawn(player.team(), hex.pos()));

		// saves the player's unit
		units.put(player, player.unit());

		hex.clear();
		world.tile(hex.cx, hex.cy).setNet(Blocks.coreNucleus, player.team(), 0);
		hex.door = (byte) 0x00FFFFFF;

		citadel = hex;
		production = new Production(this);

		hex.owner = this;
		hex.build(HexBuilds.citadel);

		hex.open();
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
