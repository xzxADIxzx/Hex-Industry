package hex.content;

import hex.types.HexBuild;
import hex.types.Production;

public class HexBuilds {

	public static HexBuild citadel;

	public static void load() {
		citadel = new HexBuild(Schems.citadel) {
			@Override
			public void onBuilded(Production prod) {
				prod.ppl(1);
				prod.pplMax(20);
			}

			@Override
			public void onBreaked(Production prod) {

			}
		};
	}
}
