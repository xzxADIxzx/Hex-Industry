package hex;

import arc.func.Cons2;
import arc.func.Cons4;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Strings;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;
import mindustry.gen.Player;

import java.util.Locale;

import static hex.Main.hexes;
import static hex.components.Bundle.findLocale;
import static hex.components.Bundle.get;
import static hex.components.MenuListener.fractionChooseMenu;
import static hex.components.MenuListener.weaponChooseMenu;

public class Politics {

    public static Seq<Offer> offers = new Seq<>();

    public static Hex attacked;

    public static void join(Player player) {
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
        if (human != null) human.lose();
    }

    public static void spectate(Player player) {
        Human human = Human.from(player);
        if (human == null) join(player);
        else human.lose();
    }

    public static void attack(Player player, int option) {
        if (Mathf.chance((option + 1) / 3f)) attacked.clear();
    }

    public static void attack(String arg, Player player) {
        find(player, (human, locale) -> {
            attacked = arg.isEmpty() ? human.location() : hexes.get(Strings.parseInt(arg, 0));

            if (attacked.isEmpty() || attacked.owner == human.leader) player.sendMessage(get("attack", locale));
            else Call.menu(player.con, weaponChooseMenu, get("fract.title", locale), "chance to win", new String[][] {
                    {"33%"},
                    {"66%"},
                    {"100%"}
            });
        });
    }

    public static void peace(String arg, Player player) {
        offer(arg, "offer.peace", 0, player, (o, ol, t, tl) -> {});
    }

    public static void join(String arg, Player player) {
        offer(arg, "offer.join", 1, player, (human, locale, human1, locale1) -> {
            human.team(Generator.team());
            human1.team(human.player.team());
            human1.production = human.production;
            human1.leader = human;

            human.captured().each(hex -> hex.owner = human);
        });
    }

    private static void find(Player player, Cons2<Human, Locale> cons) {
        Locale loc = findLocale(player);
        Human human = Human.from(player);
        if (human == null) player.sendMessage(get("offer.spectator", loc));
        else cons.get(human, loc);
    }

    private static void offer(String arg, String msg, int type, Player player, Cons4<Human, Locale, Human, Locale> cons) {
        find(player, (human, locale) -> {
            Human target = Human.from(arg);
            if (target == null || target == human) player.sendMessage(get("offer.notfound", locale));
            else {
                Locale loc = findLocale(target.player);

                if (offers.contains(of -> of.equals(target, human, type))) {
                    player.sendMessage(get("offer.accepted", locale));
                    target.player.sendMessage(player.coloredName() + get("offer.accept", loc));

                    cons.get(human, locale, target, loc);
                    offers.remove(of -> of.equals(target, human, type));
                } else {
                    player.sendMessage(get("offer.sent", locale));
                    target.player.sendMessage(player.coloredName() + get(msg, loc));

                    if (!offers.contains(of -> of.equals(human, target, type))) offers.add(new Offer(human, target, type));
                    else player.sendMessage(get("offer.already", locale));
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
