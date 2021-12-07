//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.attack;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.network.*;

public class InstantAttackListener<M extends InstantAttackingModule> extends ModuleListener<M, PacketEvent.Receive<SPacketSpawnObject>>
{
    public InstantAttackListener(final M module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        final SPacketSpawnObject packet = event.getPacket();
        if (InstantAttackListener.mc.player == null || packet.getType() != 51 || !((InstantAttackingModule)this.module).getTimer().passed(((InstantAttackingModule)this.module).getBreakDelay()) || Managers.SWITCH.getLastSwitch() < ((InstantAttackingModule)this.module).getCooldown() || DamageUtil.isWeaknessed()) {
            return;
        }
        final EntityEnderCrystal crystal = new EntityEnderCrystal((World)InstantAttackListener.mc.world, packet.getX(), packet.getY(), packet.getZ());
        try {
            this.attack(crystal);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    protected void attack(final EntityEnderCrystal crystal) throws Throwable {
        if (!((InstantAttackingModule)this.module).shouldAttack(crystal)) {
            return;
        }
        final float damage = DamageUtil.calculate((Entity)crystal, (EntityLivingBase)RotationUtil.getRotationPlayer());
        if (!((InstantAttackingModule)this.module).getPop().shouldPop(damage, ((InstantAttackingModule)this.module).getPopTime())) {
            return;
        }
        PacketUtil.attack((Entity)crystal);
        ((InstantAttackingModule)this.module).postAttack(crystal);
    }
}
