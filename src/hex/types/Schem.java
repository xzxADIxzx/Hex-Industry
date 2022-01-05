package hex.types;

import arc.func.*;
import mindustry.game.*;
import mindustry.game.Schematic.*;

public class Schem {

	public Stile[] tiles;

	public Schem(Stile[] tiles) {
		this.tiles = tiles;
	}

	// ClassCastException
	public Schem(String base) {
		Schematic scheme = Schematics.readBase64(base);
		tiles = new Stile[scheme.tiles.size];

		for (int i = 0; i < tiles.length; i++)
			tiles[i] = scheme.tiles.get(i);
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
