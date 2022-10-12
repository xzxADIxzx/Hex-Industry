package hex.types.buttons;

import hex.components.MenuListener;
import hex.content.Packages;
import hex.types.Hex;
import hex.types.Human;
import useful.Bundle;

import static hex.components.MenuListener.*;

public class ShopButton extends Button {

    public ShopButton(Hex hex) {
        super((human, hex1) -> {
            if (human.shops() == 0) Bundle.bundled(human.player, "noshop");
            else MenuListener.menu(human.player, shop, "shop.name", "shop.text",
                        Packages.names(human), option -> Packages.from(human, option).desc.get(human));
        }, hex, hex.cx - 9, hex.cy);
    }

    @Override
    public String toString(Human human) {
        return Bundle.get("shop.name", human.locale) + "\n" + Bundle.format("shop.amount", human.locale, human.shops());
    }
}
