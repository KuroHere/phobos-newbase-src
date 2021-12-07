//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.webaura;

import me.earth.earthhack.impl.util.helpers.blocks.noattack.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.modules.combat.autotrap.modes.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.block.state.*;

final class ListenerWebAura extends NoAttackObbyListener<WebAura>
{
    public ListenerWebAura(final WebAura module) {
        super(module, -10);
    }
    
    @Override
    protected TargetResult getTargets(final TargetResult result) {
        switch (((WebAura)this.module).target.getValue()) {
            case Closest: {
                ((WebAura)this.module).currentTarget = EntityUtil.getClosestEnemy();
                if (((WebAura)this.module).currentTarget == null || ((WebAura)this.module).currentTarget.getDistanceSqToEntity((Entity)ListenerWebAura.mc.player) > MathUtil.square(((WebAura)this.module).targetRange.getValue())) {
                    return result.setValid(false);
                }
                return this.trap((Entity)((WebAura)this.module).currentTarget, result);
            }
            case Untrapped: {
                ((WebAura)this.module).currentTarget = null;
                final List<EntityPlayer> players = new ArrayList<EntityPlayer>();
                for (final EntityPlayer player : ListenerWebAura.mc.world.playerEntities) {
                    if (player != null && !EntityUtil.isDead((Entity)player) && !Managers.FRIENDS.contains(player)) {
                        if (player.equals((Object)ListenerWebAura.mc.player)) {
                            continue;
                        }
                        final BlockPos pos = new BlockPos((Entity)player);
                        if (ListenerWebAura.mc.world.getBlockState(pos).getBlock() == Blocks.WEB) {
                            continue;
                        }
                        if (ListenerWebAura.mc.world.getBlockState(pos.up()).getBlock() == Blocks.WEB) {
                            continue;
                        }
                        if (ListenerWebAura.mc.player.getDistanceSqToEntity((Entity)player) >= MathUtil.square(((WebAura)this.module).targetRange.getValue())) {
                            continue;
                        }
                        players.add(player);
                    }
                }
                players.sort(Comparator.comparingDouble(p -> p.getDistanceSqToEntity((Entity)ListenerWebAura.mc.player)));
                for (final EntityPlayer player : players) {
                    this.trap((Entity)player, result);
                }
                return result;
            }
            default: {
                return result.setValid(false);
            }
        }
    }
    
    @Override
    protected int getSlot() {
        return InventoryUtil.findHotbarBlock(Blocks.WEB, new Block[0]);
    }
    
    @Override
    protected String getDisableString() {
        return "Disabled, no Webs.";
    }
    
    private TargetResult trap(final Entity entity, final TargetResult result) {
        final BlockPos pos = new BlockPos(entity);
        final BlockPos up = pos.up();
        final IBlockState state = ListenerWebAura.mc.world.getBlockState(pos);
        final IBlockState upState = ListenerWebAura.mc.world.getBlockState(up);
        if (state.getBlock() == Blocks.WEB || upState.getBlock() == Blocks.WEB) {
            return result;
        }
        if (state.getMaterial().isReplaceable()) {
            result.getTargets().add(pos);
        }
        else if (upState.getMaterial().isReplaceable()) {
            result.getTargets().add(up);
        }
        return result;
    }
}
