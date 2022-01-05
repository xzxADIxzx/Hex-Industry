package hex.types;

import arc.Events;
import arc.struct.ObjectMap;
import hex.Main;
import hex.content.HexBuilds;
import mindustry.content.Blocks;
import mindustry.game.EventType.UnitChangeEvent;
import mindustry.game.Team;
import mindustry.gen.Player;
import mindustry.gen.Unit;

import static mindustry.Vars.world;

public class Human {

    protected static int _id;

    static ObjectMap<Player, Unit> units = new ObjectMap<>();

    static {
        Events.on(UnitChangeEvent.class, event -> {
            if (!Main.initialized) return;

            Unit unit = units.get(event.player);
            if (event.unit != unit)
                event.player.unit(unit);
        });
    }

    public Player player;
    public Hex citadel;
    public Fraction fraction;
    public Production production;

    public Human(Player ppl, Fraction abilities) {
        player = ppl;
        fraction = abilities;
    }

    public void init(Hex hex) {
        player.team(Team.all[_id++]);
        player.unit(fraction.spawn(player.team(), hex.pos()));

        // saves the player's unit
        units.put(player, player.unit());

        world.tile(hex.cx, hex.cy).setNet(Blocks.coreNucleus, player.team(), 0);

        citadel = hex;
        production = new Production(this);

        hex.owner = this;
        hex.build(HexBuilds.citadel);

		hex.open();
		hex.neighbours().each(bour -> bour.build(HexBuilds.miner));
    }

    public void cleanup() {
        Main.hexes.each(hex -> {
            if (hex.owner == this && hex.build != null)
                hex.build.explode(hex);
        });
    }

    public Hex location() {
        return Main.hexes.min(hex -> player.dst2(hex.pos()));
    }
}
