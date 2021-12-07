// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat.util;

import me.earth.earthhack.api.observable.*;
import net.minecraft.network.play.server.*;
import java.util.function.*;

public abstract class SoundObserver implements Observer<SPacketSoundEffect>
{
    private final BooleanSupplier soundRemove;
    
    public SoundObserver(final BooleanSupplier soundRemove) {
        this.soundRemove = soundRemove;
    }
    
    public boolean shouldRemove() {
        return this.soundRemove.getAsBoolean();
    }
    
    public boolean shouldBeNotified() {
        return this.shouldRemove();
    }
}
