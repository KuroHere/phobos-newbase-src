// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;

final class ListenerSound extends SoundObserver
{
    private final AutoCrystal module;
    
    public ListenerSound(final AutoCrystal module) {
        super(module.soundRemove::getValue);
        this.module = module;
    }
    
    @Override
    public void onChange(final SPacketSoundEffect value) {
        if (this.module.soundThread.getValue()) {
            this.module.threadHelper.startThread(new BlockPos[0]);
        }
    }
    
    @Override
    public boolean shouldBeNotified() {
        return true;
    }
}
