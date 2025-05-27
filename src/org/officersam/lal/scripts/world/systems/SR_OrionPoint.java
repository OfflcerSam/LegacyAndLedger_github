package org.officersam.lal.scripts.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.DerelictShipEntityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SalvageSpecialAssigner;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.procgen.themes.DerelictThemeGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.impl.campaign.procgen.DefenderDataOverride;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

import static org.officersam.lal.scripts.world.systems.lal_addmarket.addMarketplace;

public class SR_OrionPoint {
    // Star Orbits
    //asteroids
    final float asteroids1Dist = 9500f;
    final float asteroidBelt1Dist = 3450f;
    final float asteroidBelt2Dist = 19500f;
    //relays
    final float stable1Dist = 11500f;
    final float buoy1Dist = 6550f;
    final float relay1Dist = 9250f;
    final float sensor1Dist = 19200f;
    //planets
    final float lava1Dist = 2950f;
    final float arid1Dist = 5500f;
    final float betaDist = 7500f;
    final float charlieDist = 9500f;
    final float centralDist = 10550f;
    final float gasDist = 17550f;
    final float monitoringDist = 19500f;
    //jumps
    final float jumpInnerDist = 4050f;
    final float jumpOuterDist = 10850;
    final float jumpFringeDist = 23500f;


    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Orion Point");
        system.getLocation().set(-15000, -8000);

        system.setBackgroundTextureFilename("graphics/backgrounds/orion_background3.jpg");

        //praise the sun
        PlanetAPI orionStar = system.initStar("s_OrionPoint", "star_orange_giant", 1250f, 1650, 7, 0.5f, 2f);
        system.setLightColor(new Color(255, 200, 150));

        //JumppointInner
        JumpPointAPI jumpPoint_inner = Global.getFactory().createJumpPoint(
                "inner_jump",
                "Inner System Jump");

