package hex.types;

import mindustry.gen.Call;

import static hex.components.Bundle.get;

public class BuildButton extends Button {

    private final HexBuild build;

    public BuildButton(HexBuild build, Hex hex) {
        this(build, hex, hex.cx, hex.cy);
    }

    public BuildButton(HexBuild build, Hex hex, int x, int y) {
        super((human, hex1) -> {
            if ((hex1.owner != null && hex1.owner != human.leader) && human.location().neighbours().contains(hex1)) return;
            if (build.cons.sour.enough(human.production)) {
                if (hex1.isCaptured(human)) {
                    if (hex1.building) return;
                    hex1.owner = human.leader;
                    hex1.build(build);
                } else human.player.sendMessage(get("hex.toofar", human.locale));
            } else human.player.sendMessage(get("enough", human.locale));
        }, hex, x, y);

        this.build = build;
    }

    public String format(Human human) {
        return build.name + "\n" + build.prod.sour.format(human.locale, human.fraction) + build.cons.sour.format(human.locale);
    }

    @Override
    public void update(Human human) {
        Call.label(human.player.con, format(human), 1f, fx, fy);
    }
}