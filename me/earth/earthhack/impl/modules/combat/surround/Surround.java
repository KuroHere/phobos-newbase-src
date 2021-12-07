//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.surround;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.modules.movement.blocklag.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.surround.modes.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import java.util.stream.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.modules.*;

public class Surround extends ObbyModule
{
    protected static final ModuleCache<Freecam> FREECAM;
    protected static final ModuleCache<BlockLag> BLOCKLAG;
    protected final Setting<Boolean> center;
    protected final Setting<Movement> movement;
    protected final Setting<Float> speed;
    protected final Setting<Boolean> noTrap;
    protected final Setting<Boolean> floor;
    protected final Setting<Integer> extend;
    protected final Setting<Integer> eDelay;
    protected final Setting<Boolean> holeC;
    protected final Setting<Boolean> instant;
    protected final Setting<Boolean> sound;
    protected final Setting<Integer> playerExtend;
    protected final Setting<Boolean> peNoTrap;
    protected final Setting<Boolean> noTrapBlock;
    protected final Setting<Boolean> multiTrap;
    protected final Setting<Boolean> trapExtend;
    protected final Setting<Boolean> newVer;
    protected final Setting<Boolean> deltaY;
    protected final Setting<Boolean> centerY;
    protected final Setting<Boolean> predict;
    protected final Setting<Boolean> async;
    protected final Setting<Boolean> resync;
    protected final Setting<Boolean> crystalCheck;
    protected final Setting<Boolean> burrow;
    protected final Setting<Boolean> noSelfExtend;
    protected final Setting<SurroundFreecamMode> freecam;
    protected final ListenerSound soundObserver;
    protected final StopWatch extendingWatch;
    protected Set<BlockPos> targets;
    protected Set<BlockPos> placed;
    protected Set<BlockPos> confirmed;
    protected BlockPos startPos;
    protected boolean setPosition;
    
    public Surround() {
        super("Surround", Category.Combat);
        this.center = this.register(new BooleanSetting("Center", true));
        this.movement = this.register(new EnumSetting("Movement", Movement.Static));
        this.speed = this.register(new NumberSetting("Speed", 19.5f, 0.0f, 35.0f));
        this.noTrap = this.register(new BooleanSetting("NoTrap", false));
        this.floor = this.register(new BooleanSetting("Floor", false));
        this.extend = this.register(new NumberSetting("Extend", 1, 1, 3));
        this.eDelay = this.register(new NumberSetting("E-Delay", 100, 0, 1000));
        this.holeC = this.register(new BooleanSetting("Hole-C", false));
        this.instant = this.register(new BooleanSetting("Instant", false));
        this.sound = this.register(new BooleanSetting("Sound", false));
        this.playerExtend = this.register(new NumberSetting("PlayerExtend", 0, 0, 4));
        this.peNoTrap = this.register(new BooleanSetting("PE-NoTrap", false));
        this.noTrapBlock = this.register(new BooleanSetting("NoTrapBlock", false));
        this.multiTrap = this.register(new BooleanSetting("MultiTrap", false));
        this.trapExtend = this.register(new BooleanSetting("TrapExtend", false));
        this.newVer = this.register(new BooleanSetting("1.13+", false));
        this.deltaY = this.register(new BooleanSetting("Delta-Y", true));
        this.centerY = this.register(new BooleanSetting("Center-Y", false));
        this.predict = this.register(new BooleanSetting("Predict", false));
        this.async = this.register(new BooleanSetting("Async", false));
        this.resync = this.register(new BooleanSetting("Resync", false));
        this.crystalCheck = this.register(new BooleanSetting("Crystal-Check", true));
        this.burrow = this.register(new BooleanSetting("Burrow", false));
        this.noSelfExtend = this.register(new BooleanSetting("NoSelfExtend", false));
        this.freecam = this.register(new EnumSetting("Freecam", SurroundFreecamMode.Off));
        this.soundObserver = new ListenerSound(this);
        this.extendingWatch = new StopWatch();
        this.targets = new HashSet<BlockPos>();
        this.placed = new HashSet<BlockPos>();
        this.confirmed = new HashSet<BlockPos>();
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerBlockChange(this));
        this.listeners.add(new ListenerMultiBlockChange(this));
        this.listeners.add(new ListenerExplosion(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.setData(new SurroundData(this));
    }
    
    @Override
    protected void onEnable() {
        Managers.SET_DEAD.addObserver(this.soundObserver);
        super.onEnable();
        if (super.checkNull()) {
            this.confirmed.clear();
            this.targets.clear();
            this.placed.clear();
            this.attacking = null;
            this.setPosition = false;
            this.startPos = this.getPlayerPos();
            this.extendingWatch.reset();
            if (this.burrow.getValue() && !Surround.BLOCKLAG.isEnabled()) {
                Surround.BLOCKLAG.toggle();
            }
        }
    }
    
    @Override
    protected void onDisable() {
        Managers.SET_DEAD.removeObserver(this.soundObserver);
    }
    
    protected void center() {
        if (this.center.getValue() && !this.setPosition && this.startPos != null && Surround.mc.world.getBlockState(this.startPos).getBlock() != Blocks.WEB && (this.holeC.getValue() || !HoleUtil.isHole(this.startPos, false)[0])) {
            final double x = this.startPos.getX() + 0.5;
            final double y = this.centerY.getValue() ? this.startPos.getY() : this.getPlayer().posY;
            final double z = this.startPos.getZ() + 0.5;
            this.getPlayer().setPosition(x, y, z);
            this.getPlayer().setVelocity(0.0, this.getPlayer().motionY, 0.0);
        }
        else {
            this.setPosition = true;
        }
    }
    
    protected boolean updatePosAndBlocks() {
        if (this.check()) {
            final Set<BlockPos> blocked = this.createBlocked();
            final Set<BlockPos> surrounding = this.createSurrounding(blocked, Surround.mc.world.playerEntities);
            this.placed.retainAll(surrounding);
            this.targets = surrounding;
            return true;
        }
        return false;
    }
    
    private boolean check() {
        if (Surround.FREECAM.isEnabled() && this.freecam.getValue() == SurroundFreecamMode.Off) {
            return false;
        }
        this.slot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, Blocks.ENDER_CHEST);
        if (this.slot == -1) {
            ModuleUtil.disable(this, "§cDisabled, no Obsidian.");
            return false;
        }
        if (Surround.FREECAM.isEnabled() && this.movement.getValue() != Movement.Static) {
            return this.timer.passed(this.delay.getValue());
        }
        switch (this.movement.getValue()) {
            case Static: {
                final BlockPos currentPos = this.getPlayerPos();
                if (!currentPos.equals((Object)this.startPos)) {
                    this.disable();
                    return false;
                }
                break;
            }
            case Limit: {
                if (Managers.SPEED.getSpeed() > this.speed.getValue()) {
                    return false;
                }
                break;
            }
            case Disable: {
                if (Managers.SPEED.getSpeed() > this.speed.getValue()) {
                    this.disable();
                    return false;
                }
                break;
            }
        }
        return this.timer.passed(this.delay.getValue());
    }
    
