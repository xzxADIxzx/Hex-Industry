package hex;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import hex.components.MenuListener;
import hex.content.Fractions;
import hex.content.Weapons;
import hex.types.Hex;
import hex.types.Human;
import hex.types.Weapon;
import mindustry.gen.Player;

import java.util.Locale;

import static hex.Main.hexes;
import static hex.Main.humans;
import static hex.components.Bundle.get;
import static hex.components.Bundle.findLocale;
import static hex.components.MenuListener.*;

public class Politics {

    public static Seq<Offer> offers = new Seq<>();
    public static ObjectMap<Human, Seq<Human>> slaves = new ObjectMap<>();
    public static ObjectMap<Human, Hex> attacked = new ObjectMap<>();
    public static ObjectMap<String, Human> left = new ObjectMap<>();

    public static void join(Player player) {
        Human human = left.get(player.con.uuid);
        if (human != null) {
            human.player = player;
            human.lose.cancel(); // oh yes
            left.remove(player.con.uuid);

            return;
        } else if (hexes.count(Hex::isClosed) == 0) return;

        Locale loc = findLocale(player);
        MenuListener.menu(player, fractionChoose, get("fract.title", loc), get("fract.text", loc),
                Fractions.names(loc, true), option -> Fractions.from(option).desc(loc));
    }

    public static void leave(Player player) {
        Human human = Human.from(player);
        if (human == null) return;

        slaves.get(human).filter(h -> h.leader == human);
        offers.filter(o -> o.from != human && o.to != human);
        left.put(player.con.uuid, human);
        human.lose = Time.runTask(7200f, () -> {
            human.lose();
            left.remove(player.con.uuid);
        });
    }

    public static void spectate(Player player) {
        Human human = Human.from(player);
        if (human == null) join(player);
        else human.lose();
    }

    public static void attack(Hex hex, Human human) {
        attacked.put(human, hex);
        if (Weapon.attackable(human)) MenuListener.menu(human.player, weaponChoose, get("weapon.title", human.locale), get("weapon.text", human.locale),
                Weapons.names(human), option -> Weapons.from(human.weapons).get(option).desc(human));
    }

    public static void join(String arg, Player player) {
        Human from = Human.from(player);
        Human to = Human.from(arg);

        if (from == null) player.sendMessage(get("offer.spectator", findLocale(player)));
        else if (to == null || from == to) player.sendMessage(get("offer.notfound", from.locale));
        else if (humans.contains(h -> h.slaves().contains(to))) player.sendMessage(to.player.coloredName() + get("offer.notleader", from.locale));
        else if (contains(to, from)) { // a bit of code that is hard to understand but I don't care :D
            player.sendMessage(get("offer.accepted", from.locale));
            to.player.sendMessage(player.coloredName() + get("offer.accept", to.locale));

            slaves.get(to).add(from);
            if (offers.count(offer -> offer.from == to) < 2)
                MenuListener.menu(to.player, leaderChoose, get("fract.title", to.locale), get("fract.text", to.locale),
                        Fractions.names(to.locale, false), option -> Fractions.from(option).desc(to.locale));
        } else {
            if (contains(from, to)) player.sendMessage(get("offer.already", from.locale));
            else {
                player.sendMessage(get("offer.sent", from.locale));
                to.player.sendMessage(player.coloredName() + get("offer.join", to.locale));
                offers.add(new Offer(from, to));
            }
        }
    }

    public static boolean contains(Human from, Human to) {
        return offers.contains(offer -> offer.from == from && offer.to == to);
    }

    public static class Offer {

        public Human from;
        public Human to;

        public Offer(Human from, Human to) {
            this.from = from;
            this.to = to;
        }
    }
}
