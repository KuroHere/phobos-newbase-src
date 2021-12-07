//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.freecam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.client.entity.*;

final class ListenerUpdate extends ModuleListener<Freecam, UpdateEvent>
{
    public ListenerUpdate(final Freecam module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        ListenerUpdate.mc.player.noClip = true;
        ListenerUpdate.mc.player.setVelocity(0.0, 0.0, 0.0);
        ListenerUpdate.mc.player.jumpMovementFactor = ((Freecam)this.module).speed.getValue();
        final double[] dir = MovementUtil.strafe(((Freecam)this.module).speed.getValue());
        if (ListenerUpdate.mc.player.movementInput.moveStrafe != 0.0f || ListenerUpdate.mc.player.movementInput.field_192832_b != 0.0f) {
            ListenerUpdate.mc.player.motionX = dir[0];
            ListenerUpdate.mc.player.motionZ = dir[1];
        }
        else {
            ListenerUpdate.mc.player.motionX = 0.0;
            ListenerUpdate.mc.player.motionZ = 0.0;
        }
        ListenerUpdate.mc.player.setSprinting(false);
        if (ListenerUpdate.mc.gameSettings.keyBindJump.isKeyDown()) {
            final EntityPlayerSP player = ListenerUpdate.mc.player;
            player.motionY += ((Freecam)this.module).speed.getValue();
        }
        if (ListenerUpdate.mc.gameSettings.keyBindSneak.isKeyDown()) {
            final EntityPlayerSP player2 = ListenerUpdate.mc.player;
            player2.motionY -= ((Freecam)this.module).speed.getValue();
        }
    }
}
