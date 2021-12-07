//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.network;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.pingspoof.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import net.minecraft.util.text.*;
import net.minecraft.client.network.*;
import me.earth.earthhack.impl.modules.*;

public class ServerUtil implements Globals
{
    private static final ModuleCache<PingSpoof> PING_SPOOF;
    private static final ModuleCache<PingBypass> PINGBYPASS;
    
    public static void disconnectFromMC(final String message) {
        final NetHandlerPlayClient connection = ServerUtil.mc.getConnection();
        if (connection != null) {
            connection.getNetworkManager().closeChannel((ITextComponent)new TextComponentString(message));
        }
    }
    
    public static int getPingNoPingSpoof() {
        int ping = getPing();
        if (ServerUtil.PING_SPOOF.isEnabled()) {
            ping -= ServerUtil.PING_SPOOF.get().getDelay();
        }
        return ping;
    }
    
    public static int getPing() {
        if (ServerUtil.PINGBYPASS.isEnabled()) {
            return ServerUtil.PINGBYPASS.get().getServerPing();
        }
        try {
            final NetHandlerPlayClient connection = ServerUtil.mc.getConnection();
            if (connection != null) {
                final NetworkPlayerInfo info = connection.getPlayerInfo(ServerUtil.mc.getConnection().getGameProfile().getId());
                if (info != null) {
                    return info.getResponseTime();
                }
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return 0;
    }
    
    static {
        PING_SPOOF = Caches.getModule(PingSpoof.class);
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
