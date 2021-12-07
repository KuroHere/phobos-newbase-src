// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.surround;

import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import net.minecraft.network.play.server.*;

final class ListenerSound extends SoundObserver
{
    private final Surround module;
    
    public ListenerSound(final Surround module) {
        super(() -> module.shouldInstant(true));
        this.module = module;
    }
    
    @Override
    public void onChange(final SPacketSoundEffect value) {
        ListenerMotion.start(this.module);
    }
}
