package hex.types;

import arc.func.*;
import arc.graphics.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.entities.*;

import static mindustry.Vars.*;

public class HexBuild {

	public Schem scheme;
	public Effect boom;

	public Cons<Production> onBuild;
	public Cons<Production> onBreak;

	public void build(Hex hex) {
		onBuild.get(hex.owner.production);
		Team team = hex.owner.player.team();

		// TODO: spawn poly & add build plan
		scheme.each(st -> {
			Tile tile = world.tile(st.x + hex.x, st.y + hex.y);
			tile.setNet(st.block, team, 0);
			if (st.config != null)
				tile.build.configureAny(st.config);
		});
	}

	public void explode(Hex hex) {
		float x = hex.cx * tilesize;
		float y = hex.cy * tilesize;

		Call.effect(boom, x, y, 0, Color.white);
		Call.soundAt(Sounds.explosionbig, x, y, 1, 1);

		Damage.damage(x, y, 13 * 8, 1000000);
	}
}
