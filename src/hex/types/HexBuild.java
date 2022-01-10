package hex.types;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;

import static mindustry.Vars.*;

public class HexBuild {

	static {
		UnitTypes.poly.defaultController = HexBuilderAI::new;

		// TODO: remove
		Items.titanium.hardness = 2;
	}

	public String name;
	public HexBuild next;
	public Schem scheme;
	public Effect boom;

	public Production prod;
	public Production cons;

	public void build(Hex hex) {
		if (!hex.isEmpty()) explode(hex); // cleanup old build

		Unit poly = UnitTypes.poly.spawn(hex.owner.player.team(), hex.pos());
		scheme.each(st -> poly.addBuild(new BuildPlan(st.x + hex.x, st.y + hex.y, st.rotation, st.block, st.config)));

		prod.sour.produce(hex.owner.production);
		cons.sour.consume(hex.owner.production);

		hex.clearButtons();
		if (next != null) hex.buttons.add(new BuildButton(next, hex));
	}

	public void explode(Hex hex) {
		float x = hex.cx * tilesize;
		float y = hex.cy * tilesize;

		Call.effect(boom, x, y, 0, Color.white);
		Call.soundAt(Sounds.explosionbig, x, y, 1, 1);

		Damage.damage(null, x, y, 13 * 8, 1000000, false, true);
	}
}
