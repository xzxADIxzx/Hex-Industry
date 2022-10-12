package hex.types;

import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Timer.Task;
import hex.Generator;
import hex.Politics;
import hex.content.HexBuilds;
import hex.content.Weapons;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import useful.Bundle;

import static hex.Main.*;
import static hex.components.MenuListener.*;

import java.util.Locale;

public class Human {

    public static final ObjectMap<Player, Unit> units = new ObjectMap<>();
    public static final String prefix = "[accent]<[white]\uE872[]>[] ";

    public Human leader;
    public Hex citadel;

    public Player player;
    public Locale locale;

    public Fraction fraction;
    public Production production;

    public Weaponry weaponry = new Weaponry();
    public Statistics stats = new Statistics();

    public Task lose; // oh no
    public Hex attacked;

    private String hudname;
    private String levname;

    public Human(Player player, Fraction fraction) {
        this.leader = this; // for team mechanics
        this.citadel = Generator.citadel(player);

        setPlayer(player);
        setFraction(fraction);
        this.production = new Production(this);

        this.citadel.owner = this;
        this.citadel.build(HexBuilds.citadel);

        menu(player, welcome, "welcome.name", "welcome.text", new String[][] {{ "welcome.open" }});
    }

    public void update() {
        if (leader == this) production.update(this);
        Hex hex = location();

        var core = player.team().core(); // update ItemModule so player can see resources in CoreItemsDisplay
        if (core != null) core.items = production.items;

        hex.neighbours().each(h -> h.update(this)); // shows labels of nearby buttons
        Call.setHudText(player.con, Bundle.format("hud", locale, hex.id,
                hex.owner == null ? Bundle.get(hex.open ? "hex.nobody" : "hex.closed", locale) : hex.owner.hudname,
                hex.health(this), production.unit(), production.crawler(), production.liquids()));
    }

    public void win() {
        if (lose != null) lose.cancel();
        menu(player, over, "over.win.name", "over.win.text", new String[][] {{ Bundle.get("over.stats.name", locale )}}, option -> stats.toString());
    }

    public void lose() {
        Call.unitDespawn(units.remove(player));
        Call.hideHudText(player.con);

        if (lose != null) lose.cancel(); // for safety
        if (citadel.owner == null) // if lose is called from Politics.spectate no need to call lose msg
            menu(player, over, "over.lose.name", "over.lose.text", new String[][] {{ Bundle.get("over.stats.name", locale) }}, option -> stats.toString());

        if (leader == this) { // just saving server resources
            despawnUnits();
            slaves().each(human -> human.citadel.kill());
            captured().each(hex -> Time.run(Mathf.random(300f), () -> hex.clear()));
        }

        player.team(Team.derelict);
        humans.remove(this);

        if (humans.count(h -> h.leader == h) == 1) { // if only one team is alive, the game is over
            humans.each(Human::win); // call win msg
            Bundle.sendToChat("over.new");
            Time.run(720f, Generator::restart); // restarts the game after 12 seconds
        } else if (humans.isEmpty()) Generator.restart(); // so yeah it happens rly often
    }

    public void teamup(Human leader) {
        despawnUnits();
        unoffer();

        player.team(leader.player.team());
        setFraction(leader.fraction);

        captured().each(hex -> {
            hex.owner = leader;
            Time.run(Mathf.random(300f), () -> hex.build(hex.build));
        });

        this.leader = leader;
    }

    public void lead() {
        leaderPrefix();
        Fraction.leader(player.unit());

        Time.run(300f, () -> Generator.onEmpty(() -> { // recalculate production
            production = new Production(this);
            captured().each(hex -> hex.build.create(production));
            slaves().each(human -> {
                human.production = production;
                human.weaponry = weaponry.merge(human.weaponry);
            });
        }));
    }

    // region setters

    public void setPlayer(Player player) {
        this.player = player;
        this.locale = Bundle.locale(player);

        this.hudname = Strings.stripColors(player.name()); // used in hud and search
        this.levname = Strings.stripGlyphs(hudname).toLowerCase().replace(" ", "");
    }

    public void setFraction(Fraction fraction) {
        Unit unit = fraction.spawn(player.team(), this.fraction == null ? citadel : player);

        this.player.unit(unit);
        units.put(player, unit); // saves the player's unit

        this.fraction = fraction;
    }

    public void rejoined(Player player) {
        Unit unit = units.remove(this.player);
        units.put(player, unit);
        
        player.team(unit.team());
        player.unit(unit);

        lose.cancel(); // oh yes
        setPlayer(player);

        if (slaves().any()) leaderPrefix();
    }

    // endregion
    // region getters

    public Hex location() {
        return hexes.min(hex -> hex.dst(player));
    }

    public Seq<Hex> captured() {
        return hexes.select(hex -> hex.owner == this);
    }

    public Seq<Human> slaves() {
        return humans.select(human -> human.leader == this && human != this);
    }

    public int cost(Hex hex) {
        return Mathf.round(leader.citadel.dst(hex) / 1120) + 1;
    }

    public int shops() {
        return leader.captured().count(h -> h.build == HexBuilds.maze);
    }

    public int cities() {
        return leader.captured().count(h -> h.build == HexBuilds.city);
    }

    // endregion
    // region find

    public static Human find(Player player) {
        return humans.find(human -> human.player == player);
    }

    public static Human find(String name) {
        String stripped = Strings.stripGlyphs(Strings.stripColors(name)).toLowerCase();
        return humans.min( // works pretty good but not perfect
                human -> human.levname.contains(stripped) && Strings.levenshtein(human.levname, stripped) < 6,
                human -> Strings.levenshtein(human.levname, stripped));
    }

    public static Human find(Hex citadel) {
        return humans.find(human -> human.citadel == citadel);
    }

    public static Human find(Team team) {
        return humans.find(human -> human.player.team() == team);
    }

    // endregion
    // region other stuffs

    public void enough() {
        Bundle.bundled(player, "enough");
    }

    public void unoffer() {
        Politics.offers.filter(offer -> offer.from() != this && offer.to() != this);
    }

    public void despawnUnits() {
        player.team().data().units.each(Call::unitDespawn);
    }

    public void leaderPrefix() {
        if (!player.name().startsWith(prefix)) player.name(prefix + player.name());
    }

    // endregion

    public class Weaponry {

        public Seq<Weapon> unlocked = Seq.with(Weapons.flare, Weapons.horizon, Weapons.zenith);
        public Seq<Weapon> bought = new Seq<>();

        public void unlock(Weapon weapon) {
            unlocked.addUnique(weapon);
            unlocked.sort(wp -> wp.id);
        }

        public Weaponry merge(Weaponry slave) {
            slave.unlocked.each(unlocked::addUnique);
            slave.bought.each(bought::addUnique);

            unlocked.sort(wp -> wp.id);
            return this;
        }
    }

    public class Statistics {

        public int opened;
        public int builded;
        public int destroyed;
        public int shops;

        public String locked(Weapon weapon) {
            return Bundle.get(weaponry.unlocked.contains(weapon) ? "over.stats.open" : "over.stats.lock", locale);
        }

        public String toString() {
            return Bundle.format("over.stats.text", locale, opened, shops, builded, destroyed, locked(Weapons.crawler), locked(Weapons.atomic));
        }
    }
}
