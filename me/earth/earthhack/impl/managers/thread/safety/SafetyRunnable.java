//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.safety;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.math.*;

public class SafetyRunnable implements Globals, SafeRunnable
{
    private final SafetyManager manager;
    private final List<Entity> crystals;
    private final boolean newVerEntities;
    private final boolean newerVersion;
    private final boolean bedCheck;
    private final float maxDamage;
    private final boolean longs;
    private final boolean big;
    private final boolean anvils;
    private final boolean terrain;
    
    public SafetyRunnable(final SafetyManager manager, final List<Entity> crystals, final boolean newVerEntities, final boolean newerVersion, final boolean bedCheck, final float maxDamage, final boolean longs, final boolean big, final boolean anvils, final boolean terrain) {
        this.manager = manager;
        this.crystals = crystals;
        this.newVerEntities = newVerEntities;
        this.newerVersion = newerVersion;
        this.bedCheck = bedCheck;
        this.maxDamage = maxDamage;
        this.longs = longs;
        this.big = big;
        this.anvils = anvils;
        this.terrain = terrain;
    }
    
    @Override
    public void runSafely() {
        for (final Entity entity : this.crystals) {
            if (entity instanceof EntityEnderCrystal && !entity.isDead) {
                final float damage = DamageUtil.calculate(entity);
                if (damage > this.maxDamage || damage > EntityUtil.getHealth((EntityLivingBase)SafetyRunnable.mc.player) - 1.0) {
                    this.manager.setSafe(false);
                    return;
                }
                continue;
            }
        }
        boolean fullArmor = true;
        for (final ItemStack stack : SafetyRunnable.mc.player.inventory.armorInventory) {
            if (stack.func_190926_b()) {
                fullArmor = false;
                break;
            }
        }
        final Vec3d serverVec = Managers.POSITION.getVec();
        final BlockPos position = new BlockPos(serverVec);
        if (fullArmor && position.getY() == serverVec.yCoord) {
            final boolean[] hole = HoleUtil.isHole(position, false);
            if (hole[0] && (!this.anvils || hole[1]) && (!this.newerVersion || !this.bedCheck)) {
                this.manager.setSafe(true);
                return;
            }
            if (!this.anvils && ((HoleUtil.is2x1(position) && this.longs) || (HoleUtil.is2x2Partial(position) && this.big)) && !this.bedCheck) {
                this.manager.setSafe(true);
                return;
            }
        }
        final AxisAlignedBB serverBB = Managers.POSITION.getBB();
        final BlockPos middle = PositionUtil.fromBB(serverBB);
        final int x = middle.getX();
        final int y = middle.getY();
        final int z = middle.getZ();
        final int maxRadius = Sphere.getRadius(6.0);
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int i = 1; i < maxRadius; ++i) {
            final Vec3i v = Sphere.get(i);
            pos.setPos(x + v.getX(), y + v.getY(), z + v.getZ());
            if (BlockUtil.canPlaceCrystal((BlockPos)pos, true, this.newerVersion, this.crystals, this.newVerEntities, 0L) || (this.bedCheck && BlockUtil.canPlaceBed((BlockPos)pos, this.newerVersion))) {
                final float damage2 = DamageUtil.calculate(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, serverBB, (EntityLivingBase)SafetyRunnable.mc.player, (IBlockAccess)SafetyRunnable.mc.world, this.terrain, this.anvils);
                if (damage2 > this.maxDamage || damage2 > EntityUtil.getHealth((EntityLivingBase)SafetyRunnable.mc.player) - 1.0) {
                    this.manager.setSafe(false);
                    return;
                }
            }
        }
        this.manager.setSafe(true);
    }
}
