//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.ncptweaks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.item.*;

final class ListenerInput extends ModuleListener<NCPTweaks, MovementInputEvent>
{
    public ListenerInput(final NCPTweaks module) {
        super(module, (Class<? super Object>)MovementInputEvent.class);
    }
    
    public void invoke(final MovementInputEvent event) {
        if (((NCPTweaks)this.module).sneakEat.getValue() && ListenerInput.mc.gameSettings.keyBindUseItem.isKeyDown() && ListenerInput.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
            event.getInput().sneak = true;
            if (((NCPTweaks)this.module).stopSpeed.getValue()) {
                ((NCPTweaks)this.module).speedStopped = true;
            }
            return;
        }
        ((NCPTweaks)this.module).speedStopped = false;
    }
}
