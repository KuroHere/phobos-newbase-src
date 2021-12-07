//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.legswitch.*;
import me.earth.earthhack.impl.modules.player.noglitchblocks.*;
import me.earth.earthhack.api.setting.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.awt.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.modules.combat.antisurround.util.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import java.util.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.modules.*;

public class AntiSurround extends ObbyListenerModule<ListenerObby>
{
    protected static final ModuleCache<LegSwitch> LEG_SWITCH;
    private static final ModuleCache<NoGlitchBlocks> NOGLITCHBLOCKS;
    protected final Setting<Double> range;
    protected final Setting<Boolean> async;
    protected final Setting<Boolean> instant;
    protected final Setting<Boolean> persistent;
    protected final Setting<Boolean> obby;
    protected final Setting<Boolean> digSwing;
    protected final Setting<Boolean> newVer;
    protected final Setting<Boolean> newVerEntities;
    protected final Setting<Boolean> onGround;
    protected final Setting<Float> minDmg;
    protected final Setting<Integer> itemDeathTime;
    protected final Setting<Boolean> pickaxeOnly;
    protected final Setting<Boolean> anvil;
    protected final Setting<Boolean> drawEsp;
    protected final Setting<Boolean> preCrystal;
    protected final ColorSetting color;
    protected final ColorSetting outline;
    protected final Setting<Float> lineWidth;
    protected final Setting<Float> height;
    protected final Setting<Boolean> normal;
    protected final Setting<Float> minMine;
    protected final AtomicBoolean semiActive;
    protected final AtomicBoolean active;
    protected volatile long semiActiveTime;
    protected final IAxisESP esp;
    protected int crystalSlot;
    protected int toolSlot;
    protected int obbySlot;
    protected EntityPlayer target;
    protected volatile BlockPos semiPos;
    protected BlockPos crystalPos;
    protected BlockPos playerPos;
    protected BlockPos pos;
    protected boolean hasMined;
    protected boolean isAnvil;
    protected boolean mine;
    protected int ticks;
    
