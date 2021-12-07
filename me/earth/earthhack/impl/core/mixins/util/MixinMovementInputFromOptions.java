//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.event.events.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ MovementInputFromOptions.class })
public abstract class MixinMovementInputFromOptions
{
    @Redirect(method = { "updatePlayerMoveState" }, at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInputFromOptions;sneak:Z", ordinal = 1))
    private boolean sneakHook(final MovementInputFromOptions input) {
        final boolean sneak = input.sneak;
        final Event event = new MovementInputEvent((MovementInput)input);
        Bus.EVENT_BUS.post(event);
        return !event.isCancelled() && sneak;
    }
}
