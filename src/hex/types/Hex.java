package hex.types;

import arc.func.Cons3;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Point2;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import hex.content.Buttons;
import hex.content.HexBuilds;
import hex.content.HexSchematics;
import mindustry.content.Blocks;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import static hex.Main.hexes;
import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class Hex {

    public static final int width = 27;
    public static final int height = 25;
    public static final Rand random = new Rand();
    public static final float basedst = 600f;

    protected static int _id;

    public int x;
    public int y;
    public int cx;
    public int cy;

    public float fx;
    public float fy;

    public Human owner;
    public int id;

    public Seq<Button> buttons = new Seq<>();
    public HexBuild build;
    public boolean building;

    public boolean base;
    public HexEnv env;
    public byte door;

    public Hex(int x, int y) {
        this.x = x;
        this.y = y;

        cx = x + width / 2;
        cy = y + height / 2;

        fx = cx * tilesize;
        fy = cy * tilesize;

        base = (cx - 13) % 57 == 0 && (cy - ((cx - 13) % 114 == 0 ? 12 : 45)) % 66 == 0;
        env = base ? HexEnv.base : HexEnv.get();
        door = (byte) random.nextLong();
        id = _id++;

        HexSchematics.hex.floor(x, y);
        HexSchematics.closed.floor(x, y);
    }

    public static boolean bounds(int x, int y) {
        return x + width > world.width() || y + height > world.height();
    }

    public void build(HexBuild build) {
        build.build(this);
        this.build = build;

        building = true; // cooldown
        Time.runTask(300f, () -> building = false);
    }

    public void open() {
        HexSchematics.door(door).airNet(x, y);
        env.build(this);

        openedNeighbours().each(bour -> {
            if (bour.isClosed()) bour.buttons.add(new OpenButton(bour));
        });
    }

    public void clear() {
        build.explode(this);
        clearButtons(true);

        build = null;
        owner = null;
    }

    public void clearButtons(boolean full) {
        buttons.each(Buttons::unregister);
        buttons.clear();

        if (full) env.build(this);
        else if (build == null || build.next == null) env.terrain(this);
    }

    public Seq<Hex> neighbours() {
        return hexes.copy().select(hex -> pos().within(hex.pos(), 210f));
    }

    public Seq<Hex> openedNeighbours() {
        return neighbours().select(hex -> world.tile((hex.cx + cx) / 2, (hex.cy + cy) / 2).block() == Blocks.air && hex != this);
    }

    public Position pos() {
        return new Vec2(fx, fy);
    }

    public Point2 point() {
        return new Point2(cx, cy);
    }

    public boolean isEmpty() {
        return build == null;
    }

    public boolean isClosed() {
        return world.tile(cx, cy + 3).block() == Blocks.darkMetal;
    }

    public boolean isCaptured(Human owner) {
        return hexes.contains(hex -> hex.base && pos().within(hex.pos(), basedst * Mathf.sqrt(owner.fraction.distance)) && hex.owner == owner.leader);
    }

    public enum HexEnv {
        citadel(0f, HexSchematics.citadelLr1, HexSchematics.citadelLr2) {
            // there is nothing, because the citadel building will add the necessary buttons
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {}
        },
        base(0f, HexSchematics.baseLr1, HexSchematics.baseLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.base, 0, 0);
            }
        },
        titanium(.3f, HexSchematics.titaniumLr1, HexSchematics.titaniumLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.compressor, 4, 4);
                add.get(HexBuilds.miner, -6, -3);
            }
        },
        thorium(.4f, HexSchematics.thoriumLr1, HexSchematics.thoriumLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.thory, 0, 0);
            }
        },
        spore(.2f, HexSchematics.sporeLr1, HexSchematics.sporeLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.spore, -8, -3);
                add.get(HexBuilds.maze, 5, -7);
            }
        },
        oil(.3f, HexSchematics.oilLr1, HexSchematics.oilLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.oil, 7, 2);
            }
        },
        water(.4f, HexSchematics.waterLr1, HexSchematics.waterLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.water, 0, 0);
            }
        },
        cryo(.4f, HexSchematics.cryoLr1, HexSchematics.cryoLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.cryo, -3, -6);
            }
        },
        forest(1f, HexSchematics.forestLr1, HexSchematics.forestLr2) {
            public void addButtons(Cons3<HexBuild, Integer, Integer> add) {
                add.get(HexBuilds.city, -1, 1);
            }
        };

        protected final float frq;
        protected final HexSchematic Lr1;
        protected final HexSchematic Lr2;

        HexEnv(float chance, HexSchematic floor, HexSchematic block) {
            frq = chance;
            Lr1 = floor;
            Lr2 = block;
        }

        public static HexEnv get() {
            for (HexEnv env : values())
                if (Mathf.chance(env.frq)) return env;
            return null; // never happen because the last one has a 100% drop chance
        }

        public void build(Hex hex) {
            hex.clearButtons(false);
            addButtons((build, x, y) -> hex.buttons.add(new BuildButton(build, hex, hex.cx + x, hex.cy + y)));
        }

        /** build terrain from schematics */
        public void terrain(Hex hex) {
            Lr1.floorNet(hex.x, hex.y);
            Lr1.airNet(hex.x, hex.y);

            Lr2.each(st -> {
                Tile tile = world.tile(st.x + hex.x, st.y + hex.y);
                if (st.block instanceof Floor) tile.setFloorNet(tile.floor(), st.block.asFloor());
                else tile.setNet(st.block);
            });
        }

        public abstract void addButtons(Cons3<HexBuild, Integer, Integer> add);
    }
}
