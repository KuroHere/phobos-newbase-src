// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.item;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.item.*;

@Mixin({ ItemTool.class })
public interface IItemTool
{
    @Accessor("attackDamage")
    float getAttackDamage();
    
    @Accessor("toolMaterial")
    Item.ToolMaterial getToolMaterial();
}
