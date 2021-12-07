// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.longjump;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class LongJumpData extends DefaultData<LongJump>
{
    public LongJumpData(final LongJump module) {
        super(module);
        this.register(module.mode, "-Normal best more for Anarchy\n-Cowabunga ...");
        this.register(module.boost, "Amount your jump will be boosted by.");
        this.register(module.noKick, "Prevents you from getting kicked by disabling this module automatically.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Makes you jump far.";
    }
}
