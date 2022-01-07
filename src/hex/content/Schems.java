package hex.content;

import hex.types.*;
import arc.struct.*;
import mindustry.game.Schematic.*;

public class Schems {

	private static Schem[] doors;
	public static Schem closed;

	public static Schem 
			hex,
			button,
			citadelMk1,
			citadelMk3,
			minerMk1,
			minerMk2,
			titaniumLr1,
			titaniumLr2;

	public static void load() {
		hex = new Schem("DWQzQrCMBCEJwm0oq2H9M/GV7AnH0c8VOzJIqLvD+5kaEn5OrvfUrI4Y3Qo1vmxrD/4292hes7f1/SZ38s6XQFc7MUegTggP5VSrXSEg53cs3F4IvcK+/ZET5RWDoRXCkodx8tNGVjc2awnnJKnsjMlp0HpRESZUWaUGWVGmVFmI6WhTgSlTr1exfz3Vkqr4mgX80Ql1ETSQpJ6SQtJW88W8gc2DQx5");
		button = new Schem(-2, -2, "C2OQQ7CQAhFP9C6mNilx5iV8TTGxRinG9Ga6v0jDATIC+FBwIyZcNB27/oFX2+M5dV/Teuq27bXC46Ptj/rp7271jOAkxWogJyclEHLAiZQNASGOMTCMJWxkhL7wMGBIXFKkpLEJQlJQpKUJn9DnJy0N+QPLwMNbg==");
		citadelMk1 = new Schem(8, 7, "CWNwQ7DIAxDH6AiUA/dj+yLph0YY1IrBBPr/n8kUw52HNthZTX4mh6lfrC3u2N79ZHL9T36UfLZBzH3dqa9lUH4ttrTczK4gJmDQPxzhxPwOItlWWBzohr0GBAVK7qyoEwC6vDiYJbpFiRoJ2iPEUF9Ub79AB8YEbM=");
		citadelMk3 = new Schem(2, 3, "E1TS3bTQBBsyfrYHn0dCKfwKTgGj4ViDUQ82TKSHD+23AhWcBAWXCU8Yqq6Y2ChKfX0v7pHXsnNQqJDs/eSvu7mpvW9uNZPu7E7zt1wEJGkb+58P0n45m0t5bFvprk5dKf99tz0vayn0/je2/9mvh/Gq2bbN1BIdhzOftxOw2nceXHd7PdXoXw3ALbHcfjgd/MwSt53H09de9Wn07kZ936U4m7sWiTZDYf21M2S/Z9H1gjjp/umHc5SdocHP86eMQCjpLtPu344MNgRSUYv+fG+mf7FSufphPY7KQZ4tmP34LftADpEPuOTpYQBYCUhpbWEoQSSUgp4SUkvQ4klXABWy4XEl1+SXJ4kgYaqjPbgWcIIkMYiLoghIkCCCxfjOqBFwVAwo32kPzgQOAUkLCYSlwotRYuKEFpok5vps39JKUZpqJ15hBAbOIOM7jH8FApGiaWmO/LQPeGxFmYOHCSkzXCsJGApDtFSePMLaFzRKWEEMXoiADvNUHWKqi+Xy3d8P4T6yqjVTEvWWdCKNt/w/VRRG03kalQx6FI21vhz/xtR/sVmE/BqTfYSwIKsggrHzitATt7WUi7NrLIGawuhcR2PABCYpDS450igXsrYkjt2QMPaDLWOzOrIrI7M6sj+emfq7VSluTPLnVnu3BolnWGOgYD2NQAyL9UhN8vCjsIIe1Q/BOdoeKtUf32muqBLCKXOtDQOS/P8olRzW5zOifrKrHWMlY2x0jGm2rMSVNn4alYaqmm4AkTsurY1qzk4Qsq12nDRU0jOLnODgrlqK6q2lBvb+Q13ntNNbORa7m8jCGJuUBBu6BECYrJ9g1r5liL8OX1mVGVmqI/jBbdzAcA7BXfR5fHyBAxVxd2Rl/bYb+3p314l6P4Ah/Serw==");
		minerMk1 = new Schem(10, 4, "C2Ra26EMAyEnYSHeW8PsufoIar+SAGpSFmQtixSb99xpiuWz449ZjAyyZuTKsWvNf2I//isROdjv9bf4ynNdq6P+3Vsi5RXfKVTbo91/o77Nsd0X55bSqKvPR1xWdEN3Rm3HaHIO/4SJP8KBiVRETWhREO0REf0xECMxETcDI6xQ+YFV848s8AsMPu3UFjm4AQFDyeQBGQj7aFW4FDosiGypcosBaDnYZ5S8w3rHOCmzBrrrKETw8haNmENGdmNciHKhagtJABqOuWDlDpbjg9ARbTcWK61rLWYKQblGnssBRiIkZiIrOuo6ziz48wOOs+947C3mSWg/Bi9HQ4mKIDWOgdInAG1P8XRH2U=");
		minerMk2 = new Schem(3, 4, "DWS2W6EMAxFHZaQAGGZqfoX80VVH+gMD0gsIwojtV9f31z1AZ3Yvl7iIO/SG7Hz8DXO35J8fDppn+t4LsMx3W+PfZpn6Y/pGNbpXG73bX2NP9sudt/OY9zFneu8DQ895a/hnA/x0zEut9c2PcSr+himVYPl85xf4z796llk0U8KSY0YyUSAnJaVFLaHlSAmipyICalGBUhopURGeIkVDRIySICMsJBkkMCq6Axw5qyZM90y3bK0RXfA0ioIR3iiJCqiJgLREC3RAQX7FbxYgQSgkQTODnCUOK7D8X6OW3KUeEr8/8ais6SzpLPUISLiECWGSBSdmFSHFUkz7R0XWcEGaloBBSrNi04tnWtEEKtjpqJGrEYsRRvEAucMnDNwZ4E7C7gMlF4SLKfUxorYIaCK6NlA2cTXVehDYisWHRqtEp0O3Rt2b3jplgktlECJWItyQEtEZUdlF38OhYOy02IGCER8h57KnjV71uyxLiMXxi6oYhUOeZe4Srly3CtewigqIsbeoPwDf0gt8A==");
		titaniumLr1 = new Schem(2, 3, "DXUSW4jRxBA0aSGnLMmUuoz9RkML9pAA17I8EL3B8yql+ZCH8ViRjxSosKPcL+F+PXrr99f3+Hljz9fwvv337++foe3f/79/g7h9jM8HzlcjyL1/HELSbIUmffamecsiZIkS5Eq80CXceY1vMm7REmSpUiVJl3mlOXM23PqlVd5k3eJkiRLkSpNugyZo1fZzrw/3/2VF3mVN3mXKEmyFKnSpMuQRVaZ+/Yz0a8m2h5tj7ZH26Pt0Wuj7dH2aHu0PdoebY+2R9uj7dH2+P/240yCSBAJIkEkiASRIJIjCSJBJIgEkSASRIJIEAkiQSSI+beaITJEhsgQGSJDZIjsZIbIEBkiQ2SIDJEhMkSGyBDzm1IgCkSBKBAFokAUiAJRDCgQBaJAFIgCUSAKRIEoEBWiQlSIClEhKkSFqBAVokJUcypEhagQFaJCVIh6Is7/BMe1slE0ikbRKBpFo2gUjaJRNIpG0YxrFI2iUTSKRtEo2lR0ik7RKTpFp+gUnaJTdIpO0Sk6RTe1U3SKTtFPxe2ZvSrGwBgYA2NgDIyBMTAGxsAYGANjYAyMYfjAGBgDY0zGmIwFY8FYMBaMBWPBWDAWjAVjwVgwFowFY8FY7FhOxu2Zreo+y7FyrBwrx8qxcqwcK8fKsXKsHCvHyrFyrKfj9szy3HR2ra632X2WZCPZSDaSjWQj2Ug2ko1kI9lItnAN3UK7lm6hz+HD08t8ep0v22b3WZYdYofYIXaIHWKH2CF2iB1ih9gnYr8QZ8d8fpnX67zeZvfrYzysPaw9rD2sPaw9rD2sPaw9wjzepJ9Dj7n0mEuPufS4loZwN/5u/N34u/F34+/G38M80M7z9/mm7nP+/ZofwsOgh0EPgx4GPQx6+Hge8+N5zEmPa1IIH858OPPhzIczH+eZ5+PTzU83P8+b/wGVfiMK");
		titaniumLr2 = new Schem(4, 3, "DWU7W7UMBBFJ87GdrJJnI8tb9EnQvxYpEpU2gKiRTwir8XcOaJSe2LH995xPK59srOz/Lh/fXm8W/r8pbf5x6+X54/Xj/v3199vNr1/uz9env/cHw9//ql38Wz213+tWhJG/emsaNQx2f2fnKwXrtZ3jkXvkmVNJhcEKpMhSAgSgoSgt4smextABgVUloxgkq53eYxmRu7SuUenBFn1wgUMIIMCqnQXeQphdpGZsIAVNK0c2OtgKjkcO+ECBpBBAdU6CSJhIGHwhHi3gBU0rcwkyCNGvcaZPWT2kJnO1JmpM1NnVp3CpnILX6IgL5JrsqiWQknFS4olUUuhlqJahE1FVD0LPQh5RVdJr6RX0ivpI4JRX00I3eiC6KQIGtn0xJJJ79QYB50UX/uK51We6pZ4N7uZ/8w00awmEmYQvTR7QixpTEZJkW2x12SxLhDNsLDDhboXt46VVzCDBcHKqLEyKluobOX8VhJWEvRxAnF+K+e3sofVYwMjmMAVzGBBvjJqyCN2JbYR24htxDZiG8ff+J6NFmukN9Ib6Y30RnojvZHelJ4ckeeNoe+5EbQRtHmQCQPIoIAKRjCBK5gtJccm692teiGu747njueO547njueO547njueO564jEDZNHpgdmB2YHZgdmB2YHZgdbtYJfmGTQ+dpJxf95KKfyE/kJ/IT+Ulj3riIN27SjQa78R/sicknnwxM1v0Dh7shpA==");

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
