package hex.types.buttons;

import hex.components.MenuListener;
import hex.content.Packages;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;
import useful.Bundle;

import static hex.components.MenuListener.*;

public class ShopButton extends Button {

    public ShopButton(Hex hex) {
        super((human, hex1) -> {
            if (human.shops() == 0) Bundle.bundled(human.player, "noshop");
            else MenuListener.menu(human.player, shop, Bundle.get("shop.title", human.locale), Bundle.get("shop.text", human.locale),
                        Packages.names(human), option -> Packages.from(human, option).desc.get(human));
        }, hex, hex.cx - 9, hex.cy);
    }

    @Override
    public void update(Human human) {
        Call.label(human.player.con, Bundle.get("shop.title", human.locale) + "\n" + Bundle.format("shop.amount", human.locale, human.shops()), 1f, fx, fy);
    }
}
