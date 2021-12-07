// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.audio;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.client.audio.*;

public class PlaySoundEvent extends Event
{
    private final ISound sound;
    
    public PlaySoundEvent(final ISound sound) {
        this.sound = sound;
    }
    
    public ISound getSound() {
        return this.sound;
    }
}
