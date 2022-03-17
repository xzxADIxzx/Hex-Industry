package hex.types.buttons;

import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;

import static hex.components.Bundle.format;
import static hex.components.Bundle.get;

public class OpenButton extends Button {

    public OpenButton(Hex hex) {
        super((human, hex1) -> {
            if (human.production.unit(human, human.cost(hex))) {
                hex.open();
                human.stats.opened++;
            }
        }, hex);
    }

    @Override
    public void update(Human human) {
        Call.label(human.player.con, get("hex.open", human.locale) + "\n" + format("cons.unit", human.locale, human.cost(hex)), 1f, fx, fy);
    }
}