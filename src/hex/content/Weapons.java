package hex.content;

import arc.struct.Seq;
import hex.types.Human;
import hex.types.Production;
import hex.types.Weapon;

import static hex.components.Bundle.get;

public class Weapons {

    public static Weapon standart, crawler, atomic;

    public static void load() {
        standart = new Weapon() {{
            name = "weapon.standart";
            damage = 9;

            cost = 0;
            cons = new Production() {{
                titanium = 20;
            }};
        }};

        crawler = new Weapon() {{
            name = "weapon.crawler";
            damage = 18;

            cost = 1;
            cons = new Production() {{
                spore = 20;
            }};
        }};

        atomic = new Weapon() {{
            name = "weapon.atomic";
            damage = 36;

            cost = 2;
            cons = new Production() {{
                thorium = 20;
            }};
        }};
    }

    public static Weapon from(int id) {
        return new Weapon[] {standart, crawler, atomic}[id];
    }

    public static Seq<Weapon> from(byte id) {
        Seq<Weapon> weapons = new Seq<>();

        for (int i = 0; i < 3; i++)
            if ((1 << i & id) == 1 << i) weapons.add(from(i));

        return weapons;
    }

    public static String[][] names(Human human) {
        Seq<Weapon> weapons = from(human.weapons);
        String[][] names = new String[weapons.size][1];
        for (int i = 0; i < names.length; i++) names[i][0] = get(weapons.get(i).name + ".name", human.locale);
        return names;
    }
}
