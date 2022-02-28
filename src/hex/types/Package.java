package hex.types;

import arc.func.Cons;
import arc.func.Func;
import arc.util.Time;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Package {

    // TODO: пакеты вроде что-то стоят но пофакту игрок ничего пока-что не платит
    public static final float delay = 2f * Time.toMinutes;

    public String name;
    public int cost;

    public Cons<Human> cont;
    public Func<Human, String> desc;

    public void send(Human human) {
        human.player.sendMessage(format("shop.pack.in", human.locale, get(name + ".name", human.locale)));
        Time.run(delay, () -> got(human));
    }

    public void got(Human human) {
        human.player.sendMessage(format("shop.pack.got", human.locale, get(name + ".name", human.locale)));
        cont.get(human);
    }
}