// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

@Mixin({ CPacketClientSettings.class })
public interface ICPacketClientSettings
{
    @Accessor("lang")
    void setLang(final String p0);
    
    @Accessor("view")
    void setView(final int p0);
    
    @Accessor("chatVisibility")
    void setChatVisibility(final EntityPlayer.EnumChatVisibility p0);
    
    @Accessor("enableColors")
    void setEnableColors(final boolean p0);
    
    @Accessor("modelPartFlags")
    void setModelPartFlags(final int p0);
    
    @Accessor("mainHand")
    void setMainHand(final EnumHandSide p0);
    
    @Accessor("lang")
    String getLang();
    
    @Accessor("view")
    int getView();
    
    @Accessor("chatVisibility")
    EntityPlayer.EnumChatVisibility getChatVisibility();
    
    @Accessor("enableColors")
    boolean getEnableColors();
    
    @Accessor("modelPartFlags")
    int getModelPartFlags();
    
    @Accessor("mainHand")
    EnumHandSide getMainHand();
}
