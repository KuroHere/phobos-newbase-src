//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autocraft;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import net.minecraft.util.math.*;
import java.util.concurrent.atomic.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.world.*;
import java.util.stream.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.item.crafting.*;
import java.util.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;

public class AutoCraft extends BlockPlacingModule
{
    protected final Setting<Integer> delay;
    protected final Setting<Integer> clickDelay;
    protected final Setting<Boolean> placeTable;
    protected final Setting<Boolean> craftTable;
    protected final Setting<Boolean> moveTable;
    protected final Setting<Float> tableRange;
    private final Queue<CraftTask> taskQueue;
    protected CraftTask currentTask;
    protected CraftTask lastTask;
    protected final StopWatch delayTimer;
    protected final StopWatch clickDelayTimer;
    protected boolean shouldTable;
    
    public AutoCraft() {
        super("AutoCraft", Category.Misc);
        this.delay = this.register(new NumberSetting("Delay", 50, 0, 500));
        this.clickDelay = this.register(new NumberSetting("ClickDelay", 50, 0, 500));
        this.placeTable = this.register(new BooleanSetting("PlaceTable", false));
        this.craftTable = this.register(new BooleanSetting("CraftTable", false));
        this.moveTable = this.register(new BooleanSetting("MoveTable", false));
        this.tableRange = this.register(new NumberSetting("TableRange", 6.0f, 1.0f, 8.0f));
        this.taskQueue = new ConcurrentLinkedDeque<CraftTask>();
        this.delayTimer = new StopWatch();
        this.clickDelayTimer = new StopWatch();
        this.shouldTable = false;
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerMotion(this));
    }
    
    @Override
    protected void onEnable() {
        this.delayTimer.reset();
        this.clickDelayTimer.reset();
        this.submit(new CraftTask("furnace", 1));
    }
    
    public BlockPos getCraftingTable() {
        final AtomicReference<BlockPos> craftingTable = new AtomicReference<BlockPos>();
        BlockUtil.sphere(this.tableRange.getValue(), pos -> {
            if (AutoCraft.mc.world.getBlockState(pos).getBlock() == Blocks.CRAFTING_TABLE) {
                craftingTable.set(pos);
            }
            return false;
        });
        return craftingTable.get();
    }
    
    public BlockPos getCraftingTableBlock() {
        final Set<BlockPos> positions = new HashSet<BlockPos>();
        BlockUtil.sphere(this.tableRange.getValue(), pos -> {
            if (AutoCraft.mc.world.getBlockState(pos).getBlock().isReplaceable((IBlockAccess)AutoCraft.mc.world, pos) && this.entityCheck(pos)) {
                positions.add(pos);
            }
            return false;
        });
        return positions.stream().sorted(Comparator.comparingInt(pos -> this.safety(pos) * -1)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).get(0);
    }
    
    public void submit(final CraftTask task) {
        this.taskQueue.add(task);
    }
    
    public CraftTask dequeue() {
        return this.taskQueue.poll();
    }
    
    private int safetyFactor(final BlockPos pos) {
        return this.safety(pos) + this.safety(pos.up());
    }
    
    private int safety(final BlockPos pos) {
        int safety = 0;
        for (final EnumFacing facing : EnumFacing.values()) {
            if (!AutoCraft.mc.world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable()) {
                ++safety;
            }
        }
        return safety;
    }
    
    public static class SlotEntry
    {
        private final int guiSlot;
        private final int inventorySlot;
        
        public SlotEntry(final int guiSlot, final int inventorySlot) {
            this.guiSlot = guiSlot;
            this.inventorySlot = inventorySlot;
        }
        
        public int getGuiSlot() {
            return this.guiSlot;
        }
        
        public int getInventorySlot() {
            return this.inventorySlot;
        }
    }
    
    public static class CraftTask
    {
        private final IRecipe recipe;
        private List<SlotEntry> entryList;
        private final boolean inTable;
        protected int runs;
        protected int step;
        
        public CraftTask(final String recipeName, final int runs) {
            this(Objects.requireNonNull(CraftingManager.func_193373_a(new ResourceLocation(recipeName))), runs);
        }
        
        public CraftTask(final IRecipe recipe, final int runs) {
            this.step = 0;
            this.recipe = recipe;
            this.entryList = new ArrayList<SlotEntry>();
            this.inTable = !recipe.func_194133_a(2, 2);
            this.runs = runs;
            int i = 1;
            for (final Ingredient stack : recipe.func_192400_c()) {
                if (recipe instanceof ShapedRecipes) {
                    if (stack != Ingredient.field_193370_a) {
                        final int inventorySlot = InventoryUtil.findInInventory(itemStack -> Arrays.stream(stack.func_193365_a()).anyMatch(itemStack1 -> itemStack1.getItem() == itemStack.getItem()), false);
                        this.entryList.add(new SlotEntry(i, inventorySlot));
                    }
                    ++i;
                }
                else if (recipe instanceof ShapelessRecipes) {}
            }
        }
        
        public void updateSlots() {
            int i = 1;
            final List<SlotEntry> entries = new ArrayList<SlotEntry>();
            for (final Ingredient stack : this.recipe.func_192400_c()) {
                if (this.recipe instanceof ShapedRecipes) {
                    if (stack != Ingredient.field_193370_a) {
                        int inventorySlot;
                        if (Globals.mc.currentScreen instanceof GuiCrafting) {
                            inventorySlot = InventoryUtil.findInCraftingTable(((GuiContainer)Globals.mc.currentScreen).inventorySlots, itemStack -> Arrays.stream(stack.func_193365_a()).anyMatch(itemStack1 -> itemStack1.getItem() == itemStack.getItem()));
                        }
                        else {
                            inventorySlot = InventoryUtil.findInInventory(itemStack -> Arrays.stream(stack.func_193365_a()).anyMatch(itemStack1 -> itemStack1.getItem() == itemStack.getItem()), false);
                        }
                        if (inventorySlot != -1) {
                            entries.add(new SlotEntry(i, inventorySlot));
                        }
                    }
                    ++i;
                }
                else if (this.recipe instanceof ShapelessRecipes) {}
            }
            this.entryList = entries;
        }
        
        public IRecipe getRecipe() {
            return this.recipe;
        }
        
        public List<SlotEntry> getSlotToSlotMap() {
            return this.entryList;
        }
        
        public boolean isInTable() {
            return this.inTable;
        }
        
        public int getStep() {
            return this.step;
        }
        
        public void setStep(final int step) {
            this.step = step;
        }
        
        public int getRuns() {
            return this.runs;
        }
        
        public void setRuns(final int runs) {
            this.runs = runs;
        }
    }
}
