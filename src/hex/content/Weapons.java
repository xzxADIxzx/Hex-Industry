package hex.content;

import arc.math.Mathf;
import arc.struct.Seq;
import hex.types.Production;
import hex.types.Weapon;

import java.util.Locale;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Weapons {

    public static Weapon standart, crawler, atomic;

    public static void load() {
        standart = new Weapon() {{
            name = "weapon.standart";
            chance = .3f;

            cost = 0;
            cons = new Production() {{
                titanium = 20;
            }};
        }};

        crawler = new Weapon() {{
            name = "weapon.crawler";
            chance = .6f;

            cost = 1;
            cons = new Production() {{
                spore = 20;
            }};
        }};

        atomic = new Weapon() {{
            name = "weapon.atomic";
            chance = 1f;

            cost = 2;
            cons = new Production() {{
                thorium = 20;
            }};
        }};
    }

    public static Weapon from(int id) {
        if (id == -1) id = Mathf.random(2);
        return new Weapon[] {standart, crawler, atomic}[id];
    }

    public static Seq<Weapon> from(byte id) {
        Seq<Weapon> weapons = new Seq<>();

        for (int i = 0; i < 2; i++)
            if ((1 << i & id) == 1 << i) weapons.add(from(i));

        return weapons;
    }

    public static String[][] names(Locale loc, byte id) {
        Seq<Weapon> weapons = from(id);
        String[][] names = new String[weapons.size][1];
        weapons.each(w -> names[w.id][0] = get(w.name + ".name", loc));
        return names;
    }

    public static String desc(Locale loc, byte id, int option, boolean stats) {
        Weapon weapon = from(id).get(option);
        return get(weapon.name + ".desc", loc) + (stats ? format("weapon.stats", loc, weapon.chance) : format("research.stats", loc, weapon.cost));
    }
}
