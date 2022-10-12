package hex;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import hex.content.Fractions;
import hex.content.Weapons;
import hex.types.Hex;
import hex.types.Human;
import hex.types.Weapon;
import mindustry.gen.Player;
import useful.Bundle;

import static hex.Main.*;
import static hex.components.MenuListener.*;

import java.util.Locale;

public class Politics {

    public static final Seq<Offer> offers = new Seq<>();
    public static final ObjectMap<Human, Hex> attacked = new ObjectMap<>();
    public static final ObjectMap<String, Human> left = new ObjectMap<>();

    public static void join(Player player) {
        player.name(player.name.replace(Human.prefix, ""));
        Human human = left.get(player.con.uuid);
        if (human != null) {
            human.rejoined(player);
            left.remove(player.con.uuid);

            return;
        } else if (hexes.count(Hex::isClosed) == 0) return;

        Locale loc = Bundle.locale(player);
        menu(player, fractionChoose, "fract.title", "fract.text",
                Fractions.names(loc), option -> Fractions.from(option).desc(loc));
    }

    public static void leave(Player player) {
        Human human = Human.find(player);
        if (human == null) return;

        left.put(player.con.uuid, human);
        human.unoffer();
        human.lose = Time.runTask(7200f, () -> {
            human.lose();
            left.remove(player.con.uuid);
        });
    }

    public static void spectate(Player player) {
        Human human = Human.find(player);
        if (human == null) join(player);
        else human.lose();
    }

    public static void attack(Hex hex, Human human) {
        attacked.put(human, hex);
        if (Weapon.attackable(human)) menu(human.player, weaponChoose, "weapon.title", "weapon.text",
                Weapons.names(human), option -> Weapons.from(human).get(option).desc(human));
    }

    public static void join(String[] args, Player player) {
        Human from = Human.find(player); // you can accept last offer without a nickname
        Human to = args.length == 0 ? findLast(from) : Human.find(args[0]);

        if (from == null) Bundle.bundled(player, "offer.spectator");
        else if (to == null || from == to) Bundle.bundled(player, "offer.notfound");
        else if (contains(to, from)) { // a bit of code that is hard to understand, but I don't care :D
            Bundle.bundled(player, "offer.accepted");
            Bundle.bundled(to.player, "offer.accept", player.coloredName());

            to.teamup(from);
            from.lead();
        } else {
            if (from.leader != from || !from.slaves().isEmpty()) Bundle.bundled(player, "offer.notfree");
            else if (to.leader != to) Bundle.bundled(player, "offer.notleader", to.player.coloredName());
            else if (contains(from, to)) Bundle.bundled(player, "offer.already");
            else {
                Bundle.bundled(player, "offer.sent");
                Bundle.bundled(to.player, "offer.join", player.coloredName());
                offers.add(new Offer(from, to));
            }
        }
    }

    private static boolean contains(Human from, Human to) {
        return offers.contains(offer -> offer.from == from && offer.to == to);
    }

    private static Human findLast(Human to) {
        Offer last = offers.reverse().find(offer -> offer.to == to);
        offers.reverse();
        return last == null ? null : last.from;
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
