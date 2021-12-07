//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.thread.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.impl.modules.client.server.host.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.modules.client.server.client.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.modules.client.server.protocol.*;
import me.earth.earthhack.impl.modules.client.server.util.*;
import me.earth.earthhack.impl.modules.client.server.protocol.handlers.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.player.*;

public class ServerModule extends Module implements IShutDownHandler, GlobalExecutor, IVelocityHandler
{
    protected final Setting<ServerMode> mode;
    protected final Setting<String> ip;
    protected final Setting<String> port;
    protected final Setting<Integer> max;
    protected final Setting<Boolean> clientInput;
    protected final Setting<String> name;
    protected final Setting<Boolean> sync;
    protected final IServerList serverList;
    protected final IPacketManager sPackets;
    protected final IPacketManager cPackets;
    protected ServerMode currentMode;
    protected IConnectionManager connectionManager;
    protected IClient client;
    protected IHost host;
    protected boolean isEating;
    protected double lastX;
    protected double lastY;
    protected double lastZ;
    
    public ServerModule() {
        super("Server", Category.Client);
        this.mode = this.register(new EnumSetting("Mode", ServerMode.Client));
        this.ip = this.register(new StringSetting("IP", "127.0.0.1"));
        this.port = this.register(new StringSetting("Port", "0"));
        this.max = this.register(new NumberSetting("Connections", 50, 1, 50));
        this.clientInput = this.register(new BooleanSetting("ClientMessages", false));
        this.name = this.register(new StringSetting("Name", "3arthh4ck-Host"));
        this.sync = this.register(new BooleanSetting("Sync", false));
        this.serverList = new SimpleServerList();
        this.sPackets = new SimplePacketManager();
        this.cPackets = new SimplePacketManager();
        this.listeners.addAll(new ListenerCPacket(this).getListeners());
        this.listeners.add(new ListenerStartEating(this));
        this.listeners.add(new ListenerStopEating(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerNoUpdate(this));
        this.name.setValue(ServerModule.mc.getSession().getProfile().getName());
        final Observer<Object> observer = e -> {
            if (this.isEnabled()) {
                ChatUtil.sendMessageScheduled("The server has to be restarted in order for the changes to take effect.");
            }
            return;
        };
        this.mode.addObserver(observer);
        this.ip.addObserver(observer);
        this.port.addObserver(observer);
        this.max.addObserver(observer);
        this.clientInput.addObserver(observer);
        this.name.addObserver(observer);
        this.setupConnectionManagers();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.host != null) {
            return this.host.getConnectionManager().getConnections().size() + "";
        }
        return null;
    }
    
    @Override
    protected void onEnable() {
        try {
            final boolean receive = this.clientInput.getValue();
            final int port = Integer.parseInt(this.port.getValue());
            this.currentMode = this.mode.getValue();
            switch (this.currentMode) {
                case Host: {
                    this.connectionManager = new SimpleConnectionManager(this.cPackets, this.max.getValue());
                    this.host = Host.createAndStart(ServerModule.EXECUTOR, this.connectionManager, this, port, receive);
                    ModuleUtil.sendMessage(this, "§aServer is listening on port: §f" + this.host.getPort() + "§a" + ".");
                    break;
                }
                case Client: {
                    this.client = new Client(this.sPackets, this.serverList, this.ip.getValue(), port);
                    Managers.THREAD.submit((SafeRunnable)this.client);
                    final String s = this.name.getValue();
                    this.client.setName(s);
                    ProtocolUtil.sendMessage(this.client, 0, s);
                    break;
                }
            }
        }
        catch (NumberFormatException e) {
            ModuleUtil.disableRed(this, "Couldn't parse port: " + this.port.getValue() + ".");
        }
        catch (Throwable t) {
            ModuleUtil.disableRed(this, t.getMessage());
        }
    }
    
    @Override
    protected void onDisable() {
        if (this.client != null) {
            this.client.close();
            this.client = null;
        }
        if (this.host != null) {
            this.host.close();
            this.host = null;
        }
        this.connectionManager = null;
        this.serverList.set(new IConnectionEntry[0]);
    }
    
    @Override
    public void disable(final String message) {
        ServerModule.mc.addScheduledTask(() -> ModuleUtil.disableRed(this, message));
    }
    
    public IClient getClient() {
        return this.client;
    }
    
    public IHost getHost() {
        return this.host;
    }
    
    private void setupConnectionManagers() {
        final ILogger logger = new ChatLogger();
        this.sPackets.add(3, new PacketHandler(logger));
        this.sPackets.add(9, new PositionHandler(logger));
        this.sPackets.add(10, new VelocityHandler(this));
        this.sPackets.add(11, new EatingHandler());
        this.sPackets.add(8, new ServerListHandler(this.serverList));
    }
    
    @Override
    public void onVelocity(final double x, final double y, final double z) {
        final EntityPlayer player = RotationUtil.getRotationPlayer();
        if (player == null) {
            return;
        }
        this.lastX = x;
        this.lastY = y;
        this.lastZ = z;
        ServerModule.mc.addScheduledTask(() -> player.setVelocity(x, y, z));
    }
    
    @Override
    public double getLastX() {
        return this.lastX;
    }
    
    @Override
    public double getLastY() {
        return this.lastY;
    }
    
    @Override
    public double getLastZ() {
        return this.lastZ;
    }
}
