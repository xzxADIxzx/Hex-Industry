package hex;

import arc.Events;
import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import arc.util.Timer;
import hex.components.MenuListener;
import hex.content.*;
import hex.types.Hex;
import hex.types.Human;
import hex.types.ai.*;
import hex.Generator.MapSize;
import mindustry.game.EventType.*;
import mindustry.game.Rules.TeamRule;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.core.UI;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.mod.Plugin;
import mindustry.net.Administration;
import useful.Bundle;

import static mindustry.Vars.*;

public class Main extends Plugin {

    public static final Seq<Hex> hexes = new Seq<>();
    public static final Seq<Human> humans = new Seq<>();
    public static final Rules rules = new Rules();

    @Override
    public void init() {
        Bundle.load(Main.class);
        HexSchematics.load();
        Fractions.load();
        HexBuilds.load();
        Packages.load();
        Weapons.load();
        Buttons.load();
        MenuListener.load();

        netServer.admins.actionFilters.clear().add(action -> false);
        netServer.assigner = (player, players) -> Team.derelict;

        UI.thousands = "k";
        UI.millions = "m";

        Blocks.unloader.solid = false; // why?
        Blocks.groundFactory.solid = false;

        UnitTypes.crawler.controller = unit -> new HexSuicideAI();
        UnitTypes.mono.controller = unit -> new HexMinerAI();
        UnitTypes.poly.controller = unit -> new HexBuilderAI();
        UnitTypes.mega.controller = unit -> new HexFlyingAI();
        UnitTypes.quad.controller = unit -> new HexAtomicAI();
        UnitTypes.flare.controller = unit -> new HexFlyingAI();
        UnitTypes.horizon.controller = unit -> new HexFlyingAI();
        UnitTypes.zenith.controller = unit -> new HexFlyingAI();
        UnitTypes.alpha.controller = unit -> new HexEmptyAI();
        UnitTypes.beta.controller = unit -> new HexEmptyAI();
        UnitTypes.gamma.controller = unit -> new HexEmptyAI();

        rules.enemyCoreBuildRadius = 0f;
        rules.unitCap = 16;
        rules.infiniteResources = true;
        rules.damageExplosions = false;
        rules.fire = false;
        rules.waves = false;
        rules.canGameOver = false;
        rules.ghostBlocks = false;
        rules.revealedBlocks.add(Blocks.radar);
        rules.modeName = "Hex Industry";

        for (Team team : Team.all) {
            TeamRule rule = rules.teams.get(team);
            rule.cheat = true; // just for visual
            rule.unitDamageMultiplier = rule.blockDamageMultiplier = 0f;
        }

        Timer.schedule(() -> {
            humans.each(Human::update);
            Buttons.update();
        }, 0f, 1f);
        Timer.schedule(Generator::update, 0f, 0.02f);

        Events.on(PlayerJoin.class, event -> Politics.join(event.player));
        Events.on(PlayerLeave.class, event -> Politics.leave(event.player));

        Events.on(UnitChangeEvent.class, event -> {
            Unit unit = Human.units.get(event.player);
            if (event.unit != unit && unit != null) event.player.unit(unit);
        });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("guide", "Manual with info about mechanics.", (args, player) -> Guide.show(player));

        handler.<Player>register("join", "[player]", "Offer the player to team up.", (args, player) -> Politics.join(args, player));

        handler.<Player>register("spectate", "Watching the game is fun too!", (args, player) -> Politics.spectate(player));

        handler.<Player>register("author", "Plugin creators.", (args, player) -> Bundle.bundled(player, "author"));
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("host", "<size>", "Initialize new game.", args -> {
            MapSize size = MapSize.get(args[0]);
            if (size == null) Log.err("Can't find a MapSize with provided name.");
            else {
                Generator.clear();
                Generator.play(size);
                state.rules = rules;

                logic.play();
                netServer.openServer();
            }
        });

        handler.register("hack", "<human>", "Cheating is bad, but sometimes.", args -> {
            Human human = Human.find(args[0]);
            if (human == null) Log.err("Can't find a Human with provided name.");
            else human.hack();
        });

        handler.register("openall", "Open all hexes.", args -> hexes.each(Hex::isClosed, Hex::open));

        handler.register("strictoff", "DO NOT USE THIS ONLY FOR TESTING!", args -> {
            netServer.admins.actionFilters.clear();
            Administration.Config.strict.set(false);
        });
    }
}
