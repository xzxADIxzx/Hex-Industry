package hex.types;

import arc.struct.*;
import mindustry.game.*;
import mindustry.game.Schematic.*;

public class Door {

	public static int id;

	public Seq<Stile> scheme;
	public byte key;

	public Door(String base, int x, int y){
		// very difficult boolean logic to store open doors
		key = (byte)(1 << id++);

		scheme = Schematics.readBase64(base).tiles;
		scheme.each(st -> {
			st.x += x;
			st.y += y;
		});
	}
}