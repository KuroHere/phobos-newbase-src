//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.nbt.*;

public class NBTTagCompoundArgument extends AbstractArgument<NBTTagCompound>
{
    public NBTTagCompoundArgument() {
        super(NBTTagCompound.class);
    }
    
    @Override
    public NBTTagCompound fromString(final String argument) throws ArgParseException {
        NBTTagCompound compound;
        try {
            compound = JsonToNBT.getTagFromJson(argument);
        }
        catch (NBTException e) {
            throw new ArgParseException("Couldn't parse NBT: " + e.getMessage());
        }
        return compound;
    }
}
