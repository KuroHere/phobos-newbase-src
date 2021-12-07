//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.selftrap;

import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.math.path.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerSelfTrap extends ObbyListener<SelfTrap>
{
    public ListenerSelfTrap(final SelfTrap module) {
        super(module, -9);
    }
    
    @Override
    protected boolean updatePlaced() {
        if (!((SelfTrap)this.module).autoOff.getValue()) {
            return super.updatePlaced();
        }
        final BlockPos p = PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer());
        if (!p.equals((Object)((SelfTrap)this.module).startPos)) {
            ((SelfTrap)this.module).disable();
            return true;
        }
        if (((SelfTrap)this.module).smartOff.getValue()) {
            for (final Vec3i offset : ((SelfTrap)this.module).mode.getValue().getOffsets()) {
                if (ObbyModule.HELPER.getBlockState(p.add(offset)).getBlock() != ((SelfTrap)this.module).mode.getValue().getBlock()) {
                    return super.updatePlaced();
                }
            }
            ((SelfTrap)this.module).disable();
            return true;
        }
        return super.updatePlaced();
    }
    
    @Override
    protected TargetResult getTargets(final TargetResult result) {
        if (((SelfTrap)this.module).smart.getValue()) {
            final EntityPlayer closest = EntityUtil.getClosestEnemy();
            if (closest == null || ListenerSelfTrap.mc.player.getDistanceSqToEntity((Entity)closest) > MathUtil.square(((SelfTrap)this.module).range.getValue())) {
                return result.setValid(false);
            }
        }
        if (((SelfTrap)this.module).mode.getValue() != SelfTrapMode.Obsidian) {
            for (final Vec3i offset : ((SelfTrap)this.module).mode.getValue().getOffsets()) {
                result.getTargets().add(PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer()).add(offset));
            }
            return result;
        }
        final BlockPos pos = PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer()).up(2);
        if (!ListenerSelfTrap.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return result.setValid(false);
        }
        for (final BlockPos alreadyPlaced : this.placed.keySet()) {
            ObbyModule.HELPER.addBlockState(alreadyPlaced, Blocks.OBSIDIAN.getDefaultState());
        }
        final BasePath path = new BasePath((Entity)RotationUtil.getRotationPlayer(), pos, ((SelfTrap)this.module).maxHelping.getValue());
        if (((SelfTrap)this.module).prioBehind.getValue()) {
            final List<BlockPos> checkFirst = new ArrayList<BlockPos>(13);
            final EnumFacing look = ListenerSelfTrap.mc.player.getHorizontalFacing();
            final BlockPos off = pos.offset(look.getOpposite());
            checkFirst.add(off);
            checkFirst.add(off.down());
            checkFirst.add(off.down(2));
            checkFirst.add(pos.up());
            for (final EnumFacing facing : EnumFacing.values()) {
                if (facing != look) {
                    checkFirst.add(off.offset(facing));
                    checkFirst.add(off.down().offset(facing));
                    checkFirst.add(off.down(2).offset(facing));
                }
            }
            PathFinder.efficient(path, ((SelfTrap)this.module).placeRange.getValue(), ListenerSelfTrap.mc.world.loadedEntityList, ((SelfTrap)this.module).smartRay.getValue(), ObbyModule.HELPER, Blocks.OBSIDIAN.getDefaultState(), PathFinder.CHECK, checkFirst, pos.down(), pos.down(2));
        }
        else {
            PathFinder.findPath(path, ((SelfTrap)this.module).placeRange.getValue(), ListenerSelfTrap.mc.world.loadedEntityList, ((SelfTrap)this.module).smartRay.getValue(), ObbyModule.HELPER, Blocks.OBSIDIAN.getDefaultState(), PathFinder.CHECK, pos.down(), pos.down(2));
        }
        return result.setValid(ObbyUtil.place((ObbyModule)this.module, path));
    }
    
    @Override
    protected int getSlot() {
        switch (((SelfTrap)this.module).mode.getValue()) {
            case Obsidian: {
                return InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
            }
            case Web:
            case HighWeb:
            case FullWeb: {
                return InventoryUtil.findHotbarBlock(Blocks.WEB, new Block[0]);
            }
            default: {
                return -1;
            }
        }
    }
    
    @Override
    protected String getDisableString() {
        switch (((SelfTrap)this.module).mode.getValue()) {
            case Obsidian: {
                return "Disabled, no Obsidian.";
            }
            case Web:
            case HighWeb:
            case FullWeb: {
                return "Disabled, no Webs.";
            }
            default: {
                return "Disabled, unknown Mode!";
            }
        }
    }
}
