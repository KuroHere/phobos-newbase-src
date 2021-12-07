//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.noglitchblocks.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.blocks.data.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.managers.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.entity.item.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

public abstract class BlockPlacingModule extends DisablingModule
{
    private static final ModuleCache<NoGlitchBlocks> NO_GLITCH_BLOCKS;
    public final Setting<Integer> blocks;
    public final Setting<Integer> delay;
    public final Setting<Rotate> rotate;
    public final Setting<Boolean> packet;
    public final Setting<Boolean> swing;
    public final Setting<CooldownBypass> cooldownBypass;
    public final Setting<Boolean> stackPacket;
    public final Setting<Boolean> smartSneak;
    public final Setting<PlaceSwing> placeSwing;
    public final Setting<BlockingType> blockingType;
    public final Setting<RayTraceMode> smartRay;
    public final DiscreteTimer timer;
    public final List<Packet<?>> packets;
    public final List<Runnable> post;
    public int blocksPlaced;
    public int slot;
    public int lastSlot;
    public float[] rotations;
    
    protected BlockPlacingModule(final String name, final Category category) {
        super(name, category);
        this.blocks = this.register(new NumberSetting("Blocks/Place", 4, 1, 10));
        this.delay = this.register(new NumberSetting("Delay", 25, 0, 1000));
        this.rotate = this.register(new EnumSetting("Rotations", Rotate.None));
        this.packet = this.register(new BooleanSetting("Packet", true));
        this.swing = this.register(new BooleanSetting("Swing", false));
        this.cooldownBypass = this.register(new EnumSetting("Cooldown-Bypass", CooldownBypass.None));
        this.stackPacket = this.register(new BooleanSetting("StackPacket", false));
        this.smartSneak = this.register(new BooleanSetting("Smart-Sneak", false));
        this.placeSwing = this.register(new EnumSetting("PlaceSwing", PlaceSwing.Always));
        this.blockingType = this.register(new EnumSetting("Blocking", BlockingType.Strict));
        this.smartRay = this.register(new EnumSetting("Raytrace", RayTraceMode.Fast));
        this.timer = new GuardTimer(500L).reset(this.getDelay());
        this.packets = new ArrayList<Packet<?>>();
        this.post = new ArrayList<Runnable>();
        this.blocksPlaced = 0;
        this.slot = -1;
        this.lastSlot = -1;
        this.setData(new BlockPlacingData<Object>(this));
    }
    
    @Override
    protected void onEnable() {
        this.checkNull();
    }
    
    public void placeBlock(final BlockPos on, final EnumFacing facing) {
        final Entity from = (Entity)this.getPlayerForRotations();
        final float[] r = RotationUtil.getRotations(on, facing, from);
        final RayTraceResult result = RayTraceUtil.getRayTraceResultWithEntity(r[0], r[1], from);
        this.placeBlock(on, facing, r, result.hitVec);
    }
    
    public void placeBlock(final BlockPos on, final EnumFacing facing, final float[] helpingRotations, final Vec3d hitVec) {
        if (this.rotations == null && (this.rotate.getValue() == Rotate.Normal || (this.blocksPlaced == 0 && this.rotate.getValue() == Rotate.Packet))) {
            this.rotations = helpingRotations;
        }
        else if (this.rotate.getValue() == Rotate.Packet) {
            this.packets.add((Packet<?>)new CPacketPlayer.Rotation(helpingRotations[0], helpingRotations[1], this.getPlayer().onGround));
        }
        final float[] f = RayTraceUtil.hitVecToPlaceVec(on, hitVec);
        final EnumHand hand = InventoryUtil.getHand(this.slot);
        this.packets.add((Packet<?>)new CPacketPlayerTryUseItemOnBlock(on, facing, hand, f[0], f[1], f[2]));
        if (this.placeSwing.getValue() == PlaceSwing.Always) {
            this.packets.add((Packet<?>)new CPacketAnimation(InventoryUtil.getHand(this.slot)));
        }
        if (!this.packet.getValue() && (!BlockPlacingModule.NO_GLITCH_BLOCKS.isPresent() || !BlockPlacingModule.NO_GLITCH_BLOCKS.get().noPlace())) {
            final ItemStack stack = (this.slot == -2) ? BlockPlacingModule.mc.player.getHeldItemOffhand() : BlockPlacingModule.mc.player.inventory.getStackInSlot(this.slot);
            BlockPlacingModule.mc.addScheduledTask(() -> this.placeClient(stack, on, hand, facing, f[0], f[1], f[2]));
        }
        ++this.blocksPlaced;
    }
    
