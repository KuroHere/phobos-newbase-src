//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.blink;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.player.blink.mode.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.misc.collections.*;

public class Blink extends DisablingModule
{
    protected final Setting<PacketMode> packetMode;
    protected final Setting<Boolean> lagDisable;
    protected final Queue<Packet<?>> packets;
    protected EntityOtherPlayerMP fakePlayer;
    protected boolean shouldSend;
    
    public Blink() {
        super("Blink", Category.Player);
        this.packetMode = this.register(new EnumSetting("Packets", PacketMode.CPacketPlayer));
        this.lagDisable = this.register(new BooleanSetting("LagDisable", false));
        this.packets = new LinkedList<Packet<?>>();
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerPacket(this));
        final SimpleData data = new SimpleData(this, "Suppresses all movement packets send to the server. It will look like you don't move at all and then teleport when you disable this module.");
        data.register(this.packetMode, "-All cancels all packets. Will cause packet spam.\n-CPacketPlayer only cancels movement packets.\nFiltered leaves some packets through, still spammy.");
        data.register(this.lagDisable, "Disable this module when the server lags you back.");
        this.setData(data);
    }
    
    @Override
    protected void onEnable() {
        if (Blink.mc.player == null) {
            this.disable();
            return;
        }
        this.fakePlayer = PlayerUtil.createFakePlayerAndAddToWorld(Blink.mc.player.getGameProfile());
    }
    
    @Override
    protected void onDisable() {
        PlayerUtil.removeFakePlayer(this.fakePlayer);
        if (this.shouldSend && Blink.mc.getConnection() != null) {
            CollectionUtil.emptyQueue(this.packets, p -> Blink.mc.getConnection().sendPacket(p));
        }
        else {
            this.packets.clear();
        }
        this.shouldSend = true;
    }
    
    @Override
    public void onShutDown() {
        this.shouldSend = false;
        super.onShutDown();
    }
    
    @Override
    public void onDeath() {
        this.shouldSend = false;
        super.onShutDown();
    }
    
    @Override
    public void onDisconnect() {
        this.shouldSend = false;
        super.onShutDown();
    }
}
