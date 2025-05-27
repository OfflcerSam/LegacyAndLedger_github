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

public class ET_Aeri {
    // Star Orbits
    //asteroids
    final float asteroids1Dist = 8500f;
    final float stable1Dist = 8100f;
    final float asteroidBelt1Dist = 3350f;
    final float asteroidBelt2Dist = 12000f;
    //relays
    final float buoy1Dist = 4550f;
    final float relay1Dist = 6650f;
    final float sensor1Dist = 11050f;
    //planets
    final float desert1Dist = 2900f;
    final float arid1Dist = 4200f;
    final float betaDist = 5500f;
    final float centralDist = 7500f;
    final float gasDist = 9500f;
    final float monitoringDist = 12000f;
    //jumps
    final float jumpInnerDist = 3050f;
    final float jumpOuterDist = 9300;
    final float jumpFringeDist = 16700f;


    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Aeri");
        system.getLocation().set(-15000, -8000);

        system.setBackgroundTextureFilename("graphics/backgrounds/orion_background3.jpg");

        //praise the sun
        PlanetAPI aeriStar = system.initStar("J_Aeri", "star_orange_giant", 1250f, 1400, 7, 0.5f, 2f);
        system.setLightColor(new Color(255, 200, 150));

        //JumppointInner
        JumpPointAPI jumpPoint_inner = Global.getFactory().createJumpPoint(
                "inner_jump",
                "Inner System Jump");

