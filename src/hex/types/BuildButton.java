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
				} else p.player.sendMessage("[scarlet]You don't have enough resources");
			} else  p.player.sendMessage("ATTACK in development :D");
		}, hex, x, y);

		this.build = build;
	}

	public String format(Human human) {
		return hex.owner == null || hex.owner == human ? build.name + "\n" + build.prod.sour.formated(findLocale(human.player), human.fraction) + "\n" + build.cons.sour.formated(findLocale(human.player)) : "ATTACK";
	}

	@Override
	public void update() {
		Main.humans.each(h -> Call.label(h.player.con, format(h), 1f, fx, fy));
	}
}
