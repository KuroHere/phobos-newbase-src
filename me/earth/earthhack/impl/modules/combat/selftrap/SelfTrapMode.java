//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.selftrap;

import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public enum SelfTrapMode
{
    Obsidian(Blocks.OBSIDIAN, new Vec3i[] { new Vec3i(0, 2, 0) }), 
    Web(Blocks.WEB, new Vec3i[] { Vec3i.NULL_VECTOR }), 
    HighWeb(Blocks.WEB, new Vec3i[] { new Vec3i(0, 1, 0) }), 
    FullWeb(Blocks.WEB, new Vec3i[] { Vec3i.NULL_VECTOR, new Vec3i(0, 1, 0) });
    
    private final Vec3i[] offsets;
    private final Block block;
    
    private SelfTrapMode(final Block block, final Vec3i[] offsets) {
        this.offsets = offsets;
        this.block = block;
    }
    
    public Vec3i[] getOffsets() {
        return this.offsets;
    }
    
    public Block getBlock() {
        return this.block;
    }
}
