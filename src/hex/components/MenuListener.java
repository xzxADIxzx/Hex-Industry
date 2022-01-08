package hex.components;

import mindustry.ui.Menus;

public class MenuListener {

    public static int fractionChooseMenu;

    public static void load() {
        fractionChooseMenu = Menus.registerMenu((player, option) -> {
            //TODO (xzxADIxzx) обработка опции, выбранной игроком
        });
    }
}
