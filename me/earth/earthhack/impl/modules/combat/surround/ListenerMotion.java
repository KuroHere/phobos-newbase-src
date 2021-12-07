//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.surround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import java.util.*;

final class ListenerMotion extends ModuleListener<Surround, MotionUpdateEvent>
{
    public ListenerMotion(final Surround module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, -999999999);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            start((Surround)this.module, event);
        }
        else {
            ((Surround)this.module).setPosition = true;
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, (Surround)this.module::execute);
            Managers.ROTATION.setBlocking(false);
        }
    }
    
    public static void start(final Surround module) {
        start(module, new MotionUpdateEvent(Stage.PRE, 0.0, 0.0, 0.0, 0.0f, 0.0f, false));
    }
    
    public static void start(final Surround module, final MotionUpdateEvent event) {
        module.rotations = null;
        module.attacking = null;
        module.blocksPlaced = 0;
        module.center();
        if (module.updatePosAndBlocks()) {
            module.placed.removeAll(module.confirmed);
            boolean hasPlaced = false;
            BlockPos pos = null;
            final Optional<BlockPos> crystalPos = module.targets.stream().filter(pos -> !ListenerMotion.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(pos)).isEmpty() && ListenerMotion.mc.world.getBlockState(pos).getMaterial().isReplaceable()).findFirst();
            if (crystalPos.isPresent()) {
                pos = crystalPos.get();
                module.confirmed.remove(pos);
                hasPlaced = module.placeBlock(pos);
            }
            if (!hasPlaced || !module.crystalCheck.getValue()) {
                final List<BlockPos> surrounding = new ArrayList<BlockPos>(module.targets);
                if (module.getPlayer().motionX != 0.0 || module.getPlayer().motionZ != 0.0) {
                    final BlockPos pos2 = new BlockPos((Entity)module.getPlayer()).add(module.getPlayer().motionX * 10000.0, 0.0, module.getPlayer().motionZ * 10000.0);
                    surrounding.sort(Comparator.comparingDouble(p -> p.distanceSq(pos.getX() + 0.5, (double)pos.getY(), pos.getZ() + 0.5)));
                }
                for (final BlockPos pos3 : surrounding) {
                    if (!module.placed.contains(pos3) && ListenerMotion.mc.world.getBlockState(pos3).getMaterial().isReplaceable()) {
                        module.confirmed.remove(pos3);
                        if (module.placeBlock(pos3)) {
                            break;
                        }
                        continue;
                    }
                }
            }
        }
        if (module.blocksPlaced == 0) {
            module.placed.clear();
        }
        if (module.rotate.getValue() != Rotate.None) {
            if (module.rotations != null) {
                Managers.ROTATION.setBlocking(true);
                event.setYaw(module.rotations[0]);
                event.setPitch(module.rotations[1]);
                if (Surround.FREECAM.isEnabled()) {
                    Surround.FREECAM.get().rotate(module.rotations[0], module.rotations[1]);
                }
            }
        }
        else if (module.setPosition) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, module::execute);
        }
    }
}
