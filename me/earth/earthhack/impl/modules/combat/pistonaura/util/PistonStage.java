//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura.util;

import java.util.function.*;
import net.minecraft.util.math.*;

public enum PistonStage
{
    CRYSTAL(PistonData::getCrystalPos), 
    PISTON(PistonData::getPistonPos), 
    REDSTONE(PistonData::getRedstonePos), 
    BREAK(data -> data.getCrystalPos().up());
    
    private final Function<PistonData, BlockPos> function;
    
    private PistonStage(final Function<PistonData, BlockPos> function) {
        this.function = function;
    }
    
    public BlockPos getPos(final PistonData data) {
        return this.function.apply(data);
    }
}
