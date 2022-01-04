package hex.types;

import arc.func.*;
import mindustry.game.*;
import mindustry.game.Schematic.*;

public class Schem {

	public Stile[] tiles;

	public Schem(Stile[] tiles) {
		this.tiles = tiles;
	}

	public Schem(String base) {
		tiles = Schematics.readBase64(base).tiles.toArray();
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
