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

		env = HexEnv.titanium;
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
			if (bour.isClosed()) {
				bour.buttons.add(new Button((h, x) -> x.open(), bour));
				Schems.button.airNet(bour.cx, bour.cy);
			}
		});
	}

	public void clearButtons() {
		buttons.each(Buttons::unregister);
		buttons.clear();

		// removes the button's floor
		env.Lr1.floorNet(x, y);
	}

	public Seq<Hex> neighbours() {
		return Main.hexes.copy().select(hex -> pos().within(hex.pos(), 210f) && world.tile((hex.cx + cx) / 2, (hex.cy + cy) / 2).block() == Blocks.air && hex != this);
	}

	public Position pos() {
		return new Vec2(cx * tilesize, cy * tilesize);
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
		empty(null, null) {
			public void addButtons(Cons3<Cons2<Human, Hex>, Integer, Integer> button) {}
		},
		citadel(Schems.citadelLr1, Schems.citadelLr2) {
			// there is nothing, because the citadel building will add the necessary buttons
			public void addButtons(Cons3<Cons2<Human, Hex>, Integer, Integer> button) {}
		},
		titanium(Schems.titaniumLr1, Schems.titaniumLr2) {
			public void addButtons(Cons3<Cons2<Human, Hex>, Integer, Integer> button) {
				button.get((h, x) -> {
					Lr1.airNet(x.x, x.y);
					// build(plastanium)
				}, 6, 4);
				button.get((h, x) -> x.build(HexBuilds.miner), -6, -4);
			}
		},
		thorium(null, null) {
			public void addButtons(Cons3<Cons2<Human, Hex>, Integer, Integer> button) {}
		},
		oil(null, null) {
			public void addButtons(Cons3<Cons2<Human, Hex>, Integer, Integer> button) {}
		},
		spore(null, null) {
			public void addButtons(Cons3<Cons2<Human, Hex>, Integer, Integer> button) {}
		};

		protected final Schem Lr1;
		protected final Schem Lr2;

		HexEnv(Schem floor, Schem block) {
			Lr1 = floor;
			Lr2 = block;
		}

		// build terrain from schematics
		public void build(Hex hex) {
			hex.clearButtons();

			Lr1.airNet(hex.x, hex.y);
			Lr2.each(st -> {
				Tile tile = world.tile(st.x + hex.x, st.y + hex.y);
				if (st.block instanceof OreBlock) tile.setFloorNet(tile.floor(), st.block.asFloor());
				else tile.setNet(st.block);
			});

			addButtons((build, bx, by) -> hex.buttons.add(new Button((h, x) -> {
				x.owner = h;
				build.get(h, x);
			}, hex, hex.cx + bx, hex.cy + by)));
		}

		public abstract void addButtons(Cons3<Cons2<Human, Hex>, Integer, Integer> button);
	}
}
