package hex.content;

import arc.struct.Seq;
import hex.types.Schem;
import mindustry.game.Schematic.Stile;

public class Schems {

	private static Schem[] doors;

	public static Schem 
			hex,
			space,
			closed,
			citadelMk1,
			citadelMk3,
			minerMk1,
			minerMk2;

	public static void load() {
		hex = new Schem("DWRXU7DMBCE17bUVu46SO4fCWfIE+IAnAPxEER4wbRV4f5iZyeNEn2ZnXGya8uT9EFWbfqY26/Et/co+jndvsfrdJ7b+CzlZ/6b2vjVLpfb+CIir/ZIlgRsxS+lKlSdBLHbvWBeBNxb2XsEjsDaygmIVInqgOXre+SE4sbWRiBQRUQ2FnF1onqUgJ9uM6kLi1MlZ3xCreJQLFK0BbPL7JGhwlAxw6cqNBnqllBnoQQoUWgy9IA2EqgLi+9Y5SCVg1QOUjlI5SAVg4jsGNkhDiSqA70ji745e0b2LPbsvee+99aWYeB5DfQGtjzcPTuvfyDuFA4=");
		space = new Schem(2, 3, "DXTS24jZRiG0crtv9+qyg6rYCGsATEIEgMkg5C6W2yfOMd4kGdQqe89UZLtp+182sLt4/c/bt+2519/e9rmP7ePb98//v7zx18///txu23b0y/b5ydtX58s5f7laYuSJMvjWb3neXuTIFGSZCnyeKFJv+dle5U3CRIlSZYiVZo8rox7Xj+vfuVFXuVNgkRJkqVIlSZdHqenrHvePn/6rzzLi7zKmwSJkiRLkSpNugyZ8tjb7wl+NcF6sB6sB+vBevC9wXqwHqwH68F6sB6sB+vBerAe/l8/7okQESJCRIgIESEiRPRKhIgQESJCRIgIESEiRISIEBHi8beaIBJEgkgQCSJBJIjkzQSRIBJEgkgQCSJBJIgEkSAe/ykZIkNkiAyRITJEhsgQ2YEMkSEyRIbIEBkiQ2SIDFEgCkSBKBAFokAUiAJRIApEcadAFIgCUSAKRIEoEAWiQlSIClEhKkSFqBAVokJUiApRnasQFaJCVIgKUSEqRINoEA2iQTSIBtEgGkSDaBANokE0VxtEg2gQDaJBNIgO0SE6RIfoEB2iQ3SIDtEhOkSH6BDd8Q7RITpEh+gQA2JADIgBMSAGxIAYEANiQAyIATEgBsSwMSAGxIAYEBNiQkyICTEhJsSEmBATYkJMiAkxISbEhJimJsSEmBALYkEsiAWxIBbEglgQC2JBLIgFsSAWxIJYEMviglgQu/Xd+m59t75b363v1nfru/Xd+m59t75b363v1nfru/XD3mHvsHfYO+wd9g57h73D3mHvsHfYO+wd9g57p9On06fTp9On06fTp9On06fTp9On0xdXLq5cXLm4cnHl4srFlYsrF1euXrh64eqFqxeuXnj37N2z9/uz/wA0ByOI");
		citadelMk1 = new Schem(8, 7, "CWNwQ7DIAxDH6AiUA/dj+yLph0YY1IrBBPr/n8kUw52HNthZTX4mh6lfrC3u2N79ZHL9T36UfLZBzH3dqa9lUH4ttrTczK4gJmDQPxzhxPwOItlWWBzohr0GBAVK7qyoEwC6vDiYJbpFiRoJ2iPEUF9Ub79AB8YEbM=");
		citadelMk3 = new Schem(2, 3, "E1TS3bTQBBsyfrYHn0dCKfwKTgGj4ViDUQ82TKSHD+23AhWcBAWXCU8Yqq6Y2ChKfX0v7pHXsnNQqJDs/eSvu7mpvW9uNZPu7E7zt1wEJGkb+58P0n45m0t5bFvprk5dKf99tz0vayn0/je2/9mvh/Gq2bbN1BIdhzOftxOw2nceXHd7PdXoXw3ALbHcfjgd/MwSt53H09de9Wn07kZ936U4m7sWiTZDYf21M2S/Z9H1gjjp/umHc5SdocHP86eMQCjpLtPu344MNgRSUYv+fG+mf7FSufphPY7KQZ4tmP34LftADpEPuOTpYQBYCUhpbWEoQSSUgp4SUkvQ4klXABWy4XEl1+SXJ4kgYaqjPbgWcIIkMYiLoghIkCCCxfjOqBFwVAwo32kPzgQOAUkLCYSlwotRYuKEFpok5vps39JKUZpqJ15hBAbOIOM7jH8FApGiaWmO/LQPeGxFmYOHCSkzXCsJGApDtFSePMLaFzRKWEEMXoiADvNUHWKqi+Xy3d8P4T6yqjVTEvWWdCKNt/w/VRRG03kalQx6FI21vhz/xtR/sVmE/BqTfYSwIKsggrHzitATt7WUi7NrLIGawuhcR2PABCYpDS450igXsrYkjt2QMPaDLWOzOrIrI7M6sj+emfq7VSluTPLnVnu3BolnWGOgYD2NQAyL9UhN8vCjsIIe1Q/BOdoeKtUf32muqBLCKXOtDQOS/P8olRzW5zOifrKrHWMlY2x0jGm2rMSVNn4alYaqmm4AkTsurY1qzk4Qsq12nDRU0jOLnODgrlqK6q2lBvb+Q13ntNNbORa7m8jCGJuUBBu6BECYrJ9g1r5liL8OX1mVGVmqI/jBbdzAcA7BXfR5fHyBAxVxd2Rl/bYb+3p314l6P4Ah/Serw==");
		minerMk1 = new Schem(10, 4, "CWRfW6DMAzFTQL5akq7g/Qau8S0PzKKNKQUpJYi7fZ7zguCX2zn2Y6RUT46cbX8zPUl5uvbSZi29Zj/tqfEZZ8ft2Nb7jIc5V13uT7m6besy1Tq7f5capXwXutW7jNOQ7eXZcVW5BOvGGnL4sHqiYFwhCcCEYlEnIhMnImRuBBXRcdCHSyjRQ1rN8vSsmp1aAFbgxaMWAtrZF+I9XAK24tEa2LQJiyQ6WxZXEsIeF4k0Ip60kEnipGx1oRnG54j8ByB5wi8Ri0+QXWehTx1mthYwBGJo2qxyFjUOykC55cxBuBMjMSFaLpEXWLOxJwJOsOBw3nSnHrzwL+Q1ZlV0ANJT2a4OwVi/7ebHxc=");
		minerMk2 = new Schem(3, 4, "DWS2W6DMBBFxyzGBsySVP2LfFHVB5rwgMQSUYjUfn3n+qoP6HjmzuYx8i69ETsPX+P8LcnHp5P2uY7nMhzT/fbYp3mW/piOYZ3O5Xbf1tf4s+1i9+08xl3cuc7b8NBT/hrO+RA/HeNye23TQ7xGH8O0qlg+z/k17tOvnkUW/aSQ1IiRTATIaVlJYXtYCTRR5ERMSFUVIKGVEhnhJVY0SMgQAmSERUiGEFgVnQHOnDVzplumW5a26A5YWgXhCE+UREXURCAaoiU6oGC/ghcrkAA0ksDZAY4hjutwvJ/jlhxDPEP8/8ais6SzpLPUISLiECWGSBSdmFSHFUkz7R0XWcEGaloBBSrNi04tnasi0OqYqaih1dBStIEWOGfgnIE7C9xZwGUQ6SXBckptrIgdAqqIng0im/i6Cn1IbMWiQ6NVotOhe8PuDS/dMqFFJFBCa6l11Lr4OygctE7TDRCIhmiJmNczr2fNnjV7rMvIhdoFNa3CIe8SVylXjnvFSxhFRUTtDZF/f20t9A==");

		String[] base = {
				"C2OQQ4CIQxFf4vDjBM9gWdgZTyNcYERN1bHjN4/9hch4f08aAu2SIJs9drsAz1fFPtn+1Yrd1uWtZywu9X1Ud711awcARzANUPFIawGFIm529g8lFC/TpH5RqEzeuxgtcOtpw07JFL+1OAQXpzCAUM0RaZ1k1lKxLyxdx5jvkPjT1O3U3zOQfsDVV4NqA==",
				"C2LTQ5CIQyEpy2gIbrzGqyMpzEuMOJG9Jmn949MkYZ+zfwgYStIvV5b/0DPF8XuVtdHeddX6+WI/bN9ay/3vixrOQE4jI8EfxtAxoQMIWOGUnFX6GIINhEoqWfI5B2D/KnetZk2pjEXIewGz5CWqcTpRt4/nxoNUQ==",
				"EWLQQrCMBBFXzJJlKI7r5GVeBpxETFujFZq708zSanD8B9vmE9gbwgl3XP5Ya83y+GRplf8pk8u8czxnedU4rOM4xQvwIn/mC0UpocdsEoZkGqYlXqnrui3xSmkm6jVBr51hdA6DrfSt64j6Ldjp/DdvNoClHINWQ=="
		};

		doors = new Schem[] {
				new Schem(9, 22, base[0]),
				new Schem(20, 14, base[1]),
				new Schem(20, 3, base[2]),
				new Schem(9, 0, base[0]),
				new Schem(1, 3, base[1]),
				new Schem(1, 14, base[2])
		};

		closed = door((byte) -1);
	}

	public static Schem door(byte openned) {
		Seq<Stile> tiles = new Seq<>();

		for (int i = 0; i < doors.length; i++)
			if ((1 << i & openned) == 1 << i) tiles.addAll(doors[i].tiles);

		return new Schem(tiles);
	}
}
