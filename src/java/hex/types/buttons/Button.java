package hex.types.buttons;

import arc.func.Cons2;
import hex.content.Buttons;
import hex.content.HexSchematics;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Call;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

public abstract class Button {

    public final int x, y;
    public final float fx, fy;

    public Cons2<Human, Hex> clicked;
    public Hex hex;

    public Button(Cons2<Human, Hex> clicked, Hex hex) {
        this(clicked, hex, hex.cx, hex.cy);
    }

    public Button(Cons2<Human, Hex> clicked, Hex hex, int x, int y) {
        this.x = x;
        this.y = y;

        this.fx = x * tilesize;
        this.fy = y * tilesize;

        this.clicked = clicked;
        this.hex = hex;

        terrain();
        Buttons.register(this);
    }

    public void terrain() {
        if (!hex.isCitadel()) {
            HexSchematics.button.floorNet(x, y);
            HexSchematics.button.airNet(x, y);
        }
    }

    public void check(Tile tile, Human human) {
        if (bounds(tile.x, tile.y)) clicked.get(human, hex);
    }

    public boolean bounds(int ix, int iy) {
        return ix >= x - 1 && ix <= x + 1 && iy >= y - 1 && iy <= y + 1;
    }

    /** Show labels for players & etc. */
    public void update(Human human) {
        Call.label(human.player.con, toString(human), 1f, fx, fy);
    }

    public abstract String toString(Human human);
}
