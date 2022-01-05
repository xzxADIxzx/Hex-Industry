package hex.types;

import arc.util.*;
import arc.func.*;
import arc.struct.*;
import arc.graphics.*;
import hex.content.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class HexBuild {

	static Seq<Unit> units = new Seq<>();
	static {
		Timer.schedule(() -> {
			// removes poly if she finished building
			units.each(poly -> {
				if (poly.buildPlan() == null) {
					poly.spawnedByCore(true);
					units.remove(poly);
				}
			});
		}, 0f, 10f);

		UnitTypes.poly.health = 1000000000;
		UnitTypes.poly.buildSpeed = 10;
	}

	public HexBuild next;
	public Schem scheme;
	public Effect boom;

	public Cons<Production> onBuild;
	public Cons<Production> onBreak;

	public void build(Hex hex) {
		// cleanup old build
		if (!hex.isEmpty()) explode(hex);

		Unit poly = UnitTypes.poly.spawn(hex.owner.player.team(), hex.pos());
		scheme.each(st -> poly.addBuild(new BuildPlan(st.x + hex.x, st.y + hex.y, st.rotation, st.block, st.config)));
		units.add(poly);

		onBuild.get(hex.owner.production);

		hex.buttons.each(btn -> Buttons.unregister(btn));
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
