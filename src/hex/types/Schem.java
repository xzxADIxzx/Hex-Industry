package hex.types;

import arc.func.*;
import arc.struct.*;
import mindustry.game.*;
import mindustry.game.Schematic.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Schem {

	public Stile[] tiles;

	public Schem() {}

	public Schem(Seq<Stile> scheme) {
		tiles = new Stile[scheme.size];
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = scheme.get(i);
	}

	public Schem(String base) {
		this(Schematics.readBase64(schematicBaseStart + "F4n" + base).tiles);
	}

	public Schem(int x, int y, String base) {
		this(base);
		each(st -> {
			st.x += x;
			st.y += y;
		});
	}

	public void each(Cons<? super Stile> cons) {
		for (Stile st : tiles)
			cons.get(st);
	}

	public void floor(int x, int y) {
		each(st -> world.tile(st.x + x, st.y + y).setFloor(st.block.asFloor()));
	}

	public void floorNet(int x, int y) {
		each(st -> world.tile(st.x + x, st.y + y).setFloorNet(st.block.asFloor()));
	}

	public void air(int x, int y) {
		each(st -> world.tile(st.x + x, st.y + y).setAir());
	}

	public void airNet(int x, int y) {
		each(st -> world.tile(st.x + x, st.y + y).setNet(Blocks.air));
	}
}
