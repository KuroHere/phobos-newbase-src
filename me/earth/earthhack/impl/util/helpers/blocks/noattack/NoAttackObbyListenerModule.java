// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.noattack;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.util.math.*;

public abstract class NoAttackObbyListenerModule<T extends NoAttackObbyListener<?>> extends ObbyListenerModule<T>
{
    protected NoAttackObbyListenerModule(final String name, final Category category) {
        super(name, category);
        this.unregister(this.attack);
        this.unregister(this.pop);
        this.unregister(this.popTime);
        this.unregister(this.cooldown);
        this.unregister(this.breakDelay);
    }
    
    @Override
    public boolean execute() {
        this.attacking = null;
        return super.execute();
    }
    
    @Override
    public boolean entityCheck(final BlockPos pos) {
        return this.entityCheckSimple(pos);
    }
}
