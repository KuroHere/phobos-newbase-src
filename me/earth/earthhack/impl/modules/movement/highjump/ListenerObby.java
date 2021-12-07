//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.highjump;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

final class ListenerObby extends ObbyListener<HighJump>
{
    public ListenerObby(final HighJump module, final int priority) {
        super(module, priority);
    }
    
    @Override
    public void invoke(final MotionUpdateEvent event) {
        if (!((HighJump)this.module).scaffold.getValue() || !ListenerObby.mc.gameSettings.keyBindJump.isKeyDown() || ((HighJump)this.module).motionY < ((HighJump)this.module).scaffoldY.getValue() || ((HighJump)this.module).motionY > ((HighJump)this.module).scaffoldMaxY.getValue() || InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]) == -1) {
            return;
        }
        super.invoke(event);
    }
    
    @Override
    protected TargetResult getTargets(final TargetResult result) {
        final BlockPos pos = PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer());
        BlockPos firstSolid = null;
        for (int y = ((HighJump)this.module).scaffoldOffset.getValue(); y <= ((HighJump)this.module).range.getValue(); ++y) {
            final BlockPos p = pos.down(y);
            final IBlockState state = ListenerObby.mc.world.getBlockState(p);
            if (state.getMaterial().blocksMovement() && !state.getMaterial().isReplaceable()) {
                firstSolid = p;
                break;
            }
        }
        if (firstSolid == null) {
            return result;
        }
        for (int y = firstSolid.getY(); y >= ((HighJump)this.module).scaffoldOffset.getValue(); --y) {
            final BlockPos p = pos.down(y);
            if (!p.equals((Object)firstSolid)) {
                result.getTargets().add(p);
            }
        }
        return result;
    }
    
    @Override
    protected void disableModule() {
    }
}
