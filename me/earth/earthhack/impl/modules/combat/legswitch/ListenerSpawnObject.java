//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.combat.legswitch.modes.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.entity.*;

final class ListenerSpawnObject extends ModuleListener<LegSwitch, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final LegSwitch module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        final EntityPlayerSP player = ListenerSpawnObject.mc.player;
        if (((LegSwitch)this.module).instant.getValue() && player != null && Managers.SWITCH.getLastSwitch() >= ((LegSwitch)this.module).coolDown.getValue() && !DamageUtil.isWeaknessed() && ((LegSwitch)this.module).timer.passed(((LegSwitch)this.module).delay.getValue()) && event.getPacket().getType() == 51) {
            final SPacketSpawnObject packet = event.getPacket();
            final LegConstellation constellation = ((LegSwitch)this.module).constellation;
            if (constellation != null && !constellation.firstNeedsObby && !constellation.secondNeedsObby && (InventoryUtil.isHolding(Items.END_CRYSTAL) || ((LegSwitch)this.module).autoSwitch.getValue() != LegAutoSwitch.None)) {
                final double x = packet.getX();
                final double y = packet.getY();
                final double z = packet.getZ();
                final BlockPos pos = new BlockPos(x, y - 1.0, z);
                final BlockPos previous = ((LegSwitch)this.module).targetPos;
                if (!pos.equals((Object)previous)) {
                    return;
                }
                final BlockPos targetPos = constellation.firstPos.equals((Object)previous) ? constellation.secondPos : constellation.firstPos;
                final EntityEnderCrystal entity = new EntityEnderCrystal((World)ListenerSpawnObject.mc.world, x, y, z);
                if ((!((LegSwitch)this.module).rotate.getValue().noRotate(ACRotate.Break) && !RotationUtil.isLegit((Entity)entity, new Entity[0])) || (!((LegSwitch)this.module).rotate.getValue().noRotate(ACRotate.Place) && !RotationUtil.isLegit(targetPos))) {
                    return;
                }
                RayTraceResult result = RotationUtil.rayTraceTo(targetPos, (IBlockAccess)ListenerSpawnObject.mc.world);
                if (result == null) {
                    result = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP);
                }
                entity.setUniqueId(packet.getUniqueId());
                entity.setEntityId(packet.getEntityID());
                entity.setShowBottom(false);
                final int slot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
                final RayTraceResult finalResult = result;
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                    final int last = player.inventory.currentItem;
                    final EnumHand hand = (player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || slot != -2) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                    player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)entity));
                    player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    InventoryUtil.switchTo(slot);
                    player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetPos, finalResult.sideHit, hand, (float)finalResult.hitVec.xCoord, (float)finalResult.hitVec.yCoord, (float)finalResult.hitVec.zCoord));
                    player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                    if (last != slot && ((LegSwitch)this.module).autoSwitch.getValue() != LegAutoSwitch.Keep) {
                        InventoryUtil.switchTo(last);
                    }
                    return;
                });
                ((LegSwitch)this.module).targetPos = targetPos;
                if (((LegSwitch)this.module).setDead.getValue()) {
                    event.addPostEvent(() -> {
                        if (ListenerSpawnObject.mc.world != null) {
                            final Entity e = ListenerSpawnObject.mc.world.getEntityByID(packet.getEntityID());
                            if (e != null) {
                                Managers.SET_DEAD.setDead(e);
                            }
                        }
                        return;
                    });
                }
                ((LegSwitch)this.module).timer.reset(((LegSwitch)this.module).delay.getValue());
            }
        }
    }
}
