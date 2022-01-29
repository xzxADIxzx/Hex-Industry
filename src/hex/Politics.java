package hex;

import arc.func.Cons2;
import arc.func.Cons4;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;
import mindustry.gen.Player;

import java.util.Locale;

import static hex.Main.hexes;
import static hex.components.Bundle.findLocale;
import static hex.components.Bundle.get;
import static hex.components.MenuListener.*;

public class Politics {

    public static Seq<Offer> offers = new Seq<>();
    public static ObjectMap<Human, Hex> attacked = new ObjectMap<>();

    public static void join(Player player) {
        if (hexes.count(Hex::isClosed) == 0) return;

        Locale loc = findLocale(player);
        Call.menu(player.con, fractionChooseMenu, get("fract.title", loc), get("fract.text", loc), new String[][] {
                {get("fract.horde", loc)},
                {get("fract.engineer", loc)},
                {get("fract.militant", loc)},
                {get("fract.spectator", loc)}
        });
    }

    public static void leave(Player player) {
        Human human = Human.from(player);
        if (human != null) human.lose(); // TODO: не сразу убивать а дать время на перезаход
    }

    public static void spectate(Player player) {
        Human human = Human.from(player);
        if (human == null) join(player);
        else human.lose();
    }

    public static boolean attack(Human human) {
        Hex hex = attacked.get(human);
        boolean result = hex.isEmpty() || hex.owner == human.leader || hex.owner == human || hex.building;
        if (result) human.player.sendMessage(get("hex.attack", human.locale));
        return !result;
    }

    public static void attack(Hex hex, Human human) {
        attacked.put(human, hex);
        if (attack(human))
            Call.menu(human.player.con, weaponChooseMenu, get("weapon.title", human.locale), get("weapon.text", human.locale), new String[][] {
                    {get("weapon.standart", human.locale)},
                    {get("weapon.crawler", human.locale)},
                    {get("weapon.atomic", human.locale)}
            });
    }

    public static void peace(String arg, Player player) {
        offer(arg, "offer.peace", 0, player, (o, ol, t, tl) -> {});
    }

    public static void join(String arg, Player player) {
        offer(arg, "offer.join", 1, player, (human, locale, leader, locale1) -> {
            human.leader = leader;
            if (offers.contains(of -> of.equals(leader, null, 2))) return;
            offers.add(new Offer(leader, null, 2));
            Call.menu(leader.player.con, leaderFractionChooseMenu, get("fract.title", locale1), get("fract.leader", locale1), new String[][] {
                    {get("fract.horde", locale1)},
                    {get("fract.engineer", locale1)},
                    {get("fract.militant", locale1)}
            });
        });
    }

    private static void find(Player player, Cons2<Human, Locale> cons) {
        Human human = Human.from(player);
        if (human == null) player.sendMessage(get("offer.spectator", findLocale(player)));
        else cons.get(human, human.locale);
    }

    private static void offer(String arg, String msg, int type, Player player, Cons4<Human, Locale, Human, Locale> cons) {
        find(player, (human, locale) -> {
            Human target = Human.from(arg);
            if (target == null || target == human) player.sendMessage(get("offer.notfound", locale));
            else if (target.leader != target) player.sendMessage(player.coloredName() + get("offer.notleader", locale));
            else {
                if (offers.contains(of -> of.equals(target, human, type))) {
                    player.sendMessage(get("offer.accepted", locale));
                    target.player.sendMessage(player.coloredName() + get("offer.accept", target.locale));

                    cons.get(human, locale, target, target.locale);
                    offers.remove(of -> of.equals(target, human, type));
                } else {
                    if (offers.contains(of -> of.equals(human, target, type)))
                        player.sendMessage(get("offer.already", locale));
                    else {
                        player.sendMessage(get("offer.sent", locale));
                        target.player.sendMessage(player.coloredName() + get(msg, target.locale));
                        offers.add(new Offer(human, target, type));
                    }

                }
            }
        });
    }

    public static class Offer {
        public Human offerer;
        public Human target;

        public int type;

        public Offer(Human offerer, Human target, int type) {
            this.offerer = offerer;
            this.target = target;
            this.type = type;
        }

        public boolean equals(Human offerer, Human target, int type) {
            return this.offerer == offerer && this.target == target && this.type == type;
        }
    }
}
