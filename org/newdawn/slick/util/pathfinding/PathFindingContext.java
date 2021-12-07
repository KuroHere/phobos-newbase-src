// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.newdawn.slick.util.pathfinding;

public interface PathFindingContext
{
    Mover getMover();
    
    int getSourceX();
    
    int getSourceY();
    
    int getSearchDistance();
}
