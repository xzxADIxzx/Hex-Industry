package hex.components;

import arc.struct.Seq;
import arc.struct.StringMap;
import mindustry.game.Schematic;
import mindustry.game.Schematics;
import mindustry.game.Schematic.Stile;
import mindustry.world.Block;

import java.nio.charset.StandardCharsets;

import static mindustry.Vars.*;

/**
 * This exists for more compact storage of schematics than via base64,
 * therefore does not support any block configuration.
 * 
 * @author xzxADIxzx
 */
public class ShortSchem {

    /** List of all characters that are used to store integers */
    public static final String symbols = new String( // I know it's a bad idea
            "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwYyXxZz" +
            "ÁáB́b́ĆćD́d́ÉéF́f́ǴǵH́h́ÍíJ́j́ḰḱĹĺḾḿŃńÓóṔṕQ́q́ŔŕŚśT́t́ÚúV́v́ẂẃÝýX́x́Źź" +
            "ÀàB̀b̀C̀c̀  ÈèF̀f̀G̀g̀H̀h̀Ìì  K̀k̀L̀l̀M̀m̀ǸǹÒòP̀p̀  R̀r̀S̀s̀T̀t̀ÙùV̀v̀ẀẁỲỳX̀x̀Z̀z̀" +
            "ÂâB̂b̂ĈĉD̂d̂Êê  ĜĝĤĥÎîĴĵK̂k̂L̂l̂M̂m̂N̂n̂ÔôP̑p̑  R̂r̂ŜŝṰṱÛûV̂v̂ŴŵŶŷX̂x̂Ẑẑ" +
            "ÃãB̃b̃C̃c̃D̃d̃ḚḛF̃f̃G̃g̃  ĨĩJ̃j̃K̃k̃L̃l̃M̃m̃ÑñÕõP̃p̃Q̃q̃R̃r̃S̃s̃T̃t̃ŨũṼṽW̃w̃ỸỹX̃x̃Z̃z̃" +
            "ÄäB̈b̈C̈c̈D̤d̤Ëë  G̈g̈ḦḧÏïJ̈j̈K̈k̈L̈l̈M̈m̈N̈n̈ÖöP̈p̈Q̈q̈R̤r̤S̈s̈T̈ẗÜüV̈v̈ẄẅŸÿẌẍZ̈z̈" +
            "ḀḁB̥b̥C̥c̥D̥d̥E̥e̥  G̥g̥H̥h̥I̥i̥J̥j̥K̥k̥L̥l̥M̥m̥N̥n̥O̥o̥    R̥r̥S̥s̥T̥t̥U̥u̥V̥v̥W̥w̥Y̥y̥X̥x̥Z̥z̥").replaceAll(" ", "");

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

        String result = "#"; // no ways ;-;
        for (Short value : out) result += symbols.charAt(value);
        return result;
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

    /** Yes it is an iterator but it is very necessary */
    public static class Strinter {

        private final String base;
        private int index;

        public Strinter(String base) {
            if (!base.startsWith("#")) throw new RuntimeException("All short schematics start with #");
            this.base = base.replace("#", "");
        }

        public int next() {
            return symbols.indexOf(base.charAt(index++));
        }

        public boolean hasNext() {
            return index < base.length();
        }
    }
}
