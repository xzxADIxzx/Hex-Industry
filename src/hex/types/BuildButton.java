package hex.types;

import hex.*;
import arc.struct.*;
import mindustry.gen.*;

public class BuildButton extends Button {

	private Seq<Human> cached;

	public BuildButton(HexBuild build, Hex hex) {
		this(build, hex, hex.cx, hex.cy);
	}

	public BuildButton(HexBuild build, Hex hex, int x, int y) {
		super((p, h) -> {
			h.owner = p;
			h.build(build);
		}, hex, x, y);

		if (hex.owner != null) cached = Main.humans.copy().select(h -> h != hex.owner);
	}

	@Override
	public void update() {
		if (cached == null)
			Call.label("build", 1f, fx, fy);
		else {
			Call.label(hex.owner.player.con, "upgrade", 1f, fx, fy);
			cached.each(h -> Call.label(h.player.con, "attack", 1f, fx, fy));
		}
	}
}
