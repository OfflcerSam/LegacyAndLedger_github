/*
By Tartiflette
 */
package org.officersam.lal.scripts.util;

import com.fs.starfarer.api.Global;

public class Lal_txt {
    private static final String SR="SR";
    public static String txt(String id){
        return Global.getSettings().getString(SR, id);
    }    
}