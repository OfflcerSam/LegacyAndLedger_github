package org.officersam.lal.scripts.world;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import org.officersam.lal.scripts.world.systems.ET_Aeri;
import org.officersam.lal.scripts.world.systems.SR_OrionPoint;

//@SuppressWarnings("unchecked")
public class Lal_gen implements SectorGeneratorPlugin {

    @Override
    public void generate(SectorAPI sector) {
	
        new SR_OrionPoint().generate(sector);
        new ET_Aeri().generate(sector);
		
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("SR");
        SharedData.getData().getPersonBountyEventData().addParticipatingFaction("UTFE");

        FactionAPI utfe = sector.getFaction("UTFE");
        FactionAPI sr = sector.getFaction("SR");
        FactionAPI player = sector.getFaction(Factions.PLAYER);
        FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
        FactionAPI tritachyon = sector.getFaction(Factions.TRITACHYON);
        FactionAPI pirates = sector.getFaction(Factions.PIRATES);
        FactionAPI independent = sector.getFaction(Factions.INDEPENDENT); 
        FactionAPI church = sector.getFaction(Factions.LUDDIC_CHURCH);
        FactionAPI path = sector.getFaction(Factions.LUDDIC_PATH);   
        FactionAPI kol = sector.getFaction(Factions.KOL);	
        FactionAPI diktat = sector.getFaction(Factions.DIKTAT); 
		FactionAPI persean = sector.getFaction(Factions.PERSEAN);
        FactionAPI guard = sector.getFaction(Factions.LIONS_GUARD);

        sr.setRelationship(utfe.getId(), 0.3f);
        utfe.setRelationship(sr.getId(), 0.3f);

        sr.setRelationship(player.getId(), 0.3f);
        sr.setRelationship(hegemony.getId(), 0.0f);
        sr.setRelationship(tritachyon.getId(), 0.2f);
        sr.setRelationship(pirates.getId(), -0.9f);
        sr.setRelationship(independent.getId(), 0.6f);
        sr.setRelationship(persean.getId(), 0.15f);
        sr.setRelationship(church.getId(), -0.3f);
        sr.setRelationship(path.getId(), -0.9f);
        sr.setRelationship(kol.getId(), 0.0f);
        sr.setRelationship(diktat.getId(), 0.0f);
        sr.setRelationship(guard.getId(), 0.0f);


        utfe.setRelationship(player.getId(), 0.3f);
        utfe.setRelationship(hegemony.getId(), 0.0f);
        utfe.setRelationship(tritachyon.getId(), 0.2f);
        utfe.setRelationship(pirates.getId(), -0.9f);
        utfe.setRelationship(independent.getId(), 0.6f);
        utfe.setRelationship(persean.getId(), 0.15f);
        utfe.setRelationship(church.getId(), -0.3f);
        utfe.setRelationship(path.getId(), -0.9f);
        utfe.setRelationship(kol.getId(), 0.0f);
        utfe.setRelationship(diktat.getId(), 0.0f);
        utfe.setRelationship(guard.getId(), 0.0f);

	
    }
}
