// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main;

import me.earth.earthhack.impl.modules.client.server.util.*;
import me.earth.earthhack.impl.modules.client.server.protocol.handlers.*;
import me.earth.earthhack.impl.modules.client.server.client.*;
import me.earth.earthhack.impl.managers.thread.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.modules.client.server.main.command.handlers.*;
import me.earth.earthhack.impl.modules.client.server.main.command.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import java.io.*;

public class ClientMain
{
    public static void main(final String[] args) throws IOException {
        if (args.length < 4) {
            throw new IllegalArgumentException("Ip and port and name are missing!");
        }
        final String ip = args[1];
        final int port = Integer.parseInt(args[2]);
        final ILogger log = new SystemLogger();
        log.log("Attempting to connect to: " + ip + ", " + port);
        final IPacketManager manager = new SimplePacketManager();
        manager.add(2, new MessageHandler(log));
        manager.add(4, new MessageHandler(log, s -> "global: " + s));
        manager.add(6, new MessageHandler(log, s -> "error: " + s));
        manager.add(1, new MessageHandler(log, s -> "command: " + s));
        for (final int id : Protocol.ids()) {
            if (manager.getHandlerFor(id) == null) {
                manager.add(id, new CUnsupportedHandler(log, id));
            }
        }
        final IServerList serverList = new SimpleServerList();
        final Client client = new Client(manager, serverList, ip, port);
        GlobalExecutor.EXECUTOR.submit(client);
        log.log("Client connected. Enter \"exit\" or \"stop\" to exit.");
        log.log("Setting name to " + args[3] + "...");
        client.setName(args[3]);
        client.send(ProtocolUtil.writeString(0, args[3]));
        final BaseCommandLineHandler commands = new BaseCommandLineHandler(client);
        commands.add("msg", new MessageCommand(client, 2));
        commands.add("message", new MessageCommand(client, 2));
        commands.add("name", new MessageCommand(client, 0));
        commands.add("global", new MessageCommand(client, 4));
        commands.startListening();
    }
}
