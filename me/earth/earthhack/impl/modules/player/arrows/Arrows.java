//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.arrows;

import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.util.*;
import net.minecraft.client.resources.*;
import java.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class Arrows extends RegisteringModule<Boolean, SimpleRemovingSetting>
{
    protected static final PotionType SPECTRAL;
    protected static final Set<PotionType> BAD_TYPES;
    protected final Setting<Boolean> shoot;
    protected final Setting<Boolean> cycle;
    protected final Setting<Boolean> autoRelease;
    protected final Setting<Integer> releaseTicks;
    protected final Setting<Integer> maxTicks;
    protected final Setting<Boolean> tpsSync;
    protected final Setting<Integer> cancelTime;
    protected final Setting<Integer> delay;
    protected final Setting<Integer> shootDelay;
    protected final Setting<Integer> minDura;
    protected final Setting<Bind> cycleButton;
    protected final Setting<Boolean> keyCycle;
    protected final Setting<Boolean> preCycle;
    protected final Setting<Boolean> fastCancel;
    protected final Set<PotionType> cycled;
    protected final StopWatch cycleTimer;
    protected final StopWatch timer;
    protected boolean fast;
    
    public Arrows() {
        super("Arrows", Category.Player, "Add_Potion", "potion", SimpleRemovingSetting::new, s -> "Black/Whitelist " + s.getName() + " potion arrows.");
        this.shoot = this.register(new BooleanSetting("Shoot", false));
        this.cycle = this.register(new BooleanSetting("Cycle-Shoot", true));
        this.autoRelease = this.register(new BooleanSetting("Auto-Release", false));
        this.releaseTicks = this.register(new NumberSetting("Release-Ticks", 3, 0, 20));
        this.maxTicks = this.register(new NumberSetting("Max-Ticks", 10, 0, 20));
        this.tpsSync = this.register(new BooleanSetting("Tps-Sync", true));
        this.cancelTime = this.register(new NumberSetting("Cancel-Time", 0, 0, 500));
        this.delay = this.register(new NumberSetting("Cycle-Delay", 250, 0, 500));
        this.shootDelay = this.register(new NumberSetting("Shoot-Delay", 500, 0, 500));
        this.minDura = this.register(new NumberSetting("Min-Potion", 0, 0, 1000));
        this.cycleButton = this.register(new BindSetting("Cycle-Bind", Bind.none()));
        this.keyCycle = this.register(new BooleanSetting("Bind-Cycle-BlackListed", true));
        this.preCycle = this.register(new BooleanSetting("Fast-Cycle", false));
        this.fastCancel = this.register(new BooleanSetting("Fast-Cancel", false));
        this.cycled = new HashSet<PotionType>();
        this.cycleTimer = new StopWatch();
        this.timer = new StopWatch();
        super.listType.setValue(ListType.BlackList);
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerUseItem(this));
        this.listeners.add(new ListenerKeyboard(this));
        final ModuleData data = new SimpleData(this, "Cycles through your arrows. Not compatible with AntiPotion.");
        this.setData(data);
    }
    
    @Override
    protected void onEnable() {
        this.fast = false;
    }
    
    @Override
    public String getInput(final String input, final boolean add) {
        if (!add) {
            return super.getInput(input, false);
        }
        final String potionName = getPotionNameStartingWith(input);
        if (potionName != null) {
            return TextUtil.substring(potionName, input.length());
        }
        return "";
    }
    
    @Override
    public String getDisplayInfo() {
        final ItemStack stack = this.findArrow();
        if (!stack.func_190926_b()) {
            return stack.getItem().getItemStackDisplayName(stack).replace("Arrow of ", "").replace(" Arrow", "");
        }
        return null;
    }
    
    protected boolean badStack(final ItemStack stack) {
        return this.badStack(stack, true, Collections.emptySet());
    }
    
    protected boolean badStack(final ItemStack stack, final boolean checkType, final Set<PotionType> cycled) {
        PotionType type = PotionUtils.getPotionFromItem(stack);
        if (stack.getItem() instanceof ItemSpectralArrow) {
            type = Arrows.SPECTRAL;
        }
        if (cycled.contains(type)) {
            return true;
        }
        if (checkType) {
            if (Arrows.BAD_TYPES.contains(type)) {
                return true;
            }
        }
        else if (this.keyCycle.getValue() || (type.getEffects().isEmpty() && this.isValid("none"))) {
            return false;
        }
        if (stack.getItem() instanceof ItemSpectralArrow) {
            return !this.isValid("Spectral") || Arrows.mc.player.isGlowing();
        }
        boolean inValid = true;
        for (final PotionEffect e : type.getEffects()) {
            if (!this.isValid(I18n.format(e.getPotion().getName(), new Object[0]))) {
                return true;
            }
            final PotionEffect eff = Arrows.mc.player.getActivePotionEffect(e.getPotion());
            if (eff != null && eff.getDuration() >= this.minDura.getValue()) {
                continue;
            }
            inValid = false;
        }
        return (checkType || this.keyCycle.getValue()) && inValid;
    }
    
    public void cycle(final boolean recursive, final boolean key) {
        if (!InventoryUtil.validScreen() || (key && !this.cycleTimer.passed(this.delay.getValue()))) {
            return;
        }
        int firstSlot = -1;
        int secondSlot = -1;
        ItemStack arrow = null;
        if (this.isArrow(Arrows.mc.player.getHeldItem(EnumHand.OFF_HAND))) {
            firstSlot = 45;
        }
        if (this.isArrow(Arrows.mc.player.getHeldItem(EnumHand.MAIN_HAND))) {
            if (firstSlot == -1) {
                firstSlot = InventoryUtil.hotbarToInventory(Arrows.mc.player.inventory.currentItem);
            }
            else if (!this.badStack(Arrows.mc.player.getHeldItem(EnumHand.MAIN_HAND), key, this.cycled)) {
                secondSlot = InventoryUtil.hotbarToInventory(Arrows.mc.player.inventory.currentItem);
                arrow = Arrows.mc.player.getHeldItem(EnumHand.MAIN_HAND);
            }
        }
        if (!this.badStack(Arrows.mc.player.inventory.getItemStack(), key, this.cycled)) {
            secondSlot = -2;
            arrow = Arrows.mc.player.inventory.getItemStack();
        }
        if (firstSlot == -1 || secondSlot == -1) {
            for (int i = 0; i < Arrows.mc.player.inventory.getSizeInventory(); ++i) {
                final ItemStack stack = Arrows.mc.player.inventory.getStackInSlot(i);
                if (this.isArrow(stack)) {
                    if (firstSlot == -1) {
                        firstSlot = InventoryUtil.hotbarToInventory(i);
                    }
                    else if (!this.badStack(stack, key, this.cycled)) {
                        secondSlot = InventoryUtil.hotbarToInventory(i);
                        arrow = stack;
                        break;
                    }
                }
            }
        }
        if (firstSlot == -1) {
            return;
        }
        if (secondSlot == -1) {
            if (!recursive && !this.cycled.isEmpty()) {
                this.cycled.clear();
                this.cycle(true, key);
            }
            return;
        }
        PotionType type = PotionUtils.getPotionFromItem(arrow);
        if (arrow.getItem() instanceof ItemSpectralArrow) {
            type = Arrows.SPECTRAL;
        }
        this.cycled.add(type);
        final int finalFirstSlot = firstSlot;
        final int finalSecondSlot = secondSlot;
        final Item inFirst = InventoryUtil.get(finalFirstSlot).getItem();
        final Item inSecond = InventoryUtil.get(finalSecondSlot).getItem();
        Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
            if (InventoryUtil.get(finalFirstSlot).getItem() == inFirst && InventoryUtil.get(finalSecondSlot).getItem() == inSecond) {
                if (finalSecondSlot == -2) {
                    InventoryUtil.click(finalFirstSlot);
                }
                else {
                    InventoryUtil.click(finalSecondSlot);
                    InventoryUtil.click(finalFirstSlot);
                    InventoryUtil.click(finalSecondSlot);
                }
            }
            return;
        });
        this.cycleTimer.reset();
    }
    
    protected ItemStack findArrow() {
        if (this.isArrow(Arrows.mc.player.getHeldItem(EnumHand.OFF_HAND))) {
            return Arrows.mc.player.getHeldItem(EnumHand.OFF_HAND);
        }
        if (this.isArrow(Arrows.mc.player.getHeldItem(EnumHand.MAIN_HAND))) {
            return Arrows.mc.player.getHeldItem(EnumHand.MAIN_HAND);
        }
        for (int i = 0; i < Arrows.mc.player.inventory.getSizeInventory(); ++i) {
            final ItemStack stack = Arrows.mc.player.inventory.getStackInSlot(i);
            if (this.isArrow(stack)) {
                return stack;
            }
        }
        return ItemStack.field_190927_a;
    }
    
    protected boolean isArrow(final ItemStack stack) {
        return stack.getItem() instanceof ItemArrow;
    }
    
    public static String getPotionNameStartingWith(final String name) {
        final Potion potion = getPotionStartingWith(name);
        if (potion == SpecialPot.SPECTRAL) {
            return "Spectral";
        }
        if (potion == SpecialPot.NONE) {
            return "None";
        }
        if (potion != null) {
            return I18n.format(potion.getName(), new Object[0]);
        }
        return null;
    }
    
    public static Potion getPotionStartingWith(String name) {
        if (name == null) {
            return null;
        }
        name = name.toLowerCase();
        for (final Potion potion : Potion.REGISTRY) {
            if (I18n.format(potion.getName(), new Object[0]).toLowerCase().startsWith(name)) {
                return potion;
            }
        }
        if ("spectral".startsWith(name)) {
            return SpecialPot.SPECTRAL;
        }
        if ("none".startsWith(name)) {
            return SpecialPot.NONE;
        }
        return null;
    }
    
    static {
        SPECTRAL = new PotionType(new PotionEffect[0]);
        BAD_TYPES = Sets.newHashSet((Object[])new PotionType[] { PotionTypes.EMPTY, PotionTypes.WATER, PotionTypes.MUNDANE, PotionTypes.THICK, PotionTypes.AWKWARD, PotionTypes.HEALING, PotionTypes.STRONG_HEALING, PotionTypes.STRONG_HARMING, PotionTypes.HARMING });
    }
    
    private static final class SpecialPot extends Potion
    {
        public static final SpecialPot SPECTRAL;
        public static final SpecialPot NONE;
        
        private SpecialPot() {
            super(false, 0);
        }
        
        static {
            SPECTRAL = new SpecialPot();
            NONE = new SpecialPot();
        }
    }
}
