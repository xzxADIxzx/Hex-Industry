package hex.types;

import arc.Events;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import hex.Generator;
import hex.content.HexBuilds;
import mindustry.game.EventType.UnitChangeEvent;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.world.blocks.storage.CoreBlock.CoreBuild;

import java.util.Locale;

import static hex.Main.hexes;
import static hex.Main.humans;
import static hex.Politics.slaves;
import static hex.components.Bundle.*;
import static mindustry.Vars.world;

public class Human {

    public static ObjectMap<Player, Unit> units = new ObjectMap<>();
    public static String prefix = "[accent]<[white]\uE872[]> ";

    static {
        Events.on(UnitChangeEvent.class, event -> {
            Unit unit = units.get(event.player);
            if (event.unit != unit && unit != null) event.player.unit(unit);
        });
    }

    public Human leader;
    public Player player;
    public Locale locale;
    public Hex citadel;
    public Fraction fraction;
    public Production production;
    public byte weapons;

    public String levname;
    public String hudname;

    public Human(Player player, Fraction fraction) {
        this.player = player;
        this.locale = findLocale(player);

        this.leader = this; // for team mechanics
        this.fraction = fraction;

        this.citadel = Generator.citadel(player);
        this.production = new Production(this);

        this.citadel.owner = this;
        this.citadel.build(HexBuilds.citadel);

        this.hudname = Strings.stripColors(player.name());
        this.levname = Strings.stripGlyphs(hudname).toLowerCase().replace(" ", "");

        this.player.unit(fraction.spawn(player.team(), citadel.pos()));
        this.weapons = 0x1;

        units.put(player, player.unit()); // saves the player's unit
        slaves.put(this, new Seq<>()); // initially the player has no allies
        player.sendMessage(get("welcome", locale)); // some info
    }

    public static Human from(Player player) {
        return humans.find(human -> human.player == player);
    }

    public static Human from(String name) {
        String striped = Strings.stripGlyphs(Strings.stripColors(name)).toLowerCase();
        return humans.min(human -> human.levname.contains(striped) && Strings.levenshtein(human.levname, name) < 6, human -> Strings.levenshtein(human.levname, striped));
    }

    public void update() {
        if (leader == this) production.update();
        Hex hex = location();

        Call.setHudText(player.con, format("hud", locale, hex.id, hex.owner == null ? get(hex.isClosed() ? "hex.closed" : "hex.nobody", locale) : hex.owner.hudname, production.unit(), production.crawler(), production.liquids()));
        hex.neighbours().each(h -> h.update(this));
    }

    public void team(Team team) {
        player.team(team);
        Call.setTeam(core(), team);

        captured().each(hex -> Time.run(Mathf.random(300f), () -> hex.build.build(hex)));
    }

    public void unit(Fraction fract, boolean leader) {
        fraction = fract;
        Call.unitDespawn(units.put(player, fract.spawn(player.team(), player)));

        if (leader) {
            if (!player.name().startsWith(prefix)) player.name(prefix + player.name());
            Fraction.leader(units.get(player));
        } else if (player.name().startsWith(prefix)) player.name(player.name().substring(prefix.length()));
    }

    public void lose() {
        Call.unitDespawn(units.remove(player));
        Call.hideHudText(player.con);

        player.team(Team.derelict);
        core().kill();

        if (leader == this) captured().each(hex -> Time.run(Mathf.random(300f), hex::clear));
        else citadel.clear();

        humans.remove(this);
    }

    public void enough() {
        player.sendMessage(get("enough", locale));
    }

    public void unlock(int id) {
        weapons |= id;
        leader.slaves().each(h -> h.unlock(id));
    }

    public byte locked() {
        return (byte) (~weapons & 0xFF);
    }

    public CoreBuild core() {
        return (CoreBuild) world.build(citadel.cx, citadel.cy);
    }

    public Hex location() {
        return hexes.min(hex -> hex.pos().dst(player));
    }

    public Seq<Hex> captured() {
        return hexes.copy().filter(hex -> hex.owner == this);
    }

    public Seq<Human> slaves() {
        return slaves.get(this);
    }

    public int builds(HexBuild build) {
        return leader.captured().sum(h -> h.build.parent == build.parent ? 1 : 0);
    }

    public int shops() {
        return builds(HexBuilds.maze);
    }

    public int cities() {
        return builds(HexBuilds.city);
    }
}
