//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import java.util.concurrent.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.thread.*;

public class IDHelper extends SubscriberImpl implements Globals
{
    private static final ScheduledExecutorService THREAD;
    private volatile int highestID;
    private boolean updated;
    
    public IDHelper() {
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnObject.class, event -> this.checkID(((SPacketSpawnObject)event.getPacket()).getEntityID())));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnExperienceOrb.class, event -> this.checkID(((SPacketSpawnExperienceOrb)event.getPacket()).getEntityID())));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnPlayer.class, event -> this.checkID(((SPacketSpawnPlayer)event.getPacket()).getEntityID())));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnGlobalEntity.class, event -> this.checkID(((SPacketSpawnGlobalEntity)event.getPacket()).getEntityId())));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnPainting.class, event -> this.checkID(((SPacketSpawnPainting)event.getPacket()).getEntityID())));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnMob.class, event -> this.checkID(((SPacketSpawnMob)event.getPacket()).getEntityID())));
    }
    
    public int getHighestID() {
        return this.highestID;
    }
    
    public void setHighestID(final int id) {
        this.highestID = id;
    }
    
    public boolean isUpdated() {
        return this.updated;
    }
    
    public void setUpdated(final boolean updated) {
        this.updated = updated;
    }
    
    public void update() {
        int highest = this.getHighestID();
        for (final Entity entity : IDHelper.mc.world.loadedEntityList) {
            if (entity.getEntityId() > highest) {
                highest = entity.getEntityId();
            }
        }
        if (highest > this.highestID) {
            this.highestID = highest;
        }
    }
    
    public boolean isSafe(final List<EntityPlayer> players, final boolean holdingCheck, final boolean toolCheck) {
        if (!holdingCheck) {
            return true;
        }
        for (final EntityPlayer player : players) {
            if (this.isDangerous(player, true, toolCheck)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isDangerous(final EntityPlayer player, final boolean holdingCheck, final boolean toolCheck) {
        return holdingCheck && (InventoryUtil.isHolding((EntityLivingBase)player, (Item)Items.BOW) || InventoryUtil.isHolding((EntityLivingBase)player, Items.EXPERIENCE_BOTTLE) || (toolCheck && (player.getHeldItemMainhand().getItem() instanceof ItemPickaxe || player.getHeldItemMainhand().getItem() instanceof ItemSpade)));
    }
    
    public void attack(final SwingTime breakSwing, final PlaceSwing godSwing, final int idOffset, final int packets, final int sleep) {
        if (sleep <= 0) {
            this.attackPackets(breakSwing, godSwing, idOffset, packets);
        }
        else {
            IDHelper.THREAD.schedule(() -> {
                this.update();
                this.attackPackets(breakSwing, godSwing, idOffset, packets);
            }, sleep, TimeUnit.MILLISECONDS);
        }
    }
    
    private void attackPackets(final SwingTime breakSwing, final PlaceSwing godSwing, final int idOffset, final int packets) {
        for (int i = 0; i < packets; ++i) {
            final int id = this.highestID + idOffset + i;
            final Entity entity = IDHelper.mc.world.getEntityByID(id);
            if (entity == null || entity instanceof EntityEnderCrystal) {
                if (godSwing == PlaceSwing.Always && breakSwing == SwingTime.Pre) {
                    Swing.Packet.swing(EnumHand.MAIN_HAND);
                }
                final CPacketUseEntity packet = PacketUtil.attackPacket(id);
                IDHelper.mc.player.connection.sendPacket((Packet)packet);
                if (godSwing == PlaceSwing.Always && breakSwing == SwingTime.Post) {
                    Swing.Packet.swing(EnumHand.MAIN_HAND);
                }
            }
        }
        if (godSwing == PlaceSwing.Once) {
            Swing.Packet.swing(EnumHand.MAIN_HAND);
        }
    }
    
    private void checkID(final int id) {
        if (id > this.highestID) {
            this.highestID = id;
        }
    }
    
    static {
        THREAD = ThreadUtil.newDaemonScheduledExecutor("ID-Helper");
    }
}
