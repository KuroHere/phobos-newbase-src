//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import net.minecraft.server.*;
import java.net.*;
import net.minecraft.util.datafix.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.minecraft.*;
import com.mojang.authlib.*;
import net.minecraft.server.management.*;
import java.io.*;
import net.minecraft.world.*;

public class DummyServer extends MinecraftServer
{
    public DummyServer(final File anvilFileIn, final Proxy proxyIn, final DataFixer dataFixerIn, final YggdrasilAuthenticationService authServiceIn, final MinecraftSessionService sessionServiceIn, final GameProfileRepository profileRepoIn, final PlayerProfileCache profileCacheIn) {
        super(new File("*"), proxyIn, dataFixerIn, authServiceIn, sessionServiceIn, profileRepoIn, profileCacheIn);
    }
    
    public boolean startServer() throws IOException {
        return false;
    }
    
    public boolean canStructuresSpawn() {
        return false;
    }
    
    public GameType getGameType() {
        return GameType.SURVIVAL;
    }
    
    public EnumDifficulty getDifficulty() {
        return EnumDifficulty.PEACEFUL;
    }
    
    public boolean isHardcore() {
        return false;
    }
    
    public int getOpPermissionLevel() {
        return 0;
    }
    
    public boolean shouldBroadcastRconToOps() {
        return false;
    }
    
    public boolean shouldBroadcastConsoleToOps() {
        return false;
    }
    
    public boolean isDedicatedServer() {
        return false;
    }
    
    public boolean shouldUseNativeTransport() {
        return false;
    }
    
    public boolean isCommandBlockEnabled() {
        return false;
    }
    
    public String shareToLAN(final GameType type, final boolean allowCheats) {
        return "Dummy-Value";
    }
}
