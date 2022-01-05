package hex.content;

import hex.types.*;
import mindustry.content.*;

public class HexBuilds {

	public static HexBuild
			citadel,
			miner;

	public static void load() {
		citadel = new HexBuild() {{
			scheme = Schems.citadel1;
			boom = Fx.impactReactorExplosion;

			onBuild = prod -> {
				prod.ppl(1);
				prod.pplMax(20);
			};
			onBreak = prod -> {
				prod.ppl(-1);
				prod.pplMax(-20);
			};

			next = new HexBuild() {{
				scheme = Schems.citadel3;
				boom = Fx.impactReactorExplosion;

				onBuild = prod -> {
					prod.ppl(1);
					prod.pplMax(20);
				};
				onBreak = prod -> {
					prod.ppl(-2);
					prod.pplMax(-40);
				};
			}};
		}};

		miner = new HexBuild() {{
			scheme = Schems.miner1;
			boom = Fx.reactorExplosion;

			onBuild = prod -> {
				prod.titanium(10);
			};
			onBreak = prod -> {
				prod.titanium(-10);
			};

			next = new HexBuild() {{
				scheme = Schems.miner2;
				boom = Fx.reactorExplosion;

				onBuild = prod -> {
					prod.titanium(20);
				};
				onBreak = prod -> {
					prod.titanium(-30);
				};
			}};
		}};
	}
}
