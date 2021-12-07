//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.nofall;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.movement.nofall.mode.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

final class ListenerMotion extends ModuleListener<NoFall, MotionUpdateEvent>
{
    public ListenerMotion(final NoFall module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (((NoFall)this.module).mode.getValue() == FallMode.Bucket) {
            final int slot = InventoryUtil.findHotbarItem(Items.WATER_BUCKET, new Item[0]);
            if (slot != -1) {
                final Vec3d positionVector = ListenerMotion.mc.player.getPositionVector();
                final RayTraceResult rayTraceBlocks = ListenerMotion.mc.world.rayTraceBlocks(positionVector, new Vec3d(positionVector.xCoord, positionVector.yCoord - 3.0, positionVector.zCoord), true);
                if (ListenerMotion.mc.player.fallDistance < 5.0f || rayTraceBlocks == null || rayTraceBlocks.typeOfHit != RayTraceResult.Type.BLOCK || ListenerMotion.mc.world.getBlockState(rayTraceBlocks.getBlockPos()).getBlock() instanceof BlockLiquid || PositionUtil.inLiquid() || PositionUtil.inLiquid(false)) {
                    return;
                }
                if (event.getStage() == Stage.PRE) {
                    event.setPitch(90.0f);
                }
                else {
                    final RayTraceResult rayTraceBlocks2 = ListenerMotion.mc.world.rayTraceBlocks(positionVector, new Vec3d(positionVector.xCoord, positionVector.yCoord - 5.0, positionVector.zCoord), true);
                    if (rayTraceBlocks2 != null && rayTraceBlocks2.typeOfHit == RayTraceResult.Type.BLOCK && !(ListenerMotion.mc.world.getBlockState(rayTraceBlocks2.getBlockPos()).getBlock() instanceof BlockLiquid) && ((NoFall)this.module).timer.passed(1000L)) {
                        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                            InventoryUtil.switchTo(slot);
                            ListenerMotion.mc.playerController.processRightClick((EntityPlayer)ListenerMotion.mc.player, (World)ListenerMotion.mc.world, (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                            return;
                        });
                        ((NoFall)this.module).timer.reset();
                    }
                }
            }
        }
    }
}
