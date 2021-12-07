//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.snowballer;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import java.util.*;
import java.util.stream.*;

public class Snowballer extends Module
{
    protected float[] rotations;
    protected Entity target;
    protected Set<Integer> blackList;
    protected StopWatch timer;
    private int lastSlot;
    private boolean shouldThrow;
    final Setting<Float> range;
    final Setting<Integer> delay;
    final Setting<Boolean> swap;
    final Setting<Boolean> back;
    final Setting<Boolean> blacklist;
    
    public Snowballer() {
        super("Snowballer", Category.Combat);
        this.target = null;
        this.blackList = new HashSet<Integer>();
        this.timer = new StopWatch();
        this.range = this.register(new NumberSetting("Range", 6.0f, 1.0f, 6.0f));
        this.delay = this.register(new NumberSetting("Delay", 50, 0, 500));
        this.swap = this.register(new BooleanSetting("Swap", true));
        this.back = this.register(new BooleanSetting("SwapBack", true));
        this.blacklist = this.register(new BooleanSetting("Blacklist", true));
        this.listeners.add(new ListenerMotion(this));
        this.timer.reset();
    }
    
    protected void runPre(final MotionUpdateEvent event) {
        if ((this.timer.passed(this.delay.getValue()) || this.delay.getValue() == 0) && (this.swap.getValue() || InventoryUtil.isHolding(Items.SNOWBALL))) {
            final int slot = InventoryUtil.findHotbarItem(Items.SNOWBALL, new Item[0]);
            this.lastSlot = Snowballer.mc.player.inventory.currentItem;
            final List<Entity> entities = this.getCrystals(this.range.getValue());
            for (final Entity entity : entities) {
                if (RayTraceUtil.canBeSeen(entity, (EntityLivingBase)Snowballer.mc.player)) {
                    if (this.blackList.contains(entity.getEntityId())) {
                        continue;
                    }
                    this.target = entity;
                }
            }
            if (this.target != null) {
                if (this.swap.getValue() && slot != -1 && !InventoryUtil.isHolding(Items.SNOWBALL)) {
                    InventoryUtil.switchTo(slot);
                }
                this.rotations = RotationUtil.getRotations(this.target);
                event.setYaw(this.rotations[0]);
                event.setPitch(this.rotations[1]);
                if (this.blacklist.getValue()) {
                    this.blackList.add(this.target.getEntityId());
                }
                this.target = null;
                this.shouldThrow = true;
            }
        }
    }
    
    protected void runPost(final MotionUpdateEvent event) {
        if (this.shouldThrow && (this.timer.passed(this.delay.getValue()) || this.delay.getValue() == 0) && InventoryUtil.isHolding(Items.SNOWBALL)) {
            this.shouldThrow = false;
            final boolean offhand = Snowballer.mc.player.getHeldItemOffhand().getItem() == Items.SNOWBALL;
            final CPacketPlayerTryUseItem packet = new CPacketPlayerTryUseItem(offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            Snowballer.mc.player.connection.sendPacket((Packet)packet);
            if (this.swap.getValue() && this.back.getValue()) {
                InventoryUtil.switchTo(this.lastSlot);
            }
            this.timer.reset();
        }
    }
    
    protected List<Entity> getCrystals(final float range) {
        final List<Entity> loadedEntities = new ArrayList<Entity>(Snowballer.mc.world.loadedEntityList);
        return loadedEntities.stream().filter(entity -> Snowballer.mc.player.getDistanceSqToEntity(entity) <= range * range).filter(entity -> entity instanceof EntityEnderCrystal).sorted(Comparator.comparingDouble(entity -> Snowballer.mc.player.getDistanceSqToEntity(entity))).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
    }
}
