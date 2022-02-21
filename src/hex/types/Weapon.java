package hex.types;

public class Weapon {

    protected static int _id;

    public int id;
    public String name;

    public int damage;
    public int cost;
    public Production cons;

    public Weapon() {
        id = 1 << _id++;
    }

    public int format(Human human) {
        return damage * human.fraction.damage;
    }

    public void attack(Human human, Hex hex) {
        if (cons.sour.enough(human.production)) {
            cons.sour.consume(human.production);
            if (hex.damage(format(human))) hex.lose(human.player.coloredName());
        } else human.enough();
    }
}