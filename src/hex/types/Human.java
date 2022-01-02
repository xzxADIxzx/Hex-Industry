package hex.types;

import hex.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Human {

	protected static int id;

	public Player player;
	public Hex citadel;
	public Fraction fraction;

	public Human(Player ppl, Fraction abilities){
		player = ppl;
		fraction = abilities;
	}

	public void init(Hex hex){
		this.citadel = hex;
		player.team(Team.baseTeams[id++]);

		// TODO: move to hex.build
		world.tile(hex.x, hex.y).setNet(Blocks.coreNucleus, player.team(), 0);
		Schems.citadel.tiles.each(st -> world.tile(st.x + hex.x + 2, st.y + hex.y + 3).setBlock(st.block));
	}
}
