//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import net.minecraft.world.*;

public class EntityPlayerArgument extends AbstractEntityPlayerArgument<EntityPlayer>
{
    public EntityPlayerArgument() {
        super(EntityPlayer.class);
    }
    
    @Override
    protected EntityPlayer create() {
        return new DummyPlayer((World)EntityPlayerArgument.mc.world);
    }
}
