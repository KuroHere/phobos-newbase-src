// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.network;

import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public interface ICPacketUseEntity
{
    void setEntityId(final int p0);
    
    void setAction(final CPacketUseEntity.Action p0);
    
    void setVec(final Vec3d p0);
    
    void setHand(final EnumHand p0);
    
    int getEntityID();
    
    CPacketUseEntity.Action getAction();
    
    Vec3d getHitVec();
    
    EnumHand getHand();
    
    Entity getAttackedEntity();
}
