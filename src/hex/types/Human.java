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

	public static ObjectMap<Player, Unit> units = new ObjectMap<>();

	static {
		Events.on(UnitChangeEvent.class, event -> {
			Unit unit = units.get(event.player);
			if (event.unit != unit && unit != null)
				event.player.unit(unit);
		});
	}

	public Player player;
	public Hex citadel;
	public Fraction fraction;
	public Production production;

	public Human(Player p, Fraction abilities) {
		player = p;
		fraction = abilities;

		citadel = Generator.citadel(player);
		production = new Production(this);

		citadel.owner = this;
		citadel.build(HexBuilds.citadel);

		player.unit(fraction.spawn(player.team(), citadel.pos()));
		units.put(player, player.unit()); // saves the player's unit
	}

	public void team(Team team) {
		player.team(team);
		production.team(team);

		captured().each(hex -> Time.runTask(Mathf.random(180f), () -> hex.build.build(hex)));
	}

	public void lose() {
		Call.unitDespawn(units.remove(player));
		cleanup();
	}

	public void cleanup() {
		captured().each(hex -> Time.runTask(Mathf.random(180f), () -> {
			hex.build.explode(hex);
			hex.env.build(hex);

			hex.build = null;
			hex.owner = null;
		}));
	}

	public Hex location() {
		return Main.hexes.min(hex -> hex.pos().dst(player));
	}

	public Hex lookAt() {
		return Main.hexes.min(hex -> hex.pos().dst(player.mouseX, player.mouseY));
	}

	public Seq<Hex> captured() {
		return Main.hexes.copy().filter(hex -> hex.owner == this);
	}

	public static Human from(Player player) {
		return Main.humans.find(h -> h.player == player);
	}

	public static Human from(String name) {
		return Main.humans.find(h -> Strings.stripGlyphs(Strings.stripColors(h.player.name)).equalsIgnoreCase(Strings.stripGlyphs(Strings.stripColors(name))));
	}
}
