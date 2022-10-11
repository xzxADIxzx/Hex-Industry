package hex.types;

import arc.struct.Seq;
import hex.Generator;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Schematic.Stile;
import mindustry.gen.Unit;
import mindustry.game.Schematics;
import useful.ShortSchematics;

import static mindustry.Vars.*;

public class HexSchematic {

    public Seq<Stile> tiles;

    public HexSchematic() {
        tiles = new Seq<>();
    }

    public HexSchematic(Seq<Stile> tiles) {
        this.tiles = tiles;
    }

    public HexSchematic(String base) {
        this(base.startsWith("#") ? ShortSchematics.read(base).tiles : Schematics.readBase64(schematicBaseStart + "F4n" + base).tiles);
    }

    public HexSchematic(int x, int y, String base) {
        this(base);
        tiles.each(st -> {
            st.x += x;
            st.y += y;
        });
    }

    public void floor(int x, int y) {
        tiles.each(st -> world.tile(st.x + x, st.y + y).setFloor(st.block.asFloor()));
    }

    public void floorNet(int x, int y) {
        tiles.each(st -> Generator.set(st.x + x, st.y + y, st.block, Blocks.air));
    }

    public void airNet(int x, int y) {
        tiles.each(st -> Generator.set(st.x + x, st.y + y, Blocks.air));
    }

    public void build(Hex hex) {
        Unit poly = UnitTypes.poly.spawn(hex.owner.player.team(), hex);
        tiles.each(st -> poly.addBuild(new BuildPlan(st.x + hex.x, st.y + hex.y, st.rotation, st.block, st.config)));
    }
}
