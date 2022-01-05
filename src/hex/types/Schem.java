package hex.types;

import arc.func.Cons;
import arc.struct.Seq;
import mindustry.game.Schematics;
import mindustry.game.Schematic.Stile;

import static mindustry.Vars.*;

public class Schem {

    public Stile[] tiles;

    public Schem(Seq<Stile> scheme) {
        tiles = new Stile[scheme.size];
        for (int i = 0; i < tiles.length; i++)
            tiles[i] = scheme.get(i);
    }

    public Schem(String base) {
        this(Schematics.readBase64(schematicBaseStart + base).tiles);
    }

    public Schem(int x, int y, String base) {
        this(base);
        each(st -> {
            st.x += x;
            st.y += y;
        });
    }

    public void each(Cons<? super Stile> cons) {
        for (Stile st : tiles) cons.get(st);
    }
}
