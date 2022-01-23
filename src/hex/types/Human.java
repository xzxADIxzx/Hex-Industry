package hex.types;

import arc.Events;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import hex.Generator;
import hex.content.HexBuilds;
import mindustry.content.Blocks;
import mindustry.game.EventType.UnitChangeEvent;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.gen.Unit;

import java.util.Locale;

import static hex.Main.hexes;
import static hex.Main.humans;
import static hex.components.Bundle.*;
import static mindustry.Vars.world;

public class Human {

    public static ObjectMap<Player, Unit> units = new ObjectMap<>();

    static {
        Events.on(UnitChangeEvent.class, event -> {
            Unit unit = units.get(event.player);
            if (event.unit != unit && unit != null) event.player.unit(unit);
        });
    }

    public Human leader;
    public Locale locale;
    public Player player;
    public Hex citadel;
    public Fraction fraction;
    public Production production;

    public Human(Player player, Fraction fraction) {
        this.player = player;
        this.locale = findLocale(player);
        
        this.leader = this; // for team mechanics
        this.fraction = fraction;

        this.citadel = Generator.citadel(player);
        this.production = new Production(this);

        this.citadel.owner = this;
        this.citadel.build(HexBuilds.citadel);

        this.player.unit(fraction.spawn(player.team(), citadel.pos()));

        units.put(player, player.unit()); // saves the player's unit
    }

    public static Human from(Player player) {
        return humans.find(human -> human.player == player);
    }

    public static Human from(String name) {
        return humans.find(human -> Strings.stripGlyphs(Strings.stripColors(human.player.name)).equalsIgnoreCase(Strings.stripGlyphs(Strings.stripColors(name))));
    }

    public void update() {
        if (leader == this) production.update();
        Hex hex = location();

        Call.setHudText(player.con, format("ui.hud", locale, hex.id, hex.isClosed() ? get("closed", locale) : hex.owner != null ? hex.owner.player.coloredName() : "", production.human(), production.crawler(), production.liquids()));
        hex.neighbours().each(h -> h.buttons.each(b -> b.update(this)));
    }

    public void team(Team team) {
        player.team(team);
        production.team(team);

        captured().each(hex -> Time.runTask(Mathf.random(300f), () -> hex.build.build(hex)));
    }

    public void unit(Fraction fract) {
        fraction = fract;
        Call.unitDespawn(units.put(player, fract.spawn(player.team(), citadel.pos())));
    }

    public void lose() {
        Call.unitDespawn(units.remove(player));
        Call.hideHudText(player.con);

        player.team(Team.derelict);
        world.tile(citadel.point().pack()).setNet(Blocks.air);

        captured().each(hex -> Time.runTask(Mathf.random(300f), hex::clear));

        humans.remove(this);
    }

    public Hex location() {
        return hexes.min(hex -> hex.pos().dst(player));
    }

    public Seq<Hex> captured() {
        return hexes.copy().filter(hex -> hex.owner == this);
    }

    public Seq<Human> slaves() {
        return humans.copy().filter(human -> human.leader == this && human != this);
    }
}
