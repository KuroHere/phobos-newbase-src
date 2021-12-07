// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main;

import me.earth.earthhack.impl.modules.client.server.util.*;
import me.earth.earthhack.impl.modules.client.server.protocol.handlers.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.managers.thread.*;
import me.earth.earthhack.impl.modules.client.server.host.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import java.io.*;

public class ServerMain
{
    public static void main(final String[] args) throws IOException {
        int port = 0;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        final int t = (int)(Runtime.getRuntime().availableProcessors() / 1.5) - 1;
        final ILogger logger = new SystemLogger();
        final IPacketManager pManager = new SimplePacketManager();
        final IConnectionManager cManager = new SimpleConnectionManager(pManager, t);
        pManager.add(0, new NameHandler(logger));
        pManager.add(2, new MessageHandler(logger));
        pManager.add(4, new GlobalMessageHandler(logger, cManager));
        for (final int id : Protocol.ids()) {
            if (pManager.getHandlerFor(id) == null) {
                pManager.add(id, new SUnsupportedHandler("This is a command-line server. This type of packet is not supported!"));
            }
        }
        final Host host = Host.createAndStart(GlobalExecutor.FIXED_EXECUTOR, cManager, new SystemShutdownHandler(), port, true);
        System.out.println("Listening on port: " + host.getPort() + ". Enter \"exit\" or \"stop\" to exit.");
        final BaseCommandLineHandler commandLine = new BaseCommandLineHandler(host);
        commandLine.startListening();
    }
}
