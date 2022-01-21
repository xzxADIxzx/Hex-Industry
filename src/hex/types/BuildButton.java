package hex.types;

import hex.*;
import mindustry.gen.*;

import static hex.components.Bundle.*;

public class BuildButton extends Button {

	private HexBuild build;

	public BuildButton(HexBuild build, Hex hex) {
		this(build, hex, hex.cx, hex.cy);
	}

	public BuildButton(HexBuild build, Hex hex, int x, int y) {
		super((p, h) -> {
			if (h.owner == null || h.owner == p) {
				if (build.cons.sour.enough(p.production)) {
					h.owner = p;
					h.build(build);
				} else p.player.sendMessage(get("enough", findLocale(p.player)));
			}
		}, hex, x, y);

		this.build = build;
	}

	public String format(Human human) {
		return build.name + "\n" + build.prod.sour.format(findLocale(human.player), human.fraction) + build.cons.sour.format(findLocale(human.player));
	}

	@Override
	public void update() {
		Main.humans.each(h -> {
			if (hex.owner == null || hex.owner == h) Call.label(h.player.con, format(h), 1f, fx, fy);
		});
	}
}
