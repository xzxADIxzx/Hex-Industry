package hex;

import static mindustry.Vars.*;

import hex.types.*;
import arc.math.Mathf;
import arc.math.geom.*;
import mindustry.world.*;
import mindustry.content.*;

public class Generator {

	public static void generate() {
		world.loadGenerator(198, 201, tiles -> tiles.each((x, y) -> {
			tiles.set(x, y, new Tile(x, y, Blocks.air, Blocks.darkMetal, Blocks.air));
		}));

		Point2 start = new Point2();
		Point2 point = new Point2();

		while (true) {
			Main.hexes.add(new Hex(point.x, point.y));
			point.add(38, 0);

			if (!Hex.bounds(point.x, point.y)) {
				start.add(19 * (start.x == 0 ? 1 : -1), 11);
				point.set(start);

				if (!Hex.bounds(start.x, start.y)) break;
			}
		}
	}

	public static Hex citadel() {
		Hex hex = Main.hexes.get(Mathf.random(Main.hexes.size - 1));

		hex.door = (byte) 0x00FFFFFF;
		hex.open();

		world.tile(hex.cx, hex.cy).setNet(Blocks.coreNucleus, player.team(), 0);

		return hex;
	}
}
