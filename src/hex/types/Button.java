package hex.types;

import hex.content.*;
import arc.func.*;
import mindustry.world.*;

import static mindustry.Vars.*;

public class Button {

	public Cons2<Human, Hex> clicked;
	public Hex hex;

	protected final int x;
	protected final int y;
	protected final int fx;
	protected final int fy;

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

		Schems.button.floorFix(x, y);
		Buttons.register(this);
	}

	public void check(Tile tile, Human human) {
		if (bounds(tile.x, tile.y)) clicked.get(human, hex);
	}

	public boolean bounds(int ix, int iy) {
		return ix >= x - 1 && ix <= x + 1 && iy >= y - 1 && iy <= y + 1;
	}

	/** show labels for players & etc */
	public void update() {}

	/** called when new player joined */
	public void joined() {}
}
