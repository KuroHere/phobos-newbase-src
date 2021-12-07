//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.media.*;
import me.earth.earthhack.impl.modules.client.pingbypass.packets.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.client.pingbypass.serializer.setting.*;
import me.earth.earthhack.impl.modules.client.pingbypass.serializer.friend.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautototem.*;
import me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal.*;
import me.earth.earthhack.impl.modules.client.pingbypass.submodules.sinventory.*;
import me.earth.earthhack.impl.modules.client.pingbypass.submodules.sSafety.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.register.exception.*;
import me.earth.earthhack.impl.modules.player.fakeplayer.*;
import me.earth.earthhack.impl.modules.misc.pingspoof.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.impl.managers.client.event.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.register.*;

public class PingBypass extends Module
{
    private static final ModuleCache<Media> MEDIA;
    private final PayloadManager payloadManager;
    protected final Setting<String> port;
    protected final Setting<Integer> pings;
    protected SettingSerializer serializer;
    protected FriendSerializer friendSerializer;
    protected StopWatch timer;
    protected String serverName;
    protected long startTime;
    protected int serverPing;
    protected long ping;
    protected boolean handled;
    
    public PingBypass() {
        super("PingBypass", Category.Client);
        this.payloadManager = new PayloadManager();
        this.port = this.register(new StringSetting("Port", "0"));
        this.pings = this.register(new NumberSetting("Pings", 5, 1, 30));
        this.timer = new StopWatch();
        this.register(new BooleanSetting("NoRender", false));
        this.register(new StringSetting("IP", "Proxy-IP"));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerKeepAlive(this));
        this.listeners.add(new ListenerLogin(this));
        this.listeners.add(new ListenerCustomPayload(this.payloadManager));
        final ServerAutoTotem sAutoTotem = new ServerAutoTotem(this);
        final ServerAutoCrystal sCrystal = new ServerAutoCrystal(this);
        final ServerInventory sInventory = new ServerInventory(this);
        final ServerSafety sSafety = new ServerSafety(this);
        try {
            ((IterationRegister<ServerAutoTotem>)Managers.MODULES).register(sAutoTotem);
            ((IterationRegister<ServerAutoCrystal>)Managers.MODULES).register(sCrystal);
            ((IterationRegister<ServerInventory>)Managers.MODULES).register(sInventory);
            ((IterationRegister<ServerSafety>)Managers.MODULES).register(sSafety);
        }
        catch (AlreadyRegisteredException e) {
            throw new IllegalStateException("Couldn't register PingBypass Submodules : " + e.getTrying().getName(), e);
        }
        this.serializer = new SettingSerializer(new Module[] { this, sAutoTotem, sCrystal, Managers.MODULES.getByClass(FakePlayer.class), sSafety, Managers.MODULES.getByClass(PingSpoof.class), sInventory });
        this.listeners.addAll(this.serializer.getListeners());
        this.friendSerializer = new FriendSerializer();
        this.listeners.addAll(this.friendSerializer.getListeners());
        this.registerPayloadReaders();
        this.setData(new PingBypassData(this));
    }
    
    @Override
    protected void onEnable() {
        Managers.FRIENDS.addObserver(this.friendSerializer.getObserver());
        ServerUtil.disconnectFromMC("PingBypass enabled.");
        this.serializer.clear();
        this.friendSerializer.clear();
    }
    
    @Override
    protected void onDisable() {
        Managers.FRIENDS.removeObserver(this.friendSerializer.getObserver());
        ServerUtil.disconnectFromMC("PingBypass disabled.");
        this.serializer.clear();
        this.friendSerializer.clear();
    }
    
    @Override
    public String getDisplayInfo() {
        return this.ping + "ms";
    }
    
    private void registerPayloadReaders() {
        this.payloadManager.register((short)0, buffer -> {
            final String name = buffer.readStringFromBuffer(32767);
            PingBypass.mc.addScheduledTask(() -> {
                this.setServerName(name);
                PingBypass.MEDIA.computeIfPresent(media -> media.setPingBypassName(this.getServerName()));
            });
        });
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public void setServerName(final String name) {
        this.serverName = name;
    }
    
    public int getServerPing() {
        return this.serverPing;
    }
    
    public int getPort() {
        try {
            return Integer.parseInt(this.port.getValue());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public PayloadManager getPayloadManager() {
        return this.payloadManager;
    }
    
    static {
        MEDIA = Caches.getModule(Media.class);
    }
}
