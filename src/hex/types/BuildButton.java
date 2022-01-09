package hex.types;

import hex.*;
import arc.struct.*;
import mindustry.gen.*;

public class BuildButton extends Button {

	private HexBuild build;
	private Seq<Human> cached;

	public BuildButton(HexBuild build, Hex hex) {
		this(build, hex, hex.cx, hex.cy);
	}

	public BuildButton(HexBuild build, Hex hex, int x, int y) {
		super((p, h) -> {
			h.owner = p;
			h.build(build);
		}, hex, x, y);

		this.build = build;
		joined();
	}

	public String format(Fraction fract) {
		return build.name + "\n" + build.prod.sour.format(fract) + "\n" + build.cons.sour.format();
	}

	@Override
	public void update() {
		if (cached == null)
			Main.humans.each(h -> Call.label(h.player.con, format(h.fraction), 1f, fx, fy));
		else {
			Call.label(hex.owner.player.con, format(hex.owner.fraction), 1f, fx, fy);
			cached.each(h -> Call.label(h.player.con, format(h.fraction), 1f, fx, fy));
		}
	}

	@Override
	public void joined() {
		if (hex.owner != null) cached = Main.humans.copy().select(h -> h != hex.owner);
	}
}
