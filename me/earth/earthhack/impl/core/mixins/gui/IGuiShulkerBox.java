// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.entity.player.*;

@Mixin({ GuiShulkerBox.class })
public interface IGuiShulkerBox
{
    @Accessor("inventory")
    IInventory getInventory();
    
    @Accessor("playerInventory")
    InventoryPlayer getPlayerInventory();
}
