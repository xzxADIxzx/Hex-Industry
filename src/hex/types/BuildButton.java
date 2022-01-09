package hex.types;

import hex.*;
import mindustry.gen.*;

public class BuildButton extends Button {

	private HexBuild build;

	public BuildButton(HexBuild build, Hex hex) {
		this(build, hex, hex.cx, hex.cy);
	}

	public BuildButton(HexBuild build, Hex hex, int x, int y) {
		super((p, h) -> {
			h.owner = p;
			h.build(build);
		}, hex, x, y);

		this.build = build;
	}

	public String format(Human owner) {
		return owner == hex.owner ? build.name + "\n" + build.prod.sour.format(owner.fraction) + "\n" + build.cons.sour.format() : "ATTACK";
	}

	@Override
	public void update() {
		Main.humans.each(h -> Call.label(h.player.con, format(h), 1f, fx, fy));
	}
}
