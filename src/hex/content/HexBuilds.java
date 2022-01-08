package hex.content;

import hex.types.*;
import mindustry.content.*;

public class HexBuilds {

	public static HexBuild
			citadel,
			miner;

	public static void load() {
		citadel = new HexBuild() {{
			scheme = Schems.citadelMk1;
			boom = Fx.impactReactorExplosion;

			cons = prod -> {
				prod.ppl(1);
				prod.pplMax(20);
			};

			next = new HexBuild() {{
				scheme = Schems.citadelMk3;
				boom = Fx.impactReactorExplosion;

				cons = prod -> {
					prod.ppl(1);
					prod.pplMax(20);
				};
			}};
		}};

		miner = new HexBuild() {{
			scheme = Schems.minerMk1;
			boom = Fx.reactorExplosion;

			cons = prod -> prod.titaniumProd(10);

			next = new HexBuild() {{
				scheme = Schems.minerMk2;
				boom = Fx.reactorExplosion;

				cons = prod -> prod.titaniumProd(10);
			}};
		}};
	}
}
