package hex;

import hex.types.*;
import arc.struct.*;
import mindustry.game.*;
import mindustry.game.Schematic.*;

public class Schems {

	private static Door[] doors;

	public static Schematic 
			hex,
			closed,
			citadel;

	public static void load() {
		hex = Schematics.readBase64("bXNjaAF4nDWQWw6CMBBFp60BwsOP8pK6Bd2HezB+oPaDpCIRE12+M72RQA537mkIQ3saDG3m8eHJnPyXirtfb69peU/PmYiSMF59WEmfL4pybsbl+BlD4OrAD+VkBAXFq0SqkLakiO/YKe60IHYJv2tBJ0h5bAQaySC1cjz9K70MMz6rBQpJi5KxElOPtBNYmBamhWlhWpgWZg2lFl1gkFp0HYbx6w2UBsOBf0wLSqASOCzEoXNYiPt3vJAfDVATrA==");

		String[] base = {
				"bXNjaAF4nC2OQQ4CIQxFf4vDjBM9gWdgZTyNcYERN1bHjN4/9hch4f08aAu2SIJs9drsAz1fFPtn+1Yrd1uWtZywu9X1Ud711awcARzANUPFIawGFIm529g8lFC/TpH5RqEzeuxgtcOtpw07JFL+1OAQXpzCAUM0RaZ1k1lKxLyxdx5jvkPjT1O3U3zOQfsDVV4NqA==",
				"bXNjaAF4nC2LTQ5CIQyEpy2gIbrzGqyMpzEuMOJG9Jmn949MkYZ+zfwgYStIvV5b/0DPF8XuVtdHeddX6+WI/bN9ay/3vixrOQE4jI8EfxtAxoQMIWOGUnFX6GIINhEoqWfI5B2D/KnetZk2pjEXIewGz5CWqcTpRt4/nxoNUQ==",
				"bXNjaAF4nEWLQQrCMBBFXzJJlKI7r5GVeBpxETFujFZq708zSanD8B9vmE9gbwgl3XP5Ya83y+GRplf8pk8u8czxnedU4rOM4xQvwIn/mC0UpocdsEoZkGqYlXqnrui3xSmkm6jVBr51hdA6DrfSt64j6Ldjp/DdvNoClHINWQ=="
		};

		doors = new Door[] {
				new Door(base[0], 9, 22),
				new Door(base[1], 20, 14),
				new Door(base[2], 20, 3),
				new Door(base[0], 9, 0),
				new Door(base[1], 1, 3),
				new Door(base[2], 1, 14)
		}; closed = door((byte)-1);
	}

	public static Schematic door(byte openned) {
		Seq<Stile> tiles = new Seq<>();

		for (Door door : doors)
			if ((door.key & openned) == door.key) tiles.addAll(door.scheme);

		return new Schematic(tiles, hex.tags, hex.width, hex.height);
	}
}
