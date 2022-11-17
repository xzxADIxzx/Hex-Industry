package hex.types.buttons;

import hex.types.Hex;
import hex.types.Human;
import useful.Bundle;

public class OpenButton extends Button {

    public OpenButton(Hex hex) {
        super((human, hex1) -> {
            if (hex.isClosed() && human.production.consUnits(human, human.cost(hex))) {
                hex.open();
                human.stats.opened++;
            }
        }, hex);
    }

    @Override
    public String toString(Human human) {
        return Bundle.get("hex.open", human) + "\n" + Bundle.format("cons.unit", human, human.cost(hex));
    }
}