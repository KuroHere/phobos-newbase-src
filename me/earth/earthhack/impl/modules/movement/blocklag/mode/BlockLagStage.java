// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.blocklag.mode;

import me.earth.earthhack.api.event.events.*;

public enum BlockLagStage
{
    Pre {
        @Override
        public boolean shouldBlockLag(final Stage stage) {
            return stage == Stage.PRE;
        }
    }, 
    Post {
        @Override
        public boolean shouldBlockLag(final Stage stage) {
            return stage == Stage.POST;
        }
    }, 
    All {
        @Override
        public boolean shouldBlockLag(final Stage stage) {
            return true;
        }
    };
    
    public abstract boolean shouldBlockLag(final Stage p0);
}
