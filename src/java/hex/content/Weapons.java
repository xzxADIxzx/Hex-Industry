package hex.content;

import arc.func.Cons3;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import hex.types.Hex;
import hex.types.Human;
import hex.types.Production;
import hex.types.Weapon;
import mindustry.content.UnitTypes;
import useful.Bundle;

import static hex.Main.*;

public class Weapons {

    public static Weapon flare, horizon, zenith, crawler, atomic;

    public static void load() {
        flare = new Weapon() {{
            name = "weapon.flare";
            damage = 3;

            cost = 0;
            cons = new Production() {{
                titanium = 150;
            }};
            todo = attack(UnitTypes.flare, 12, 18);
        }};

        horizon = new Weapon() {{
            name = "weapon.horizon";
            damage = 6;

            cost = 0;
            cons = new Production() {{
                titanium = 500;
                thorium = 100;
            }};
            todo = attack(UnitTypes.horizon, 8, 14);
        }};

        zenith = new Weapon() {{
            name = "weapon.zenith";
            damage = 9;

            cost = 0;
            cons = new Production() {{
                titanium = 800;
                plastanium = 250;
            }};
            todo = attack(UnitTypes.zenith, 4, 10);
        }};

        crawler = new Weapon() {{
            name = "weapon.crawler";
            damage = 18;

            cost = 1;
            cons = new Production() {{
                plastanium = 1000;
                spore = 100;
            }};
            todo = (human, hex, dmg) -> {
                Vec2 cursor = hex.pos();
                Vec2 dir = hex.owner.citadel.pos().sub(cursor).setLength(200f);

                Cons3<Human, Hex, Integer> cons = attack(UnitTypes.crawler, 10, 16);
                for (int i = 0; i < 6; i++) {
                    Hex attacked = hexes.min(hex1 -> hex1.pos().dst(cursor));
                    cursor.add(dir); // move the cursor in the direction of the core by a distance of ±one hex
                    if (attacked.owner == hex.owner) cons.get(human, attacked, dmg);
                }
            };
        }};

        atomic = new Weapon() {{
            name = "weapon.atomic";
            damage = 36;

            cost = 2;
            cons = new Production() {{
                thorium = 5000;
                plastanium = 2000;
                spore = 200;
            }};
            todo = (human, hex, dmg) -> {
                UnitTypes.quad.spawn(human.player.team(), hex.fx - 200f, hex.fy - 200f);
                hex.neighbours().each(hex1 -> {
                    if (hex1.build != null && hex1.damage(dmg)) Time.run(360f, () -> hex1.lose(human));
                });
            };
        }};
    }

    public static Weapon from(int id) {
        return new Weapon[] {flare, horizon, zenith, crawler, atomic}[id];
    }

    public static Seq<Weapon> from(byte id) {
        Seq<Weapon> weapons = new Seq<>();

        for (int i = 0; i < 5; i++)
            if ((1 << i & id) == 1 << i) weapons.add(from(i));

        return weapons;
    }

    public static String[][] names(Human human) {
        Seq<Weapon> weapons = from(human.weapons);
        String[][] names = new String[weapons.size][1];
        for (int i = 0; i < names.length; i++) names[i][0] = Bundle.get(weapons.get(i).name + ".name", human.locale);
        return names;
    }
}
