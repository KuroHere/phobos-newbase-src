//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.scaffold;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.blocks.attack.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;

public class Scaffold extends BlockAddingModule implements InstantAttackingModule
{
    protected final Setting<Boolean> tower;
    protected final Setting<Boolean> down;
    protected final Setting<Boolean> offset;
    protected final Setting<Boolean> rotate;
    protected final Setting<Integer> keepRotations;
    protected final Setting<Integer> preRotate;
    protected final Setting<Boolean> aac;
    protected final Setting<Integer> aacDelay;
    protected final Setting<Boolean> stopSprint;
    protected final Setting<Boolean> fastSneak;
    protected final Setting<Boolean> helping;
    protected final Setting<Boolean> swing;
    protected final Setting<Boolean> checkState;
    protected final Setting<Boolean> smartSneak;
    protected final Setting<Boolean> attack;
    protected final Setting<Boolean> instant;
    protected final Setting<Pop> pop;
    protected final Setting<Integer> popTime;
    protected final Setting<Integer> cooldown;
    protected final Setting<Integer> breakDelay;
    protected final Setting<Boolean> freecam;
    protected final Setting<Boolean> spectate;
    protected final StopWatch rotationTimer;
    protected final StopWatch breakTimer;
    protected final StopWatch towerTimer;
    protected final StopWatch aacTimer;
    protected final StopWatch timer;
    protected float[] rotations;
    protected EnumFacing facing;
    protected Entity crystal;
    protected BlockPos pos;
    protected BlockPos rot;
    
    public Scaffold() {
        super("Scaffold", Category.Player, s -> "Black/Whitelist " + s.getName() + " from Scaffolding.");
        this.tower = this.register(new BooleanSetting("Tower", true));
        this.down = this.register(new BooleanSetting("Down", false));
        this.offset = this.register(new BooleanSetting("Offset", true));
        this.rotate = this.register(new BooleanSetting("Rotate", true));
        this.keepRotations = this.register(new NumberSetting("Keep-Rotations", 0, 0, 500));
        this.preRotate = this.register(new NumberSetting("Pre-Rotations", 0, 0, 500));
        this.aac = this.register(new BooleanSetting("AAC", false));
        this.aacDelay = this.register(new NumberSetting("AAC-Delay", 150, 0, 1000));
        this.stopSprint = this.register(new BooleanSetting("StopSprint", false));
        this.fastSneak = this.register(new BooleanSetting("FastDown", false));
        this.helping = this.register(new BooleanSetting("Helping", false));
        this.swing = this.register(new BooleanSetting("Swing", false));
        this.checkState = this.register(new BooleanSetting("CheckState", true));
        this.smartSneak = this.register(new BooleanSetting("Smart-Sneak", true));
        this.attack = this.register(new BooleanSetting("Attack", false));
        this.instant = this.register(new BooleanSetting("Instant", true));
        this.pop = this.register(new EnumSetting("Pop", Pop.None));
        this.popTime = this.register(new NumberSetting("Pop-Time", 500, 0, 500));
        this.cooldown = this.register(new NumberSetting("Cooldown", 500, 0, 500));
        this.breakDelay = this.register(new NumberSetting("BreakDelay", 250, 0, 500));
        this.freecam = this.register(new BooleanSetting("Freecam", false));
        this.spectate = this.register(new BooleanSetting("Spectate", false));
        this.rotationTimer = new StopWatch();
        this.breakTimer = new StopWatch();
        this.towerTimer = new StopWatch();
        this.aacTimer = new StopWatch();
        this.timer = new StopWatch();
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerPush(this));
        this.listeners.add(new ListenerInput(this));
        this.listeners.add(new InstantAttackListener<Object>(this));
        super.listType.setValue(ListType.BlackList);
    }
    
    @Override
    protected void onEnable() {
        this.towerTimer.reset();
        this.pos = null;
        this.facing = null;
        this.rot = null;
    }
    
    protected BlockPos findNextPos() {
        BlockPos underPos = new BlockPos((Entity)Scaffold.mc.player).down();
        boolean under = false;
        if (this.down.getValue() && !Scaffold.mc.gameSettings.keyBindJump.isKeyDown() && Scaffold.mc.gameSettings.keyBindSneak.isKeyDown()) {
            under = true;
            underPos = underPos.down();
        }
        if (Scaffold.mc.world.getBlockState(underPos).getMaterial().isReplaceable() && (!under || Scaffold.mc.world.getBlockState(underPos.up()).getMaterial().isReplaceable())) {
            return underPos;
        }
        if (!this.offset.getValue()) {
            return null;
        }
        if (Scaffold.mc.gameSettings.keyBindForward.isKeyDown() && !Scaffold.mc.gameSettings.keyBindBack.isKeyDown()) {
            final BlockPos forwardPos = underPos.offset(Scaffold.mc.player.getHorizontalFacing());
            if (Scaffold.mc.world.getBlockState(forwardPos).getMaterial().isReplaceable()) {
                return forwardPos;
            }
        }
        else if (Scaffold.mc.gameSettings.keyBindBack.isKeyDown() && !Scaffold.mc.gameSettings.keyBindForward.isKeyDown()) {
            final BlockPos backPos = underPos.offset(Scaffold.mc.player.getHorizontalFacing().getOpposite());
            if (Scaffold.mc.world.getBlockState(backPos).getMaterial().isReplaceable()) {
                return backPos;
            }
        }
        if (Scaffold.mc.gameSettings.keyBindRight.isKeyDown() && !Scaffold.mc.gameSettings.keyBindLeft.isKeyDown()) {
            final BlockPos rightPos = underPos.offset(Scaffold.mc.player.getHorizontalFacing().rotateY());
            if (Scaffold.mc.world.getBlockState(rightPos).getMaterial().isReplaceable()) {
                return rightPos;
            }
        }
        else if (Scaffold.mc.gameSettings.keyBindLeft.isKeyDown() && !Scaffold.mc.gameSettings.keyBindRight.isKeyDown()) {
            final BlockPos leftPos = underPos.offset(Scaffold.mc.player.getHorizontalFacing().rotateYCCW());
            if (Scaffold.mc.world.getBlockState(leftPos).getMaterial().isReplaceable()) {
                return leftPos;
            }
        }
        return null;
    }
    
    @Override
    public Pop getPop() {
        return this.pop.getValue();
    }
    
    @Override
    public int getPopTime() {
        return this.popTime.getValue();
    }
    
    @Override
    public double getRange() {
        return 6.0;
    }
    
    @Override
    public double getTrace() {
        return 3.0;
    }
    
    @Override
    public boolean shouldAttack(final EntityEnderCrystal entity) {
        if (!this.attack.getValue() || !this.instant.getValue() || (MovementUtil.noMovementKeys() && !Scaffold.mc.player.movementInput.jump)) {
            return false;
        }
        final BlockPos pos = this.pos;
        return pos != null && entity.getEntityBoundingBox().intersectsWith(new AxisAlignedBB(pos));
    }
    
    @Override
    public Passable getTimer() {
        return this.breakTimer;
    }
    
    @Override
    public int getBreakDelay() {
        return this.breakDelay.getValue();
    }
    
    @Override
    public int getCooldown() {
        return this.cooldown.getValue();
    }
}
