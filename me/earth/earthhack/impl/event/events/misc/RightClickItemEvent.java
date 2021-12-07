// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class RightClickItemEvent extends Event
{
    private final EntityPlayer player;
    private final World worldIn;
    private final EnumHand hand;
    
    public RightClickItemEvent(final EntityPlayer player, final World worldIn, final EnumHand hand) {
        this.player = player;
        this.worldIn = worldIn;
        this.hand = hand;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public World getWorldIn() {
        return this.worldIn;
    }
    
    public EnumHand getHand() {
        return this.hand;
    }
}
