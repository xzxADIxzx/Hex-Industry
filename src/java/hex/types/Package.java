package hex.types;

import arc.func.Cons;
import arc.func.Func;
import arc.util.Time;

import static hex.components.Bundle.get;
import static hex.components.Bundle.format;

public class Package {

    public static final float delay = Time.toMinutes;

    public String name;
    public int cost;

    public Cons<Human> cont;
    public Cons<Human> post = human -> {};
    public Func<Human, String> desc;
    public Func<Human, Boolean> cons = human -> human.production.crawler(human, cost);
    public Func<Human, Boolean> pred = human -> true;

    public void send(Human human) {
        if (!cons.get(human)) return;

        msg(human, "shop.pack.in");
        Time.run(delay, () -> got(human));
        post.get(human);
    }

    public void got(Human human) {
        msg(human, "shop.pack.got");
        cont.get(human);
    }

    public void msg(Human human, String key) {
        human.player.sendMessage(format(key, human.locale, get(name + ".name", human.locale)));
    }
}