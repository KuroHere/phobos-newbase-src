// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowspam;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class BowSpam extends Module
{
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> tpsSync;
    protected final Setting<Boolean> bowBomb;
    protected final Setting<Boolean> rape;
    
    public BowSpam() {
        super("BowSpam", Category.Combat);
        this.delay = this.register(new NumberSetting("Delay", 10, 0, 20));
        this.tpsSync = this.register(new BooleanSetting("TPS-Sync", true));
        this.bowBomb = this.register(new BooleanSetting("BowBomb", false));
        this.rape = this.register(new BooleanSetting("Rape", false));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerMove(this));
        this.setData(new BowSpamData(this));
    }
}
