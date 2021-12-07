// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks;

import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;

public interface IMinecraft
{
    int getRightClickDelay();
    
    void setRightClickDelay(final int p0);
    
    Timer getTimer();
    
    void click(final Click p0);
    
    int getGameLoop();
    
    boolean isEarthhackRunning();
    
    void runScheduledTasks();
    
    MetadataSerializer getMetadataSerializer();
    
    public enum Click
    {
        RIGHT, 
        LEFT, 
        MIDDLE;
    }
}
