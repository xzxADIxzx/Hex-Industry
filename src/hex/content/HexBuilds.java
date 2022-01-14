package hex.content;

import hex.types.*;
import mindustry.content.*;

public class HexBuilds {

	public static HexBuild citadel, miner, thory;

	public static void load() {
		citadel = new HexBuild() {{
			name = "Citadel Mk1";
			scheme = Schems.citadelMk1;
			boom = Fx.impactReactorExplosion;

			prod = new Production() {{
				human = 20;
			}};
			cons = new Production();

			next = new HexBuild() {{
				name = "Citadel Mk2";
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
			name = "Miner Mk1";
			scheme = Schems.minerMk1;
			boom = Fx.reactorExplosion;

			prod = new Production() {{
				titanium = 1;
			}};
			cons = new Production() {{
				human = 1;
			}};

			next = new HexBuild() {{
				name = "Miner Mk2";
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

		thory = new HexBuild() {{
			name = "Miner Mk1";
			scheme = Schems.thoryMk1;
			boom = Fx.reactorExplosion;

			prod = new Production() {{
				thorium = 1;
			}};
			cons = new Production() {{
				titanium = 20;
				human = 1;
			}};

			next = new HexBuild() {{
				name = "Miner Mk2";
				scheme = Schems.thoryMk2;
				boom = Fx.reactorExplosion;

				prod = new Production() {{
					thorium = 2;
				}};
				cons = new Production() {{
					plastanium = 20;
					human = 1;
				}};
			}};
		}};
	}
}
