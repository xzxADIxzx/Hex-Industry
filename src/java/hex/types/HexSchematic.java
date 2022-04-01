package hex.types;

import arc.func.Cons;
import arc.struct.Seq;
import hex.Generator;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Schematic.Stile;
import mindustry.gen.Unit;
import mindustry.game.Schematics;

import static mindustry.Vars.schematicBaseStart;
import static mindustry.Vars.world;

public class HexSchematic {

    public Stile[] tiles;

    public HexSchematic() {
        tiles = new Stile[0];
    }

    public HexSchematic(Seq<Stile> scheme) {
        tiles = new Stile[scheme.size];
        for (int i = 0; i < tiles.length; i++) tiles[i] = scheme.get(i);
    }

    public HexSchematic(String base) {
        this(Schematics.readBase64(schematicBaseStart + "F4n" + base).tiles);
    }

    public HexSchematic(int x, int y, String base) {
        this(base);
        each(st -> {
            st.x += x;
            st.y += y;
        });
    }

    public void each(Cons<? super Stile> cons) {
        for (Stile st : tiles) cons.get(st);
    }

    public void floor(int x, int y) {
        each(st -> world.tile(st.x + x, st.y + y).setFloor(st.block.asFloor()));
    }

    public void floorNet(int x, int y) {
        each(st -> Generator.set(st.x + x, st.y + y, st.block, Blocks.air));
    }

    public void airNet(int x, int y) {
        each(st -> Generator.set(st.x + x, st.y + y, Blocks.air));
    }

    public void build(Hex hex) {
        Unit poly = UnitTypes.poly.spawn(hex.owner.player.team(), hex.pos());
        each(st -> poly.addBuild(new BuildPlan(st.x + hex.x, st.y + hex.y, st.rotation, st.block, st.config)));
    }
}
