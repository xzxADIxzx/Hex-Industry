package hex;

import mindustry.game.*;

public class Schems {

	public static Schematic hex,
							doors,
							citadel;

	public static void load() {
		hex = Schematics.readBase64("bXNjaAF4nDWQWw6CMBBFp60BwsOP8pK6Bd2HezB+oPaDpCIRE12+M72RQA537mkIQ3saDG3m8eHJnPyXirtfb69peU/PmYiSMF59WEmfL4pybsbl+BlD4OrAD+VkBAXFq0SqkLakiO/YKe60IHYJv2tBJ0h5bAQaySC1cjz9K70MMz6rBQpJi5KxElOPtBNYmBamhWlhWpgWZg2lFl1gkFp0HYbx6w2UBsOBf0wLSqASOCzEoXNYiPt3vJAfDVATrA==");
		doors = Schematics.readBase64("bXNjaAF4nD2T7VLCMBBFt2n5aCq00DQJD+F7+A6OP1D4wQwCA87oE/jc5u7dUUcP3ZwsaXIjO9nV0lz2n0epX44/0h2Oj4/76fZ1ul5EZH7evx/PD3Gvb058Gdnfnr/357M0h+v1XoTf8iet6I8n1kQvUpXfhZcaXOpoRbcSe1oT5g6e3PjyT5zMMepkgarTDmCrHZ30xQIHT21DbGGVd1KrlpmO1uxVoxewIcwdPRn0e5vySYAG1UY7gHPt2MhWv7fRWdACMQEzzp1hLhCICR2W2CEnhRWqS3xGNVo1sZptl7Ta0m3ptua2dFu4/9uua8fGOs/HSCRWs1Y7uh3dDm6Fx4h1d8WtUc36hk+YXYOV0RmjMRmzcqW+FOqaV1zzSm08JlaznbtnVirCsRqtmohsQSIqRsXpHvR8v57v12MNpTrQHegO5g50B7qDuSPPauRZjTyrkecYOBaYgYAMOJAZCJaBgAw4aIG2nvNk2Zt0lhRq9iZmb2L2JmZv0g4gsxfpRmY+IvMOZOajZT4i8w7ahrb2SnbXks6SQr1riTudeNcS71rSDiDvWqab6Wa6Ge4fa3QgZw==");
		// citadel = Schematics.readBase64("citadel.msch");
	}
}
