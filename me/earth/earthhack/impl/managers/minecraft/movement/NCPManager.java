//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.movement;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class NCPManager extends SubscriberImpl implements Globals
{
    private final AtomicLong lagTimer;
    private final StopWatch clickTimer;
    private boolean endedSprint;
    private boolean endedSneak;
    private boolean windowClicks;
    private boolean strict;
    
    public NCPManager() {
        this.lagTimer = new AtomicLong();
        this.clickTimer = new StopWatch();
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketPlayerPosLook>>(PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketPlayerPosLook.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
                NCPManager.this.lagTimer.set(System.currentTimeMillis());
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                NCPManager.this.endedSneak = false;
                NCPManager.this.endedSprint = false;
                NCPManager.this.windowClicks = false;
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Send<CPacketClickWindow>>(PacketEvent.Send.class, -1000, CPacketClickWindow.class) {
            @Override
            public void invoke(final PacketEvent.Send<CPacketClickWindow> event) {
                if (!NCPManager.this.isStrict() || event.isCancelled()) {
                    return;
                }
                if (Globals.mc.player.isActiveItemStackBlocking()) {
                    Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> Globals.mc.playerController.onStoppedUsingItem((EntityPlayer)Globals.mc.player));
                }
                if (Managers.ACTION.isSneaking()) {
                    NCPManager.this.endedSneak = true;
                    Globals.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Globals.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (Managers.ACTION.isSprinting()) {
                    NCPManager.this.endedSprint = true;
                    Globals.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Globals.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Post<CPacketClickWindow>>(PacketEvent.Post.class, -1000, CPacketClickWindow.class) {
            @Override
            public void invoke(final PacketEvent.Post<CPacketClickWindow> event) {
                NCPManager.this.clickTimer.reset();
                if (!NCPManager.this.windowClicks && NCPManager.this.isStrict()) {
                    NCPManager.this.release();
                }
            }
        });
    }
    
    public StopWatch getClickTimer() {
        return this.clickTimer;
    }
    
    public boolean isStrict() {
        return this.strict;
    }
    
    public void setStrict(final boolean strict) {
        if (this.strict && !strict) {
            this.releaseMultiClick();
        }
        this.strict = strict;
    }
    
    public void startMultiClick() {
        this.windowClicks = true;
    }
    
    public void releaseMultiClick() {
        this.windowClicks = false;
        this.release();
    }
    
    public boolean passed(final int ms) {
        return System.currentTimeMillis() - this.lagTimer.get() >= ms;
    }
    
    public long getTimeStamp() {
        return this.lagTimer.get();
    }
    
    public void reset() {
        this.lagTimer.set(System.currentTimeMillis());
    }
    
    private void release() {
        if (this.endedSneak) {
            this.endedSneak = false;
            NCPManager.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NCPManager.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        if (this.endedSprint) {
            this.endedSprint = false;
            NCPManager.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NCPManager.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
}