    @Override
    public boolean placeBlock(final BlockPos pos) {
        final boolean hasPlaced = super.placeBlock(pos);
        if (hasPlaced) {
            this.placed.add(pos);
        }
        return hasPlaced;
    }
    
    protected Set<BlockPos> createBlocked() {
        final Set<BlockPos> blocked = new HashSet<BlockPos>();
        final BlockPos playerPos = this.getPlayerPos();
        if (HoleUtil.isHole(playerPos, false)[0] || this.center.getValue() || this.extend.getValue() == 1 || !this.extendingWatch.passed(this.eDelay.getValue())) {
            blocked.add(playerPos);
        }
        else {
            final List<BlockPos> unfiltered = new ArrayList<Object>(PositionUtil.getBlockedPositions((Entity)this.getPlayer())).stream().sorted(Comparator.comparingDouble(pos -> BlockUtil.getDistanceSq((Entity)this.getPlayer(), pos))).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
            final List<BlockPos> filtered = new ArrayList<Object>(unfiltered).stream().filter(pos -> Surround.mc.world.getBlockState(pos).getMaterial().isReplaceable() && Surround.mc.world.getBlockState(pos.up()).getMaterial().isReplaceable()).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
            if (this.extend.getValue() == 3 && filtered.size() == 2 && unfiltered.size() == 4 && unfiltered.get(0).equals((Object)filtered.get(0)) && unfiltered.get(3).equals((Object)filtered.get(1))) {
                filtered.clear();
                filtered.add(playerPos);
            }
            if ((this.extend.getValue() == 2 && filtered.size() > 2) || (this.extend.getValue() == 3 && filtered.size() == 3)) {
                while (filtered.size() > 2) {
                    filtered.remove(filtered.size() - 1);
                }
            }
            blocked.addAll(filtered);
        }
        if (blocked.isEmpty()) {
            blocked.add(playerPos);
        }
        return blocked;
    }
    
    protected boolean shouldInstant(final boolean sound) {
        return this.instant.getValue() && this.rotate.getValue() != Rotate.Normal && (!sound || this.sound.getValue());
    }
    
