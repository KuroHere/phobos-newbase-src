//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.phase.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.network.*;

final class ListenerUpdate extends ModuleListener<Phase, UpdateEvent>
{
    public ListenerUpdate(final Phase module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        if (((Phase)this.module).mode.getValue() == PhaseMode.NoClip) {
            ListenerUpdate.mc.player.noClip = true;
            ListenerUpdate.mc.player.onGround = false;
            ListenerUpdate.mc.player.fallDistance = 0.0f;
        }
        if (((Phase)this.module).mode.getValue() == PhaseMode.Constantiam && MovementUtil.isMoving() && ((Phase)this.module).constTeleport.getValue() && ((Phase)this.module).isPhasing()) {
            final double multiplier = ((Phase)this.module).constSpeed.getValue();
            final double mx = -Math.sin(Math.toRadians(ListenerUpdate.mc.player.rotationYaw));
            final double mz = Math.cos(Math.toRadians(ListenerUpdate.mc.player.rotationYaw));
            final double x = ListenerUpdate.mc.player.movementInput.field_192832_b * multiplier * mx + ListenerUpdate.mc.player.movementInput.moveStrafe * multiplier * mz;
            final double z = ListenerUpdate.mc.player.movementInput.field_192832_b * multiplier * mz - ListenerUpdate.mc.player.movementInput.moveStrafe * multiplier * mx;
            ListenerUpdate.mc.player.setPosition(ListenerUpdate.mc.player.posX + x, ListenerUpdate.mc.player.posY, ListenerUpdate.mc.player.posZ + z);
        }
        if (((Phase)this.module).mode.getValue() == PhaseMode.ConstantiamNew) {
            final double multiplier = 0.3;
            final double mx = -Math.sin(Math.toRadians(ListenerUpdate.mc.player.rotationYaw));
            final double mz = Math.cos(Math.toRadians(ListenerUpdate.mc.player.rotationYaw));
            final double x = ListenerUpdate.mc.player.movementInput.field_192832_b * multiplier * mx + ListenerUpdate.mc.player.movementInput.moveStrafe * multiplier * mz;
            final double z = ListenerUpdate.mc.player.movementInput.field_192832_b * multiplier * mz - ListenerUpdate.mc.player.movementInput.moveStrafe * multiplier * mx;
            if (ListenerUpdate.mc.player.isCollidedHorizontally && !ListenerUpdate.mc.player.isOnLadder()) {
                PacketUtil.doPosition(ListenerUpdate.mc.player.posX + x, ListenerUpdate.mc.player.posY, ListenerUpdate.mc.player.posZ + z, false);
                for (int i = 1; i < 10; ++i) {
                    PacketUtil.doPosition(ListenerUpdate.mc.player.posX, 8.988465674311579E307, ListenerUpdate.mc.player.posZ, false);
                }
                ListenerUpdate.mc.player.setPosition(ListenerUpdate.mc.player.posX + x, ListenerUpdate.mc.player.posY, ListenerUpdate.mc.player.posZ + z);
            }
        }
    }
}
