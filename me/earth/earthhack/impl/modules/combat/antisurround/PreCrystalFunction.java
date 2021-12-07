//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.modules.combat.antisurround.util.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.modules.combat.legswitch.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

final class PreCrystalFunction implements AntiSurroundFunction, Globals
{
    private final AntiSurround module;
    
    public PreCrystalFunction(final AntiSurround module) {
        this.module = module;
    }
    
    @Override
    public void accept(final BlockPos pos, final BlockPos down, final BlockPos on, final EnumFacing onFacing, final int obbySlot, final MineSlots slots, final int crystalSlot, final Entity blocking, final EntityPlayer found, final boolean execute) {
        if (blocking != null) {
            return;
        }
        final IBlockState state = PreCrystalFunction.mc.world.getBlockState(down);
        if (state.getBlock() != Blocks.OBSIDIAN && state.getBlock() != Blocks.BEDROCK) {
            return;
        }
        synchronized (this.module) {
            if (this.module.isActive() || AntiSurround.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false)) {
                return;
            }
            if (this.module.normal.getValue() || this.module.async.getValue()) {
                this.module.semiActiveTime = System.nanoTime();
                this.module.semiActive.set(true);
                this.module.semiPos = down;
            }
            RayTraceResult ray = null;
            if (this.module.rotations != null) {
                ray = RotationUtil.rayTraceWithYP(down, (IBlockAccess)PreCrystalFunction.mc.world, this.module.rotations[0], this.module.rotations[1], (b, p) -> p.equals((Object)down));
            }
            if (ray == null) {
                this.module.rotations = RotationUtil.getRotations(down.getX() + 0.5f, down.getY() + 1, down.getZ() + 0.5f, RotationUtil.getRotationPlayer().posX, RotationUtil.getRotationPlayer().posY, RotationUtil.getRotationPlayer().posZ, PreCrystalFunction.mc.player.getEyeHeight());
                ray = RotationUtil.rayTraceWithYP(down, (IBlockAccess)PreCrystalFunction.mc.world, this.module.rotations[0], this.module.rotations[1], (b, p) -> p.equals((Object)down));
                if (ray == null) {
                    ray = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, down);
                }
            }
            final RayTraceResult finalResult = ray;
            final float[] f = RayTraceUtil.hitVecToPlaceVec(down, ray.hitVec);
            final EnumHand h = InventoryUtil.getHand(crystalSlot);
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                final int lastSlot = PreCrystalFunction.mc.player.inventory.currentItem;
                InventoryUtil.switchTo(crystalSlot);
                PreCrystalFunction.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(down, finalResult.sideHit, h, f[0], f[1], f[2]));
                InventoryUtil.switchTo(lastSlot);
            });
        }
    }
}
