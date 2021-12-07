//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import java.util.*;
import com.mojang.authlib.*;

public class DummyPlayer extends EntityPlayer implements Dummy
{
    public DummyPlayer(final World worldIn) {
        super(worldIn, new GameProfile(UUID.randomUUID(), "Dummy-Player"));
    }
    
    public boolean isSpectator() {
        return false;
    }
    
    public boolean isCreative() {
        return false;
    }
}
