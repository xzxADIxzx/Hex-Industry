package hex.content;

import arc.math.Mathf;
import hex.types.Weapon;

public class Weapons {

    public static Weapon standart, crawler, atomic;

    public static void load() {
        standart = new Weapon() {{
            name = "weapon.standart";
            chance = .3f;
        }};
        
        crawler = new Weapon() {{
            name = "weapon.crawler";
            chance = .6f;
        }};
        
        atomic = new Weapon() {{
            name = "weapon.atomic";
            chance = 1f;
        }};
    }

    public static Weapon from(int id) {
        if (id == -1) id = Mathf.random(2);
        return new Weapon[] {standart, crawler, atomic}[id];
    }
}
