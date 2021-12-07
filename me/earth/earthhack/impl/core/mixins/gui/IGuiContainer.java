// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ GuiContainer.class })
public interface IGuiContainer
{
    @Accessor("hoveredSlot")
    Slot getHoveredSlot();
}
