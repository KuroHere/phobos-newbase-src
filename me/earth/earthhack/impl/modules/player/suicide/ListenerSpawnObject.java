//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.suicide;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerSpawnObject extends ModuleListener<Suicide, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final Suicide module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if (event.getPacket().getType() != 51 || !((Suicide)this.module).instant.getValue() || !((Suicide)this.module).breakTimer.passed(((Suicide)this.module).breakDelay.getValue()) || ((Suicide)this.module).mode.getValue() == SuicideMode.Command) {
            return;
        }
        final EntityEnderCrystal crystal = new EntityEnderCrystal((World)ListenerSpawnObject.mc.world, event.getPacket().getX(), event.getPacket().getY(), event.getPacket().getZ());
        if (RotationUtil.getRotationPlayer().getDistanceSqToEntity((Entity)crystal) >= MathUtil.square(((Suicide)this.module).breakRange.getValue()) || (RotationUtil.getRotationPlayer().getDistanceSqToEntity((Entity)crystal) >= MathUtil.square(((Suicide)this.module).trace.getValue()) && !RotationUtil.getRotationPlayer().canEntityBeSeen((Entity)crystal)) || (((Suicide)this.module).rotate.getValue() && !RotationUtil.isLegit((Entity)crystal, (Entity)crystal))) {
            return;
        }
        if (!((Suicide)this.module).instantCalc.getValue()) {
            if (((Suicide)this.module).placed.remove(new BlockPos(crystal.posX, crystal.posY - 1.0, crystal.posZ))) {
                PacketUtil.attack(event.getPacket().getEntityID());
                ((Suicide)this.module).breakTimer.reset();
            }
            return;
        }
        final float damage = DamageUtil.calculate((Entity)crystal);
        if (damage > ((Suicide)this.module).minInstant.getValue()) {
            PacketUtil.attack(event.getPacket().getEntityID());
            ((Suicide)this.module).breakTimer.reset();
        }
    }
}
