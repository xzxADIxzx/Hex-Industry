package hex;

import static mindustry.Vars.*;

import hex.types.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.content.*;

import static hex.Main.*;

public class Generator {

	protected static int last;

	public static void generate() {
		world.loadGenerator(198, 201, tiles -> tiles.each((x, y) -> tiles.set(x, y, new Tile(x, y, Blocks.air, Blocks.air, Blocks.darkMetal))));

		Point2 start = new Point2();
		Point2 point = new Point2();

		while (true) {
			hexes.add(new Hex(point.x, point.y));
			point.add(38, 0);

			if (Hex.bounds(point.x, point.y)) {
				start.add(19 * (start.x == 0 ? 1 : -1), 11);
				point.set(start);

				if (Hex.bounds(start.x, start.y)) break;
			}
		}
	}

	public static Hex citadel(Player player) {
		Hex hex = citadel();

		hex.env = Hex.HexEnv.citadel;
		hex.door = (byte) 0x00FFFFFF;
		hex.open();

		player.team(Team.all[++last]);
		world.tile(hex.cx, hex.cy).setNet(Blocks.coreNucleus, player.team(), 0);

		return hex;
	}

	public static Hex citadel() {
		return hexes.max(h -> humans.sumf(p -> h.point().dst(p.citadel.point())) + Mathf.random(50f));
	}
}
