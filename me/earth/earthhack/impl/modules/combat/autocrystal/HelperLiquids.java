//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class HelperLiquids implements Globals
{
    public PlaceData calculate(final HelperPlace placeHelper, final PlaceData placeData, final List<EntityPlayer> friends, final List<EntityPlayer> players, final float minDamage) {
        final PlaceData newData = new PlaceData(minDamage);
        newData.setTarget(placeData.getTarget());
        for (final PositionData data : placeData.getLiquid()) {
            if (placeHelper.validate(data, friends) != null) {
                placeHelper.calcPositionData(newData, data, players);
            }
        }
        return newData;
    }
    
    public EnumFacing getAbsorbFacing(final BlockPos pos, final List<Entity> entities, final IBlockAccess access, final double placeRange) {
        for (final EnumFacing facing : EnumFacing.VALUES) {
            if (facing != EnumFacing.DOWN) {
                final BlockPos offset = pos.offset(facing);
                if (BlockUtil.getDistanceSq(offset) < MathUtil.square(placeRange)) {
                    if (access.getBlockState(offset).getMaterial().isReplaceable()) {
                        boolean found = false;
                        final AxisAlignedBB bb = new AxisAlignedBB(offset);
                        for (final Entity entity : entities) {
                            if (entity != null && !EntityUtil.isDead(entity)) {
                                if (!entity.preventEntitySpawning) {
                                    continue;
                                }
                                if (entity.getEntityBoundingBox().intersectsWith(bb)) {
                                    found = true;
                                    break;
                                }
                                continue;
                            }
                        }
                        if (!found) {
                            return facing;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public static MineSlots getSlots(final boolean onGroundCheck) {
        int bestBlock = -1;
        int bestTool = -1;
        float maxSpeed = 0.0f;
        for (int i = 8; i > -1; --i) {
            final ItemStack stack = HelperLiquids.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                final int tool = MineUtil.findBestTool(BlockPos.ORIGIN, block.getDefaultState());
                final float damage = MineUtil.getDamage(block.getDefaultState(), HelperLiquids.mc.player.inventory.getStackInSlot(tool), BlockPos.ORIGIN, !onGroundCheck || RotationUtil.getRotationPlayer().onGround);
                if (damage > maxSpeed) {
                    bestBlock = i;
                    bestTool = tool;
                    maxSpeed = damage;
                }
            }
        }
        return new MineSlots(bestBlock, bestTool, maxSpeed);
    }
}
