package hex.types;

import arc.func.Cons2;
import arc.func.Prov;
import arc.graphics.Color;
import hex.Generator;
import hex.types.Production.Resource;
import hex.types.buttons.BuildButton;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.Call;
import mindustry.gen.Sounds;

public class HexBuild {

    public String name;
    public int health;

    public HexSchematic scheme;
    public Effect boom;

    public Resource prod;
    public Resource cons;

    public Prov<HexBuild> parent = () -> this;
    public HexBuild next;

    public void build(Hex hex) {
        hex.owner.production.builded(prod, cons);

        hex.clearBuild(); // cleanup old build
        if (next != null) hex.buttons.add(new BuildButton(next, hex));

        Generator.onEmpty(() -> scheme.build(hex));
    }

    public void destroy(Production production) {
        family((prod, cons) -> production.destroyed(prod, cons));
    }

    public void family(Cons2<Resource, Resource> cons) {
        cons.get(this.prod, this.cons);
        HexBuild build = parent.get();
        while (build != this) {
            cons.get(build.prod, build.cons);
            build = build.next;
        }
    }

    public void explode(Hex hex) {
        Call.effect(boom, hex.fx, hex.fy, 0, Color.white);
        Call.soundAt(Sounds.explosionbig, hex.fx, hex.fy, 1, 1);

        Damage.damage(null, hex.fx, hex.fy, 13 * 8, 1000000, false, true);
    }
}
