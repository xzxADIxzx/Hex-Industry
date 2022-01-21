package hex;

import hex.types.*;
import arc.func.*;
import arc.math.*;
import mindustry.gen.*;

import java.util.Locale;

import static hex.Main.*;
import static hex.components.Bundle.*;
import static hex.components.MenuListener.*;

public class Politics {

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
		if (human == null)
			join(player);
		else
			human.lose();
	}

	public static void attack(Player player, int option) {
		if (Mathf.chance(option / 3f)) attacked.clear();
	}

	public static void attack(String arg, Player player) {
		Locale loc = findLocale(player);
		Human human = Human.from(player);

		attacked = arg.isEmpty() ? human.location() : hexes.get(Integer.valueOf(arg));

		if (attacked.isEmpty() || attacked.owner == human)
			player.sendMessage("you can't to attack this hex");
		else
			Call.menu(player.con, weaponChooseMenu, get("fract.title", loc), "chance to win", new String[][] {
					{ "33%" },
					{ "66%" },
					{ "100%" } });
	}

	public static void peace(String arg, Player player) {
		template(arg, player, "offer.peace", (o, t, l) -> {
			o.team(Generator.team());
			t.team(o.player.team());
			t.production = o.production;
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
}
