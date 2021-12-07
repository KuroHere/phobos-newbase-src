// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.network;

import net.minecraft.util.text.*;

public class DisconnectEvent
{
    private final ITextComponent component;
    
    public DisconnectEvent(final ITextComponent component) {
        this.component = component;
    }
    
    public ITextComponent getComponent() {
        return this.component;
    }
}
