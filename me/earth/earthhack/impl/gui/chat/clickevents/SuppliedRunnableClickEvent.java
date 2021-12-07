// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.clickevents;

import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import java.util.function.*;

public class SuppliedRunnableClickEvent extends ClickEvent implements IClickEvent
{
    private final Supplier<Runnable> supplier;
    
    public SuppliedRunnableClickEvent(final Supplier<Runnable> supplier) {
        super(ClickEvent.Action.RUN_COMMAND, "$runnable-supplied$");
        this.supplier = supplier;
    }
    
    public void setRunnable(final Runnable runnable) {
    }
    
    public Runnable getRunnable() {
        return this.supplier.get();
    }
}
