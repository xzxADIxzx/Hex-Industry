package hex.types;

import hex.*;
import hex.content.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.world.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Hex {

	protected static int _id;

	public static final int width = 27;
	public static final int height = 25;
	public static final Rand random = new Rand();

	public int x;
	public int y;
	public int cx;
	public int cy;

	public Human owner;
	public int id;
	
	public Seq<Button> buttons = new Seq<>();
	public HexBuild build;

	public HexType type;
	public byte door;


	public Hex(int x, int y) {
		this.x = x;
		this.y = y;

		cx = x + width / 2;
		cy = y + height / 2;

		type = HexType.from(world.tile(cx, cy).block());
		door = (byte) random.nextLong();
		id = _id++;

		Schems.hex.floor(x, y);
		Schems.closed.floor(x, y);
	}

	public void build(HexBuild building) {
		building.build(this);
		build = building;
	}

	// TEMP TODO: move buttons & Schems.space to Type.build
	public void open() {
		Schems.door(door).each(st -> world.tile(st.x + x, st.y + y).setNet(Blocks.air));
		Schems.space.each(st -> world.tile(st.x + x, st.y + y).setNet(Blocks.air));
		type.build(this);

		clearButtons();
		buttons.add(new Button((h, x) -> {
			x.owner = h;
			x.build(HexBuilds.miner);
		}, this, cx, cy));

		neighbours().each(bour -> {
			if (bour.isClosed())
				bour.buttons.add(new Button((h, x) -> x.open(), bour, bour.cx, bour.cy));
		});
	}

	public void clearButtons() {
		buttons.each(btn -> Buttons.unregister(btn));
		buttons.clear();
	}

	public Position pos() {
		return new Vec2(cx * tilesize, cy * tilesize);
	}

	public Seq<Hex> neighbours() {
		return Main.hexes.copy().select(hex -> {
			return pos().within(hex.pos(), 210f) && world.tile((hex.cx + cx) / 2, (hex.cy + cy) / 2).block() == Blocks.air && hex != this;
		});
	}

	public boolean isEmpty() {
		return build == null;
	}

	public boolean isClosed(){
		return world.tile(cx, cy + 3).solid();
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

		// build terrain from schematics
		public void build(Hex hex) {}

		public static HexType from(Block id) {
			// java gods forgive me for this
			for (HexType type : HexType.values())
				if (type.id == id)
					return type;
			return empty;
		}
	}
}
