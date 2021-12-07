// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.selftrap;

import me.earth.earthhack.api.setting.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;

public class SelfTrap extends ObbyListenerModule<ListenerSelfTrap>
{
    protected final Setting<SelfTrapMode> mode;
    protected final Setting<Boolean> smart;
    protected final Setting<Float> range;
    protected final Setting<Double> placeRange;
    protected final Setting<Integer> maxHelping;
    protected final Setting<Boolean> autoOff;
    protected final Setting<Boolean> smartOff;
    protected final Setting<Boolean> prioBehind;
    protected BlockPos startPos;
    
    public SelfTrap() {
        super("SelfTrap", Category.Combat);
        this.mode = this.register(new EnumSetting("Mode", SelfTrapMode.Obsidian));
        this.smart = this.register(new BooleanSetting("Smart", false));
        this.range = this.register(new NumberSetting("SmartRange", 6.0f, 0.0f, 20.0f));
        this.placeRange = this.register(new NumberSetting("PlaceRange", 6.0, 0.0, 7.5));
        this.maxHelping = this.register(new NumberSetting("HelpingBlocks", 4, 0, 20));
        this.autoOff = this.register(new BooleanSetting("Auto-Off", true));
        this.smartOff = this.register(new BooleanSetting("Smart-Off", true));
        this.prioBehind = this.register(new BooleanSetting("Prio-Behind", true));
    }
    
    @Override
    protected void onEnable() {
        final Entity entity = (Entity)RotationUtil.getRotationPlayer();
        if (entity != null) {
            this.startPos = PositionUtil.getPosition(entity);
        }
        super.onEnable();
    }
    
    @Override
    protected void onDisable() {
        super.onDisable();
        this.startPos = null;
    }
    
    @Override
    public boolean execute() {
        if (this.mode.getValue() != SelfTrapMode.Obsidian) {
            this.attacking = null;
        }
        return super.execute();
    }
    
    @Override
    protected ListenerSelfTrap createListener() {
        return new ListenerSelfTrap(this);
    }
    
    @Override
    public EntityPlayer getPlayerForRotations() {
        return RotationUtil.getRotationPlayer();
    }
    
    @Override
    public EntityPlayer getPlayer() {
        return RotationUtil.getRotationPlayer();
    }
    
    @Override
    protected boolean entityCheckSimple(final BlockPos pos) {
        return true;
    }
    
    @Override
    public boolean entityCheck(final BlockPos pos) {
        return true;
    }
    
    @Override
    protected boolean quickEntityCheck(final BlockPos pos) {
        return false;
    }
}
