package hex.types;

import arc.Events;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Timer.Task;
import hex.Generator;
import hex.components.MenuListener;
import hex.content.HexBuilds;
import mindustry.game.EventType.UnitChangeEvent;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.gen.Unit;

import java.util.Locale;

import static hex.Main.hexes;
import static hex.Main.humans;
import static hex.Politics.*;
import static hex.components.Bundle.*;
import static hex.components.MenuListener.statistics;

// TODO: добавить статистику +по статистике считать кол-во людей при присоединении к команде
public class Human {

    public static ObjectMap<Player, Unit> units = new ObjectMap<>();
    public static String prefix = "[accent]<[white]\uE872[]>[] ";

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
    public Task lose; // oh no

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
        player.sendMessage(get("welcome", locale)); // some info
    }

    public static Human from(Player player) {
        return humans.find(human -> human.player == player);
    }

    public static Human from(String name) {
        String striped = Strings.stripGlyphs(Strings.stripColors(name)).toLowerCase();
        return humans.min(human -> human.levname.contains(striped) && Strings.levenshtein(human.levname, name) < 6, human -> Strings.levenshtein(human.levname, striped));
    }

    public static Human from(Hex citadel) {
        return humans.find(human -> human.citadel == citadel);
    }

    public void update() {
        if (leader == this) production.update();
        Hex hex = location();

        Call.setHudText(player.con, format("hud", locale, hex.id, hex.owner == null ? get(hex.isClosed() ? "hex.closed" : "hex.nobody", locale) : hex.owner.hudname, production.unit(), production.crawler(), production.liquids()));
        hex.neighbours().each(h -> h.update(this));
    }

    public void teamup(Human leader) {
        player.team(leader.player.team());
        fraction = leader.fraction;
        Call.unitDespawn(units.put(player, fraction.spawn(player.team(), player)));

        unoffer();
        unlock(leader.weapons);
        captured().each(hex -> {
            hex.owner = leader;
            Time.run(Mathf.random(300f), () -> hex.build(hex.build));
        });

        this.leader = leader;
    }

    public void lead() {
        if (!player.name().startsWith(prefix)) player.name(prefix + player.name());
        Fraction.leader(player.unit());

        Time.run(300f, () -> { // recalculate production
            production = new Production(this);
            updateModule();
            captured().each(hex -> hex.build.create(production));
            slaves().each(human -> human.production = production);
        });
    }

    public void player(Player player) {
        Unit unit = units.remove(this.player);
        units.put(player, unit);
        lose.cancel(); // oh yes

        player.team(unit.team());
        player.unit(unit);
        this.player = player;
    }

    public void lose() {
        Call.unitDespawn(units.remove(player));
        Call.hideHudText(player.con);

        if (citadel.owner == null) // if lose is called from Politics.spectate no need to call lose msg
            MenuListener.menu(player, statistics, get("over.lose.title", locale), get("over.lose.text", locale),
                    new String[][] {{"over.stats.title"}}, option -> get("over.stats.text", locale));

        if (leader == this) { // just saving resources 
            slaves().each(human -> human.citadel.lose(null));
            captured().each(hex -> Time.run(Mathf.random(300f), hex::clear));
            player.team().data().units.each(Call::unitDespawn);
        }

        player.team(Team.derelict);
        humans.remove(this);
    }

    public void enough() {
        player.sendMessage(get("enough", locale));
    }

    public void unlock(int id) {
        leader.weapons |= id;
        leader.slaves().each(h -> h.weapons |= id);
    }

    public void unoffer() {
        offers.filter(offer -> offer.from != this && offer.to != this);
    }

    public void updateModule() {
        player.team().core().items = production.items;
    } // update ItemModule so player can see resources in CoreItemsDisplay

    public byte locked() {
        return (byte) (~weapons & 0xFF);
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

    public int cost(Hex hex) {
        return Mathf.round(leader.citadel.point().dst(hex.point()) / 140) + 1;
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

    public class Statistics {
    
        public int opened;
    }
}
