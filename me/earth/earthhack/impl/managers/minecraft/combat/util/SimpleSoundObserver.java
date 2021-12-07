// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat.util;

import java.util.function.*;
import net.minecraft.network.play.server.*;

public class SimpleSoundObserver extends SoundObserver
{
    public SimpleSoundObserver() {
        this(() -> true);
    }
    
    public SimpleSoundObserver(final BooleanSupplier soundRemove) {
        super(soundRemove);
    }
    
    @Override
    public void onChange(final SPacketSoundEffect value) {
    }
}
