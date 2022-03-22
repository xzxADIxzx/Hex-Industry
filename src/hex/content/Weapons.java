package hex.content;

import arc.math.Mathf;
import arc.struct.Seq;
import hex.types.Human;
import hex.types.Production;
import hex.types.Weapon;
import mindustry.content.UnitTypes;

import static hex.components.Bundle.get;

public class Weapons {

    public static Weapon flare, horizon, zenith, crawler, atomic;

    public static void load() {
        flare = new Weapon() {{
            name = "weapon.flare";
            damage = 3;

            cost = 0;
            cons = new Production() {{
                titanium = 20;
            }};
            destroy = (human, hex) -> hex.lose(human, UnitTypes.flare, Mathf.random(12, 18));
        }};

        horizon = new Weapon() {{
            name = "weapon.horizon";
            damage = 6;

            cost = 0;
            cons = new Production() {{
                thorium = 20;
            }};
            destroy = (human, hex) -> hex.lose(human, UnitTypes.horizon, Mathf.random(8, 14));
        }};

        zenith = new Weapon() {{
            name = "weapon.zenith";
            damage = 9;

            cost = 0;
            cons = new Production() {{
                plastanium = 20;
            }};
            destroy = (human, hex) -> hex.lose(human, UnitTypes.zenith, Mathf.random(4, 10));
        }};

        crawler = new Weapon() {{
            name = "weapon.crawler";
            damage = 18;

            cost = 1;
            cons = new Production() {{
                spore = 20;
            }};
            destroy = (human, hex) -> hex.lose(human);
        }};

        atomic = new Weapon() {{
            name = "weapon.atomic";
            damage = 36;

            cost = 2;
            cons = new Production() {{
                thorium = 20;
            }};
            destroy = (human, hex) -> hex.lose(human);
        }};
    }

    public static Weapon from(int id) {
        return new Weapon[] {flare, horizon, zenith, crawler, atomic}[id];
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