        jumpPoint_inner.setCircularOrbit(system.getEntityById("J_Aeri"), 360 * (float) Math.random(), jumpInnerDist, 4000f);
        jumpPoint_inner.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_inner);

        //JumppointOuter
        JumpPointAPI jumpPoint_outer = Global.getFactory().createJumpPoint(
                "outer_jump",
                "Outer System Jump");

        jumpPoint_outer.setCircularOrbit(system.getEntityById("J_Aeri"), 360 * (float) Math.random(), jumpOuterDist, 2000f);
        jumpPoint_outer.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_outer);

        //JumppointFring
        JumpPointAPI jumpPoint_fringe = Global.getFactory().createJumpPoint(
                "fringe_jump",
                "Fringe System Jump");

        jumpPoint_fringe.setCircularOrbit(system.getEntityById("J_Aeri"), 360 * (float) Math.random(), jumpFringeDist, 6000f);
        jumpPoint_fringe.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint_fringe);

        //autogenerate hyperspace points
        system.autogenerateHyperspaceJumpPoints(true, false);

        //asteroid field
        SectorEntityToken sampstonAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(250f, 350f, 10, 24, 4f, 16f, "Asteroids Field"));
        sampstonAF1.setCircularOrbit(aeriStar, 150, asteroids1Dist, 220);

        SectorEntityToken stableLoc1 = system.addCustomEntity("sampston_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(aeriStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        addDerelict(system, sampstonAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 270f, (Math.random() < 0.6));
        addDerelict(system, sampstonAF1, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 240f, (Math.random() < 0.6));
        addDerelict(system, sampstonAF1, "lasher_PD", ShipRecoverySpecial.ShipCondition.BATTERED, 125f, (Math.random() < 0.6));

        //asteroid belt1 ring
        system.addAsteroidBelt(aeriStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(aeriStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(aeriStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(aeriStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        //asteroid belt2 ring
        system.addAsteroidBelt(aeriStar, 1000, asteroidBelt2Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Outer Band");
        system.addRingBand(aeriStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt2Dist - 200, 250f);
        system.addRingBand(aeriStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt2Dist, 350f);
        system.addRingBand(aeriStar, "misc", "rings_asteroids0", 256f, 1, Color.gray, 256f, asteroidBelt2Dist + 200, 400f);

        // Relays
        SectorEntityToken aeriStar_relay = system.addCustomEntity("aeriStar_relay", // unique id
                "Orion Relay", // name - if null, defaultName from custom_entities.json will be used
                "comm_relay_makeshift", // type of object, defined in custom_entities.json
                "SR"); // faction
        aeriStar_relay.setCircularOrbitPointingDown(aeriStar, MathUtils.getRandomNumberInRange(0f, 360f), relay1Dist, 520);

        SectorEntityToken aeriStar_buoy = system.addCustomEntity("aeriStar_buoy", // unique id
                "Orion Nav Buoy", // name - if null, defaultName from custom_entities.json will be used
                "nav_buoy_makeshift", // type of object, defined in custom_entities.json
                "SR"); // faction
        aeriStar_buoy.setCircularOrbitPointingDown(aeriStar, MathUtils.getRandomNumberInRange(0f, 360f), buoy1Dist, 520);

        SectorEntityToken aeriStar_sensor = system.addCustomEntity("aeriStar_sensor", // unique id
                "Orion Sensor Array", // name - if null, defaultName from custom_entities.json will be used
                "sensor_array_makeshift", // type of object, defined in custom_entities.json
                "SR"); // faction
        aeriStar_sensor.setCircularOrbitPointingDown(aeriStar, MathUtils.getRandomNumberInRange(0f, 360f), sensor1Dist, 520);

        // Gas Giant
        PlanetAPI gas = system.addPlanet("gas", aeriStar, "gas", "gas_giant", 360f * (float) Math.random(), 400f, gasDist, 380f);
        //gas.setCustomDescriptionId("sr_orion_gasgiant"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(gas, StarAge.OLD);

        SectorEntityToken scrap1 = DerelictThemeGenerator.addSalvageEntity(system, Entities.SUPPLY_CACHE, Factions.DERELICT);
        scrap1.setId("gas_scrap1");
        scrap1.setCircularOrbit(gas, 105, 250, 150);
        Misc.setDefenderOverride(scrap1, new DefenderDataOverride(Factions.DERELICT, 0, 0, 0));
        scrap1.setDiscoverable(Boolean.TRUE);

        // Arid
        PlanetAPI arid1 = system.addPlanet("arid1", aeriStar, "Arid", "arid", 360f * (float) Math.random(), 210f, arid1Dist, 380f);
        // arid1.setCustomDescriptionId("sr"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(arid1, StarAge.OLD);

        // Desert
        PlanetAPI desert1 = system.addPlanet("desert1", aeriStar, "Desert", "desert", 360f * (float) Math.random(), 130f, desert1Dist, 200f);
        // vengus.setCustomDescriptionId("sr"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(desert1, StarAge.OLD);

        // UTFE Point Jay
        PlanetAPI central;
        central = system.addPlanet("jcentral",
                aeriStar,
                "UTFE Point Jay",
                "tundra",
                360f * (float) Math.random(),
                220f,
                centralDist,
                380f);

        central.setCustomDescriptionId("et_aeri_j"); //reference descriptions.csv

        MarketAPI central_market = addMarketplace("UTFE", central, null,
                "UTFE Point Jay",
                8,
                Arrays.asList(
                        Conditions.POPULATION_8,
                        Conditions.INDUSTRIAL_POLITY,
                        Conditions.ORE_ULTRARICH,
                        Conditions.RARE_ORE_RICH,
                        Conditions.COLD,
                        Conditions.HABITABLE,
                        Conditions.RUINS_SCATTERED,
                        Conditions.REGIONAL_CAPITAL,
                        Conditions.VOLATILES_ABUNDANT,
                        Conditions.STEALTH_MINEFIELDS,
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
                        Industries.MINING,
                        Industries.STARFORTRESS_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.HEAVYINDUSTRY,
                        Industries.LIGHTINDUSTRY,
                        Industries.HIGHCOMMAND,
                        Industries.FARMING,
                        Industries.WAYSTATION
                ),
                0.19f,
                false,
                true);

        central_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE));
        central_market.getIndustry(Industries.ORBITALWORKS).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.POPULATION).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.MEGAPORT).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.MINING).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.STARFORTRESS_HIGH).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.HEAVYINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.LIGHTINDUSTRY).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.HIGHCOMMAND).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.FARMING).setAICoreId(Commodities.ALPHA_CORE);
        central_market.getIndustry(Industries.WAYSTATION).setAICoreId(Commodities.ALPHA_CORE);


        addDerelict(system, central, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 240f, (Math.random() < 0.6));
        addDerelict(system, central, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 250f, (Math.random() < 0.6));
        addDerelict(system, central, "wolf_d_pirates_Attack", ShipRecoverySpecial.ShipCondition.BATTERED, 220f, (Math.random() < 0.6));



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