    public AntiSurround() {
        super("AntiSurround", Category.Combat);
        this.range = this.register(new NumberSetting("Range", 5.25, 0.1, 6.0));
        this.async = this.register(new BooleanSetting("Asnyc", false));
        this.instant = this.register(new BooleanSetting("Instant", false));
        this.persistent = this.register(new BooleanSetting("Persistent", true));
        this.obby = this.register(new BooleanSetting("Obby", false));
        this.digSwing = this.register(new BooleanSetting("DigSwing", false));
        this.newVer = this.register(new BooleanSetting("1.13+", false));
        this.newVerEntities = this.register(new BooleanSetting("1.13-Entities", false));
        this.onGround = this.register(new BooleanSetting("OnGround", true));
        this.minDmg = this.register(new NumberSetting("MinDamage", 5.0f, 0.0f, 36.0f));
        this.itemDeathTime = this.register(new NumberSetting("ItemDeathTime", 100, 0, 1000));
        this.pickaxeOnly = this.register(new BooleanSetting("HoldingPickaxe", false));
        this.anvil = this.register(new BooleanSetting("Anvil", false));
        this.drawEsp = this.register(new BooleanSetting("ESP", true));
        this.preCrystal = this.register(new BooleanSetting("PreCrystal", false));
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 75)));
        this.outline = this.register(new ColorSetting("Outline", new Color(255, 255, 255, 240)));
        this.lineWidth = this.register(new NumberSetting("LineWidth", 1.5f, 0.0f, 10.0f));
        this.height = this.register(new NumberSetting("ESP-Height", 1.0f, -1.0f, 1.0f));
        this.normal = this.register(new BooleanSetting("Normal", true));
        this.minMine = new NumberSetting<Float>("MinMine", 1.0f, 0.0f, 10.0f);
        this.semiActive = new AtomicBoolean();
        this.active = new AtomicBoolean();
        this.crystalSlot = -1;
        this.toolSlot = -1;
        this.obbySlot = -1;
        this.listeners.clear();
        this.listeners.add(this.listener);
        this.listeners.add(new ListenerBlockBreak(this));
        this.listeners.add(new ListenerBlockChange(this));
        this.listeners.add(new ListenerBlockMulti(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerDigging(this));
        this.listeners.add(new ListenerDiggingNoEvent(this));
        this.attack.setValue(true);
        this.unregister(this.attack);
        this.attack.addObserver(e -> e.setCancelled(true));
        this.attackAny.setValue(false);
        this.unregister(this.attackAny);
        this.unregister(this.attackRange);
        this.unregister(this.attackTrace);
        this.breakDelay.setValue(50);
        this.attackAny.addObserver(e -> e.setCancelled(true));
        this.pop.setValue(Pop.Time);
        this.cooldown.setValue(0);
        this.esp = new BlockESPBuilder().withColor(this.color).withOutlineColor(this.outline).withLineWidth(this.lineWidth).build();
    }
    
    @Override
    public String getDisplayInfo() {
        final EntityPlayer target = this.target;
        if (target != null) {
            return target.getName();
        }
        return null;
    }
    
    @Override
    protected void onEnable() {
        if (AntiSurround.NOGLITCHBLOCKS.returnIfPresent(NoGlitchBlocks::noBreak, false)) {
            ModuleUtil.sendMessage(this, "§cNoGlitchBlocks - Break is active. This can cause issues with AntiSurround!");
        }
        super.onEnable();
        this.reset();
    }
    
    @Override
    protected void onDisable() {
        super.onDisable();
        this.reset();
    }
    
    @Override
    protected boolean checkNull() {
        this.packets.clear();
        this.blocksPlaced = 0;
        return AntiSurround.mc.player != null && AntiSurround.mc.world != null;
    }
    
    @Override
    public boolean execute() {
        if (!this.packets.isEmpty() && this.mine) {
            final BlockPos pos = this.pos;
            final EnumFacing finalFacing;
            final EnumFacing facing = finalFacing = RayTraceUtil.getFacing((Entity)RotationUtil.getRotationPlayer(), pos, true);
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                final int lastSlot = AntiSurround.mc.player.inventory.currentItem;
                InventoryUtil.switchTo(this.toolSlot);
                if (!this.isAnvil) {
                    PacketUtil.startDigging(pos, finalFacing);
                }
                PacketUtil.stopDigging(pos, finalFacing);
                this.hasMined = false;
                if (this.digSwing.getValue()) {
                    Swing.Packet.swing(EnumHand.MAIN_HAND);
                }
                InventoryUtil.switchTo(lastSlot);
                return;
            });
        }
        this.lastSlot = -1;
        boolean execute = false;
        if (!this.packets.isEmpty()) {
            execute = super.execute();
        }
        else if (!this.post.isEmpty()) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                final int lastSlot2 = AntiSurround.mc.player.inventory.currentItem;
                this.post.forEach(Runnable::run);
                InventoryUtil.switchTo(lastSlot2);
                return;
            });
            this.post.clear();
        }
        this.mine = false;
        return execute;
    }
    
    @Override
    protected ListenerObby createListener() {
        return new ListenerObby(this);
    }
    
    public boolean holdingCheck() {
        return this.pickaxeOnly.getValue() && !(AntiSurround.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe);
    }
    
    public boolean isActive() {
        return this.isEnabled() && (this.active.get() || this.semiActive.get());
    }
    
    public void reset() {
        this.semiActive.set(false);
        this.active.set(false);
        this.slot = -1;
        this.crystalSlot = -1;
        this.toolSlot = -1;
        this.obbySlot = -1;
        this.semiPos = null;
        this.target = null;
        this.pos = null;
        this.crystalPos = null;
        this.mine = false;
        this.hasMined = false;
    }
    
    public boolean onBlockBreak(final BlockPos pos, final List<EntityPlayer> players, final List<Entity> entities) {
        return this.onBlockBreak(pos, players, entities, this::placeSync);
    }
    
    public boolean onBlockBreak(final BlockPos pos, final List<EntityPlayer> players, final List<Entity> entities, final AntiSurroundFunction function) {
        if (AntiSurround.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false)) {
            return false;
        }
        final MineSlots slots = HelperLiquids.getSlots(this.onGround.getValue());
        if ((slots.getDamage() < this.minMine.getValue() && !(this.isAnvil = this.anvilCheck(slots))) || slots.getToolSlot() == -1 || slots.getBlockSlot() == -1) {
            return false;
        }
        final int crystalSlot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
        if (crystalSlot == -1) {
            return false;
        }
        final int obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
        final IBlockStateHelper helper = new BlockStateHelper();
        helper.addBlockState(pos, Blocks.AIR.getDefaultState());
        final Entity blocking = this.getBlockingEntity(pos, entities);
        if (blocking != null && !(blocking instanceof EntityEnderCrystal)) {
            return false;
        }
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos offset = pos.offset(facing);
            if (AntiSurround.mc.world.getBlockState(offset).getMaterial().isReplaceable()) {
                EntityPlayer found = null;
                final AxisAlignedBB offsetBB = new AxisAlignedBB(offset);
                for (final EntityPlayer player : players) {
                    if (player != null && !EntityUtil.isDead((Entity)player) && !player.equals((Object)AntiSurround.mc.player) && !player.equals((Object)RotationUtil.getRotationPlayer()) && !Managers.FRIENDS.contains(player)) {
                        if (!player.getEntityBoundingBox().intersectsWith(offsetBB)) {
                            continue;
                        }
                        found = player;
                        break;
                    }
                }
                if (found != null) {
                    final BlockPos opposite = pos.offset(facing.getOpposite());
                    final BlockPos down = opposite.down();
                    if (BlockUtil.getDistanceSq(down) <= MathUtil.square(this.range.getValue())) {
                        if (BlockUtil.canPlaceCrystalReplaceable(down, true, this.newVer.getValue(), entities, this.newVerEntities.getValue(), 0L)) {
                            final IBlockState state = AntiSurround.mc.world.getBlockState(down);
                            if ((this.obby.getValue() && obbySlot != -1) || state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK) {
                                helper.addBlockState(down, Blocks.OBSIDIAN.getDefaultState());
                                final float damage = DamageUtil.calculate(down, (EntityLivingBase)found, (IBlockAccess)helper);
                                helper.delete(down);
                                if (damage >= this.minDmg.getValue()) {
                                    BlockPos on = null;
                                    EnumFacing onFacing = null;
                                    for (final EnumFacing off : EnumFacing.values()) {
                                        on = pos.offset(off);
                                        if (BlockUtil.getDistanceSq(on) <= MathUtil.square(this.range.getValue()) && !AntiSurround.mc.world.getBlockState(on).getMaterial().isReplaceable()) {
                                            onFacing = off.getOpposite();
                                            break;
                                        }
                                    }
                                    if (onFacing != null) {
                                        function.accept(pos, down, on, onFacing, obbySlot, slots, crystalSlot, blocking, found, true);
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    protected Entity getBlockingEntity(final BlockPos pos, final List<Entity> entities) {
        Entity blocking = null;
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        for (final Entity entity : entities) {
            if (entity != null && !EntityUtil.isDead(entity) && entity.preventEntitySpawning) {
                if (!entity.getEntityBoundingBox().intersectsWith(bb)) {
                    continue;
                }
                if (!(entity instanceof EntityEnderCrystal)) {
                    return entity;
                }
                blocking = entity;
            }
        }
        return blocking;
    }
    
    public synchronized boolean placeSync(final BlockPos pos, final BlockPos down, final BlockPos on, final EnumFacing onFacing, final int obbySlot, final MineSlots slots, final int crystalSlot, final Entity blocking, final EntityPlayer found, final boolean execute) {
        if (this.active.get() || AntiSurround.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false)) {
            return false;
        }
        this.obbySlot = obbySlot;
        this.slot = slots.getBlockSlot();
        this.toolSlot = slots.getToolSlot();
        this.crystalSlot = crystalSlot;
        this.crystalPos = down;
        this.pos = pos;
        this.target = found;
        this.playerPos = PositionUtil.getPosition((Entity)found);
        this.active.set(true);
        this.placeBlock(on, onFacing);
        if (blocking != null) {
            this.attacking = new CPacketUseEntity(blocking);
        }
        if (execute && (blocking != null || this.semiPos == null)) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, this::execute);
        }
        return true;
    }
    
    public boolean anvilCheck(final MineSlots slots) {
        final int slot = slots.getBlockSlot();
        if (slot == -1 || !this.anvil.getValue()) {
            return false;
        }
        final ItemStack stack = AntiSurround.mc.player.inventory.getStackInSlot(slot);
        return stack.getItem() instanceof ItemAnvilBlock;
    }
    
    static {
        LEG_SWITCH = Caches.getModule(LegSwitch.class);
        NOGLITCHBLOCKS = Caches.getModule(NoGlitchBlocks.class);
    }
}
