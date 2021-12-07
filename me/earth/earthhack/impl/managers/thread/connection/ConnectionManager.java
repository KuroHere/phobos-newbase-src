//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.connection;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.event.bus.*;
import java.util.function.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.lookup.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class ConnectionManager extends SubscriberImpl implements Globals
{
    public ConnectionManager() {
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketPlayerListItem>>(PacketEvent.Receive.class, SPacketPlayerListItem.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketPlayerListItem> event) {
                ConnectionManager.this.onEvent(event);
            }
        });
    }
    
    private void onEvent(final PacketEvent.Receive<SPacketPlayerListItem> event) {
        final SPacketPlayerListItem packet = event.getPacket();
        if (ConnectionManager.mc.world == null || (SPacketPlayerListItem.Action.ADD_PLAYER != packet.getAction() && SPacketPlayerListItem.Action.REMOVE_PLAYER != packet.getAction())) {
            return;
        }
        packet.getEntries().stream().filter(Objects::nonNull).filter(data -> (data.getProfile().getName() != null && !data.getProfile().getName().isEmpty()) || data.getProfile().getId() != null).forEach(data -> {
            switch (packet.getAction()) {
                case ADD_PLAYER: {
                    this.onAdd(data);
                    break;
                }
                case REMOVE_PLAYER: {
                    this.onRemove(data);
                    break;
                }
            }
        });
    }
    
    private void onAdd(final SPacketPlayerListItem.AddPlayerData data) {
        if (Bus.EVENT_BUS.hasSubscribers(ConnectionEvent.Join.class)) {
            final UUID uuid = data.getProfile().getId();
            final String packetName = data.getProfile().getName();
            final EntityPlayer player = ConnectionManager.mc.world.getPlayerEntityByUUID(uuid);
            if (packetName == null && player == null) {
                Managers.LOOK_UP.doLookUp(new LookUp(LookUp.Type.NAME, uuid) {
                    @Override
                    public void onSuccess() {
                        Bus.EVENT_BUS.post(new ConnectionEvent.Join(this.name, this.uuid, null));
                    }
                    
                    @Override
                    public void onFailure() {
                    }
                });
                return;
            }
            Bus.EVENT_BUS.post(new ConnectionEvent.Join(packetName, uuid, player));
        }
    }
    
    private void onRemove(final SPacketPlayerListItem.AddPlayerData data) {
        if (Bus.EVENT_BUS.hasSubscribers(ConnectionEvent.Leave.class)) {
            final UUID uuid = data.getProfile().getId();
            final String packetName = data.getProfile().getName();
            final EntityPlayer player = ConnectionManager.mc.world.getPlayerEntityByUUID(uuid);
            if (packetName == null && player == null) {
                Managers.LOOK_UP.doLookUp(new LookUp(LookUp.Type.NAME, uuid) {
                    @Override
                    public void onSuccess() {
                        Bus.EVENT_BUS.post(new ConnectionEvent.Leave(this.name, this.uuid, null));
                    }
                    
                    @Override
                    public void onFailure() {
                    }
                });
                return;
            }
            Bus.EVENT_BUS.post(new ConnectionEvent.Leave(packetName, uuid, player));
        }
    }
}
