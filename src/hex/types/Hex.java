package hex.types;

import hex.*;
import hex.content.*;
import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
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

	public float fx;
	public float fy;
	public float lx;
	public float ly;

	public Human owner;
	public int id;

	public Seq<Button> buttons = new Seq<>();
	public HexBuild build;

	public HexEnv env;
	public byte door;

	public Hex(int x, int y) {
		this.x = x;
		this.y = y;

		cx = x + width / 2;
		cy = y + height / 2;

		fx = cx * tilesize;
		fy = cy * tilesize;

		lx = (x + 3.5f) * tilesize;
		ly = (y + 6.5f) * tilesize;

		env = HexEnv.get();
		door = (byte) random.nextLong();
		id = _id++;

		Schems.hex.floor(x, y);
		Schems.closed.floor(x, y);
	}

	public void build(HexBuild building) {
		building.build(this);
		build = building;
	}

	public void open() {
		Schems.door(door).airNet(x, y);
		env.build(this);

		neighbours().each(bour -> {
			if (bour.isClosed()) bour.buttons.add(new Button((h, x) -> x.open(), bour));
		});
	}

	public void clear() {
		build.explode(this);
		clearButtons(true);

		build = null;
		owner = null;
	}

	public void clearButtons(boolean full) {
		buttons.each(Buttons::unregister);
		buttons.clear();

		if (full) env.build(this);
		else env.terrain(this);
	}

	public Seq<Hex> neighbours() {
		return Main.hexes.copy().select(hex -> pos().within(hex.pos(), 210f) && world.tile((hex.cx + cx) / 2, (hex.cy + cy) / 2).block() == Blocks.air && hex != this);
	}

	public Position pos() {
		return new Vec2(fx, fy);
	}

	public Point2 point() {
		return new Point2(cx, cy);
	}

	public boolean isEmpty() {
		return build == null;
	}

	public boolean isClosed() {
		return world.tile(cx, cy + 3).block() == Blocks.darkMetal;
	}

	public static boolean bounds(int x, int y) {
		return x + width > world.width() || y + height > world.height();
	}

	public enum HexEnv {
		citadel(0f, Schems.citadelLr1, Schems.citadelLr2) {
			// there is nothing, because the citadel building will add the necessary buttons
			public void addButtons(Cons3<HexBuild, Integer, Integer> add) {}
		},
		titanium(.4f, Schems.titaniumLr1, Schems.titaniumLr2) {
			public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
				add.get(HexBuilds.compressor, 4, 4);
				add.get(HexBuilds.miner, -6, -3);
			}
		},
		thorium(.4f, Schems.thoriumLr1, Schems.thoriumLr2) {
			public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
				add.get(HexBuilds.thory, 0, 0);
			}
		},
		spore(0f, null, null) {
			public void addButtons(Cons3<HexBuild, Integer, Integer> add) {}
		},
		oil(.2f, Schems.oilLr1, Schems.oilLr2) {
			public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
				add.get(HexBuilds.oil, 7, 2);
			}
		},
		water(0f, null, null) {
			public void addButtons(Cons3<HexBuild, Integer, Integer> add) {}
		},
		cryo(1f, Schems.cryoLr1, Schems.cryoLr2) {
			public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
				add.get(HexBuilds.cryo, -3, -6);
			}
		};

		protected final float frq;
		protected final Schem Lr1;
		protected final Schem Lr2;

		HexEnv(float chance, Schem floor, Schem block) {
			frq = chance;
			Lr1 = floor;
			Lr2 = block;
		}

		public void build(Hex hex) {
			hex.clearButtons(false);
			addButtons((build, x, y) -> hex.buttons.add(new BuildButton(build, hex, hex.cx + x, hex.cy + y)));
		}

		/** build terrain from schematics */
		public void terrain(Hex hex) {
			Lr1.floorNet(hex.x, hex.y);
			Lr1.airNet(hex.x, hex.y);

			Lr2.each(st -> {
				Tile tile = world.tile(st.x + hex.x, st.y + hex.y);
				if (st.block instanceof OreBlock) tile.setFloorNet(tile.floor(), st.block.asFloor());
				else tile.setNet(st.block);
			});
		}

		public static HexEnv get() {
			for (HexEnv env : values())
				if (Mathf.chance(env.frq)) return env;
			return null; // never happen because the last one has a 100% drop chance
		}

		public abstract void addButtons(Cons3<HexBuild, Integer, Integer> add);
	}
}