    public void placeClient(final ItemStack stack, BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (stack.getItem() instanceof ItemBlock) {
            final ItemBlock itemBlock = (ItemBlock)stack.getItem();
            final Block block = itemBlock.getBlock();
            final IBlockState iblockstate = BlockPlacingModule.mc.world.getBlockState(pos);
            final Block iBlock = iblockstate.getBlock();
            if (!iBlock.isReplaceable((IBlockAccess)BlockPlacingModule.mc.world, pos)) {
                pos = pos.offset(facing);
            }
            if (!stack.func_190926_b() && BlockPlacingModule.mc.player.canPlayerEdit(pos, facing, stack) && BlockPlacingModule.mc.world.func_190527_a(block, pos, false, facing, (Entity)null)) {
                final int i = itemBlock.getMetadata(stack.getMetadata());
                IBlockState placeState = block.getStateForPlacement((World)BlockPlacingModule.mc.world, pos, facing, hitX, hitY, hitZ, i, (EntityLivingBase)BlockPlacingModule.mc.player, hand);
                if (itemBlock.placeBlockAt(stack, (EntityPlayer)BlockPlacingModule.mc.player, (World)BlockPlacingModule.mc.world, pos, facing, hitX, hitY, hitZ, placeState)) {
                    placeState = BlockPlacingModule.mc.world.getBlockState(pos);
                    final SoundType soundtype = placeState.getBlock().getSoundType(placeState, (World)BlockPlacingModule.mc.world, pos, (Entity)BlockPlacingModule.mc.player);
                    BlockPlacingModule.mc.world.playSound((EntityPlayer)BlockPlacingModule.mc.player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
                    if (!BlockPlacingModule.mc.player.isCreative() && this.stackPacket.getValue()) {
                        stack.func_190918_g(1);
                    }
                }
            }
        }
    }
    
    public boolean execute() {
        if (!this.packets.isEmpty()) {
            final boolean sneaking = this.sneak(this.packets);
            final int lastSlot = (this.lastSlot == -1) ? BlockPlacingModule.mc.player.inventory.currentItem : this.lastSlot;
            switch (this.cooldownBypass.getValue()) {
                case None: {
                    InventoryUtil.switchTo(this.slot);
                    break;
                }
                case Pick: {
                    InventoryUtil.bypassSwitch(this.slot);
                    break;
                }
                case Slot: {
                    InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(this.slot));
                    break;
                }
            }
            if (!sneaking) {
                BlockPlacingModule.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockPlacingModule.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            this.packets.forEach(packet -> BlockPlacingModule.mc.player.connection.sendPacket(packet));
            this.timer.reset(this.delay.getValue());
            if (this.placeSwing.getValue() == PlaceSwing.Once) {
                Swing.Packet.swing(InventoryUtil.getHand(this.slot));
            }
            if (!sneaking) {
                BlockPlacingModule.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockPlacingModule.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.post.forEach(Runnable::run);
            this.packets.clear();
            this.post.clear();
            switch (this.cooldownBypass.getValue()) {
                case None: {
                    InventoryUtil.switchTo(lastSlot);
                    break;
                }
                case Slot: {
                    InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(this.slot));
                    break;
                }
                case Pick: {
                    InventoryUtil.bypassSwitch(this.slot);
                    break;
                }
            }
            if (this.swing.getValue()) {
                Swing.Client.swing(InventoryUtil.getHand(this.slot));
            }
            return true;
        }
        return false;
    }
    
    protected boolean sneak(final Collection<Packet<?>> packets) {
        return this.smartSneak.getValue() && !Managers.ACTION.isSneaking() && !packets.stream().anyMatch(SpecialBlocks.PACKETCHECK);
    }
    
    protected boolean checkNull() {
        this.packets.clear();
        this.blocksPlaced = 0;
        if (BlockPlacingModule.mc.player == null || BlockPlacingModule.mc.world == null) {
            this.disable();
            return false;
        }
        return true;
    }
    
    public boolean entityCheck(final BlockPos pos) {
        return this.entityCheckSimple(pos);
    }
    
    protected boolean entityCheckSimple(final BlockPos pos) {
        for (final Entity entity : BlockPlacingModule.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (entity != null && !EntityUtil.isDead(entity) && entity.preventEntitySpawning && (!(entity instanceof EntityPlayer) || BlockUtil.isBlocking(pos, (EntityPlayer)entity, this.blockingType.getValue()))) {
                if (entity instanceof EntityEnderCrystal && this.blockingType.getValue() == BlockingType.Crystals) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public EntityPlayer getPlayerForRotations() {
        return (EntityPlayer)BlockPlacingModule.mc.player;
    }
    
    public EntityPlayer getPlayer() {
        return (EntityPlayer)BlockPlacingModule.mc.player;
    }
    
    public int getDelay() {
        return this.delay.getValue();
    }
    
    static {
        NO_GLITCH_BLOCKS = Caches.getModule(NoGlitchBlocks.class);
    }
}
