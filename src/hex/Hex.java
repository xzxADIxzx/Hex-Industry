package hex;

import mindustry.content.Blocks;
import mindustry.world.*;

public class Hex {
	public static final int width = 27;
	public static final int height = 25;

	public int x;
	public int y;

	public boolean openned = false;
	public HexType type;

	public enum HexType {
		empty(Blocks.air),
		titanium(Blocks.oreTitanium),
		thorium(Blocks.oreThorium),
		oil(Blocks.oreCoal),
		apore(Blocks.sporeCluster);

		protected Block id;

		private HexType(Block id) {
			this.id = id;
		}

		public HexType from(Block id){
			// java gods forgive me for this
			for (HexType type : HexType.values())
				if(type.id == id) return type;
			return empty;
		}
	}
}
