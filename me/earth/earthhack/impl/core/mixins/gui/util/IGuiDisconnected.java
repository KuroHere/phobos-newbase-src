// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui.util;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.util.text.*;
import java.util.*;

@Mixin({ GuiDisconnected.class })
public interface IGuiDisconnected
{
    @Accessor("parentScreen")
    GuiScreen getParentScreen();
    
    @Accessor("reason")
    String getReason();
    
    @Accessor("message")
    ITextComponent getMessage();
    
    @Accessor("multilineMessage")
    List<String> getMultilineMessage();
}
