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
		template(player.name(), player, "", (o, t, l) -> {
			attacked = arg.isEmpty() ? o.location() : hexes.get(Integer.valueOf(arg));

			if (attacked.isEmpty() || attacked.owner == o) player.sendMessage(get("attack", l));
			else Call.menu(player.con, weaponChooseMenu, get("fract.title", l), "chance to win", new String[][] {
						{ "33%" },
						{ "66%" },
						{ "100%" }
			});
		});
	}

	public static void peace(String arg, Player player) {
		template(arg, player, "offer.peace", (o, t, l) -> {
			if (offers.contains(of -> of.equals(t, o, 0))) {
				o.player.sendMessage("peace");
				t.player.sendMessage("you offer accepted");
			} else offers.add(new Offer(o, t, 0));
		});
	}

	public static void join(String arg, Player player) {
		template(arg, player, "offer.join", (o, t, l) -> {
			o.team(Generator.team());
			t.team(o.player.team());
			t.production = o.production;
		});
	}

	private static void template(String arg, Player player, String msg, Cons3<Human, Human, Locale> cons) {
		Locale loc = findLocale(player);
		Human offer = Human.from(player);
		if (offer == null) player.sendMessage(get("offer.spectator", loc));
		else {
			Human target = Human.from(arg);
			if (target == null) player.sendMessage(get("offer.notfound", loc));
			else {
				player.sendMessage(get("offer.sent", loc));
				target.player.sendMessage(player.coloredName() + " " + get(msg, loc));

				cons.get(offer, target, loc);
			}
		}
	}

	public static class Offer {

		public Human offerer;
		public Human target;

		public int type;

		public Offer(Human o, Human t, int l) {
			type = l;
			offerer = o;
			target = t;
		}

		public boolean equals(Human o, Human t, int l) {
			return type == l && offerer == o && target == t;
		}
	}
}
