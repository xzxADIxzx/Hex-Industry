package hex.content;

import hex.types.*;
import mindustry.content.*;

public class HexBuilds {

	public static HexBuild citadel, miner;

	public static void load() {
		citadel = new HexBuild() {{
			scheme = Schems.citadelMk1;
			boom = Fx.impactReactorExplosion;

			prod = new Production() {{
				human = 20;
			}};

			next = new HexBuild() {{
				scheme = Schems.citadelMk3;
				boom = Fx.impactReactorExplosion;

				prod = new Production() {{
					human = 20;
				}};
				cons = new Production() {{
					titanium = 20;
				}};
			}};
		}};

		miner = new HexBuild() {{
			scheme = Schems.minerMk1;
			boom = Fx.reactorExplosion;

			prod = new Production() {{
				titanium = 1;
			}};
			cons = new Production() {{
				human = 1;
			}};

			next = new HexBuild() {{
				scheme = Schems.minerMk2;
				boom = Fx.reactorExplosion;

				prod = new Production() {{
					titanium = 2;
				}};
				cons = new Production() {{
					titanium = 20;
					human = 1;
				}};
			}};
		}};
	}
}
