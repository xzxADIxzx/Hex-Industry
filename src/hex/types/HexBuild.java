package hex.types;

import arc.graphics.Color;
import mindustry.content.UnitTypes;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Call;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;

public class HexBuild {

    static {
        UnitTypes.poly.defaultController = HexBuilderAI::new;
        UnitTypes.poly.weapons.clear();
    }

    public String name;
    public HexBuild next;
    public HexSchematic scheme;
    public Effect boom;

    public Production prod;
    public Production cons;

    public void build(Hex hex) {
        if (!hex.isEmpty()) explode(hex); // cleanup old build

        Unit poly = UnitTypes.poly.spawn(hex.owner.player.team(), hex.pos());
        scheme.each(st -> poly.addBuild(new BuildPlan(st.x + hex.x, st.y + hex.y, st.rotation, st.block, st.config)));

        prod.sour.produce(hex.owner.production);
        cons.sour.consume(hex.owner.production);

        hex.clearButtons(false);
        if (next != null) hex.buttons.add(new BuildButton(next, hex));
    }

    public void destroy(Hex hex) {
        // TODO: remove prod from human
    }

    public void explode(Hex hex) {
        Call.effect(boom, hex.fx, hex.fy, 0, Color.white);
        Call.soundAt(Sounds.explosionbig, hex.fx, hex.fy, 1, 1);

        Damage.damage(null, hex.fx, hex.fy, 13 * 8, 1000000, false, true);
    }
}
