//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.serializer.friend;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.modules.client.pingbypass.serializer.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.client.event.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.*;
import net.minecraft.client.network.*;
import java.util.*;

public class FriendSerializer extends SubscriberImpl implements Serializer<PlayerEvent>, Globals
{
    private final Observer<PlayerEvent> observer;
    private final Set<PlayerEvent> changed;
    private boolean cleared;
    
    public FriendSerializer() {
        this.observer = new ListenerFriends(this);
        this.changed = new LinkedHashSet<PlayerEvent>();
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerDisconnect(this));
    }
    
    public void clear() {
        synchronized (this.changed) {
            this.changed.clear();
            Managers.FRIENDS.getPlayersWithUUID().forEach((k, v) -> {
                final PlayerEvent event = new PlayerEvent(PlayerEventType.ADD, k, v);
                this.changed.add(event);
                return;
            });
            this.cleared = true;
        }
    }
    
    protected void onChange(final PlayerEvent event) {
        if (!event.isCancelled()) {
            synchronized (this.changed) {
                this.changed.add(event);
            }
        }
    }
    
    protected void onTick() {
        if (FriendSerializer.mc.player != null && FriendSerializer.mc.getConnection() != null && !this.changed.isEmpty()) {
            if (this.cleared) {
                FriendSerializer.mc.getConnection().sendPacket((Packet)new CPacketChatMessage("@ServerFriend clear"));
                this.cleared = false;
            }
            final PlayerEvent friend = this.pollFriend();
            if (friend != null) {
                this.serializeAndSend(friend);
            }
        }
    }
    
    @Override
    public void serializeAndSend(final PlayerEvent event) {
        String command = "@ServerFriend";
        if (event.getType() == PlayerEventType.ADD) {
            command = command + " add " + event.getName() + " " + event.getUuid();
        }
        else {
            command = command + " del " + event.getName();
        }
        Earthhack.getLogger().info(command);
        final CPacketChatMessage packet = new CPacketChatMessage(command);
        Objects.requireNonNull(FriendSerializer.mc.getConnection()).sendPacket((Packet)packet);
    }
    
    private PlayerEvent pollFriend() {
        if (!this.changed.isEmpty()) {
            final PlayerEvent friend = this.changed.iterator().next();
            this.changed.remove(friend);
            return friend;
        }
        return null;
    }
    
    public Observer<PlayerEvent> getObserver() {
        return this.observer;
    }
}
