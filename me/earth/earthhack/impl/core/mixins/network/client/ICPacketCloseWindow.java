// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketCloseWindow.class })
public interface ICPacketCloseWindow
{
    @Accessor("windowId")
    int getWindowId();
}
