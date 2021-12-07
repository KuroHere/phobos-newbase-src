//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.voidesp;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.awt.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.init.*;
import java.util.*;

public class VoidESP extends BlockESPModule
{
    protected final Setting<Integer> radius;
    protected final Setting<Integer> holes;
    protected final Setting<Integer> update;
    protected final Setting<Boolean> liveHoles;
    protected final Setting<Integer> maxY;
    protected List<BlockPos> voidHoles;
    protected final StopWatch timer;
    
    public VoidESP() {
        super("VoidESP", Category.Render);
        this.radius = this.register(new NumberSetting("Radius", 20, 0, 200));
        this.holes = this.register(new NumberSetting("Holes", 500, 0, 1000));
        this.update = this.register(new NumberSetting("Update", 500, 0, 10000));
        this.liveHoles = this.register(new BooleanSetting("Live-Holes", false));
        this.maxY = this.register(new NumberSetting("Max-Y", 256, -256, 256));
        this.voidHoles = Collections.emptyList();
        this.timer = new StopWatch();
        this.listeners.add(new ListenerRender(this));
        this.color.setValue(new Color(255, 0, 0, 76));
        this.outline.setValue(new Color(255, 0, 0, 242));
        this.height.setValue(0.0f);
        final SimpleData data = new SimpleData(this, "Renders void holes.");
        data.register(this.radius, "The radius in which holes will get rendered.");
        data.register(this.holes, "The maximum amount of holes to render.");
        data.register(this.color, "The color void holes will be rendered in.");
        data.register(this.update, "Time in milliseconds until Holes get updated again.");
        data.register(this.height, "If the Hole should be rendered flat or not.");
        data.register(this.liveHoles, "Saves some CPU but shouldn't matter at all.");
        data.register(this.maxY, "If your Y-Level is higher than this Holes won't be rendered.");
        this.setData(data);
    }
    
    protected void updateHoles() {
        if (this.timer.passed(this.update.getValue())) {
            Managers.THREAD.submit(() -> {
                final BlockPos playerPos = PositionUtil.getPosition();
                final int r = this.radius.getValue();
                if (this.voidHoles.size() != 0) {
                    new(java.util.ArrayList.class)();
                    new ArrayList(this.voidHoles.size());
                }
                else {
                    new(java.util.LinkedList.class)();
                    new LinkedList();
                }
                final List<BlockPos> list;
                final List<BlockPos> voidHolesIn = list;
                final int cx = playerPos.getX();
                final int cz = playerPos.getZ();
                int holeAmount = 0;
                BlockPos pos = null;
                for (int x = cx - r; x <= cx + r; ++x) {
                    for (int z = cz - r; z <= cz + r; ++z) {
                        final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
                        if (dist < r * r) {
                            pos = new BlockPos(x, 0, z);
                            if (VoidESP.mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK) {
                                voidHolesIn.add(pos);
                                if (this.liveHoles.getValue() && ++holeAmount >= this.holes.getValue()) {
                                    break;
                                }
                            }
                        }
                    }
                }
                voidHolesIn.sort(Comparator.comparingDouble(pos -> VoidESP.mc.player.getDistanceSq(pos)));
                VoidESP.mc.addScheduledTask(() -> this.voidHoles = voidHolesIn);
                return;
            });
            this.timer.reset();
        }
    }
}
