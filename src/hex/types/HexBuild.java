package hex.types;

import arc.func.Cons2;
import arc.graphics.Color;
import hex.types.Production.Resource;
import hex.types.buttons.BuildButton;
import mindustry.content.UnitTypes;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Call;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;

public class HexBuild {

    public String name;
    public int health;

    public HexSchematic scheme;
    public Effect boom;

    public Production prod;
    public Production cons;

    public HexBuilp parent = () -> this;
    public HexBuild next;

    public void build(Hex hex) {
        if (hex.build != null) explode(hex); // cleanup old build

        Unit poly = UnitTypes.poly.spawn(hex.owner.player.team(), hex.pos());
        scheme.each(st -> poly.addBuild(new BuildPlan(st.x + hex.x, st.y + hex.y, st.rotation, st.block, st.config)));

        prod.sour.produce(hex.owner.production, true);
        cons.sour.consume(hex.owner.production);

        hex.clearButtons();
        if (next != null) hex.buttons.add(new BuildButton(next, hex));
    }

    public void create(Production production) {
        family((prod, cons) -> {
            prod.produce(production, true);
            cons.unit(production, false);
        });
    }

    public void destroy(Production production) {
        family((prod, cons) -> {
            prod.produce(production, false);
            cons.unit(production, true);
        });
    }

    public void family(Cons2<Resource, Resource> cons) {
        cons.get(prod.sour, this.cons.sour);
        HexBuild cur = parent.get();
        while (cur != this) {
            cons.get(cur.prod.sour, cur.cons.sour);
            cur = cur.next;
        }
    }

    public void explode(Hex hex) {
        Call.effect(boom, hex.fx, hex.fy, 0, Color.white);
        Call.soundAt(Sounds.explosionbig, hex.fx, hex.fy, 1, 1);

        Damage.damage(null, hex.fx, hex.fy, 13 * 8, 1000000, false, true);
    }

    public interface HexBuilp {
        HexBuild get();
    }
}
