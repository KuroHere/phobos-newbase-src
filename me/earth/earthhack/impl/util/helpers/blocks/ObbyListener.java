//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import java.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.block.state.*;

public abstract class ObbyListener<T extends ObbyListenerModule<?>> extends ModuleListener<T, MotionUpdateEvent>
{
    public final Map<BlockPos, Long> placed;
    public List<BlockPos> targets;
    
    public ObbyListener(final T module, final int priority) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, priority);
        this.placed = new HashMap<BlockPos, Long>();
        this.targets = new LinkedList<BlockPos>();
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            this.pre(event);
        }
        else {
            this.post(event);
        }
    }
    
    protected abstract TargetResult getTargets(final TargetResult p0);
    
    public void onModuleToggle() {
        this.placed.clear();
        this.targets = new LinkedList<BlockPos>();
    }
    
    protected void pre(final MotionUpdateEvent event) {
        ((ObbyListenerModule)this.module).rotations = null;
        ((ObbyListenerModule)this.module).blocksPlaced = 0;
        if (this.update() && !this.attackCrystalFirst()) {
            this.placeTargets();
        }
        if (this.rotateCheck()) {
            if (((ObbyListenerModule)this.module).rotations != null) {
                this.setRotations(((ObbyListenerModule)this.module).rotations, event);
            }
        }
        else {
            this.execute();
        }
    }
    
    protected boolean rotateCheck() {
        return ((ObbyListenerModule)this.module).rotate.getValue() != Rotate.None;
    }
    
    protected void placeTargets() {
        for (final BlockPos pos : this.targets) {
            if (!this.placed.containsKey(pos) && ObbyModule.HELPER.getBlockState(pos).getMaterial().isReplaceable() && ((ObbyListenerModule)this.module).placeBlock(pos)) {
                break;
            }
        }
    }
    
    protected boolean attackCrystalFirst() {
        boolean hasPlaced = false;
        final Optional<BlockPos> crystalPos = this.targets.stream().filter(pos -> !ObbyListener.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(pos)).isEmpty() && ObbyListener.mc.world.getBlockState(pos).getMaterial().isReplaceable()).findFirst();
        if (crystalPos.isPresent()) {
            hasPlaced = ((ObbyListenerModule)this.module).placeBlock(crystalPos.get());
        }
        return hasPlaced;
    }
    
    protected boolean update() {
        if (this.updatePlaced()) {
            return false;
        }
        ((ObbyListenerModule)this.module).slot = this.getSlot();
        if (((ObbyListenerModule)this.module).slot == -1) {
            this.disableModule();
            return false;
        }
        if (this.hasTimerNotPassed()) {
            return false;
        }
        final TargetResult result = this.getTargets(new TargetResult());
        this.targets = result.getTargets();
        return result.isValid();
    }
    
    protected boolean hasTimerNotPassed() {
        return !((ObbyListenerModule)this.module).timer.passed(((ObbyListenerModule)this.module).getDelay());
    }
    
    public void addCallback(final BlockPos pos) {
        Managers.BLOCKS.addCallback(pos, s -> ObbyListener.mc.addScheduledTask(() -> this.placed.remove(pos)));
        this.placed.put(pos, System.currentTimeMillis());
    }
    
    protected void disableModule() {
        ModuleUtil.disableRed((Module)this.module, this.getDisableString());
    }
    
    protected boolean updatePlaced() {
        this.placed.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue() >= ((ObbyListenerModule)this.module).confirm.getValue());
        return false;
    }
    
    protected int getSlot() {
        return InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
    }
    
    protected String getDisableString() {
        return "Disabled, no Obsidian.";
    }
    
    protected void post(final MotionUpdateEvent event) {
        this.execute();
    }
    
    protected void setRotations(final float[] rotations, final MotionUpdateEvent event) {
        event.setYaw(rotations[0]);
        event.setPitch(rotations[1]);
    }
    
    protected void execute() {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, (ObbyListenerModule)this.module::execute);
    }
}
