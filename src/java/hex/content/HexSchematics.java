package hex.content;

import arc.struct.Seq;
import hex.types.HexSchematic;
import mindustry.game.Schematic.Stile;
import mindustry.world.blocks.units.UnitFactory;

public class HexSchematics {

    public static HexSchematic closed;
    public static HexSchematic button, citadelLr1, citadelLr2, baseLr1, baseLr2, titaniumLr1, titaniumLr2, thoriumLr1, thoriumLr2, oilLr1, oilLr2, waterLr1, waterLr2, cryoLr1, cryoLr2, arkyciteLr1, arkyciteLr2, forestLr1, forestLr2, sporeLr1, sporeLr2, canyonLr1, canyonLr2, gradientLr1, gradientLr2, wastelandLr1, wastelandLr2, citadelMk1, citadelMk2, citadelMk3, baseMk1, baseMk2, baseMk3, minerMk1, minerMk2, minerMk3, thoryMk1, thoryMk2, compressor, cultivator, oil, water, cryo, arkycite, city, maze;
    private static HexSchematic[] doors;

    public static void load() {
        // terrain
        button = new HexSchematic(-2, -2, "#AссBHaC^BBHAbBiab^bBHCbBHABBiaB^bBHCBBHAaBiaa^bBHCaBHaA^B");
        citadelLr1 = new HexSchematic(2, 3, "#AljaSDAardAaREA^daSIAaSca^aaSdaaREa^DarhaaSIa^aarCBaScBaSDBaSdBarEBaReB^aaRfBarGBaRgB^aarhBaSIBaSiBaSJBaRCbarcbaSDbaSdbaREbaRebarFbaRfb^baShb^aaSibaRJbaRbC^aaScC^aardCaREC^aaRFC^barHCaShC^aariCaRJC^aaRBcarbcaRCcaSccaSDc^aarEcaRec^bargcaSHc^aarIcaRic^aarjcaRKcaRBDarbDaRCD^aaSDDaSdDBHED^cBHHDaShDaRID^CaSadaRBd^cBHEdBHHdaRhd^aarid^aaRjd^BaSAEaSaEaSBEarbEaRCEarcE^aaSdEBHEEBHHEaShEaSIE^BarjEaRKEaSkE^aaSAeaSaeaSBe^aarCeaSceaSDe^aBHEeBHHeaSheaSIe^aaSJeaSje^aaSke^aaSAFaSaF^baScFaSDFardFBHEFBHHFaShFaSIFariFaSJFarjF^aaSkF^aaSafarBfaSbf^BaRDfaSdfBHEfBHHfaShfarIfaRif^aarjfaRKf^aaRBGaRbGaRCGaRcGarDGaSdGBHEG^DaShGaSIGariGaRJG^aaRKGaRBgaRbgarCgaRcgaSDgaSdgaSEg^aaRFg^aaRGg^aarHgaShgaSIg^aaRJg^aaRKgaRbHaRCHaRcHaSDHaSdHarEHaReH^aaRfHaRGH^aaRHHarhHaSIH^aarJHaRjHaRCharchaSDhaSdhaREharehaRFhaRfhaRGharghaRHh^aarIhaSiharJhaRCIaScIaSDI^aaREI^BaRfI^BaRHIaRhIaSIIaSiIaSJIaSciarDiaRdi^BarFiaRfiarGiaRgiaRHiarhiaSIi^aaSDJaRdJ^baRfJarGJaRgJ^aarhJaSIJ");
        citadelLr2 = new HexSchematic();
        baseLr1 = new HexSchematic(1, 2, "#AmkaODA^aaOiA^aaODa^aBiEa^EaOia^aaODB^aBiEB^EaOiB^aBicb^HBicC^HBiCc^IBibD^CBHED^aBiFDBHfD^BBiHDBHhD^aBiiD^CBibd^CBHEdBied^DBHIdBiid^CBiBE^EBiHE^EaOAe^aBiBe^cBHEeBieeBiheBHIeBiie^caOle^aaOAF^aBiBF^cBHEFBieFBihFBHIFBiiF^caOlF^aaOAf^aBiBf^cBHEfBiefBihfBHIfBiif^caOlf^aBiBG^EBiHG^EBibg^CBHEgBieg^DBHIgBiig^CBibH^CBHEH^aBiFHBHfH^BBiHHBHhH^aBiiH^CBiCh^IBicI^HBici^HaODJ^aBiEJ^EaOiJ^aaODj^aBiEj^EaOij^aaODK^aaOiK^a");
        baseLr2 = new HexSchematic(1, 2, "#AmkBkDA^aBkiA^aBkDaBkJaBkFBBkHBBkFbBkHbBkFCBkHCBkED^aBkhD^aBkEdBkIdBkCE^BBkJE^BBkAeBkMeBkAFBkMFBkAfBkMfBkCG^BBkJG^BBkEgBkIgBkEH^aBkhH^aBkFIBkHIBkFiBkHiBkFJBkHJBkDjBkJjBkDK^aBkiK^a");
        titaniumLr1 = new HexSchematic(2, 3, "#AljaЙDA^FaЙca^GaЙCB^HaЙCb^HaЙbC^IaЙBc^JaЙBD^JaЙad^KaЙAE^LaЙAe^LaЙAF^LaЙaf^HaйIf^aaЙJf^aaйKfaйkfaЙBG^gaйIG^aaЙJGaйjG^aaЙBg^Haйig^baЙbH^faйhH^CaЙCh^Faйhh^baЙCI^eaйHI^CaЙci^DaйGi^caЙDJ^baйFJ^D");
        titaniumLr2 = new HexSchematic(3, 3, "#ALjaхcA^aaхgA^BaхCaaхcaaхDaaхda^aBOgaaхHa^aaхIaBOgB^BaхIBaхiBBOCbBOcbaхDbaхEb^aBOHb^BaхibBObC^aBOcCBODC^aBOhCBOIC^BBObc^aBOccBODc^aaхec^BBOHcBOhc^bBOjcBOBD^BBOcDBODDBOHDBOhD^bBOjDBOadBOBd^BBOcdaхdd^BaхGdBOgdBOHd^cBOKdBOAE^bBOCEBOcEBOgEBOHE^cBOKEaхkEBOAe^BBObeaхCe^aaхdeBOgeBOHe^cBOKeBOkeBOaF^BBOGFBOgF^DBOKFBOkFBOaf^aaхbf^Baхdf^aBOGfBOgf^CBOJfBOjfBObG^aBOcGBODGBOGGBOgG^BBOIG^aBOJGaхag^BBOCgBOcgBODgBOdgBOEgaхeg^aBOGg^bBOCHBOcHBODHBOdHBOEHBOeHBOGH^baлiH^aBOChBOchBODhBOdhBOEhBOehBOfh^bBODIBOdIBOEIBOfI^baлII^aaлFJ^aaлgJ^B");
        thoriumLr1 = new HexSchematic(2, 3, "#AljaRDA^FaRca^GaUCBaRcB^aaudB^BaRFB^aauGB^baRIB^BaUCb^aauDb^eaRIb^BaRbCaUCC^BaRdCauEC^EaRiC^BaRBc^BaUcc^BaREc^aauFc^daRJc^BauBD^aaRCDaUcD^BaRED^aauFD^BaUgD^aauhD^BaRJD^BaRadauBd^BaRcd^aaudd^aaRedauFd^aaUGd^bauId^BaRjd^BaRAE^aauBE^BaRcE^aaudE^aaReE^BaUGE^aauHE^CaRjE^baRAe^aauBe^baRDe^aauEe^aaRFeaUfe^BauHe^CaRje^baRAF^aauBF^CaRdF^baUfF^BauHF^BaUiFaRJF^CaRaf^aaubf^CaUEf^eaRJf^baRBGaubG^aaUcGauDG^BaUeG^daRiG^aaujG^aaRBgaUbg^CauEg^caUHg^aaRIgauig^baRbHaUCH^aauDH^daUHH^aaRIHauiH^aaRjHaUCh^aauDh^daUHhaRhhauIh^BaRCIaUcIaRDIaudI^DaRHI^aauIIaRiI^aaRci^bauei^aaRfi^DaRDJ^F");
        thoriumLr2 = new HexSchematic(2, 5, "#AKhBoGA^BaмDa^aBoFa^cBoFB^cBoFb^BaмIb^aaмhC^aBobc^aaмdcBoIc^BaмADaмBDBobD^aaмdD^aBohDBoID^BaмBdBobd^BBoHd^CaмBEBobE^BBoHE^bBobe^BBohe^BaмDF^aBoEf^BaмjfaмDGBoEG^caмJG^aaмDg^aBoEg^caмJgaмdHBoFH^B");
        oilLr1 = new HexSchematic(2, 3, "#AljaUDA^DatgA^baUca^datga^BaUIaatiaatCB^BaUdB^aaNeB^aaUfBatGB^DatCb^BaNdb^batfb^daUbCatCC^aaNDC^batFC^CaNhC^BatJC^aaUBc^BaNcc^batec^aaUfcatGcaNgc^CatJc^BaUBD^aaNCD^CateDaUFD^aaNGD^CaUiDatJD^BaUad^BaNCd^batEd^aaUFd^aaNGd^baUId^aatJd^baUAE^BaNbE^batdE^BaUFE^aaNGE^aaUHE^BatiE^aaUjE^aatkE^aaUAe^BaNbe^BatDe^baUFe^catIe^aaUJe^Batke^aaUAF^catDF^CaUfF^bathF^aaUiF^batkF^aaUaf^CatDf^eaUIf^batKf^aaUBG^aatCG^DaNfG^aatgG^BaUIG^aatJG^BatBg^daUFgaNfg^BatHg^DatbH^DaUFH^aaNGH^BaUhH^aatiH^BatCh^caUFh^aaNGh^BaUhh^BatJhatCI^caUFI^BaNgI^BaUII^Batci^caUfi^BaNHi^aaUIi^aatDJ^caUGJ^C");
        oilLr2 = new HexSchematic(2, 3, "#AKjaМhA^aaМHa^baМHB^CaМcbaМgb^caМbC^aaМGC^BaМJC^aaМBc^BaМGcaМJc^aaМBD^aaМJDaМad^BaМAE^BaМAe^BaМAF^BaМafaМdHaМDh^aaМDI^BaМci^baМDJ^B");
        waterLr1 = new HexSchematic(2, 3, "#AljBiDAasdA^eascaBiDa^aasEa^easCB^BBidBasEB^FasCb^HasbC^EaMGC^BashC^CasBc^caMEc^dasIc^CasBD^CaMdD^aaKeD^aaMfD^CasID^CasadBiBdasbd^BaMDd^BaKed^BaMGd^Casid^CasAE^CaMcE^aaKdE^caMgE^CasJE^CBiAe^basCeaMce^aaKde^DaMHe^basJeBijeasKeBike^aasAF^aBiBFasbFaMCF^baKEF^DaMhF^aasiF^BaMKF^aasLFasaf^BaMCf^CaKef^caMhfasIf^baMKf^aasBG^baMDG^CaKfG^BaMHG^aasIG^BaMjG^aasBg^CaMdg^CaKGg^aaMHgashg^baMjg^aasbH^baMdH^dashH^BaMJH^aasCh^baMEh^Basfh^caMih^aasCI^FBihIasIIaMiI^aasci^GasDJ^eBiIJ");
        waterLr2 = new HexSchematic(2, 3, "#AljBkDAaФdA^aaФhA^aaФcaBkDa^aaФha^BaФcB^aBkdBaФHB^CaФDb^BaФHb^BaФDC^aBkECaФgC^BaФdc^BaФgc^aaФED^aaФadBkBdaФKd^aaФAE^CaФJE^CBkAe^baФCe^baФhe^bBkjeaФKeBkke^aaФAF^aBkBFaФbF^BaФiF^caФaf^BaФkfaФegaФgg^aaФEH^aaФgH^BaФdh^BaФHh^baФDI^BaФHIBkhIaФII^BaФci^baФhi^BaФDJ^aaФhJBkIJ");
        cryoLr1 = new HexSchematic(2, 3, "#AljaзDA^DaЗgA^baиcaaзDa^DaЗga^CaзCB^eaЗHB^baзJBaиCbaзcb^aaиdbaзEb^CaЗgb^baзib^aaиbC^BaзDC^DaЗgC^baзiC^BaИBc^aaиCc^aaзDc^aaиEcaзec^BaЗGc^baзIc^CaИBD^baиDDaзdD^BaЗFD^caзID^CaИad^caиddaзEdaЗed^DaзId^caИAE^aanBE^aaИCE^baЗEE^DaзhE^daИAe^aanBe^baИDe^BaЗee^baзge^eaИAFaиaFanBF^CaИdF^aaЗeF^BaзGF^FaИaf^aanbf^baИdf^BaЗFf^aaзGf^eaИBG^aanCG^baИEG^aaЗFGaзfGaиGGaзgG^daиBgaИbgaиCgancg^BaИEg^aaиFgaзfg^eaИbH^aancH^baИeH^aaиfHaзGH^aaиHHaзhH^CaИCh^aanDh^baИFhaиfh^aaзgh^caИCI^BandI^BaИFI^aaиGIaзgI^aaиhIaзII^BaИci^CanFiaИfi^aaиgi^aaзhi^BaИDJ^EaиhJaзIJ");
        cryoLr2 = new HexSchematic(2, 3, "#AljaфHA^Baцfaaфha^BaфhB^aaфHb^aaфHC^aaфHcaнKcaнjD^aBOad^aaНcdaфGd^aaнjd^BBOAE^baфFE^aaнJE^CaНAeBOae^BaНdeaфFeaнie^caНAFBOaF^baнhF^dBOaf^CaНEfaнhf^baнKf^aBOBG^baнFGaцhGaнIG^aaнKGBOBg^baцjgaНbHBOCHBOdhBOeIaНciaНDJ^a");
        arkyciteLr1 = new HexSchematic(2, 3, "#AljayDA^EaZhA^aayca^EaZHa^bayCB^aaZDBaydB^aaZeB^aayfB^aaZgB^aBihBaZIB^BayCb^CaZeb^baygbaZHb^BaГibaZJbaybC^aaДcC^BayECaZeC^CBiHCaZhC^CBiBcaybc^aaДcc^BaZEc^aaГFcaZfc^aBigc^aaZhc^baГjc^aBiBDaybDaZCDaДcD^BaZED^cBiHDaZhDaГIDaZiDaГJD^aaZKDayadBiBdaybd^baZdd^BayFdaZfd^aaГgdaZHd^BaГid^BaZKd^aayAE^baZCE^GaГiE^aaZjE^aaвkE^aayAeBiaeayBeaZbe^baydeaZEe^Daдhe^BaГJeaвje^bayAFaZaFBiBFaZbFaГCFaZcF^CaГFFaZfF^baдhF^BaГJFaвjF^baZafBiBfaZbf^eaГgf^aaдhf^BaвJf^baZBG^FaГgG^aaZhGaГIGaвiG^baZBg^CaГdgaZEgBiegaZFgaГfg^caвig^baZbH^CBiEHaZeH^BaГGH^baвIH^baZCh^DaГfh^aaZghaГHhaвhh^baZCI^BaГdIaZEI^BaГfI^BaвHI^aaГIIaвiI^aaZci^BBiEiaZeiaГFi^baвHi^baZDJ^aBiEJaZeJaГFJ^aaвGJ^C");
        arkyciteLr2 = new HexSchematic(2, 3, "#AljaРEA^CBSGAaРgABSgAaРdaaРeaBSeaaРFa^aBSfaBkhBBkHCBkBcBkgc^aBkBDBkHDBkBdBkaeBkBFBkBfaСdfBRdfaСbGBRbGaСCGBRCGaСcGaСBgBRBgaСbgBRbgaСCgBkegaСbH^aBRCHaСcHBkEHaСCh^aaСCIBkEiBkEJBAjDBAGgBAgi");
        forestLr1 = new HexSchematic(2, 3, "#AljaжDA^FaжcaaKDa^Caжfa^DaжCBaKcB^caжfB^daжCbaKcb^caжfb^daжbC^aaKcC^CaжFC^eaжBc^BaKcc^baжec^EatJc^aaжKcaжBD^CaKdDaжED^EatiD^aaUjD^aaжad^HatId^BaUjd^aatkdaжAE^hatIE^BaUjE^baжAe^hatIe^aaUJeatjeaUKe^BaжAF^hatIF^aaUJF^Caжaf^HatIf^aaUJf^baжBG^haUJG^BaжBg^JaжbH^IaжCh^HaжCI^Haжci^GaжDJ^F");
        forestLr2 = new HexSchematic(2, 3, "#AljaЧDA^BaХeA^aaЧHA^BaЧca^CaХFa^aaХHaaЧha^BaЧCB^BaХFB^aaХHB^baЧJBaЧCb^aaХhb^baЧbC^BaЧHCaХiCaМJC^aaЧBc^CaМJc^BaЧBD^aaХCD^baМjD^aaХad^DaХAE^caМLEaХAe^BaЧDeaМke^aaХAF^aaЧHF^aaМIFaМkF^aaЧHf^aaМIf^BaМKf^aaЧbGaЧHG^BaМiG^baХCg^aaЧhg^BaХJgaМjg^aaХbH^baЧEHaЧIH^aaХJH^aaХCh^baЧIh^aaХJhaХCIaЧcI^aaХdIaЧhI^aaХiI^aaЧci^baХHiaЧhi^aaХiiaЧDJ^BaХHJ^B");
        sporeLr1 = new HexSchematic(2, 3, "#AljaйDA^eaкIAaйca^FaкIaaйiaaйCB^aaкDBaйdB^EaкIBaйiB^aaйCb^aaкDb^aaйEb^DaкhbaйIb^BaйbC^BaкDC^aaйEC^caкHC^baйJC^aaйBc^baкDc^aaйEc^aaкFcaйfc^BaкHc^baйJc^BaйBD^CaкdDaйED^aaкFD^aaйGD^BaкhD^CaйKDaйad^caкddaйEd^aaкFd^aaйGd^baкId^baйKd^aaйAE^iaкJE^aaйKE^BaйAe^JaкjeaйKe^BaйAF^DaкdFaйEF^aaкFF^BaйgF^eaйaf^aaкbfaйCf^caкFf^aaйGf^eaйBG^hakJGaйjG^aaйBg^gakIg^baйKgaйbH^caкeH^BaйGH^bakIH^baйCh^Baкdh^baйfh^bakhh^baйCIaкcIaйDI^baкFIaйfI^BakHI^Caйci^EakHi^baйDJ^DakgJ^b");
        sporeLr2 = new HexSchematic(2, 3, "#AljaлDA^baЦFA^baЦIAaлca^BaЦEa^CaлgaaЦIa^aaлCB^aBldBaЦIB^BaлCb^aaЦIb^BaлbC^BBldCaлEC^aaлGC^aaЦHC^baлJCBlJCaлjCaлBc^baлEc^aaлGcaЦgc^CaлJc^BBlcDaлED^aaлGD^aBliDaлKDaЦadBlBdaлEd^aBlfdaлGd^aaлKdaЦkdaЦAE^aaлCE^eaлIE^aBljEaлKEaЦkE^aaЦAe^BaлCe^eaлIe^aaЦKe^BaЦAF^BBldFaлEF^aBlFFaлIF^aaЦKF^BaЦaf^BaлEf^aaлIfaЦifaЦkfaЦBG^aaлCG^aaлEG^DaЦhG^BBlJGaЦBg^BaлcgaлEgBlEgaлeg^CaЦHg^bBlfHBlIHBljHBlJhaЦCI^BaлdI^BBlFIaлGI^BaЦhI^baЦci^baлeiaлGi^aaЦHi^baЦDJ^BBlIJ");
        canyonLr1 = new HexSchematic(2, 3, "#AljaзDA^EauhA^aaзca^EauHa^BaзiaaзCB^bauEB^BaзfB^BauHB^CaзCb^bauEb^Caзgb^aauhb^baзbC^caueC^FaзBc^aauCc^aaзDc^Bauec^caзhcauIc^CaзBD^aauCD^aaзDD^aauEDaзeDauFD^Faзad^BauCd^BaзddauEd^gauAEaзaE^baucE^CaзFEaufE^faзAe^BaubeaзCeauce^iaзAFauaF^baзcFauDF^Iauaf^aaзbfauCf^iauBG^JauBg^JaubH^IauCh^HauCI^Hauci^GauDJ^F");
        canyonLr2 = new HexSchematic(2, 3, "#AljaнDAaнeA^baмIAaнca^aaнFa^baмIaaнiaaнCB^BaнfB^BaмHB^baнCb^bBkfbaнgb^aaмhb^aaнcC^baмHC^BaнBcaнDc^BaмecBkGcaмHcaнhcaмIc^aaмKcaнBD^aaнDD^aaмEDaнeDaмFDaмhD^Baнad^BaнddaмEd^baмhd^BaмkdaнAE^CaмEE^aaнFEaмfEBNhEaмIE^aBkJEBNKEaмkE^aaнAeaнBeaмbeaнCeaмceaмee^baмIe^BaмLeaмBF^BaнcFaмDFaмeF^aBkfFaмGFaмIF^baнbfaмCf^BBkFf^aaмGfBkgfBkif^aaмjfBNKfaмCG^baмFGBkfGaмGG^aBNHGBkJGaмjG^aaмcg^BBNegaмfg^BBkHgaмhgBNigaмjg^aBkcH^aaмdH^aBkGHaмgHBkHH^aaмIHBNjHaмDh^aBkEhaмehBNfhaмGh^aBkHhaмhh^BaмdI^BaмgIBNHIaмII^aBNJIBNdiBkEiaмei^aaмgi^aaмiiaмEJ^BBNfJBNgJaмHJBNhJ");
        gradientLr1 = new HexSchematic(2, 3, "#AljaГDA^aaвEA^aaГFA^DaГca^BaвEa^aaГFa^dazCBaГcB^gazCb^BaГdb^fazbC^CaГEC^fazBc^caГEc^GazBD^BaАcD^BazED^aaГFD^BaвgD^aaГhD^caбadazBd^BaАcd^BazEd^baГGdaвgd^aaГhd^DaбAE^BazbEaАCE^bazEEaАeEazFE^caГIE^DaБAe^aaбBeaБbeaАCe^bazEe^baАGeazge^baГie^aaвje^aaГke^aaБAF^caАDF^aazEF^eaГJFaвjF^aaГkF^aaБaf^CaбDfaБdfazEf^aaАFfazff^daГjf^BaБBG^dazFGaАfG^BazHG^CaГjG^aaБBg^BaOcg^baБeg^aaАfg^bazhg^caБbH^aaOcH^baБeH^baАgH^aazhH^CaБCh^aaODh^BaБeh^CaАHhazhh^baБCI^fazII^BaБci^faziiaбDJ^aaБEJ^BaбfJ^aaБgJ^b");
        gradientLr2 = new HexSchematic(2, 3, "#AljaсDA^BaсgA^baсDa^baсga^BaТCBaсdB^baсHB^aBCIBaТCb^aaсEb^aaсhb^aaТcCaТdCaсfC^aaсhC^BaТBcaТDc^aaсfc^BaсIc^BaТBD^aaТcD^baсGD^aaсiD^aaТBd^baТdd^aaсgd^aaсid^aaсkdaТbE^BaТdEaсJE^aaсLEaУAe^aaУbeaТCeaТhe^aaсJe^aaсLeaТdF^aaТHF^baсJF^BaсLFaУafaУbf^BaУdfaТEf^aaТgf^BaТJfaсjf^aBEcGaТFGaТGG^BaТiG^aaсjG^aaУBgaУCg^aaУdg^aaУFgaТHgaТIg^CaУDH^aaУfHaТJH^aaУChaУDh^BaУghaТhh^bBEhIaТII^BaУci^daУHi^BaТiiaУEJ^B");
        wastelandLr1 = new HexSchematic(2, 3, "#AlKaVDA^aawEA^EaVca^BawEa^eaVCBaWcBaVDB^BaweB^eaVCb^bavEbaweb^eaVbC^DawFC^eaVBc^Eawfc^eaVBD^aaгCD^BaVdD^bawfD^eaVad^BaгCd^BaVdd^aavedaVFd^aawGd^EaVkdaVaEavBEaVbEaгCE^BaVdE^CavGEawgE^DaVKE^aaVAe^fawGe^BaVheawIeaVie^caVAF^eavFFaVfFawGF^aaVHF^EaVAf^caWDfaVdf^BavFfaVff^EaWKfaVkf^aaVaG^HavIGaViG^CaVBg^eaWGgaVgg^aaгhg^BaVJg^BaVBH^DaveHaVFH^CaгhH^BaVJH^BaVbh^davfhaVGh^Baгhh^BaVJh^aaVCIavcIaVDI^GaVCi^FavhiaVIi^BaVcJ^GaVDj^F");
        wastelandLr2 = new HexSchematic(2, 3, "#AjKaоfA^CaПIAaЯeaBbfaaоGa^baПIa^aaоGB^baПIB^BBbGbaоgb^aaПhb^bBdDCaоgCaПHC^BaоiC^BaПHc^aaоIc^CaПgD^aBbhD^aaоiD^BaЯGdaПgd^BaПgE^aaЯJEaПGe^BaпBF^aBdEFaПGF^aaпBf^BaпbG^BBdgGaпbg^baпCH^baпbh^DBdGhaпCI^daпCi^EaпcJ^eaпDj^E");

        // builds
        citadelMk1 = new HexSchematic(8, 7, "#AffBЩaabнbabнDaBЩeabоcBbнabbнEbbоBcbоEcbнaDbнEDbнbEbоcEbнDEBЩaeBЩee");
        citadelMk2 = new HexSchematic(5, 4, "FVRQW6DMBAcr8GAoSH5QT+QS79T9eBSV01FISIkVX7bh1RKd+xLI2RmmZ3d8S7YYWfgxvAaxxPk+aVBNVyHcZ4i6vM0zuEtLtgcP8Ip7od5usTrvKB/n5ch7o/L/BmHVYnyEs7jikYVazhMWtLlku8YLvrVHqaBdKDYfcVxVRJ4AgxKiNG4gohCXd1utx8N1sQ2elTTQiwENWwBPkpZuBLohZFPERMdtF8BBysKqdkvW1iyDSzttItThTYxFNLXofjv61QFQpuT3X3yAWCDHtZqswKG0MFUCoDUCiZDDyG5hbg0iiH0vIbPxv7e2Gdjn439vbGnMUcsOGTLu3MzasavBoY5XYDoq65ALSc3WpZWpY4bDk8ibQw8JRK09N3qX9DybS5/pG9iG8gfHutGbQ==");
        citadelMk3 = new HexSchematic(1, 0, "EVS7W7aQBCc8xmDHT4MMXb42QfgXfqzUlNVLrmEVg6OzEfUt+ob9WdfohKduU1UkJnbnWG8u7fYYOOQde230B2RfP5yg/Jl3x7D9rXtum3XDk8Bxf8MFo/9sAvbl6H/EXanfsDcyF1/uISfjKdv4tBewoCc+VP7/cBj9hy6E3F0ac/dCcvTPgzPbbd9CmRbWRX0Dsd9+9C/YrrjeXs477pwPgL4ygc5nGAO53gawwsm8AkSTC1ZWnIJDyYzJRNJBDm8JxRj2Cd1CucKf/H5ELUL05ZmsJSBR6qkxwieJ/roZGV43BpX6Q9imUzFCzKDlcGtSSq4jMwKbszKbgxoNmFHS5AZU8lGJ/xJcr4t1jtBjcTp3UhStaJacpkpecdaUYjzhGR8vV7v+fxhMOWXLC8WTmQtu/RtBAUaJBoe6xasZUe9CuZENdqZClHUGNypthklLmfVpVxm5LwGEl/8NzqPGHqDCnFatchPpDZ0WKhWEJz6L1W7YKb/LNiql4RdTZjMkGScDGeqqWWSaJROEPuvbN6VuBGBe6Ixz41bGRfvaW1XuY4XSMjUdh1bZRRve/2urAS1LU9ty1NzedjERz6/YxiXB2o6ihamjUvY2II2MgAhLm9jRTXvEu7pP4kkXHE=");
        baseMk1 = new HexSchematic(5, 4, "#AiibЖaabхEabЖHablbBABwCBBwfBblgBBblBbaBБbbBБgbblHbaBwBCBwgCbхaEbхhEBwBfBwgfblBgbBБbgBwCgBwfgBБggblHgbbЖaHblbHAblgHBbЖHHbхEh");
        baseMk2 = new HexSchematic(5, 4, "C1Q0Y6DIBAcQZDTatMP8YsufaBKciZUDWrv7u+7gw0Jszs7u8yCG24FbPSPEDeo7/sXbJrWNQZ063/y+7SH/jn9hYRyPIYdzSP6bf9QzTQP0xxEtiS4Y46LH4U2L3/EHdX269NT8npYUui3H59GAD1QwPHiyVcHraCgUWiBEiA0J9kSmAuvKSG0yPKOypI1I2ANcC2sxA6KRJ0JKmRIJbw5oYY2sChZztGFkVhzNGQFDJQDT84avuCoYO/ZVkkk46wU5TVmeWr9maVzU0FoaeZyutOMsi3NzWS/7A2KK5XUt2wkmfduZUXF/5FfUvwmGrzSFzORsCaSN/FYJ5Q=");
        baseMk3 = new HexSchematic(4, 3, "FVSy27DIBAcsDGJ8EvJd+Tef6l6cF2qJrLsyHkpX9pbDr31HyqlO2C3ChjtDszOLouxxloh65pX3x2gn18WKPcfzcFv2qE/++swwrbXtht6j+p9GFu/2Y/DzrdHOXHbvt32fmwI8hh28c3Zj1ic+m5o3sQz5+bUHbEUvWNDNoAnWbD2fr+fZH1pQjfDm1bQWEJrJJyJIANNU0Bxs4JOxRgkNLkcII1yP1QK0P1DMjVDDOWMGGuASmXImIYbLmyQUUZ+DWVBgqZx1LTyiWYv6zshTC3lmUZELUzQoFcEj5QyUGSkAa5mgU+lmBqaFWTRyD24WTOXi7l2Uy73mMtNuax4GeQeucSByCEczwW4xwJcLGAXCkglKmGncrmzSqS3msHF1Bx6sSs8qtnVKvJLaY+MUjpO3Um/lI7/QZJyvlUlU65aS33yjjVfgKYgaRWfrOUfEKCb4Q2/M0V1XA==");
        minerMk1 = new HexSchematic(3, 4, "#ALibоfABБGAbоgABэbaA^dbеHaBэbBbbеCBbОGBBэbbbbеDbBэEbA^BBэHbB^CBэaCA^aBэbCbbеCCBэECbBБgCbеHCbеICbеJCBэacbbеBcBэDcA^aBэEcbBэgcbBэaDbbеCDBэDDbBэgDbBАHDbеhDbеiDbеjDbеAdbеBdBэDdbBэgdbbоHdBэDEbBэgEbbеHEbеIEbнJEbеKEbеaeBэbeA^BBэDebbоgeBэDFbbнgFbеhFbеiFbоjFbеCfBэDfbBэffA^aBэjfaBэDGbbеdGBэfGbbеGGbеHGBэIGaBэiGB^BbеCgBэDgbBэFgABэfgbBэIgaBэDHbbеdHBэFHbbеfHbеgHBэhHaBэIHBBэDhbBэFhbBэhhaBэFIbBэfIB^C");
        minerMk2 = new HexSchematic(3, 4, "#AkIbоfABБGAbоgABЮbaA^dbОGBbЁCbBЮDbA^CbЁhbBЮDCbBШdCBЮgCbBЮDcbBЮgcbbЁJcbЁCDBЮDDbBЮgDbbЁhDBЮKDabЁadBЮDdbBЮgdbBЮKdabDbEBЮDEbBЮgEbBЮHEB^BbЁJEBЮKEaBЮbeA^BBЮDebbDIeBЮKeaBЮDFbbЁHFbDjFBЮKFaBЮDfbBЮdfB^CbЁifBюjfabDdGBЮEGbBЮfGbBЮhGaBБjGbЁcgBЮfgbbЁggBЮhgabЁEHBЮfHbBЮhHaBЮfhbBЮGhB^b");
        minerMk3 = new HexSchematic(3, 3, "#AKIBБFABwfABweabNfBBwGBBwFbBБGCbЁcDBШFDbЁHDbёiDbёaEBЮCEA^CBЮGEB^bbОFeBЮCFA^CBЮGFB^bbёiFBЮDfbbоefBБFfbоffbёgGbёcg");
        thoryMk1 = new HexSchematic(5, 5, "#AIhCPdAbZEA^abЁfabЁEBBэFbaBэFCabЁacbоdcBБEcbоecBэFcaBэbDaBэFDBbЁHDbнbdBэcdA^abОEdBэFdB^BbЁaEbоcECPhEBэEebbЁgebZhebZAFbZaFCPBFBэEFbbZhFBэEfbbЁDGbЁegCPcHbZDH^a");
        thoryMk2 = new HexSchematic(5, 4, "CWPUW6DMBBEx5CAwUlFlDv0jxNVreQQf1A5GJkkVU/RI9MZopX1PLuzg8EJJ4Mq+kuIC4qPT4vDnH5C7pf0yEOAu0S/3PtrHmOEW1L0uZ/9FCLO6Rky+8/Qzzl9h+GeMs6z7H4aH7d+SNMz/LLpbn5Z+s2aYR9TTP7KmxunYZxC9loE3nlQWZQGBhZFQVRAwSLKrdgspXYsK+xfqASDGke6qTrGaGRraMyWKTjtFEcnCoNGyUrZwwjVCy1KwaGUc1totCBsqpXaEVafciC54SQr4gAGHvBWr+v6x/Nl1O30uqM8NYf0CJ1eepQStvCOg1KwLzT8XaKl/AfA4DgM");
        compressor = new HexSchematic(4, 7, "FWOy3KEIBBFLygPpay8/yBbvyiVBaMsqEKdgE5N/j7dkiyyaA59+/YDrxgEdPKXkArkx2cHl3wJeZxzTAnuQtn+m7xcOfFrPJZx2tZb+N4y3OJLYcMtZAxbTGO479lPO9Xe/jUs1xxK4Za4TnEN2bNnSPHriPOYt2OnCfZY0+Zn+gHvgIDip6GAJLSM9g+2GnrOVBUVdIVBw7AMXWu61jSJUpLhFE0VDYsNOihDmzsKCEnaae0qegbg6kLHWYuBlkhFkBAMA6HxwD5FoMMNQdVM1xqdbfHIXeilpp+CYMGegsITz2rwXG+5U7hTpdE/3HI3yw==");
        cultivator = new HexSchematic(4, 4, "F1TbWwUZRCe3b3b29uPu+u1HkRMDEgiRpdQ0MQYsdT4B1GCDfwgLSHL3nos3bs9dvdosX4lxTaWipeDRG1oExKxRgKKCVBSrfEKSRtM/GhDNdFC6keJGtPQH2KM1OfdCiFebu+Zm3fmmZn3maVaSnIkOsYuy/GJb96xlBS/6HqWXvQs3yfFLph2wfKMwPUombdNz8WJa+IMDiVv5V1vv25ajkOi32YH5m6g6wUWDu3Ayuu+W/JM63bkLqPQSmLeclhELA8aI2eRbJacwN63WMRxc7Z5R5FE0TECK6ubbiFbsgOSiyVnn+XZL4BBc+y9JTure26JESbbEOnpVnvgGSYj04q7Dd+6nZo0vf3u8w7LyNvtLN8LcnrOujWgBuo7whXfdQxPLxoFyyHFcwMDExRL+SKl/quLyMBg90OxW0lEz+KhKAkcIEY8TxxJCdhK+1Ez+ypPlCZeEOCNkxABaDKHvzxS8OVJBMY5EVY8tCKwZISAijQSJBIQxnEAkThQUDyCSmAQSCZBJkbJcwBwRgERxhL6wa4A4miG29h+9Hj5yubqmrruLyMXt85989u8FPS+0jZ9QxzZYEVPHF/WumRm/q8tdzfuOVf/8eprb3xX+fnP/l/aRlaTNL3zeqnnyIorPz0zs+K5+r6HZp/yJ56uzj0yPVixR4+MTWUvHfj8/b6Lj658sPDulg9XTT65aqD+cLawoeXgePr1ltP3j5vuJ9km/55McmNTdm/L1vNvVZqc3krrPze+mBx974Lx6Znrh8rjesfE7IVLf3SdzzQNNW5L3dw0/PvCD30DX586d23luu+br45sOv2YJk81fDB78tdymoY6Xx59sxoN1qer2w72Xr3cv6cy1h9/fO3Zjo65scip5qzcmXlne+tn69d2LxvubpwoSjuG52uiH+18Qj5mNrz0sCfe+9WJm5nXEi2Xlx8YGP6xvm5K6SnnlM39yycPz/QNrukaOVN6+9vO2oauB1KzL6rrBs/+zd3XU7dUUHGnKi4dAH1UJj3xDCBBHKCE+jCHCuGZasRHALzMQScRSRxUp8T/dwQaxxDLaWyLwAAAfxSQYJLyCZJY1SQAx1gyFMQKSExnBnG2DRJy+BRsLkaLH4HY30hsYWGhjKcKAhnZIJYXNw5NyczFQGOLJRNaU0lhLAxktqgKaeFipWAlGNkhPEPIVVGDrwlvBPXZ2KEzxmZX0Q9GU1lvLAS91eIHCXUUjhlFqWi49gIskYS7ABpjSLL3QwKIbMgUo2WTxFgvKZLCxikcLfQrxGcAKiuQAgfexhoEQ4YasAhLAApLTZMcluNgKSRoYXfcv7vkNbg=");
        oil = new HexSchematic(5, 5, "E2PXW6DMBCEF0P4MSahB+Gtt6n64BKkoPJXYh56t16iJ6IzbCIVCYaZ/Xa9lhepI0kH/9ENdzFv71ZcuHXr6Idm2cZFsnaerlsf5DJ27c1Pffus1EP/tfXXBkDw/dStUi03f++aZ0e5zsGv30qXDzr46RPgNvwH+6nlAB9mDHmA67wFzBR5FYkk4yeSRMRAToglpovhrZgIP6kgiKUAKo4AsATw0YZiCl8wPIGKOCRVyVRKiSlOpZKYAJ4MYjglRWhieKMbJezLwB6CYayhmuBzbJEfW7BYsafAEdg0V2fRCFfAH1JRrK5mGQrwsxjDxRiWSpacwtARqbTmdAunV3J6JceGHDNynn3m5eks3YVhAimyfd9/8f4cqSVTa0etHTXDP50OQWo=");
        water = new HexSchematic(4, 3, "#AjjBБdAbжfAbжDabOeaBАBBBБfBBsBbblebbBБaCbMeCbBАAcBXCcblecbBБgcbёhcBXADBTBDBБCDblcDBbKEDbnGDbnCdbLFdBАbEbLdEbKGEblgEA^ablhEbBXeebKDFbLfFbngFbncfbLEfbnhfbkifBVAGbЗbGbKFGblGGAbMgGABБHGBБcgblegablggbbnHgbkIgbKJgBЮaHA^CBаBhBЮChbbOehbKIhBwdibleiaBwFiBБeJ");
        cryo = new HexSchematic(2, 4, "D1Qa26DMBgzhAYK4dHtHD3AzjLtR0aZisajoyCth586G7r9IMaOP8cJnnEIYDv/3nRXhK9vKbJ2qNuhmfw8TsgvZ39tjvU4nJZ2RjaNs59ux8vSXxD/qVXXfi3tSa7ZaxRuPjdT77vNWPZNffZDW/8L9XQbPzrN9O03/fkjYRqXmTR70NkPnwBe+CHWwqpaAsSiRpIJSRMYqelqut/vP1RDeo0hpAgjQgazQ4S9xEhxlpAhEHMIBTlMRChk2cmyo8npHMt0CpY5HLDaUz7DLGEdsCiVYlHBxGtdOuMtJeGeSQihUthWewl/gwiSjSDTMYlKxAwOZNmzPAS5SqRaDGEVsw2cThAkKuG2+7nN6zjHbceCDMvljAlOrBBLCIXEggUplpulpIn3rVjX7HHgk7Bntd6eUILCgQayJz3yL9LsP5o=");
        arkycite = new HexSchematic(5, 5, "#AJgbqdAA^BbqFAabqdabbqFaabqcBAbrFBabзbbbqcbABВdbAbБgbbqcCAbrFCabгICbqDca^BbqFcaBЪADBЁaDbqFDabqIDb^abиAdabБbdbГddbqFdabqhdAbQIdbqFEabвgEbqhEAbqdebbqFeabqdFbbqFFabqdfbbqFfA^BbQgfbqdGbbqEGB^C");
        city = new HexSchematic(3, 5, "#AjIbюdaaCCEDBБIDBviDCyBdCАbdCXCdCАEdbАFdCZaECCEECxAeCzCeCАaFCАafCxBfCxcfbюFHb");
        maze = new HexSchematic(5, 3, "E2QPWgUQRTH38xebu9u73aTUwSri9rEYkUsjSBRixReEYuASAiT2bm7MbM7637oHRE8ck0KCchBODCl2liYJiIIKulsJFgIghYiEhSCQmz18jagpHj85v3nffxnoApHCOQVWxAqBnpjrgqWDLgMRMQSHYEVhzoSbhiJOMZEKxa5IQuEgkIaKM08EUGR6yBhWQvYoWKJ8FxUvFQm4PCooxsqlZ7ryzYWVMIWi8X/+xJPVSJvH6yqhKk6dJWP78iEt8DxBW+xQHKm3DD1QzB99MKaAhylm5KjN81RwQmOL3mkDwnmv2GWL3wddVwulAK7Gek08NwG47i3AwDHMMAGgwBBEAp5qAI1wIQRIDlEHoAiSmCMICwgecQoUBNRNYfD4QOMTVqAAtaQIgL7S3imJUIsJKZl7IMcATAq2QQbV6601+urU/bW2XKuYtpjf3rz3feXf15YdgYL4xObvf7KxV/zL9ZOXplaur67OvsSukuNtzene7OnB/fj4NbTE+fr7XNPtp5P9set3VO1V97O3LNrV79+yn3vbczMLO5sv/52Jp3uf1l8XLv3aPDx88TR3w/f1fXaxt/a8dr2mx978u6lPfqhOLlMbbRkZJas9jr3uhQyx8RB9eAhZYASMUaRBlAHgaoFlQxj+A9lfBUQYx9AU6UM");

        city.tiles.each(st -> st.block instanceof UnitFactory, st -> st.config = 1);

        String[] base = {
                "#AebBHAA^aBiBABibABHCA^aBHDABidA^aBiAaBiaaBiBaBibaBHCaBicaBiDaBidaBiEaBiABBiaBBHBBBHbBBHCBBicBBiDBBHdB^a",
                "#ADEBiCA^aBHbaBiCa^aBHbB^aBiBb^aBHCbBHaCBiBC^aBHac^aBiAD^aBHBDBiAd^a",
                "#ADEBiAA^aBiAa^aBHBaBHaB^aBHabBiBb^aBiBC^aBHCCBHbc^aBHbDBiCD^aBiCd^a"
        };

        doors = new HexSchematic[] {
                new HexSchematic(9, 22, base[0]),
                new HexSchematic(20, 14, base[1]),
                new HexSchematic(20, 3, base[2]),
                new HexSchematic(9, 0, base[0]),
                new HexSchematic(1, 3, base[1]),
                new HexSchematic(1, 14, base[2])
        };

        closed = door((byte) -1);
    }

    public static HexSchematic door(byte opened) {
        Seq<Stile> tiles = new Seq<>();

        for (int i = 0; i < doors.length; i++)
            if ((1 << i & opened) == 1 << i) tiles.addAll(doors[i].tiles);

        return new HexSchematic(tiles);
    }
}
