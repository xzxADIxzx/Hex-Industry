package hex.content;

import hex.types.*;
import arc.struct.*;
import mindustry.game.Schematic.*;

public class Schems {

	private static Schem[] doors;

	public static Schem
			hex,
			citadel,
			closed;

	public static void load() {
		hex = new Schem(           "bXNjaAF4nDWQWw6CMBBFp60BwsOP8pK6Bd2HezB+oPaDpCIRE12+M72RQA537mkIQ3saDG3m8eHJnPyXirtfb69peU/PmYiSMF59WEmfL4pybsbl+BlD4OrAD+VkBAXFq0SqkLakiO/YKe60IHYJv2tBJ0h5bAQaySC1cjz9K70MMz6rBQpJi5KxElOPtBNYmBamhWlhWpgWZg2lFl1gkFp0HYbx6w2UBsOBf0wLSqASOCzEoXNYiPt3vJAfDVATrA==");
		citadel = new Schem(2, 3,  "bXNjaAF4nE1TS3bTQBBsyfrYHn0dCKfwKTgGj4ViDUQ82TKSHD+23AhWcBAWXCU8Yqq6Y2ChKfX0v7pHXsnNQqJDs/eSvu7mpvW9uNZPu7E7zt1wEJGkb+58P0n45m0t5bFvprk5dKf99tz0vayn0/je2/9mvh/Gq2bbN1BIdhzOftxOw2nceXHd7PdXoXw3ALbHcfjgd/MwSt53H09de9Wn07kZ936U4m7sWiTZDYf21M2S/Z9H1gjjp/umHc5SdocHP86eMQCjpLtPu344MNgRSUYv+fG+mf7FSufphPY7KQZ4tmP34LftADpEPuOTpYQBYCUhpbWEoQSSUgp4SUkvQ4klXABWy4XEl1+SXJ4kgYaqjPbgWcIIkMYiLoghIkCCCxfjOqBFwVAwo32kPzgQOAUkLCYSlwotRYuKEFpok5vps39JKUZpqJ15hBAbOIOM7jH8FApGiaWmO/LQPeGxFmYOHCSkzXCsJGApDtFSePMLaFzRKWEEMXoiADvNUHWKqi+Xy3d8P4T6yqjVTEvWWdCKNt/w/VRRG03kalQx6FI21vhz/xtR/sVmE/BqTfYSwIKsggrHzitATt7WUi7NrLIGawuhcR2PABCYpDS450igXsrYkjt2QMPaDLWOzOrIrI7M6sj+emfq7VSluTPLnVnu3BolnWGOgYD2NQAyL9UhN8vCjsIIe1Q/BOdoeKtUf32muqBLCKXOtDQOS/P8olRzW5zOifrKrHWMlY2x0jGm2rMSVNn4alYaqmm4AkTsurY1qzk4Qsq12nDRU0jOLnODgrlqK6q2lBvb+Q13ntNNbORa7m8jCGJuUBBu6BECYrJ9g1r5liL8OX1mVGVmqI/jBbdzAcA7BXfR5fHyBAxVxd2Rl/bYb+3p314l6P4Ah/Serw==");

		// hex doors
		String[] base = {
				"bXNjaAF4nC2OQQ4CIQxFf4vDjBM9gWdgZTyNcYERN1bHjN4/9hch4f08aAu2SIJs9drsAz1fFPtn+1Yrd1uWtZywu9X1Ud711awcARzANUPFIawGFIm529g8lFC/TpH5RqEzeuxgtcOtpw07JFL+1OAQXpzCAUM0RaZ1k1lKxLyxdx5jvkPjT1O3U3zOQfsDVV4NqA==",
				"bXNjaAF4nC2LTQ5CIQyEpy2gIbrzGqyMpzEuMOJG9Jmn949MkYZ+zfwgYStIvV5b/0DPF8XuVtdHeddX6+WI/bN9ay/3vixrOQE4jI8EfxtAxoQMIWOGUnFX6GIINhEoqWfI5B2D/KnetZk2pjEXIewGz5CWqcTpRt4/nxoNUQ==",
				"bXNjaAF4nEWLQQrCMBBFXzJJlKI7r5GVeBpxETFujFZq708zSanD8B9vmE9gbwgl3XP5Ya83y+GRplf8pk8u8czxnedU4rOM4xQvwIn/mC0UpocdsEoZkGqYlXqnrui3xSmkm6jVBr51hdA6DrfSt64j6Ldjp/DdvNoClHINWQ=="
		};

		doors = new Schem[] {
				new Schem(9, 22,  base[0]),
				new Schem(20, 14, base[1]),
				new Schem(20, 3,  base[2]),
				new Schem(9, 0,   base[0]),
				new Schem(1, 3,   base[1]),
				new Schem(1, 14,  base[2])
		};

		closed = door((byte)-1);
	}

	public static Schem door(byte openned) {
		Seq<Stile> tiles = new Seq<>();

		for (int i = 0; i < doors.length; i++)
			if ((1 << i & openned) == 1 << i) tiles.addAll(doors[i].tiles);

		return new Schem(tiles.toArray());
	}
}
