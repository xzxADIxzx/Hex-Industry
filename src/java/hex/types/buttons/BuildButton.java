package hex.types.buttons;

import hex.types.Hex;
import hex.types.HexBuild;
import hex.types.Human;
import useful.Bundle;

public class BuildButton extends Button {

    private final HexBuild build;

    public BuildButton(HexBuild build, Hex hex) {
        this(build, hex, hex.cx, hex.cy);
    }

    public BuildButton(HexBuild build, Hex hex, int x, int y) {
        super((human, hex1) -> {
            if ((hex1.owner != null && hex1.owner != human.leader) || hex1.busy) return;
            if (build.cons.enough(human.production)) {
                if (hex1.isCaptured(human)) {
                    hex1.owner = human.leader;
                    hex1.build(build);
                    human.stats.builded++;
                } else human.player.sendMessage(Bundle.get("hex.toofar", human));
            } else human.enough();
        }, hex, x, y, 6f);

        this.build = build;
    }

    @Override
    public String toString(Human human) {
        return Bundle.get(build.name, human) + "\n" + build.prod.formatProd(human) + "\n" + build.cons.formatCons(human);
    }
}
