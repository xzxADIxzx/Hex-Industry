package hex.types.buttons;

import hex.types.Hex;
import hex.types.HexBuild;
import hex.types.Human;
import mindustry.gen.Call;

import static hex.components.Bundle.get;

public class BuildButton extends Button {

    private final HexBuild build;

    public BuildButton(HexBuild build, Hex hex) {
        this(build, hex, hex.cx, hex.cy);
    }

    public BuildButton(HexBuild build, Hex hex, int x, int y) {
        super((human, hex1) -> {
            if ((hex1.owner != null && hex1.owner != human.leader) || hex1.busy) return;
            if (build.cons.sour.enough(human.production)) {
                if (hex1.isCaptured(human)) {
                    hex1.owner = human.leader;
                    hex1.build(build);
                    human.stats.builded++;
                } else human.player.sendMessage(get("hex.toofar", human.locale));
            } else human.enough();
        }, hex, x, y);

        this.build = build;
    }

    public String format(Human human) {
        return get(build.name, human.locale) + "\n" + build.prod.sour.formatP(human) + "\n" + build.cons.sour.formatC(human);
    }

    @Override
    public void update(Human human) {
        Call.label(human.player.con, format(human), 1f, fx, fy);
    }
}
