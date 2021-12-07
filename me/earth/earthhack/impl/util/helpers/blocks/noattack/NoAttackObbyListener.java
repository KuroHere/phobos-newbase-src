// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.noattack;

import me.earth.earthhack.impl.util.helpers.blocks.*;

public abstract class NoAttackObbyListener<T extends NoAttackObbyListenerModule<?>> extends ObbyListener<T>
{
    public NoAttackObbyListener(final T module, final int priority) {
        super(module, priority);
    }
    
    @Override
    protected boolean attackCrystalFirst() {
        return false;
    }
}
