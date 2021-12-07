// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.settingspoof;

import net.minecraft.entity.player.*;

public enum ChatVisibilityTranslator
{
    Full(EntityPlayer.EnumChatVisibility.FULL), 
    System(EntityPlayer.EnumChatVisibility.SYSTEM), 
    Hidden(EntityPlayer.EnumChatVisibility.HIDDEN);
    
    private final EntityPlayer.EnumChatVisibility visibility;
    
    private ChatVisibilityTranslator(final EntityPlayer.EnumChatVisibility visibility) {
        this.visibility = visibility;
    }
    
    public EntityPlayer.EnumChatVisibility getVisibility() {
        return this.visibility;
    }
}
