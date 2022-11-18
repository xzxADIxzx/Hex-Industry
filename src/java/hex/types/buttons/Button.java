package hex.types.buttons;

import arc.func.Cons2;
import hex.content.Buttons;
import hex.content.HexSchematics;
import hex.types.Hex;
import hex.types.Human;
import mindustry.gen.Player;
import mindustry.gen.WorldLabel;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

public abstract class Button {

    public final int x, y;
    public final WorldLabel label;

    public Cons2<Human, Hex> clicked;
    public Hex hex;

    public Button(Cons2<Human, Hex> clicked, Hex hex, int x, int y, float offsetY) {
        this.x = x;
        this.y = y;

        this.label = new ButtonLabel();
        this.label.set(x * tilesize, y * tilesize + offsetY);

        this.clicked = clicked;
        this.hex = hex;

        if (!hex.isCitadel()) terrain();
        Buttons.register(this);
    }

    public void terrain() {
        HexSchematics.button.floorNet(x, y);
        HexSchematics.button.airNet(x, y);
    }

    public void check(Tile tile, Human human) {
        if (bounds(tile.x, tile.y)) clicked.get(human, hex);
    }

    public boolean bounds(int ix, int iy) {
        return ix >= x - 1 && ix <= x + 1 && iy >= y - 1 && iy <= y + 1;
    }

    public abstract String toString(Human human);

    public class ButtonLabel extends WorldLabel {

        public ButtonLabel() {
            this.add();
        }

        @Override
        public boolean isSyncHidden(Player player) {
            Human human = Human.find(player);
            if (human == null) return true;

            text = Button.this.toString(human);
            return false;
        }
    }
}
