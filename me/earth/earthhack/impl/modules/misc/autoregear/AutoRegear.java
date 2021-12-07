//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoregear;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.util.math.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class AutoRegear extends BlockPlacingModule implements CustomCommandModule
{
    private static final String[] ARGS;
    protected final Setting<Integer> delay;
    protected final Setting<Float> range;
    protected final Setting<Bind> regear;
    protected final Setting<Boolean> grabShulker;
    protected final Setting<Boolean> placeShulker;
    protected final Setting<Boolean> placeEchest;
    protected final Setting<Boolean> steal;
    protected StopWatch delayTimer;
    protected boolean shouldRegear;
    
    public AutoRegear() {
        super("AutoRegear", Category.Misc);
        this.delay = this.register(new NumberSetting("Delay", 50, 0, 500));
        this.range = this.register(new NumberSetting("Range", 6.0f, 1.0f, 8.0f));
        this.regear = this.register(new BindSetting("Regear", Bind.none()));
        this.grabShulker = this.register(new BooleanSetting("GrabShulker", false));
        this.placeShulker = this.register(new BooleanSetting("PlaceShulker", false));
        this.placeEchest = this.register(new BooleanSetting("PlaceEchest", false));
        this.steal = this.register(new BooleanSetting("Steal", false));
        this.delayTimer = new StopWatch();
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerKeyPress(this));
        this.listeners.add(new ListenerMotion(this));
    }
    
    @Override
    protected void onEnable() {
        this.shouldRegear = false;
    }
    
    public void unregisterAll() {
        for (int i = 0; i < 28; ++i) {
            if (this.getSettingFromSlot(i) != null) {
                this.unregister(this.getSettingFromSlot(i));
            }
        }
    }
    
    public void registerInventory() {
        for (int i = 9; i < 45; ++i) {
            final int id = Item.REGISTRY.getIDForObject((Object)((ItemStack)AutoRegear.mc.player.inventoryContainer.getInventory().get(i)).getItem());
            this.register(new SimpleRemovingSetting(i - 9 + ":" + id));
        }
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length == 1) {
            this.unregisterAll();
            this.registerInventory();
            ChatUtil.sendMessage("§aKit saved!");
            return true;
        }
        return false;
    }
    
    @Override
    public Setting<?> getSettingConfig(final String name) {
        if (this.getSetting(name) == null) {
            final Setting<?> newSetting = new SimpleRemovingSetting(name);
            this.register(newSetting);
            return newSetting;
        }
        return this.getSetting(name);
    }
    
    public Setting<?> getSettingFromSlot(final int slot) {
        for (final Setting<?> setting : this.getSettings()) {
            if (setting.getName().startsWith(Integer.toString(slot))) {
                return setting;
            }
        }
        return null;
    }
    
    private int safetyFactor(final BlockPos pos) {
        return this.safety(pos) + this.safety(pos.up());
    }
    
    private int safety(final BlockPos pos) {
        int safety = 0;
        for (final EnumFacing facing : EnumFacing.values()) {
            if (!AutoRegear.mc.world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable()) {
                ++safety;
            }
        }
        return safety;
    }
    
    public BlockPos getBlock(final Block type) {
        final AtomicReference<BlockPos> block = new AtomicReference<BlockPos>();
        BlockUtil.sphere(this.range.getValue(), pos -> {
            if (AutoRegear.mc.world.getBlockState(pos).getBlock() == type) {
                block.set(pos);
            }
            return false;
        });
        return block.get();
    }
    
    public BlockPos getShulkerBox() {
        final AtomicReference<BlockPos> block = new AtomicReference<BlockPos>();
        BlockUtil.sphere(this.range.getValue(), pos -> {
            if (AutoRegear.mc.world.getBlockState(pos).getBlock() instanceof BlockShulkerBox) {
                block.set(pos);
            }
            return false;
        });
        return block.get();
    }
    
    public BlockPos getOptimalPlacePos(final boolean shulkerCheck) {
        final Set<BlockPos> positions = new HashSet<BlockPos>();
        BlockUtil.sphere(this.range.getValue(), pos -> {
            if (AutoRegear.mc.world.getBlockState(pos).getBlock().isReplaceable((IBlockAccess)AutoRegear.mc.world, pos) && this.entityCheck(pos) && AutoRegear.mc.world.getBlockState(pos.up()).getBlock().isReplaceable((IBlockAccess)AutoRegear.mc.world, pos) && (!shulkerCheck || AutoRegear.mc.world.getBlockState(pos.down()).getBlock().isReplaceable((IBlockAccess)AutoRegear.mc.world, pos))) {
                positions.add(pos);
            }
            return false;
        });
        final BlockPos position = positions.stream().sorted(Comparator.comparingInt(pos -> this.safety(pos) * -1)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).get(0);
        return position;
    }
    
    public boolean hasKit() {
        for (int i = 9; i < 45; ++i) {
            boolean foundExp = false;
            boolean foundCrystals = false;
            boolean foundGapples = false;
            final ItemStack stack = (ItemStack)AutoRegear.mc.player.inventoryContainer.getInventory().get(i);
            if (stack.getItem() instanceof ItemShulkerBox) {
                final NBTTagCompound tagCompound = stack.getTagCompound();
                if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
                    final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
                    if (blockEntityTag.hasKey("Items", 9)) {
                        final NonNullList<ItemStack> nonNullList = (NonNullList<ItemStack>)NonNullList.func_191197_a(27, (Object)ItemStack.field_190927_a);
                        ItemStackHelper.func_191283_b(blockEntityTag, (NonNullList)nonNullList);
                        for (final ItemStack stack2 : nonNullList) {
                            if (stack2.getItem() == Items.GOLDEN_APPLE) {
                                foundGapples = true;
                            }
                            else if (stack2.getItem() == Items.EXPERIENCE_BOTTLE) {
                                foundExp = true;
                            }
                            else {
                                if (stack2.getItem() != Items.END_CRYSTAL) {
                                    continue;
                                }
                                foundCrystals = true;
                            }
                        }
                        if (foundExp && foundGapples && foundCrystals) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    static {
        ARGS = new String[] { "SAVE", "RESET" };
    }
}
