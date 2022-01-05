package hex.types;

import hex.content.Buttons;
import arc.func.Cons;
import mindustry.content.Blocks;

import static mindustry.Vars.world;

public class Button {

	public Cons<Human> onClick;
	public Hex hex;
	public int y;

	public Button(Cons<Human> onClick, Hex hex, int y) {
		this.onClick = onClick;
		this.hex = hex;
		this.y = y;

		// temp
		world.tile(hex.cx, y).setBlock(Blocks.daciteWall);
		Buttons.register(this);
	}

	public void check(int x, Human human) {
		if (Buttons.bounds(x, hex.cx))
			onClick.get(human);
	}
}
