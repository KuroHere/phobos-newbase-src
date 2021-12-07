//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antitrap;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.antitrap.util.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.math.position.*;
import java.util.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.combat.offhand.*;

public class AntiTrap extends ObbyModule
{
    protected final Setting<AntiTrapMode> mode;
    protected final Setting<Boolean> offhand;
    protected final Setting<Integer> timeOut;
    protected final Setting<Boolean> empty;
    protected final Setting<Boolean> swing;
    protected final Setting<Boolean> highFill;
    protected final Setting<Integer> confirm;
    protected final Setting<Boolean> autoOff;
    protected final Map<BlockPos, Long> placed;
    protected final Set<BlockPos> confirmed;
    protected final StopWatch interval;
    protected RayTraceResult result;
    protected OffhandMode previous;
    protected BlockPos startPos;
    protected BlockPos pos;
    
    public AntiTrap() {
        super("AntiTrap", Category.Combat);
        this.mode = this.registerBefore(new EnumSetting("Mode", AntiTrapMode.Crystal), this.blocks);
        this.offhand = this.register(new BooleanSetting("Offhand", false));
        this.timeOut = this.register(new NumberSetting("TimeOut", 400, 0, 1000));
        this.empty = this.register(new BooleanSetting("Empty", true));
        this.swing = this.register(new BooleanSetting("Swing", false));
        this.highFill = this.register(new BooleanSetting("HighFill", false));
        this.confirm = this.register(new NumberSetting("Confirm", 250, 0, 1000));
        this.autoOff = this.register(new BooleanSetting("Auto-Off", true));
        this.placed = new HashMap<BlockPos, Long>();
        this.confirmed = new HashSet<BlockPos>();
        this.interval = new StopWatch();
        this.listeners.add(new ListenerMotion(this));
        this.setData(new AntiTrapData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
    
    @Override
    protected void onEnable() {
        super.onEnable();
        this.previous = null;
        this.placed.clear();
        this.confirmed.clear();
        if (super.checkNull() && this.interval.passed(this.timeOut.getValue())) {
            this.interval.reset();
            this.result = null;
            this.pos = null;
            this.startPos = PositionUtil.getPosition();
        }
        else {
            this.disable();
        }
    }
    
    @Override
    protected void onDisable() {
        if (this.offhand.getValue() && this.previous != null) {
            ListenerMotion.OFFHAND.computeIfPresent(o -> o.setMode(this.previous));
        }
    }
    
    @Override
    public boolean placeBlock(final BlockPos pos) {
        final boolean hasPlaced = super.placeBlock(pos);
        if (hasPlaced) {
            this.placed.put(pos, System.currentTimeMillis());
        }
        return hasPlaced;
    }
    
    protected List<BlockPos> getCrystalPositions() {
        final List<BlockPos> result = new ArrayList<BlockPos>();
        final BlockPos playerPos = PositionUtil.getPosition();
        if (!AntiTrap.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(playerPos, playerPos.up().add(1, 2, 1))).isEmpty()) {
            this.disable();
            return result;
        }
        for (final Vec3i vec : AntiTrapMode.Crystal.getOffsets()) {
            final BlockPos pos = playerPos.add(vec);
            if (BlockUtil.canPlaceCrystal(pos, false, false)) {
                result.add(pos);
            }
        }
        return result;
    }
}
