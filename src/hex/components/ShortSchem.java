package hex.components;

import arc.struct.IntSeq;
import arc.struct.Seq;
import arc.struct.StringMap;
import mindustry.game.Schematic;
import mindustry.game.Schematics;
import mindustry.game.Schematic.Stile;
import mindustry.world.Block;

import static mindustry.Vars.content;

/**
 * This exists for more compact storage of schematics than via base64,
 * therefore does not support any block configuration.
 * 
 * @author xzxADIxzx
 */
public class ShortSchem {

    /** needed to skip special unicode characters */
    public static int offset = 35;

    public static String write(String base) {
        return write(Schematics.readBase64(base));
    }

    public static String write(Schematic schem) {
        Seq<Short> out = new Seq<>();
        out.add((short) (schem.width),
                (short) (schem.height));

        schem.tiles.each(st -> {
            out.add(st.block.id, st.x, st.y);
            if (st.block.rotate) out.add((short) st.rotation);
        });

        IntSeq map = out.mapInt(value -> (int) value + offset);
        return new String(map.toArray(), 0, out.size);
    }

    public static Schematic read(String base) {
        return read(new Strinter(base));
    }

    public static Schematic read(Strinter i) {
        Seq<Stile> out = new Seq<>();
        int width = i.next();
        int height = i.next();

        while (i.hasNext()) {
            Block block = content.block(i.next());
            out.add(new Stile(block, i.next(), i.next(), null, (byte) (block.rotate ? i.next() : 0)));
        }

        return new Schematic(out, new StringMap(), width, height);
    }

    /** Yes it is an iterator, but it is very necessary */
    public static class Strinter {

        private final String base;
        private int index;

        public Strinter(String base) {
            this.base = base;
        }

        public int next() {
            return base.codePointAt(index++) - offset;
        }

        public boolean hasNext() {
            return index < base.length();
        }
    }
}
