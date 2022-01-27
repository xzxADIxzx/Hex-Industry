package hex.types;

import arc.math.Mathf;
import mindustry.gen.Call;

import static hex.components.Bundle.format;
import static hex.components.Bundle.get;

public class OpenButton extends Button {

    public OpenButton(Hex hex) {
        super((human, hex1) -> {
            if (human.production.human >= cost(human.citadel, hex)) {
                human.production.human -= cost(human.citadel, hex);
                hex.open();
            } else human.player.sendMessage(get("enough", human.locale));
        }, hex);
    }

    public static int cost(Hex citadel, Hex hex) {
        return Mathf.round(citadel.point().dst(hex.point()) / 140) + 1;
    }

    public int cost(Human human) {
        return cost(human.citadel, hex);
    }

    @Override
    public void update(Human human) {
        Call.label(human.player.con, get("hex.open", human.locale) + "\n" + format("cons.creature", human.locale, cost(human)), 1f, fx, fy);
    }
}