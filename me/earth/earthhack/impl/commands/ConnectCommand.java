//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;

public class ConnectCommand extends Command implements Globals, CommandScheduler
{
    private ServerList cachedServerList;
    private long lastCache;
    
    public ConnectCommand() {
        super(new String[][] { { "connect" }, { "ip" } });
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length < 2) {
            ChatUtil.sendMessage("§cPlease specify an IP!");
            return;
        }
        ServerUtil.disconnectFromMC("Disconnecting.");
        ConnectCommand.SCHEDULER.submit(() -> ConnectCommand.mc.addScheduledTask(() -> {
            final Minecraft mc = ConnectCommand.mc;
            new(net.minecraft.client.multiplayer.GuiConnecting.class)();
            new GuiMultiplayer((GuiScreen)new GuiMainMenu());
            final GuiMultiplayer guiMultiplayer;
            new GuiConnecting((GuiScreen)guiMultiplayer, ConnectCommand.mc, new ServerData(I18n.format("selectServer.defaultName", new Object[0]), args[1], false));
            final GuiScreen guiScreen;
            mc.displayGuiScreen(guiScreen);
        }), 100);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (this.cachedServerList == null || System.currentTimeMillis() - this.lastCache > 60000L) {
            (this.cachedServerList = new ServerList(ConnectCommand.mc)).loadServerList();
            this.lastCache = System.currentTimeMillis();
        }
        if (args.length == 2) {
            for (int i = 0; i < this.cachedServerList.countServers(); ++i) {
                final ServerData data = this.cachedServerList.getServerData(i);
                if (data.serverIP != null && TextUtil.startsWith(data.serverIP, args[1])) {
                    return new PossibleInputs(TextUtil.substring(data.serverIP, args[1].length()), "");
                }
            }
        }
        if (args.length >= 2) {
            return PossibleInputs.empty();
        }
        return super.getPossibleInputs(args);
    }
}
