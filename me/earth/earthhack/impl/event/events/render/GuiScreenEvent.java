// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import net.minecraft.client.gui.*;
import me.earth.earthhack.api.event.events.*;

public class GuiScreenEvent<T extends GuiScreen> extends Event
{
    private final T screen;
    
    public GuiScreenEvent(final T screen) {
        this.screen = screen;
    }
    
    public T getScreen() {
        return this.screen;
    }
}
