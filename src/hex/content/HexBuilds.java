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
            parent = citadel;
            scheme = HexSchematics.citadelMk1;
            boom = Fx.impactReactorExplosion;

            prod = new Production() {{
                human = 20;
            }};
            cons = new Production();

            next = new HexBuild() {{
                name = "Citadel Mk2";
                parent = citadel;
                scheme = HexSchematics.citadelMk2;
                boom = Fx.impactReactorExplosion;

                prod = new Production() {{
                    human = 20;
                }};
                cons = new Production() {{
                    plastanium = 20;
                }};

                next = new HexBuild() {{
                    name = "Citadel Mk3";
                    parent = citadel;
                    scheme = HexSchematics.citadelMk3;
                    boom = Fx.impactReactorExplosion;

                    prod = new Production() {{
                        human = 20;
                    }};
                    cons = new Production() {{
                        spore = 20;
                    }};
                }};
            }};
        }};

        base = new HexBuild() {{
            name = "Base Mk1";
            parent = base;
            scheme = HexSchematics.baseMk1;
            boom = Fx.impactReactorExplosion;

            prod = new Production();
            cons = new Production() {{
                titanium = 20;
                human = 1;
            }};
            
            next = new HexBuild() {{
                name = "Base Mk2";
                parent = base;
                scheme = HexSchematics.baseMk2;
                boom = Fx.impactReactorExplosion;
                
                prod = new Production();
                cons = new Production() {{
                    plastanium = 20;
                    human = 1;
                }};
                
                next = new HexBuild() {{
                    name = "Base Mk3";
                    parent = base;
                    scheme = HexSchematics.baseMk3;
                    boom = Fx.impactReactorExplosion;
                    
                    prod = new Production();
                    cons = new Production() {{
                        spore = 20;
                        human = 1;
                    }};
                }};
            }};
        }};

        miner = new HexBuild() {{
            name = "Miner Mk1";
            parent = miner;
            scheme = HexSchematics.minerMk1;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                titanium = 1;
            }};
            cons = new Production() {{
                human = 1;
            }};

            next = new HexBuild() {{
                name = "Miner Mk2";
                parent = miner;
                scheme = HexSchematics.minerMk2;
                boom = Fx.reactorExplosion;

                prod = new Production() {{
                    titanium = 2;
                }};
                cons = new Production() {{
                    plastanium = 20;
                    human = 2;
                }};

                next = new HexBuild() {{
                    name = "Miner Mk3";
                    parent = miner;
                    scheme = HexSchematics.minerMk3;
                    boom = Fx.reactorExplosion;

                    prod = new Production() {{
                        titanium = 3;
                    }};
                    cons = new Production() {{
                        spore = 20;
                        human = 3;
                    }};
                }};
            }};
        }};

        thory = new HexBuild() {{
            name = "Miner Mk1";
            parent = thory;
            scheme = HexSchematics.thoryMk1;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                thorium = 1;
            }};
            cons = new Production() {{
                titanium = 20;
                human = 1;
            }};

            next = new HexBuild() {{
                name = "Miner Mk2";
                parent = thory;
                scheme = HexSchematics.thoryMk2;
                boom = Fx.reactorExplosion;

                prod = new Production() {{
                    thorium = 2;
                }};
                cons = new Production() {{
                    plastanium = 20;
                    human = 2;
                }};
            }};
        }};

        spore = new HexBuild() {{
            name = "Cultivator";
            // scheme = HexSchematics.;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                spore = 1;
            }};
            cons = new Production() {{
                plastanium = 20;
                human = 1;
            }};
        }};

        oil = new HexBuild() {{
            name = "Oil Pump";
            scheme = HexSchematics.oil;
            boom = Fx.impactcloud;

            prod = new Production() {{
                oil = 1;
            }};
            cons = new Production() {{
                thorium = 20;
                human = 1;
            }};
        }};

        water = new HexBuild() {{
            name = "Water Pump";
            scheme = HexSchematics.water;
            boom = Fx.impactcloud;

            prod = new Production() {{
                water = 1;
            }};
            cons = new Production() {{
                thorium = 20;
                human = 1;
            }};
        }};

        cryo = new HexBuild() {{
            name = "Cryo Pump";
            scheme = HexSchematics.cryo;
            boom = Fx.impactcloud;

            prod = new Production() {{
                cryo = 1;
            }};
            cons = new Production() {{
                thorium = 20;
                human = 1;
            }};
        }};

        compressor = new HexBuild() {{
            name = "Compressor";
            scheme = HexSchematics.compressor;
            boom = Fx.impactcloud;

            prod = new Production() {{
                plastanium = 1;
            }};
            cons = new Production() {{
                titanium = 20;
                oil = 1;
                human = 1;
            }};
        }};

        city = new HexBuild() {{
            name = "Mono City";
            scheme = HexSchematics.city;
            boom = Fx.reactorExplosion;

            prod = new Production() {{
                human = 10;
            }};
            cons = new Production() {{
                titanium = 20;
                thorium = 20;
            }};
        }};

        maze = new HexBuild() {{
            name = "Crawler Maze";
            scheme = HexSchematics.maze;
            boom = Fx.reactorExplosion;

            prod = new Production();
            cons = new Production() {{
                plastanium = 20;
                cryo = 1;
                human = 5;
            }};
        }};
    }
}