        jumpPoint_inner.setCircularOrbit(system.getEntityById("s_OrionPoint"), 360 * (float) Math.random(), jumpInnerDist, 4000f);
        jumpPoint_inner.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_inner);

        //JumppointOuter
        JumpPointAPI jumpPoint_outer = Global.getFactory().createJumpPoint(
                "outer_jump",
                "Outer System Jump");

        jumpPoint_outer.setCircularOrbit(system.getEntityById("s_OrionPoint"), 360 * (float) Math.random(), jumpOuterDist, 2000f);
        jumpPoint_outer.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_outer);

        //JumppointFring
        JumpPointAPI jumpPoint_fringe = Global.getFactory().createJumpPoint(
                "fringe_jump",
                "Fringe System Jump");

        jumpPoint_fringe.setCircularOrbit(system.getEntityById("s_OrionPoint"), 360 * (float) Math.random(), jumpFringeDist, 6000f);
        jumpPoint_fringe.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_fringe);

        //autogenerate hyperspace points
        system.autogenerateHyperspaceJumpPoints(true, false);

        //asteroid field
        SectorEntityToken sampstonAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(250f, 350f, 10, 24, 4f, 16f, "Asteroids Field"));
        sampstonAF1.setCircularOrbit(orionStar, 150, asteroids1Dist, 220);

        SectorEntityToken stableLoc1 = system.addCustomEntity("sampston_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(orionStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        addDerelict(system, sampstonAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, sampstonAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 240f, (Math.random() < 0.6));
        addDerelict(system, sampstonAF1, "lasher_PD", ShipRecoverySpecial.ShipCondition.BATTERED, 125f, (Math.random() < 0.6));

        //asteroid belt1 ring
        system.addAsteroidBelt(orionStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(orionStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(orionStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(orionStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        //asteroid belt2 ring
        system.addAsteroidBelt(orionStar, 1000, asteroidBelt2Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Outer Band");
        system.addRingBand(orionStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt2Dist - 200, 250f);
        system.addRingBand(orionStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt2Dist, 350f);
        system.addRingBand(orionStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt2Dist + 200, 400f);

        // Relays
        SectorEntityToken orionStar_relay = system.addCustomEntity("orionStar_relay", // unique id
                "Orion Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay", // type of object, defined in custom_entities.json
                "SR"); // faction
        orionStar_relay.setCircularOrbitPointingDown(orionStar, MathUtils.getRandomNumberInRange(0f, 360f), relay1Dist, 520f);

        SectorEntityToken orionStar_buoy = system.addCustomEntity("orionStar_buoy", // unique id
                "Orion Nav Buoy", // name - if null, defaultName from custom_entities.json will be used
                "nav_buoy", // type of object, defined in custom_entities.json
                "SR"); // faction
        orionStar_buoy.setCircularOrbitPointingDown(orionStar, MathUtils.getRandomNumberInRange(0f, 360f), buoy1Dist, 520f);

        SectorEntityToken orionStar_sensor = system.addCustomEntity("orionStar_sensor", // unique id
                "Orion Sensor Array", // name - if null, defaultName from custom_entities.json will be used
                "sensor_array", // type of object, defined in custom_entities.json
                "SR"); // faction
        orionStar_sensor.setCircularOrbitPointingDown(orionStar, MathUtils.getRandomNumberInRange(0f, 360f), sensor1Dist, 520f);

        // Gas Giant
        PlanetAPI gas = system.addPlanet("s_gas", orionStar, "Origon Giant", "gas_giant", 360f * (float) Math.random(), 400f, gasDist, 380f);
        //gas.setCustomDescriptionId("sr_orion_gasgiant"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(gas, StarAge.OLD);

        //Gas Moon Barren
        PlanetAPI gasmoon1 = system.addPlanet("s_gasmoon1", gas, "Origon Barren Moon", "barren3", 360f * (float) Math.random(), 98f, 620f, 15f);
        //gas.setCustomDescriptionId("sr_orion_gasgiant"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(gasmoon1, StarAge.OLD);

        //Gas Moon Ice
        PlanetAPI gasmoon2 = system.addPlanet("s_gasmoon2", gas, "Origon Ice Moon", "rocky_ice", 360f * (float) Math.random(), 95f, 515f, 2f);
        //gas.setCustomDescriptionId("sr_orion_gasgiant"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(gasmoon2, StarAge.OLD);

        SectorEntityToken scrap1 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap1.setId("s_gas_scrap1");
        scrap1.setCircularOrbit(gas, 105, 250, 150);
        Misc.setDefenderOverride(scrap1, new DefenderDataOverride(Factions.DERELICT, 0, 0, 0));
        scrap1.setDiscoverable(Boolean.TRUE);

        // Arid
        PlanetAPI arid1 = system.addPlanet("s_arid1", orionStar, "Tatoco", "arid", 360f * (float) Math.random(), 220f, arid1Dist, 380f);
       // arid1.setCustomDescriptionId("sr"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(arid1, StarAge.OLD);

        // Scorned
        PlanetAPI lava1 = system.addPlanet("s_lava1", orionStar, "Scorned", "lava", 360f * (float) Math.random(), 150f, lava1Dist, 200f);
       // vengus.setCustomDescriptionId("sr"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(lava1, StarAge.OLD);

        // Orion Point Central
        PlanetAPI central;
        central = system.addPlanet("sr_central",
                orionStar,
                "Orion Point Central",
                "tundra",
                360f * (float) Math.random(),
                220f,
                centralDist,
                380f);

        central.setCustomDescriptionId("sr_orion_central"); //reference descriptions.csv

        MarketAPI central_market = addMarketplace("SR", central, null,
                "Orion Point Alpha",
                5,
                Arrays.asList(
                        Conditions.POPULATION_5,
                        Conditions.AI_CORE_ADMIN,
                        Conditions.INDUSTRIAL_POLITY,

                        Conditions.COLD,
                        Conditions.HABITABLE,
                        Conditions.RUINS_SCATTERED,

                        Conditions.ORE_MODERATE,
                        Conditions.RARE_ORE_MODERATE,

                        Conditions.STEALTH_MINEFIELDS
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.WAYSTATION,

                        Industries.MINING,
                        Industries.HEAVYINDUSTRY,
                        Industries.LIGHTINDUSTRY,
                       // Industries.REFINING,

                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.PATROLHQ
                        //Industries.FARMING,
                ),
                0.19f,
                false,
                true);

        central_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.ALPHA_CORE);

        central_market.getIndustry(Industries.PATROLHQ).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.ALPHA_CORE);

        central_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE));
        central_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        //central_market.getIndustry(Industries.REFINING).setAICoreId(Commodities.ALPHA_CORE);
        //central_market.getIndustry(Industries.FARMING).setAICoreId(Commodities.ALPHA_CORE);


        addDerelict(system, central, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 240f, (Math.random() < 0.6));
        addDerelict(system, central, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));
        addDerelict(system, central, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 220f, (Math.random() < 0.6));


        // Orion Point Beta
        PlanetAPI beta;
        beta = system.addPlanet("sr_beta",
                orionStar,
                "Orion Point Beta",
                "water",
                360f * (float) Math.random(),
                220f,
                betaDist,
                360f);

        beta.setCustomDescriptionId("sr_orionpoint_beta"); //reference descriptions.csv

        MarketAPI beta_market = addMarketplace("SR", beta, null,
                "Orion Point Beta",
                6,
                Arrays.asList(
                        Conditions.POPULATION_6,
                        Conditions.WATER_SURFACE,
                        Conditions.HABITABLE,
                        Conditions.AI_CORE_ADMIN,

                        Conditions.INDUSTRIAL_POLITY,
                        Conditions.ORE_ULTRARICH,
                        Conditions.RARE_ORE_MODERATE,
                        Conditions.VOLATILES_PLENTIFUL,
                        Conditions.VOLTURNIAN_LOBSTER_PENS,

                        Conditions.STEALTH_MINEFIELDS
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.WAYSTATION,

                        Industries.MINING,
                        Industries.REFINING,
                        //Industries.HEAVYINDUSTRY,
                        Industries.AQUACULTURE,
                        Industries.FUELPROD,

                        Industries.PATROLHQ,
                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES
                ),
                0.18f,
                false,
                true);

        beta_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        beta_market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.ALPHA_CORE);
        beta_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.ALPHA_CORE);

        beta_market.getIndustry(Industries.PATROLHQ).setAICoreId(Commodities.ALPHA_CORE);
        beta_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        beta_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.ALPHA_CORE);

        beta_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        beta_market.getIndustry(Industries.REFINING).setAICoreId(Commodities.ALPHA_CORE);
        //beta_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        beta_market.getIndustry(Industries.AQUACULTURE).setAICoreId(Commodities.ALPHA_CORE);
        beta_market.getIndustry(Industries.FUELPROD).setAICoreId(Commodities.ALPHA_CORE);



        // Orion Point Charlie
        PlanetAPI charlie;
        charlie = system.addPlanet("sr_charlie",
                orionStar,
                "Orion Point Charlie",
                "terran",
                360f * (float) Math.random(),
                220f,
                charlieDist,
                365);

        charlie.setCustomDescriptionId("sr_orionpoint_charlie"); //reference descriptions.csv

        MarketAPI charlie_market = addMarketplace("SR", charlie, null,
                "Orion Point Charlie",
                8,
                Arrays.asList(
                        Conditions.POPULATION_8,
                        Conditions.ORE_MODERATE,
                        Conditions.TERRAN,
                        Conditions.URBANIZED_POLITY,
                        //Conditions.RARE_ORE_SPARSE,
                        Conditions.VOLATILES_TRACE,
                        Conditions.HABITABLE,
                        Conditions.STEALTH_MINEFIELDS,
                        Conditions.REGIONAL_CAPITAL,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.WAYSTATION,
                        Industries.COMMERCE,
                        //Industries.REFINING,
                        Industries.MINING,
                        Industries.LIGHTINDUSTRY,

                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND
                ),
                0.17f,
                false,
                true);

        charlie_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        charlie_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.ALPHA_CORE);
        charlie_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.ALPHA_CORE);
        charlie_market.getIndustry(Industries.COMMERCE).setAICoreId(Commodities.ALPHA_CORE);

        charlie_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        charlie_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        charlie_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        charlie_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        charlie_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.ALPHA_CORE);

        // Orion Point Monitoring Station
        SectorEntityToken monitoring = system.addCustomEntity("sr_monitoring", "Orion Point Monitoring Station", "station_hightech2", "SR");
        monitoring.setCircularOrbitPointingDown(orionStar, 360f * (float) Math.random(), monitoringDist, 450f);
        monitoring.setCustomDescriptionId("sr_orionpoint_monitoring");
        MarketAPI monitoring_market = addMarketplace("SR", monitoring, null,
                "Orion Point Monitoring Station",
                4,
                Arrays.asList(
                        Conditions.POPULATION_4,
                        Conditions.NO_ATMOSPHERE,
                        Conditions.OUTPOST,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.WAYSTATION,

                        Industries.BATTLESTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE,
                        Industries.ORBITALWORKS
                ),
                0.20f,
                false,
                true);

        monitoring_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        monitoring_market.getIndustry(Industries.SPACEPORT).setAICoreId(Commodities.ALPHA_CORE);
        monitoring_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.ALPHA_CORE);

        monitoring_market.getIndustry(Industries.BATTLESTATION_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        monitoring_market.getIndustry(Industries.HEAVYBATTERIES).setAICoreId(Commodities.ALPHA_CORE);
        monitoring_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);

        addDerelict(system, monitoring, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.WRECKED, 210f, (Math.random() < 0.6));
        addDerelict(system, monitoring, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 200f, (Math.random() < 0.6));
        addDerelict(system, monitoring, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 190f, (Math.random() < 0.6));


    }

    private void addDerelict(StarSystemAPI system, SectorEntityToken focus, String variantId, ShipRecoverySpecial.ShipCondition condition, float orbitRadius, boolean recoverable) {

        DerelictShipEntityPlugin.DerelictShipData params = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(variantId, condition), true);
        SectorEntityToken ship = BaseThemeGenerator.addSalvageEntity(system, Entities.WRECK, Factions.NEUTRAL, params);
        ship.setDiscoverable(true);

        float orbitDays = 60f;
        ship.setCircularOrbit(focus, (float) MathUtils.getRandomNumberInRange(-2, 2) + 90f, orbitRadius, orbitDays);

        if (recoverable) {
            SalvageSpecialAssigner.ShipRecoverySpecialCreator creator = new SalvageSpecialAssigner.ShipRecoverySpecialCreator(null, 0, 0, false, null, null);
            Misc.setSalvageSpecial(ship, creator.createSpecial(ship, null));
        }
    }

}