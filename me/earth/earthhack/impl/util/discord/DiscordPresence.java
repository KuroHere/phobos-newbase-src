//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.discord;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.rpc.*;
import club.minnced.discord.rpc.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.modules.*;

public class DiscordPresence
{
    private static final ModuleCache<RPC> RPC;
    public static DiscordRichPresence presence;
    private static final DiscordRPC rpc;
    private static Thread thread;
    private static int index;
    
    public static void start() {
        if (DiscordPresence.RPC.isEnabled()) {
            final DiscordEventHandlers handlers = new DiscordEventHandlers();
            DiscordPresence.rpc.Discord_Initialize("875058498868760648", handlers, true, "");
            DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
            DiscordPresence.presence.details = (DiscordPresence.RPC.get().customDetails.getValue() ? DiscordPresence.RPC.get().details.getValue() : ((Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) ? "In the main menu." : ("Playing " + ((Minecraft.getMinecraft().getCurrentServerData() != null) ? (DiscordPresence.RPC.get().showIP.getValue() ? ("on " + Minecraft.getMinecraft().getCurrentServerData().serverIP + ".") : " multiplayer.") : " singleplayer."))));
            DiscordPresence.presence.state = DiscordPresence.RPC.get().state.getValue();
            DiscordPresence.presence.largeImageKey = "phobos";
            DiscordPresence.presence.largeImageText = "3arthh4ck 1.3.1";
            DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
            (DiscordPresence.thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    DiscordPresence.rpc.Discord_RunCallbacks();
                    final DiscordRichPresence presence = DiscordPresence.presence;
                    String string;
                    if (DiscordPresence.RPC.get().customDetails.getValue()) {
                        string = DiscordPresence.RPC.get().details.getValue();
                    }
                    else if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
                        string = "In the main menu.";
                    }
                    else {
                        new StringBuilder().append("Playing ");
                        String string2;
                        if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                            if (DiscordPresence.RPC.get().showIP.getValue()) {
                                string2 = "on " + Minecraft.getMinecraft().getCurrentServerData().serverIP + ".";
                            }
                            else {
                                string2 = " multiplayer.";
                            }
                        }
                        else {
                            string2 = " singleplayer.";
                        }
                        final StringBuilder sb;
                        string = sb.append(string2).toString();
                    }
                    presence.details = string;
                    DiscordPresence.presence.state = DiscordPresence.RPC.get().state.getValue();
                    if (DiscordPresence.RPC.get().froggers.getValue()) {
                        if (DiscordPresence.index == 30) {
                            DiscordPresence.index = 1;
                        }
                        DiscordPresence.presence.largeImageKey = "frog_" + DiscordPresence.index;
                        ++DiscordPresence.index;
                    }
                    DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
                    try {
                        Thread.sleep(2000L);
                    }
                    catch (InterruptedException ex) {}
                }
            }, "RPC-Callback-Handler")).start();
        }
    }
    
    public static void stop() {
        if (DiscordPresence.thread != null && !DiscordPresence.thread.isInterrupted()) {
            DiscordPresence.thread.interrupt();
        }
        DiscordPresence.rpc.Discord_Shutdown();
    }
    
    static {
        RPC = Caches.getModule(RPC.class);
        DiscordPresence.presence = new DiscordRichPresence();
        rpc = DiscordRPC.INSTANCE;
        DiscordPresence.index = 1;
    }
}
