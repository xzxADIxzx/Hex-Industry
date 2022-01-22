package hex;

import hex.types.*;
import arc.func.*;
import arc.math.*;
import arc.struct.*;
import mindustry.gen.*;

import java.util.Locale;

import static hex.Main.*;
import static hex.components.Bundle.*;
import static hex.components.MenuListener.*;

public class Politics {

	public static Seq<Offer> offers = new Seq<>();

	public static Hex attacked;

	public static void join(Player player) {
		Locale loc = findLocale(player);
		Call.menu(player.con, fractionChooseMenu, get("fract.title", loc), get("fract.text", loc), new String[][] {
				{ get("fract.horde", loc) },
				{ get("fract.engineer", loc) },
				{ get("fract.militant", loc) },
				{ get("fract.spectator", loc) }
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
		find(player, (o, ol) -> {
			attacked = arg.isEmpty() ? o.location() : hexes.get(Integer.valueOf(arg));

			if (attacked.isEmpty() || attacked.owner == o.leader) player.sendMessage(get("attack", ol));
			else Call.menu(player.con, weaponChooseMenu, get("fract.title", ol), "chance to win", new String[][] {
						{ "33%" },
						{ "66%" },
						{ "100%" }
			});
		});
	}

	public static void peace(String arg, Player player) {
		offer(arg, "offer.peace", 0, player, (o, ol, t, tl) -> {});
	}

	public static void join(String arg, Player player) {
		offer(arg, "offer.join", 1, player, (o, ol, t, tl) -> {
			o.team(Generator.team());
			t.team(o.player.team());
			t.production = o.production;
			t.leader = o;

			o.captured().each(hex -> hex.owner = o);
		});
	}

	private static void find(Player player, Cons2<Human, Locale> cons) {
		Locale loc = findLocale(player);
		Human human = Human.from(player);
		if (human == null) player.sendMessage(get("offer.spectator", loc));
		else cons.get(human, loc);
	}

	private static void offer(String arg, String msg, int type, Player player, Cons4<Human, Locale, Human, Locale> cons) {
		find(player, (o, ol) -> {
			Human target = Human.from(arg);
			if (target == null || target == o) player.sendMessage(get("offer.notfound", ol));
			else {
				Locale loc = findLocale(target.player);

				if (offers.contains(of -> of.equals(target, o, type))) {
					player.sendMessage(get("offer.accepted", ol));
					target.player.sendMessage(player.coloredName() + get("offer.accept", loc));

					cons.get(o, ol, target, loc);
					offers.remove(of -> of.equals(target, o, type));
				} else {
					player.sendMessage(get("offer.sent", ol));
					target.player.sendMessage(player.coloredName() + get(msg, loc));

					if (!offers.contains(of -> of.equals(o, target, type))) offers.add(new Offer(o, target, 0));
					else player.sendMessage(get("offer.already", ol));
				}
			}
		});
	}

	public static class Offer {

		public Human offerer;
		public Human target;

		public int type;

		public Offer(Human o, Human t, int ot) {
			type = ot;
			offerer = o;
			target = t;
		}

		public boolean equals(Human o, Human t, int ot) {
			return type == ot && offerer == o && target == t;
		}
	}
}
