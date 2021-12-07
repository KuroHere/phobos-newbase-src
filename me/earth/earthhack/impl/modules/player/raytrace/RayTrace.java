// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.raytrace;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.phase.*;
import me.earth.earthhack.impl.modules.player.liquids.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.*;

public class RayTrace extends Module
{
    private static final ModuleCache<Phase> PHASE;
    private static final ModuleCache<LiquidInteract> LIQUID_INTERACT;
    protected final Setting<Boolean> phase;
    protected final Setting<Boolean> liquids;
    protected final Setting<Boolean> liquidCrystalPlace;
    protected final Setting<Boolean> onlyPhase;
    
    public RayTrace() {
        super("PhaseTrace", Category.Player);
        this.phase = this.register(new BooleanSetting("Phase", true));
        this.liquids = this.register(new BooleanSetting("Liquids", true));
        this.liquidCrystalPlace = this.register(new BooleanSetting("Liquid-CrystalPlace", true));
        this.onlyPhase = this.register(new BooleanSetting("OnlyPhase", true));
    }
    
    public boolean isActive() {
        return this.isEnabled() && ((this.phase.getValue() && RayTrace.PHASE.isEnabled()) || (RayTrace.LIQUID_INTERACT.isEnabled() && this.liquids.getValue()));
    }
    
    public boolean phaseCheck() {
        return this.onlyPhase.getValue() && RayTrace.PHASE.isEnabled() && RayTrace.PHASE.get().isPhasing();
    }
    
    public boolean liquidCrystalPlace() {
        return RayTrace.LIQUID_INTERACT.isEnabled() && this.liquidCrystalPlace.getValue() && this.isEnabled();
    }
    
    static {
        PHASE = Caches.getModule(Phase.class);
        LIQUID_INTERACT = Caches.getModule(LiquidInteract.class);
    }
}
