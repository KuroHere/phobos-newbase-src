// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.waypoints.mode;

import net.minecraft.world.*;

public enum WayPointType
{
    OVW, 
    End, 
    Nether, 
    None;
    
    public static WayPointType fromString(final String string) {
        final String lowerCase = string.toLowerCase();
        switch (lowerCase) {
            case "ovw": {
                return WayPointType.OVW;
            }
            case "end": {
                return WayPointType.End;
            }
            case "nether": {
                return WayPointType.Nether;
            }
            default: {
                return WayPointType.None;
            }
        }
    }
    
    public static WayPointType fromDimension(final DimensionType type) {
        switch (type) {
            case OVERWORLD: {
                return WayPointType.OVW;
            }
            case NETHER: {
                return WayPointType.Nether;
            }
            case THE_END: {
                return WayPointType.End;
            }
            default: {
                return WayPointType.None;
            }
        }
    }
}
