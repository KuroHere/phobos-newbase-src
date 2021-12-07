//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.webaura;

import me.earth.earthhack.impl.util.helpers.blocks.noattack.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.autotrap.modes.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.modules.*;

public class WebAura extends NoAttackObbyListenerModule<ListenerWebAura>
{
    protected static final ModuleCache<Freecam> FREECAM;
    protected final Setting<Double> placeRange;
    protected final Setting<TrapTarget> target;
    protected final Setting<Boolean> antiSelfWeb;
    protected final Setting<Double> targetRange;
    protected EntityPlayer currentTarget;
    
    public WebAura() {
        super("WebAura", Category.Combat);
        this.placeRange = this.register(new NumberSetting("PlaceRange", 6.0, 0.1, 7.5));
        this.target = this.register(new EnumSetting("Target", TrapTarget.Closest));
        this.antiSelfWeb = this.register(new BooleanSetting("AntiSelfWeb", true));
        this.targetRange = this.register(new NumberSetting("Target-Range", 6.0, 0.1, 10.0));
        this.unregister(this.blockingType);
        this.setData(new WebAuraData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return (this.target.getValue() == TrapTarget.Closest && this.currentTarget != null) ? this.currentTarget.getName() : null;
    }
    
    @Override
    protected ListenerWebAura createListener() {
        return new ListenerWebAura(this);
    }
    
    @Override
    public boolean entityCheck(final BlockPos pos) {
        return this.selfWebCheck(pos);
    }
    
    @Override
    protected boolean quickEntityCheck(final BlockPos pos) {
        return !this.selfWebCheck(pos);
    }
    
    @Override
    public EntityPlayer getPlayerForRotations() {
        if (WebAura.FREECAM.isEnabled()) {
            final EntityPlayer target = (EntityPlayer)WebAura.FREECAM.get().getPlayer();
            if (target != null) {
                return target;
            }
        }
        return (EntityPlayer)WebAura.mc.player;
    }
    
    protected boolean selfWebCheck(final BlockPos pos) {
        return BlockUtil.getDistanceSq(pos) <= MathUtil.square(this.placeRange.getValue()) && (!this.antiSelfWeb.getValue() || !this.getPlayerForRotations().getEntityBoundingBox().intersectsWith(new AxisAlignedBB(pos)));
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
    }
}
