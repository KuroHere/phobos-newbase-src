//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.movement.phase.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.client.entity.*;

final class ListenerMove extends ModuleListener<Phase, MoveEvent>
{
    public ListenerMove(final Phase module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (((Phase)this.module).mode.getValue() == PhaseMode.Constantiam && ((Phase)this.module).isPhasing() && ((Phase)this.module).constStrafe.getValue()) {
            MovementUtil.strafe(event, 0.2873 * ((Phase)this.module).constSpeed.getValue());
        }
        if (((Phase)this.module).mode.getValue() == PhaseMode.ConstantiamNew && ((Phase)this.module).isPhasing()) {
            if (ListenerMove.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player = ListenerMove.mc.player;
                event.setY(player.motionY += 0.09000000357627869);
            }
            else if (ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player2 = ListenerMove.mc.player;
                event.setY(player2.motionY -= 0.0);
            }
            else {
                event.setY(ListenerMove.mc.player.motionY = 0.0);
            }
            MovementUtil.strafe(event, 0.2783);
        }
    }
}
