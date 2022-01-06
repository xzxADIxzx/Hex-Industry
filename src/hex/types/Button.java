package hex.types;

import hex.content.*;
import arc.func.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class Button {

	public Cons<Human> onClick;
	public Hex hex;
	public int y;

	public Button(Cons<Human> onClick, Hex hex, int y) {
		this.onClick = onClick;
		this.hex = hex;
		this.y = y;

		// TODO: add schematic
		// Schems.hex.each(st -> {
			// world.tile(st.x + hex.cx, st.y + hex.cy).setFloorNet(st.block.asFloor());
		// });

		Buttons.register(this);
	}

	public void check(int x, Human human) {
		if (Buttons.bounds(x, hex.cx))
			onClick.get(human);
	}
}
