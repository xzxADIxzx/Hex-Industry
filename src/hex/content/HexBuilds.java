package hex.content;

import hex.types.*;
import mindustry.content.*;

public class HexBuilds {

	public static HexBuild citadel;

	public static void load() {
		citadel = new HexBuild() {{
			scheme = Schems.citadel;
			boom = Fx.impactReactorExplosion;

			onBuild = prod -> {
				prod.ppl(1);
				prod.pplMax(20);
			};
			onBreak = prod -> {
				prod.ppl(-1);
				prod.pplMax(-20);
			};
		}};
	}
}
