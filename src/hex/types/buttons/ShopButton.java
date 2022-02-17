package hex.types.buttons;

import hex.components.MenuListener;
import hex.content.HexBuilds;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;
import static hex.components.MenuListener.weaponChoose;;

public class ShopButton extends Button {

    public ShopButton(Hex hex) {
        super((human, hex1) -> {
            MenuListener.menu(human.player, weaponChoose, get("shop.title", human.locale), get("shop.text", human.locale),
                    new String[][] {{"crawlers package"}, {"may be weapons"}}, option -> "no description provided");
        }, hex, -7, -2);
    }

    @Override
    public void update(Human human) {
        int amount = human.leader.captured().sum(h -> h.build == HexBuilds.maze ? 1 : 0);
        Call.label(human.player.con, get("shop.title", human.locale) + "\n" + format("shop.amount", human.locale, amount), 1f, fx, fy);
    }
}
