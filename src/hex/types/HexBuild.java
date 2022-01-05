package hex.types;

import arc.func.Cons;
import arc.graphics.Color;
import hex.content.Buttons;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Sounds;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class HexBuild {

	public HexBuild next;
	public Schem scheme;
	public Effect boom;

	public Cons<Production> onBuild;
	public Cons<Production> onBreak;

	public void build(Hex hex) {
		// cleanup old build
		if (!hex.isEmpty()) explode(hex);

		onBuild.get(hex.owner.production);
		Team team = hex.owner.player.team();

		// TODO: spawn poly & add build plan
		scheme.each(st -> {
			Tile tile = world.tile(st.x + hex.x, st.y + hex.y);
			tile.setNet(st.block, team, 0);
			if (st.config != null)
				tile.build.configureAny(st.config);
		});

		hex.buttons.each(btn -> Buttons.unregister(btn, btn.y));
		hex.buttons.clear();
		if (next != null) hex.buttons.add(new Button(h -> hex.build(next), hex, hex.cy));
	}

	public void explode(Hex hex) {
		float x = hex.cx * tilesize;
		float y = hex.cy * tilesize;

		Call.effect(boom, x, y, 0, Color.white);
		Call.soundAt(Sounds.explosionbig, x, y, 1, 1);

		Damage.damage(x, y, 13 * 8, 1000000);
	}
}
