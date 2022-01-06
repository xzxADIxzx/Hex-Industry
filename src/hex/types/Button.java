package hex.types;

import hex.content.*;
import arc.func.*;

public class Button {

	public Cons2<Human, Hex> onClick;
	public Hex hex;

	public int x;
	public int y;

	public Button(Cons2<Human, Hex> onClick, Hex hex, int x, int y) {
		this.onClick = onClick;
		this.hex = hex;
	
		this.x = x;
		this.y = y;

		Buttons.register(this);
	}

	public void check(int x, Human human) {
		if (Buttons.bounds(x, this.x))
			onClick.get(human, hex);
	}
}
