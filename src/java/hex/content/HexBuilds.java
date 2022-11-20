package hex.content;

import hex.types.Hex;
import hex.types.HexBuild;
import hex.types.Production.Resource;
import hex.types.buttons.ShopButton;
import mindustry.content.Fx;

public class HexBuilds {

    public static HexBuild citadel, base, miner, thory, compressor, cultivator, oil, water, cryo, arkycite, city, maze;

    public static void load() {
        citadel = new HexBuild() {{
            name = "build.citadel.mk1";
            health = 18;

            scheme = HexSchematics.citadelMk1;
            boom = Fx.impactReactorExplosion;

            prod = new Resource() {{
                units = 20;
            }};
            cons = new Resource();

            parent = () -> citadel;
            next = new HexBuild() {{
                name = "build.citadel.mk2";
                health = 36;

                scheme = HexSchematics.citadelMk2;
                boom = Fx.impactReactorExplosion;

                prod = new Resource() {{
                    units = 20;
                }};
                cons = new Resource() {{
                    titanium = 1000;
                    thorium = 1000;
                    plastanium = 500;
                }};

                parent = () -> citadel;
                next = new HexBuild() {{
                    name = "build.citadel.mk3";
                    health = 48;

                    scheme = HexSchematics.citadelMk3;
                    boom = Fx.impactReactorExplosion;

                    prod = new Resource() {{
                        units = 20;
                    }};
                    cons = new Resource() {{
                        titanium = 3000;
                        thorium = 3000;
                        spores = 500;
                    }};

                    parent = () -> citadel;
                    next = null;
                }};
            }};
        }};

        base = new HexBuild() {{
            name = "build.base.mk1";
            health = 9;

            scheme = HexSchematics.baseMk1;
            boom = Fx.impactReactorExplosion;

            prod = new Resource();
            cons = new Resource() {{
                titanium = 400;
                units = 1;
            }};

            parent = () -> base;
            next = new HexBuild() {{
                name = "build.base.mk2";
                health = 18;

                scheme = HexSchematics.baseMk2;
                boom = Fx.impactReactorExplosion;

                prod = new Resource();
                cons = new Resource() {{
                    titanium = 1200;
                    plastanium = 500;
                    units = 1;
                }};
                
                parent = () -> base;
                next = new HexBuild() {{
                    name = "build.base.mk3";
                    health = 36;

                    scheme = HexSchematics.baseMk3;
                    boom = Fx.impactReactorExplosion;

                    prod = new Resource();
                    cons = new Resource() {{
                        titanium = 2000;
                        spores = 300;
                        units = 2;
                    }};

                    parent = () -> base;
                    next = null;
                }};
            }};
        }};

        miner = new HexBuild() {{
            name = "build.miner.mk1";
            health = 3;

            scheme = HexSchematics.minerMk1;
            boom = Fx.titanSmoke;

            prod = new Resource() {{
                titanium = 1;
            }};
            cons = new Resource() {{
                units = 1;
            }};

            parent = () -> miner;
            next = new HexBuild() {{
                name = "build.miner.mk2";
                health = 9;

                scheme = HexSchematics.minerMk2;
                boom = Fx.titanSmoke;

                prod = new Resource() {{
                    titanium = 2;
                }};
                cons = new Resource() {{
                    thorium = 500;
                    plastanium = 100;
                    units = 1;
                }};

                parent = () -> miner;
                next = new HexBuild() {{
                    name = "build.miner.mk3";
                    health = 18;

                    scheme = HexSchematics.minerMk3;
                    boom = Fx.titanSmoke;

                    prod = new Resource() {{
                        titanium = 3;
                    }};
                    cons = new Resource() {{
                        thorium = 500;
                        spores = 50;
                        units = 2;
                    }};

                    parent = () -> miner;
                    next = null;
                }};
            }};
        }};

        thory = new HexBuild() {{
            name = "build.miner.mk1";
            health = 9;

            scheme = HexSchematics.thoryMk1;
            boom = Fx.reactorExplosion;

            prod = new Resource() {{
                thorium = 1;
            }};
            cons = new Resource() {{
                titanium = 100;
                units = 1;
            }};

            parent = () -> thory;
            next = new HexBuild() {{
                name = "build.miner.mk2";
                health = 18;

                scheme = HexSchematics.thoryMk2;
                boom = Fx.reactorExplosion;

                prod = new Resource() {{
                    thorium = 2;
                }};
                cons = new Resource() {{
                    titanium = 500;
                    plastanium = 100;
                    units = 1;
                }};

                parent = () -> thory;
                next = null;
            }};
        }};

        compressor = new HexBuild() {{
            name = "build.compressor";
            health = 18;

            scheme = HexSchematics.compressor;
            boom = Fx.titanSmoke;

            prod = new Resource() {{
                plastanium = 1;
            }};
            cons = new Resource() {{
                titanium = 400;
                oil = 1;
                units = 2;
            }};
        }};

        cultivator = new HexBuild() {{
            name = "build.cultivator";
            health = 18;

            scheme = HexSchematics.cultivator;
            boom = Fx.reactorExplosion;

            prod = new Resource() {{
                spores = 1;
            }};
            cons = new Resource() {{
                thorium = 2800;
                plastanium = 2000;
                water = 1;
                units = 5;
            }};
        }};

        oil = new HexBuild() {{
            name = "build.pump.oil";
            health = 18;

            scheme = HexSchematics.oil;
            boom = Fx.titanSmoke;

            prod = new Resource() {{
                oil = 1;
            }};
            cons = new Resource() {{
                thorium = 300;
                units = 2;
            }};
        }};

        water = new HexBuild() {{
            name = "build.pump.water";
            health = 18;

            scheme = HexSchematics.water;
            boom = Fx.titanSmoke;

            prod = new Resource() {{
                water = 1;
            }};
            cons = new Resource() {{
                thorium = 800;
                units = 2;
            }};
        }};

        cryo = new HexBuild() {{
            name = "build.pump.cryo";
            health = 18;

            scheme = HexSchematics.cryo;
            boom = Fx.titanSmoke;

            prod = new Resource() {{
                cryo = 1;
            }};
            cons = new Resource() {{
                thorium = 1000;
                plastanium = 500;
                units = 2;
            }};
        }};

        arkycite = new HexBuild() {{
            name = "build.pump.arkycite";
            health = 18;

            scheme = HexSchematics.arkycite;
            boom = Fx.reactorExplosion;

            prod = new Resource() {{
                arkycite = 1;
            }};
            cons = new Resource() {{
                thorium = 1800;
                plastanium = 1000;
                units = 2;
            }};
        }};

        city = new HexBuild() {{
            name = "build.city";
            health = 18;

            scheme = HexSchematics.city;
            boom = Fx.reactorExplosion;

            prod = new Resource() {{
                units = 10;
            }};
            cons = new Resource() {{
                titanium = 300;
                thorium = 200;
            }};
        }};

        maze = new HexBuild() {{
            name = "build.shop";
            health = 18;

            scheme = HexSchematics.maze;
            boom = Fx.reactorExplosion;

            prod = new Resource();
            cons = new Resource() {{
                titanium = 3000;
                thorium = 6000;
                plastanium = 2000;
                cryo = 1;
                units = 5;
            }};
        }

        @Override
        public void build(Hex hex) {
            super.build(hex);
            hex.buttons.add(new ShopButton(hex));
            hex.owner.stats.shops++;
        }};
    }
}
