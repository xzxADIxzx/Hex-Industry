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

import java.util.Locale;

import static hex.Main.*;
import static hex.components.Bundle.*;
import static mindustry.Vars.*;

public class Human {

	public static ObjectMap<Player, Unit> units = new ObjectMap<>();

	static {
		Events.on(UnitChangeEvent.class, event -> {
			Unit unit = units.get(event.player);
			if (event.unit != unit && unit != null)
				event.player.unit(unit);
		});
	}

	public Human leader;
	public Player player;
	public Hex citadel;
	public Fraction fraction;
	public Production production;

	public Human(Player p, Fraction abilities) {
		// for team mechanics
		leader = this;

		player = p;
		fraction = abilities;

		citadel = Generator.citadel(p);
		production = new Production(this);

		citadel.owner = this;
		citadel.build(HexBuilds.citadel);

		player.unit(fraction.spawn(player.team(), citadel.pos()));
		units.put(player, player.unit()); // saves the player's unit
	}

	public void update() {
		Hex look = lookAt();
		Locale loc = findLocale(player);

		Call.setHudText(player.con, format("ui.hud", loc, location().id, production.human(), production.crawler(), production.liquids()));
		Call.label(player.con, format("ui.label", loc, look.id, look.owner == null ? "" : look.owner.name()), .05f, look.lx, look.ly);
	}

	public void team(Team team) {
		player.team(team);
		production.team(team);

		captured().each(hex -> Time.runTask(Mathf.random(180f), () -> hex.build.build(hex)));
	}

	public void lose() {
		Call.unitDespawn(units.remove(player));
		Call.hideHudText(player.con);

		player.team(Team.derelict);
		world.tile(citadel.point().pack()).setNet(Blocks.air);

		captured().each(hex -> Time.runTask(Mathf.random(180f), hex::clear));

		humans.remove(this);
	}

	public Hex location() {
		return hexes.min(hex -> hex.pos().dst(player));
	}

	public Hex lookAt() {
		return hexes.min(hex -> hex.pos().dst(player.mouseX, player.mouseY));
	}

	public Seq<Hex> captured() {
		return hexes.copy().filter(hex -> hex.owner == this);
	}

	public String name() {
		return Strings.stripColors(player.name());
	}

	public static Human from(Player player) {
		return humans.find(h -> h.player == player);
	}

	public static Human from(String name) {
		return humans.find(h -> Strings.stripGlyphs(Strings.stripColors(h.player.name)).equalsIgnoreCase(Strings.stripGlyphs(Strings.stripColors(name))));
	}
}
