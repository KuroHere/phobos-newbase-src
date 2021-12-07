// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import me.earth.earthhack.impl.core.ducks.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.text.event.*;

@Mixin({ ClickEvent.class })
public abstract class MixinClickEvent implements IClickEvent
{
    private Runnable runnable;
    
    @Override
    public void setRunnable(final Runnable runnable) {
        this.runnable = runnable;
    }
    
    @Override
    public Runnable getRunnable() {
        return this.runnable;
    }
}
