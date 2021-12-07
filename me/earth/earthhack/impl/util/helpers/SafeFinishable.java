// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers;

import me.earth.earthhack.impl.util.thread.*;
import java.util.concurrent.atomic.*;

public abstract class SafeFinishable extends Finishable implements SafeRunnable
{
    public SafeFinishable() {
        this(new AtomicBoolean());
    }
    
    public SafeFinishable(final AtomicBoolean finished) {
        super(finished);
    }
    
    @Override
    public void run() {
        try {
            this.runSafely();
        }
        catch (Throwable t) {
            this.handle(t);
        }
        finally {
            this.setFinished(true);
        }
    }
    
    @Deprecated
    @Override
    protected void execute() {
    }
}
