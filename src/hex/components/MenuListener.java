package hex.components;

import arc.func.Cons2;
import arc.func.Func;
import arc.struct.ObjectMap;
import arc.util.Time;
import hex.Generator;
import hex.Guide;
import hex.content.Fractions;
import hex.content.Weapons;
import hex.types.Fraction;
import hex.types.Human;
import hex.types.Production;
import hex.types.Weapon;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.ui.Menus;

import static hex.Main.humans;
import static hex.Politics.attack;
import static hex.Politics.offers;
import static hex.Politics.attacked;

public class MenuListener {

    private static ObjectMap<Player, Integer> last = new ObjectMap<>();
    private static ObjectMap<Player, MenuInfo> info = new ObjectMap<>();
    private static ObjectMap<Integer, Cons2<Player, Integer>> menus = new ObjectMap<>();

    public static int fractionChoose, leaderFractionChoose, weaponChoose, weaponUnlockChoose, guide, base;

    public static void load() {
        menus.put(fractionChoose = 0, (player, option) -> {
            Fraction fract = Fractions.from(option);
            if (fract != Fractions.spectator) humans.add(new Human(player, fract));
        });

        menus.put(leaderFractionChoose = 1, (player, option) -> {
            Human leader = Human.from(player);
            Fraction fract = Fractions.from(option);

            leader.team(Generator.team());
            leader.unit(fract, true);
            leader.slaves().each(human -> {
                human.team(leader.player.team());
                human.unit(fract, false);
                human.captured().each(hex -> hex.owner = leader);
            });

            // recalculate production
            Time.runTask(300f, () -> {
                leader.production = new Production(leader);
                leader.captured().each(hex -> hex.build.create(leader.production));
                leader.slaves().each(human -> human.production = leader.production);
            });

            offers.remove(of -> of.equals(leader, null, 2));

            // TODO вооружение рассинхронизируется среди игроков, нана пофиксить
        });

        menus.put(weaponChoose = 2, (player, option) -> {
            Human human = Human.from(player);
            if (option != -1 && attack(human)) Weapons.from((byte) human.weapons).get(option).attack(human, attacked.get(human));
        });

        menus.put(weaponUnlockChoose = 3, (player, option) -> {
            if (option == -1) return;
            Human human = Human.from(player);
            Weapon weapon = Weapons.from(human.locked()).get(option);
            human.production.unlock(human, weapon);
        });

        guide = Menus.registerMenu(Guide::choose);

        base = Menus.registerMenu((player, option) -> {
            MenuInfo menu = info.get(player);
            if (last.get(player) == option || option == -1) menus.get(menu.id).get(player, option);
            else {
                Call.menu(player.con, base, menu.title, menu.text.get(option), menu.buttons);
                last.put(player, option);
            }
        });
    }

    public static void menu(Player player, int menu, String title, String text, String[][] buttons, Func<Integer, String> func) {
        info.put(player, new MenuInfo(menu, title, buttons, func));
        last.put(player, -1);
        Call.menu(player.con, base, title, text, buttons);
    }

    public static class MenuInfo {
        public final int id;
        public final String title;
        public final String[][] buttons;
        public final Func<Integer, String> text;

        public MenuInfo(int id, String title, String[][] buttons, Func<Integer, String> text) {
            this.id = id;
            this.title = title;
            this.buttons = buttons;
            this.text = text;
        }
    }
}
