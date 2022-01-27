package hex.types;

import arc.func.Cons;
import arc.graphics.Color;
import hex.types.Production.Resource;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Call;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.world.blocks.distribution.MassDriver;

public class HexBuild {

    static {
        UnitTypes.poly.defaultController = HexBuilderAI::new;
        UnitTypes.poly.weapons.clear();

        ((MassDriver) Blocks.massDriver).bullet.damage = 0f;
    }

    public String name;
    public HexBuild parent;
    public HexBuild next;
    public HexSchematic scheme;
    public Effect boom;

    public Production prod;
    public Production cons;

    public void build(Hex hex) {
        if (!hex.isEmpty()) explode(hex); // cleanup old build

        Unit poly = UnitTypes.poly.spawn(hex.owner.player.team(), hex.pos());
        scheme.each(st -> poly.addBuild(new BuildPlan(st.x + hex.x, st.y + hex.y, st.rotation, st.block, st.config)));

        prod.sour.produce(hex.owner.production, true);
        cons.sour.consume(hex.owner.production);

        hex.clearButtons(false);
        if (next != null) hex.buttons.add(new BuildButton(next, hex));
    }

    public void create(Production production) {
        family(sour -> {
            sour.produce(production, true);
            sour.human(production, false);
        });
    }

    public void destroy(Production production) {
        family(sour -> {
            sour.produce(production, false);
            sour.human(production, true);
        });
    }

    public void family(Cons<Resource> cons) {
        cons.get(prod.sour);
        HexBuild cur = parent;
        while (cur != null && cur != this) {
            cons.get(cur.prod.sour);
            cur = cur.next;
        }
    }

    public void explode(Hex hex) {
        Call.effect(boom, hex.fx, hex.fy, 0, Color.white);
        Call.soundAt(Sounds.explosionbig, hex.fx, hex.fy, 1, 1);

        Damage.damage(null, hex.fx, hex.fy, 13 * 8, 1000000, false, true);
    }
}
