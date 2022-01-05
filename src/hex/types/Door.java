package hex.types;

import arc.struct.Seq;
import mindustry.game.Schematic.Stile;
import mindustry.game.Schematics;

public class Door {

    protected static int _id;

    public Seq<Stile> scheme;
    public byte key;

    public Door(String base, int x, int y) {
        // very difficult boolean logic to store open doors
        key = (byte) (1 << _id++);

        scheme = Schematics.readBase64(base).tiles;
        scheme.each(st -> {
            st.x += x;
            st.y += y;
        });
    }
}