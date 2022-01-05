package hex.types;

import arc.func.*;
import arc.struct.*;
import mindustry.game.*;
import mindustry.game.Schematic.*;

// I hate the fucking ClassCastException
public class Schem {

	public Stile[] tiles;

	public Schem(Seq<Stile> scheme) {
		tiles = new Stile[scheme.size];
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = scheme.get(i);
	}

	public Schem(String base) {
		this(Schematics.readBase64(base).tiles);
	}

	public Schem(int x, int y, String base) {
		this(base);
		each(st -> {
			st.x += x;
			st.y += y;
		});
	}

	public void each(Cons<? super Stile> consumer) {
		for (Stile st : tiles) consumer.get(st);
	}
}
