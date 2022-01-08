package hex.types;

import hex.*;
import hex.content.*;
import arc.func.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.world.*;

import static mindustry.Vars.*;

public class Button {

	public Cons2<Human, Hex> clicked;
	public Hex hex;

	private final int x;
	private final int y;
	private final int fx;
	private final int fy;

	private Seq<Human> cached;

	public Button(Cons2<Human, Hex> clicked, Hex hex) {
		this(clicked, hex, hex.cx, hex.cy);
	}

	public Button(Cons2<Human, Hex> clicked, Hex hex, int x, int y) {
		this.clicked = clicked;
		this.hex = hex;

		this.x = x;
		this.y = y;

		this.fx = x * tilesize;
		this.fy = y * tilesize;

		Schems.button.floorNet(x, y);
		Buttons.register(this);

		if (hex.owner != null) cached = Main.humans.copy().select(h -> h != hex.owner);
	}

	/** show labels for players & etc */
	public void update() {
		if (cached == null) Call.label("build", 60f, fx, fy);
		else {
			Call.label(hex.owner.player.con, "upgrade", 60f, fx, fy);
			cached.each(h -> Call.label(h.player.con, "attack", 60f, fx, fy));
		}
	}

	public void check(Tile tile, Human human) {
		if (bounds(tile.x, tile.y)) clicked.get(human, hex);
	}

	public boolean bounds(int ix, int iy) {
		return ix >= x - 1 && ix <= x + 1 && iy >= y - 1 && iy <= y + 1;
	}
}
