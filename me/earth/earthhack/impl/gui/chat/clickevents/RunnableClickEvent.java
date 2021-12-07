// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.clickevents;

import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.core.ducks.util.*;

public class RunnableClickEvent extends ClickEvent
{
    public RunnableClickEvent(final Runnable runnable) {
        super(ClickEvent.Action.RUN_COMMAND, "$runnable$");
        ((IClickEvent)this).setRunnable(runnable);
    }
}
