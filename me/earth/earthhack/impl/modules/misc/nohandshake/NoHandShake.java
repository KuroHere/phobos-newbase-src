// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nohandshake;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;

public class NoHandShake extends Module
{
    public NoHandShake() {
        super("NoHandShake", Category.Misc);
        this.listeners.add(new ListenerCustomPayload(this));
    }
}
