package hex.types.buttons;

import hex.components.MenuListener;
import hex.content.Packages;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;
import static hex.components.MenuListener.shop;

public class ShopButton extends Button {

    public ShopButton(Hex hex) {
        super((human, hex1) -> {
            if (human.shops() == 0) human.player.sendMessage(get("noshop", human.locale));
            else MenuListener.menu(human.player, shop, get("shop.title", human.locale), get("shop.text", human.locale),
                        Packages.names(human), option -> Packages.from(human, option).desc.get(human));
        }, hex, hex.cx - 9, hex.cy);
    }

    @Override
    public void update(Human human) {
        Call.label(human.player.con, get("shop.title", human.locale) + "\n" + format("shop.amount", human.locale, human.shops()), 1f, fx, fy);
    }
}
