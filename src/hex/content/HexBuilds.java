package hex.content;

import hex.types.Hex;
import hex.types.HexBuild;
import hex.types.Production;
import hex.types.buttons.ShopButton;
import mindustry.content.Fx;

public class HexBuilds {

    public static HexBuild citadel, base, miner, thory, spore, oil, water, cryo, compressor, city, maze;

    public static void load() {
        citadel = new HexBuild() {{
            name = "Citadel Mk1";
            health = 18;

            scheme = HexSchematics.citadelMk1;
            boom = Fx.impactReactorExplosion;

            prod = new Production() {{
                unit = 20;
            }};
            cons = new Production();

            parent = () -> citadel;
            next = new HexBuild() {{
                name = "Citadel Mk2";
                health = 36;

                scheme = HexSchematics.citadelMk2;
                boom = Fx.impactReactorExplosion;

                prod = new Production() {{
                    unit = 20;
                }};
                cons = new Production() {{
                    titanium = 1000;
                    thorium = 1000;
                    plastanium = 500;
                }};

                parent = () -> citadel;
                next = new HexBuild() {{
                    name = "Citadel Mk3";
                    health = 48;

                    scheme = HexSchematics.citadelMk3;
                    boom = Fx.impactReactorExplosion;

                    prod = new Production() {{
                        unit = 20;
                    }};
                    cons = new Production() {{
                        titanium = 2000;
                        thorium = 2000;
                        spore = 500;
                    }};

                    parent = () -> citadel;
                    next = null;
                }};
            }};
        }};

        base = new HexBuild() {{
            name = "Base Mk1";
            health = 9;

            scheme = HexSchematics.baseMk1;
            boom = Fx.impactReactorExplosion;

            prod = new Production();
            cons = new Production() {{
                titanium = 500;
                unit = 1;
            }};

            parent = () -> base;
            next = new HexBuild() {{
                name = "Base Mk2";
                health = 18;

                scheme = HexSchematics.baseMk2;
                boom = Fx.impactReactorExplosion;

                prod = new Production();
                cons = new Production() {{
                    plastanium = 500;
                    unit = 1;
                }};
                
                parent = () -> base;
                next = new HexBuild() {{
                    name = "Base Mk3";
                    health = 36;

                    scheme = HexSchematics.baseMk3;
                    boom = Fx.impactReactorExplosion;

                    prod = new Production();
                    cons = new Production() {{
                        spore = 500;
                        unit = 1;
                    }};

                    parent = () -> base;
                    next = null;
                }};
            }};
        }};

        miner = new HexBuild() {{
            name = "Miner Mk1";
            health = 3;

            scheme = HexSchematics.minerMk1;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                titanium = 1;
            }};
            cons = new Production() {{
                unit = 1;
            }};

            parent = () -> miner;
            next = new HexBuild() {{
                name = "Miner Mk2";
                health = 9;

                scheme = HexSchematics.minerMk2;
                boom = Fx.reactorExplosion;

                prod = new Production() {{
                    titanium = 2;
                }};
                cons = new Production() {{
                    thorium = 200;
                    plastanium = 100;
                    unit = 2;
                }};

                parent = () -> miner;
                next = new HexBuild() {{
                    name = "Miner Mk3";
                    health = 18;

                    scheme = HexSchematics.minerMk3;
                    boom = Fx.reactorExplosion;

                    prod = new Production() {{
                        titanium = 3;
                    }};
                    cons = new Production() {{
                        thorium = 500;
                        spore = 100;
                        unit = 3;
                    }};

                    parent = () -> miner;
                    next = null;
                }};
            }};
        }};

        thory = new HexBuild() {{
            name = "Miner Mk1";
            health = 9;

            scheme = HexSchematics.thoryMk1;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                thorium = 1;
            }};
            cons = new Production() {{
                titanium = 100;
                unit = 1;
            }};

            parent = () -> thory;
            next = new HexBuild() {{
                name = "Miner Mk2";
                health = 18;

                scheme = HexSchematics.thoryMk2;
                boom = Fx.reactorExplosion;

                prod = new Production() {{
                    thorium = 2;
                }};
                cons = new Production() {{
                    titanium = 500;
                    plastanium = 100;
                    unit = 2;
                }};

                parent = () -> thory;
                next = null;
            }};
        }};

        spore = new HexBuild() {{
            name = "Cultivator";
            health = 18;

            scheme = HexSchematics.cultivator;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                spore = 1;
            }};
            cons = new Production() {{
                thorium = 3000;
                plastanium = 1000;
                water = 1;
                unit = 1;
            }};
        }};

        oil = new HexBuild() {{
            name = "Oil Pump";
            health = 18;

            scheme = HexSchematics.oil;
            boom = Fx.impactcloud;

            prod = new Production() {{
                oil = 1;
            }};
            cons = new Production() {{
                thorium = 300;
                unit = 2;
            }};
        }};

        water = new HexBuild() {{
            name = "Water Pump";
            health = 18;

            scheme = HexSchematics.water;
            boom = Fx.impactcloud;

            prod = new Production() {{
                water = 1;
            }};
            cons = new Production() {{
                thorium = 500;
                unit = 2;
            }};
        }};

        cryo = new HexBuild() {{
            name = "Cryo Pump";
            health = 18;

            scheme = HexSchematics.cryo;
            boom = Fx.impactcloud;

            prod = new Production() {{
                cryo = 1;
            }};
            cons = new Production() {{
                thorium = 500;
                plastanium = 500;
                unit = 2;
            }};
        }};

        compressor = new HexBuild() {{
            name = "Compressor";
            health = 18;

            scheme = HexSchematics.compressor;
            boom = Fx.impactcloud;

            prod = new Production() {{
                plastanium = 1;
            }};
            cons = new Production() {{
                titanium = 200;
                oil = 1;
                unit = 1;
            }};
        }};

        city = new HexBuild() {{
            name = "Mono City";
            health = 18;

            scheme = HexSchematics.city;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                unit = 10;
            }};
            cons = new Production() {{
                titanium = 200;
                thorium = 200;
            }};
        }};

        maze = new HexBuild() {{
            name = "Maze Shop";
            health = 18;

            scheme = HexSchematics.maze;
            boom = Fx.reactorExplosion;

            prod = new Production();
            cons = new Production() {{
                titanium = 3000;
                thorium = 5000;
                plastanium = 1000;
                cryo = 1;
                unit = 5;
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
