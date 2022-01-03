package hex.types;

import hex.*;
import hex.content.*;
import arc.*;
import arc.struct.*;
import arc.util.Log;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.world.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Human {

	protected static int _id;

	public static ObjectMap<Player, Unit> units = new ObjectMap<>();

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
		player.team(Team.baseTeams[_id++]);
		player.unit(fraction.spawn(player.team(), hex.pos()));

		// saves the player's unit
		Log.info(player.unit());
		units.put(player, player.unit());

		// TODO: move to hex.build
		world.tile(hex.cx, hex.cy).setNet(Blocks.coreNucleus, player.team(), 0);
		Schems.citadel.tiles.each(st -> {
			Tile tile = world.tile(st.x + hex.x + 2, st.y + hex.y + 3);
			tile.setNet(st.block, player.team(), 0);
			if (st.config != null) tile.build.configureAny(st.config);
		});

		citadel = hex;
		production = new Production(this);
	}

	public Hex location() {
		return Main.hexes.min(hex -> player.dst2(hex.pos()));
	}
}
