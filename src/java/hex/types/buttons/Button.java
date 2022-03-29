package hex.types.buttons;

import arc.func.Cons2;
import arc.math.geom.Geometry;
import hex.Generator;
import hex.content.Buttons;
import hex.content.HexSchematics;
import hex.types.Hex;
import hex.types.Human;
import mindustry.content.Blocks;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

public class Button {

    protected final int x;
    protected final int y;
    protected final int fx;
    protected final int fy;
    public Cons2<Human, Hex> clicked;
    public Hex hex;

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

        terrain();
        Buttons.register(this);
    }

    public void terrain() {
        if (!hex.isCitadel()) {
            HexSchematics.button.floorNet(x, y);
            HexSchematics.button.airNet(x, y);
        } else Geometry.circle(x, y, 3, (x, y) -> Generator.set(x, y, Blocks.darkPanel3, null));
    }

    public void check(Tile tile, Human human) {
        if (bounds(tile.x, tile.y)) clicked.get(human, hex);
    }

    public boolean bounds(int ix, int iy) {
        return ix >= x - 1 && ix <= x + 1 && iy >= y - 1 && iy <= y + 1;
    }

    /** show labels for players & etc */
    public void update(Human human) {}
}
