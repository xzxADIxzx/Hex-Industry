package hex.types.buttons;

import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;
import useful.Bundle;

public class OpenButton extends Button {

    public OpenButton(Hex hex) {
        super((human, hex1) -> {
            if (hex.isClosed() && human.production.unit(human, human.cost(hex))) {
                hex.open();
                human.stats.opened++;
            }
        }, hex);
    }

    @Override
    public void update(Human human) {
        Call.label(human.player.con, Bundle.get("hex.open", human.locale) + "\n" + Bundle.format("cons.unit", human.locale, human.cost(hex)), 1f, fx, fy);
    }
}