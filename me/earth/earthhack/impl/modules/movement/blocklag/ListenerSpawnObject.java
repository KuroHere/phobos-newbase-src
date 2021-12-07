//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.blocklag;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;

final class ListenerSpawnObject extends ModuleListener<BlockLag, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final BlockLag module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if (!((BlockLag)this.module).instantAttack.getValue() || event.getPacket().getType() != 51 || ListenerSpawnObject.mc.world == null || Managers.SWITCH.getLastSwitch() > ((BlockLag)this.module).cooldown.getValue() || (!KeyBoardUtil.isKeyDown(((BlockLag)this.module).getBind()) && !((BlockLag)this.module).isEnabled()) || DamageUtil.isWeaknessed() || ListenerSpawnObject.mc.world.getBlockState(PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer()).up(2)).getMaterial().blocksMovement()) {
            return;
        }
        final EntityPlayerSP player = ListenerSpawnObject.mc.player;
        if (player != null) {
            final BlockPos pos = PositionUtil.getPosition((Entity)player);
            if (!ListenerSpawnObject.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
                return;
            }
            final EntityEnderCrystal crystal = new EntityEnderCrystal((World)ListenerSpawnObject.mc.world, event.getPacket().getX(), event.getPacket().getY(), event.getPacket().getZ());
            if (crystal.getEntityBoundingBox().intersectsWith(new AxisAlignedBB(pos))) {
                final float damage = DamageUtil.calculate((Entity)crystal);
                if (((BlockLag)this.module).pop.getValue().shouldPop(damage, ((BlockLag)this.module).popTime.getValue())) {
                    PacketUtil.attack(event.getPacket().getEntityID());
                }
            }
        }
    }
}
