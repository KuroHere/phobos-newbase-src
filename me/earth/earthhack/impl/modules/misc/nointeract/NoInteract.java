// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nointeract;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.*;

public class NoInteract extends BlockAddingModule
{
    protected final Setting<Boolean> sneak;
    
    public NoInteract() {
        super("NoInteract", Category.Misc, s -> "Black/Whitelist " + s.getName() + " interacting.");
        this.sneak = this.register(new BooleanSetting("Sneak", true));
        super.listType.setValue(ListType.BlackList);
        this.listeners.add(new ListenerInteract(this));
    }
}
