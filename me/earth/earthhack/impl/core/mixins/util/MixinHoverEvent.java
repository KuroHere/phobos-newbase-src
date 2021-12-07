// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import me.earth.earthhack.impl.core.ducks.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.text.event.*;

@Mixin({ HoverEvent.class })
public abstract class MixinHoverEvent implements IHoverEvent
{
    private boolean offset;
    
    public MixinHoverEvent() {
        this.offset = true;
    }
    
    @Override
    public HoverEvent setOffset(final boolean offset) {
        this.offset = offset;
        return HoverEvent.class.cast(this);
    }
    
    @Override
    public boolean hasOffset() {
        return this.offset;
    }
}
