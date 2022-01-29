package hex.content;

import arc.math.Mathf;
import hex.types.Production;
import hex.types.Weapon;

public class Weapons {

    public static Weapon standart, crawler, atomic;

    public static void load() {
        standart = new Weapon() {{
            name = "weapon.standart";
            chance = .3f;

            cons = new Production() {{
                titanium = 20;
            }};
        }};

        crawler = new Weapon() {{
            name = "weapon.crawler";
            chance = .6f;

            cons = new Production() {{
                spore = 20;
            }};
        }};

        atomic = new Weapon() {{
            name = "weapon.atomic";
            chance = 1f;

            cons = new Production() {{
                thorium = 20;
            }};
        }};
    }

    public static Weapon from(int id) {
        if (id == -1) id = Mathf.random(2);
        return new Weapon[] {standart, crawler, atomic}[id];
    }
}
