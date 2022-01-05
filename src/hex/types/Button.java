package hex.types;

import hex.content.Buttons;

public class Button {

    public Runnable onClick;
    public Hex hex;

    public void check(int x) {
        if (Buttons.bounds(x, hex.cx))
            onClick.run();
    }
}
