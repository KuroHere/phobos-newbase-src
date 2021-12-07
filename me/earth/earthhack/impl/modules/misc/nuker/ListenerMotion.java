//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nuker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import java.util.*;
import net.minecraft.block.*;

final class ListenerMotion extends ModuleListener<Nuker, MotionUpdateEvent>
{
    public ListenerMotion(final Nuker module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            if (((Nuker)this.module).nuke.getValue() && ((Nuker)this.module).currentSelection != null) {
                final BlockPos pos;
                ((Nuker)this.module).currentSelection.removeIf(pos -> !MineUtil.canBreak(pos) || BlockUtil.getDistanceSqDigging(pos) > MathUtil.square(((Nuker)this.module).range.getValue()));
                if (((Nuker)this.module).timer.passed(((Nuker)this.module).timeout.getValue()) && ((Nuker)this.module).actions.isEmpty()) {
                    ((Nuker)this.module).breakSelection(((Nuker)this.module).currentSelection, ((Nuker)this.module).autoTool.getValue());
                }
            }
            final Set<Block> blocks = ((Nuker)this.module).getBlocks();
            if (!blocks.isEmpty() && ((Nuker)this.module).timer.passed(((Nuker)this.module).timeout.getValue()) && ((Nuker)this.module).actions.isEmpty()) {
                if (ListenerMotion.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
                    return;
                }
                final Set<BlockPos> toAttack = new HashSet<BlockPos>();
                final BlockPos middle = PositionUtil.getPosition();
                for (int maxRadius = Sphere.getRadius(((Nuker)this.module).range.getValue()), i = 1; i < maxRadius; ++i) {
                    final BlockPos pos = middle.add(Sphere.get(i));
                    if (BlockUtil.getDistanceSq(pos) <= MathUtil.square(((Nuker)this.module).range.getValue())) {
                        if (blocks.contains(ListenerMotion.mc.world.getBlockState(pos).getBlock())) {
                            toAttack.add(pos);
                            if (((Nuker)this.module).rotate.getValue() == Rotate.Normal) {
                                break;
                            }
                        }
                    }
                }
                ((Nuker)this.module).breakSelection(toAttack, true);
            }
            if (((Nuker)this.module).rotations != null) {
                event.setYaw(((Nuker)this.module).rotations[0]);
                event.setPitch(((Nuker)this.module).rotations[1]);
            }
            ((Nuker)this.module).rotations = null;
        }
        else {
            ((Nuker)this.module).lastSlot = -1;
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> CollectionUtil.emptyQueue(((Nuker)this.module).actions));
            ((Nuker)this.module).breaking = false;
        }
    }
}
