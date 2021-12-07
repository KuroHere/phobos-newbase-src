//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nuker;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;

public class Nuker extends Module
{
    protected final Setting<Boolean> nuke;
    protected final Setting<Integer> blocks;
    protected final Setting<Integer> delay;
    protected final Setting<Rotate> rotate;
    protected final Setting<Integer> width;
    protected final Setting<Integer> height;
    protected final Setting<Float> range;
    protected final Setting<Boolean> render;
    protected final Setting<Color> color;
    protected final Setting<Boolean> shulkers;
    protected final Setting<Boolean> hoppers;
    protected final Setting<Boolean> instant;
    protected final Setting<Swing> swing;
    protected final Setting<Boolean> speedMine;
    protected final Setting<Boolean> autoTool;
    protected final Setting<Integer> timeout;
    protected final Queue<Runnable> actions;
    protected final StopWatch timer;
    protected Set<BlockPos> currentSelection;
    protected float[] rotations;
    protected boolean breaking;
    protected int lastSlot;
    
    public Nuker() {
        super("Nuker", Category.Misc);
        this.nuke = this.register(new BooleanSetting("Nuke", true));
        this.blocks = this.register(new NumberSetting("Blocks/Attack", 1, 1, 50));
        this.delay = this.register(new NumberSetting("Click-Delay", 25, 0, 250));
        this.rotate = this.register(new EnumSetting("Rotations", Rotate.None));
        this.width = this.register(new NumberSetting("Selection-W", 1, 1, 6));
        this.height = this.register(new NumberSetting("Selection-H", 1, 1, 6));
        this.range = this.register(new NumberSetting("Range", 6.0f, 0.1f, 6.0f));
        this.render = this.register(new BooleanSetting("Render", true));
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 125)));
        this.shulkers = this.register(new BooleanSetting("Shulkers", false));
        this.hoppers = this.register(new BooleanSetting("Hoppers", false));
        this.instant = this.register(new BooleanSetting("Predict", false));
        this.swing = this.register(new EnumSetting("Swing", Swing.Packet));
        this.speedMine = this.register(new BooleanSetting("Packet", true));
        this.autoTool = this.register(new BooleanSetting("AutoTool", true));
        this.timeout = this.register(new NumberSetting("Delay", 100, 50, 500));
        this.actions = new LinkedList<Runnable>();
        this.timer = new StopWatch();
        this.listeners.add(new ListenerClickBlock(this));
        this.listeners.add(new ListenerMultiChange(this));
        this.listeners.add(new ListenerChange(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerRender(this));
        this.setData(new NukerData(this));
    }
    
    @Override
    protected void onEnable() {
        this.currentSelection = null;
        this.rotations = null;
        this.breaking = false;
        this.actions.clear();
    }
    
    protected Set<Block> getBlocks() {
        final Set<Block> result = new HashSet<Block>();
        if (this.hoppers.getValue()) {
            result.add((Block)Blocks.HOPPER);
        }
        if (this.shulkers.getValue()) {
            result.addAll(SpecialBlocks.SHULKERS);
        }
        return result;
    }
    
    protected void breakSelection(final Set<BlockPos> selection, final boolean autoTool) {
        int i = 1;
        this.lastSlot = -1;
        final Set<BlockPos> toRemove = new HashSet<BlockPos>();
        for (final BlockPos pos : selection) {
            if (!MineUtil.canBreak(pos)) {
                toRemove.add(pos);
            }
            else {
                float[] rotations;
                RayTraceResult result;
                if (this.rotate.getValue() != Rotate.None) {
                    rotations = RotationUtil.getRotationsToTopMiddle(pos.up());
                    result = RayTraceUtil.getRayTraceResult(rotations[0], rotations[1], this.range.getValue());
                }
                else {
                    rotations = null;
                    result = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP);
                }
                if (rotations != null) {
                    if (this.rotations == null) {
                        this.rotations = rotations;
                    }
                    else {
                        this.actions.add(() -> Nuker.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Nuker.mc.player.onGround)));
                    }
                }
                if (this.rotate.getValue() == Rotate.None) {
                    Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                        if (autoTool) {
                            if (this.lastSlot == -1) {
                                this.lastSlot = Nuker.mc.player.inventory.currentItem;
                            }
                            InventoryUtil.switchTo(MineUtil.findBestTool(pos));
                        }
                        if (this.speedMine.getValue()) {
                            Nuker.mc.player.connection.sendPacket((Packet)this.getPacket(pos, result.sideHit, true));
                            Nuker.mc.player.connection.sendPacket((Packet)this.getPacket(pos, result.sideHit, false));
                        }
                        else {
                            Nuker.mc.playerController.onPlayerDamageBlock(pos, result.sideHit);
                        }
                        this.swing.getValue().swing(EnumHand.MAIN_HAND);
                        return;
                    });
                }
                else {
                    if (autoTool) {
                        this.actions.add(() -> {
                            if (this.lastSlot == -1) {
                                this.lastSlot = Nuker.mc.player.inventory.currentItem;
                            }
                            InventoryUtil.switchTo(MineUtil.findBestTool(pos));
                            return;
                        });
                    }
                    if (this.speedMine.getValue()) {
                        this.actions.add(() -> {
                            Nuker.mc.player.connection.sendPacket((Packet)this.getPacket(pos, result.sideHit, true));
                            Nuker.mc.player.connection.sendPacket((Packet)this.getPacket(pos, result.sideHit, false));
                            return;
                        });
                    }
                    else {
                        this.actions.add(() -> Nuker.mc.playerController.onPlayerDamageBlock(pos, result.sideHit));
                    }
                    this.actions.add(() -> this.swing.getValue().swing(EnumHand.MAIN_HAND));
                }
                toRemove.add(pos);
                if (i >= this.blocks.getValue()) {
                    break;
                }
                if (this.rotate.getValue() == Rotate.Normal) {
                    break;
                }
                ++i;
            }
        }
        selection.removeAll(toRemove);
        if (!this.actions.isEmpty()) {
            if (autoTool) {
                InventoryUtil.switchTo(this.lastSlot);
            }
            this.timer.reset();
        }
    }
    
    protected void attack(final BlockPos pos) {
        final float[] rotations = RotationUtil.getRotationsToTopMiddle(pos.up());
        final RayTraceResult result = RayTraceUtil.getRayTraceResult(rotations[0], rotations[1], this.range.getValue());
        if (this.rotate.getValue() == Rotate.Packet) {
            Nuker.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], Nuker.mc.player.onGround));
        }
        Nuker.mc.player.connection.sendPacket((Packet)this.getPacket(pos, result.sideHit, true));
        Nuker.mc.player.connection.sendPacket((Packet)this.getPacket(pos, result.sideHit, false));
    }
    
    protected Packet<?> getPacket(final BlockPos pos, final EnumFacing facing, final boolean start) {
        if (start) {
            return (Packet<?>)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, facing);
        }
        return (Packet<?>)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, facing);
    }
    
    public Set<BlockPos> getSelection(final BlockPos pos) {
        final Set<BlockPos> result = new LinkedHashSet<BlockPos>();
        result.add(pos);
        final EnumFacing entityF = EnumFacing.func_190914_a(pos, (EntityLivingBase)Nuker.mc.player).getOpposite();
        final EnumFacing horizontal = Nuker.mc.player.getHorizontalFacing();
        for (int i = 1; i < this.width.getValue(); ++i) {
            EnumFacing facing;
            BlockPos w;
            for (facing = this.getFacing(i, entityF, false, horizontal), w = pos.offset(facing); result.contains(w); w = w.offset(facing)) {}
            if (MineUtil.canBreak(w) && BlockUtil.getDistanceSqDigging((Entity)Nuker.mc.player, w) <= MathUtil.square(this.range.getValue())) {
                result.add(w);
            }
        }
        final Set<BlockPos> added = new LinkedHashSet<BlockPos>(result);
        for (int j = 1; j < this.height.getValue(); ++j) {
            final EnumFacing facing2 = this.getFacing(j, entityF, true, horizontal);
            for (final BlockPos p : result) {
                BlockPos h;
                for (h = p.offset(facing2); added.contains(h); h = h.offset(facing2)) {}
                if (MineUtil.canBreak(h) && BlockUtil.getDistanceSqDigging((Entity)Nuker.mc.player, h) <= MathUtil.square(this.range.getValue()) && (entityF == EnumFacing.DOWN || Nuker.mc.player.posY < pos.getY())) {
                    added.add(h);
                }
            }
        }
        return added;
    }
    
    private EnumFacing getFacing(final int index, final EnumFacing entityFacing, final boolean h, final EnumFacing horizontal) {
        if (entityFacing == EnumFacing.UP || entityFacing == EnumFacing.DOWN) {
            if (h) {
                return (index % 2 == 0) ? horizontal.getOpposite() : horizontal;
            }
            final EnumFacing result = this.get2ndHorizontalOpposite(horizontal);
            return (index % 2 == 0) ? result.getOpposite() : result;
        }
        else {
            if (h) {
                return (index % 2 == 0) ? EnumFacing.UP : EnumFacing.DOWN;
            }
            final EnumFacing result = this.get2ndHorizontalOpposite(horizontal);
            return (index % 2 == 0) ? result.getOpposite() : result;
        }
    }
    
    private EnumFacing get2ndHorizontalOpposite(final EnumFacing facing) {
        for (final EnumFacing f : EnumFacing.values()) {
            if (f != facing && f.getOpposite() != facing && f != EnumFacing.UP && f != EnumFacing.DOWN) {
                return f;
            }
        }
        return EnumFacing.UP;
    }
}
