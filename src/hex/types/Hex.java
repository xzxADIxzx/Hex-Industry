package hex.types;

import hex.*;
import mindustry.world.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Hex {
	public static final int width = 27;
	public static final int height = 25;

	public int x;
	public int y;
	public int cx;
	public int cy;

	public boolean openned = false;
	public HexType type;
	public byte door;

	public Hex(int x, int y) {
		this.x = x;
		this.y = y;

		cx = x + width / 2;
		cy = y + height / 2;

		type = HexType.from(world.tile(cx, cy).block());
		door = 1 << 0 | 1 << 2 | 1 << 4;

		// add walls
		Schems.hex.tiles.each(st -> {
			Tile tile = world.tile(st.x + x, st.y + y);
			tile.setFloor(Blocks.darkPanel3.asFloor());
			tile.setBlock(Blocks.darkMetal);
		});

		// close every door
		// TODO: open random doors and save to int like Env.java
		Schems.closed.tiles.each(st -> {
			Tile tile = world.tile(st.x + x + 1, st.y + y);
			tile.setFloor(st.block.asFloor());
			tile.setBlock(Blocks.darkMetal);
		});
	}

	public void open() {
		Schems.closed.tiles.each(st -> world.tile(st.x + x + 1, st.y + y).setNet(Blocks.air));
	}

	public static boolean bounds(int x, int y) {
		return x + width <= world.width() && y + height <= world.height();
	}

	public enum HexType {
		empty(Blocks.air),
		titanium(Blocks.oreTitanium),
		thorium(Blocks.oreThorium),
		oil(Blocks.oreCoal),
		spore(Blocks.sporeCluster);

		private Block id;

		private HexType(Block id) {
			this.id = id;
		}

		public static HexType from(Block id) {
			// java gods forgive me for this
			for (HexType type : HexType.values())
				if (type.id == id)
					return type;
			return empty;
		}
	}
}
