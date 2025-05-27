package org.officersam.lal.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import exerelin.campaign.SectorManager;

//public static boolean hasGraphicsLib;

public class LalModPlugin extends BaseModPlugin {

    @Override
    public void onNewGame() {
	boolean haveNexerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
	if (!haveNexerelin || SectorManager.getManager().isCorvusMode()){
            new org.officersam.lal.scripts.world.Lal_gen().generate(Global.getSector());
        }
    }
 
}
	

