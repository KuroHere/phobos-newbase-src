//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class DummyEntity extends Entity implements Dummy
{
    public DummyEntity(final World worldIn) {
        super(worldIn);
    }
    
    protected void entityInit() {
    }
    
    protected void readEntityFromNBT(final NBTTagCompound compound) {
    }
    
    protected void writeEntityToNBT(final NBTTagCompound compound) {
    }
    
    public boolean isDummy() {
        return true;
    }
}
