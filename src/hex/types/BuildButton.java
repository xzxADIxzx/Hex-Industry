package hex.types;

import mindustry.gen.Call;

import static hex.Main.humans;
import static hex.components.Bundle.findLocale;
import static hex.components.Bundle.get;

public class BuildButton extends Button {

    private final HexBuild build;

    public BuildButton(HexBuild build, Hex hex) {
        this(build, hex, hex.cx, hex.cy);
    }

    public BuildButton(HexBuild build, Hex hex, int x, int y) {
        super((p, h) -> {
            if (h.owner == null || h.owner == p.leader) {
                if (build.cons.sour.enough(p.production)) {
                    h.owner = p.leader;
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
        humans.each(h -> {
            if (hex.owner == null || hex.owner == h.leader) Call.label(h.player.con, format(h), 1f, fx, fy);
        });
    }
}