    protected boolean isBlockingTrap(final BlockPos pos, final List<EntityPlayer> players) {
        if (Surround.mc.world.getBlockState(pos.up()).getMaterial().isReplaceable()) {
            return false;
        }
        final EnumFacing relative = this.getFacingRelativeToPlayer(pos, this.getPlayer());
        if (relative != null && !this.trapExtend.getValue() && BlockUtil.canPlaceCrystal(this.getPlayerPos().down().offset(relative, 2), true, this.newVer.getValue())) {
            return false;
        }
        for (final EntityPlayer player : players) {
            if (player != null && !this.getPlayer().equals((Object)player) && !EntityUtil.isDead((Entity)player) && !Managers.FRIENDS.contains(player)) {
                if (player.getDistanceSq(pos) > 9.0) {
                    continue;
                }
                final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
                for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                    if (facing != relative && facing.getOpposite() != relative) {
                        if (pos.offset(facing).equals((Object)playerPos)) {
                            if (BlockUtil.canPlaceCrystal(pos.offset(facing.getOpposite()).down(), true, this.newVer.getValue())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    protected EnumFacing getFacingRelativeToPlayer(final BlockPos pos, final EntityPlayer player) {
        final double x = pos.getX() + 0.5 - player.posX;
        final double z = pos.getZ() + 0.5 - player.posZ;
        final int compare = Double.compare(Math.abs(x), Math.abs(z));
        if (compare == 0) {
            return null;
        }
        return (compare < 0) ? ((z < 0.0) ? EnumFacing.NORTH : EnumFacing.SOUTH) : ((x < 0.0) ? EnumFacing.WEST : EnumFacing.EAST);
    }
    
    protected Set<BlockPos> createSurrounding(final Set<BlockPos> blocked, final List<EntityPlayer> players) {
        final Set<BlockPos> surrounding = new HashSet<BlockPos>();
        final Iterator<BlockPos> iterator = blocked.iterator();
        BlockPos pos = null;
        while (iterator.hasNext()) {
            pos = iterator.next();
            for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                final BlockPos offset = pos.offset(facing);
                if (!blocked.contains(offset)) {
                    surrounding.add(offset);
                    if (this.noTrap.getValue()) {
                        surrounding.add(offset.down());
                    }
                }
            }
            if (this.floor.getValue()) {
                surrounding.add(pos.down());
            }
        }
        for (int i = 0; i < this.playerExtend.getValue(); ++i) {
            final Set<BlockPos> extendedPositions = new HashSet<BlockPos>();
            final Iterator<BlockPos> itr = surrounding.iterator();
            while (itr.hasNext()) {
                final BlockPos pos2 = itr.next();
                boolean remove = false;
                for (final EntityPlayer player : players) {
                    if (player != null && (!this.noSelfExtend.getValue() || player != Surround.mc.player) && !PlayerUtil.isFakePlayer((Entity)player) && !EntityUtil.isDead((Entity)player)) {
                        if (!BlockUtil.isBlocking(pos2, player, this.blockingType.getValue())) {
                            continue;
                        }
                        for (final EnumFacing facing2 : EnumFacing.HORIZONTALS) {
                            final BlockPos offset2 = pos2.offset(facing2);
                            if (!blocked.contains(offset2)) {
                                remove = true;
                                extendedPositions.add(offset2);
                                if (this.peNoTrap.getValue()) {
                                    extendedPositions.add(offset2.down());
                                }
                            }
                        }
                    }
                }
                if (remove) {
                    itr.remove();
                }
            }
            surrounding.addAll(extendedPositions);
        }
        if (this.noTrapBlock.getValue()) {
            final Set<BlockPos> trapBlocks = surrounding.stream().filter(pos -> this.isBlockingTrap(pos, players)).collect((Collector<? super Object, ?, Set<BlockPos>>)Collectors.toSet());
            if (!this.multiTrap.getValue() && trapBlocks.size() > 1) {
                return surrounding;
            }
            for (final BlockPos trap : trapBlocks) {
                if (this.trapExtend.getValue()) {
                    final EnumFacing r = this.getFacingRelativeToPlayer(trap, this.getPlayer());
                    if (r != null) {
                        surrounding.add(this.getPlayerPos().offset(r, 2));
                    }
                }
                surrounding.remove(trap);
            }
        }
        return surrounding;
    }
    
    protected BlockPos getPlayerPos() {
        return (this.deltaY.getValue() && Math.abs(this.getPlayer().motionY) > 0.1) ? new BlockPos((Entity)this.getPlayer()) : PositionUtil.getPosition((Entity)this.getPlayer());
    }
    
    @Override
    public EntityPlayer getPlayerForRotations() {
        if (Surround.FREECAM.isEnabled()) {
            final EntityPlayer target = (EntityPlayer)Surround.FREECAM.get().getPlayer();
            if (target != null) {
                return target;
            }
        }
        return (EntityPlayer)Surround.mc.player;
    }
    
    @Override
    public EntityPlayer getPlayer() {
        if (this.freecam.getValue() == SurroundFreecamMode.Origin && Surround.FREECAM.isEnabled()) {
            final EntityPlayer target = (EntityPlayer)Surround.FREECAM.get().getPlayer();
            if (target != null) {
                return target;
            }
        }
        return (EntityPlayer)Surround.mc.player;
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
        BLOCKLAG = Caches.getModule(BlockLag.class);
    }
}
