//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.audio.*;
import me.earth.earthhack.impl.modules.render.sounds.util.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.text.*;

final class ListenerClientSound extends ModuleListener<Sounds, PlaySoundEvent>
{
    public ListenerClientSound(final Sounds module) {
        super(module, (Class<? super Object>)PlaySoundEvent.class, Integer.MIN_VALUE);
    }
    
    public void invoke(final PlaySoundEvent event) {
        final boolean cancelled = event.isCancelled();
        if (!((Sounds)this.module).client.getValue() || (cancelled && !((Sounds)this.module).cancelled.getValue())) {
            return;
        }
        final ISound sound = event.getSound();
        final ResourceLocation location = sound.getSoundLocation();
        final SoundEventAccessor access = ListenerClientSound.mc.getSoundHandler().getAccessor(location);
        final ITextComponent c = (access == null) ? null : access.getSubtitle();
        if ((c != null && ((Sounds)this.module).isValid(c.getUnformattedComponentText())) || (c == null && ((Sounds)this.module).isValid(location.toString()))) {
            final String s = (c != null) ? c.getUnformattedComponentText() : location.toString();
            ((Sounds)this.module).sounds.put(new SoundPosition(sound.getXPosF(), sound.getYPosF(), sound.getZPosF(), (cancelled ? "Cancelled: " : "") + s), System.currentTimeMillis());
        }
    }
}
