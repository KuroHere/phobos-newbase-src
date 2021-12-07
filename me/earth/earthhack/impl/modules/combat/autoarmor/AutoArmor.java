//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.exptweaks.*;
import me.earth.earthhack.impl.modules.player.xcarry.*;
import me.earth.earthhack.impl.modules.player.suicide.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.modes.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.util.*;
import net.minecraft.inventory.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.enchantment.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import org.lwjgl.input.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.*;

public class AutoArmor extends Module
{
    private static final ModuleCache<ExpTweaks> EXP_TWEAKS;
    private static final ModuleCache<XCarry> XCARRY;
    private static final ModuleCache<Suicide> SUICIDE;
    protected final Setting<ArmorMode> mode;
    protected final Setting<Boolean> fast;
    protected final Setting<Boolean> safe;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> autoMend;
    protected final Setting<Integer> helmet;
    protected final Setting<Integer> chest;
    protected final Setting<Integer> legs;
    protected final Setting<Integer> boots;
    protected final Setting<Boolean> curse;
    protected final Setting<Float> closest;
    protected final Setting<Float> maxDmg;
    protected final Setting<Boolean> newVer;
    protected final Setting<Boolean> newVerEntities;
    protected final Setting<Boolean> bedCheck;
    protected final Setting<Boolean> noDesync;
    protected final Setting<Boolean> illegalSync;
    protected final Setting<Boolean> screenCheck;
    protected final Setting<Integer> desyncDelay;
    protected final Setting<Integer> checkDelay;
    protected final Setting<Integer> propertyDelay;
    protected final Setting<Boolean> dragTakeOff;
    protected final Setting<Boolean> prioLow;
    protected final Setting<Float> prioThreshold;
    protected final Setting<Boolean> putBack;
    protected final Setting<Boolean> doubleClicks;
    protected final Setting<Boolean> wasteLoot;
    protected final Setting<Boolean> takeOffLoot;
    protected final Setting<Boolean> noDuraDesync;
    protected final Setting<Integer> removeTime;
    protected final Map<Integer, DesyncClick> desyncMap;
    protected final Queue<WindowClick> windowClicks;
    protected final StopWatch propertyTimer;
    protected final StopWatch desyncTimer;
    protected final DiscreteTimer timer;
    protected Set<Integer> queuedSlots;
    protected EntityEquipmentSlot lastType;
    protected final Setting<?>[] damages;
    protected WindowClick putBackClick;
    protected boolean stackSet;
    
    public AutoArmor() {
        super("AutoArmor", Category.Combat);
        this.mode = this.register(new EnumSetting("Mode", ArmorMode.Blast));
        this.fast = this.register(new BooleanSetting("Fast", true));
        this.safe = this.register(new BooleanSetting("Safe", true));
        this.delay = this.register(new NumberSetting("Delay", 50, 0, 500));
        this.autoMend = this.register(new BooleanSetting("AutoMend", false));
        this.helmet = this.register(new NumberSetting("Helmet%", 80, 1, 100));
        this.chest = this.register(new NumberSetting("Chest%", 85, 1, 100));
        this.legs = this.register(new NumberSetting("Legs%", 84, 1, 100));
        this.boots = this.register(new NumberSetting("Boots%", 80, 1, 100));
        this.curse = this.register(new BooleanSetting("CurseOfBinding", false));
        this.closest = this.register(new NumberSetting("Closest", 12.0f, 0.0f, 30.0f));
        this.maxDmg = this.register(new NumberSetting("MaxDmg", 1.5f, 0.0f, 36.0f));
        this.newVer = this.register(new BooleanSetting("1.13+", false));
        this.newVerEntities = this.register(new BooleanSetting("1.13-Entities", false));
        this.bedCheck = this.register(new BooleanSetting("BedCheck", false));
        this.noDesync = this.register(new BooleanSetting("NoDesync", false));
        this.illegalSync = this.register(new BooleanSetting("Illegal-Sync", false));
        this.screenCheck = this.register(new BooleanSetting("CheckScreen", true));
        this.desyncDelay = this.register(new NumberSetting("DesyncDelay", 2500, 0, 5000));
        this.checkDelay = this.register(new NumberSetting("CheckDelay", 250, 0, 5000));
        this.propertyDelay = this.register(new NumberSetting("PropertyDelay", 500, 0, 5000));
        this.dragTakeOff = this.register(new BooleanSetting("Drag-Mend", false));
        this.prioLow = this.register(new BooleanSetting("Prio-Low", true));
        this.prioThreshold = this.register(new NumberSetting("Prio-Threshold", 40.0f, 0.0f, 100.0f));
        this.putBack = this.register(new BooleanSetting("Put-Back", false));
        this.doubleClicks = this.register(new BooleanSetting("Double-Clicks", false));
        this.wasteLoot = this.register(new BooleanSetting("Waste-Loot", false));
        this.takeOffLoot = this.register(new BooleanSetting("TakeOff-Loot", false));
        this.noDuraDesync = this.register(new BooleanSetting("NoDuraDesync", true));
        this.removeTime = this.register(new NumberSetting("Remove-Time", 250, 0, 1000));
        this.desyncMap = new ConcurrentHashMap<Integer, DesyncClick>();
        this.windowClicks = new LinkedList<WindowClick>();
        this.propertyTimer = new StopWatch();
        this.desyncTimer = new StopWatch();
        this.timer = new GuardTimer();
        this.queuedSlots = new HashSet<Integer>();
        this.damages = new Setting[] { this.helmet, this.chest, this.legs, this.boots };
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerEntityProperties(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerSetSlot(this));
        this.setData(new AutoArmorData(this));
        this.timer.reset(this.delay.getValue());
    }
    
    @Override
    protected void onEnable() {
        this.windowClicks.clear();
        this.queuedSlots.clear();
        this.putBackClick = null;
    }
    
    @Override
    protected void onDisable() {
        this.windowClicks.clear();
        this.queuedSlots.clear();
        this.putBackClick = null;
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
    
    public boolean isActive() {
        return this.isEnabled() && !this.windowClicks.isEmpty();
    }
    
    public void resetTimer() {
        this.timer.reset(this.delay.getValue());
    }
    
    public WindowClick queueClick(final int slot, final ItemStack inSlot, final ItemStack inDrag) {
        return this.queueClick(slot, inSlot, inDrag, slot);
    }
    
    public WindowClick queueClick(final int slot, final ItemStack inSlot, final ItemStack inDrag, final int target) {
        final WindowClick click = new WindowClick(slot, inSlot, inDrag, target);
        this.queueClick(click);
        click.setFast(this.fast.getValue());
        return click;
    }
    
    public void queueClick(final WindowClick click) {
        this.windowClicks.add(click);
    }
    
    protected void runClick() {
        if (InventoryUtil.validScreen() && AutoArmor.mc.playerController != null) {
            if (this.timer.passed(this.delay.getValue())) {
                Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                    Managers.NCP.startMultiClick();
                    WindowClick windowClick = this.windowClicks.poll();
                    while (windowClick != null) {
                        if (this.safe.getValue() && !windowClick.isValid()) {
                            this.windowClicks.clear();
                            this.queuedSlots.clear();
                            Managers.NCP.releaseMultiClick();
                        }
                        else {
                            windowClick.runClick(AutoArmor.mc.playerController);
                            this.desyncMap.put(windowClick.getSlot(), new DesyncClick(windowClick));
                            this.timer.reset(this.delay.getValue());
                            if (!windowClick.isDoubleClick()) {
                                Managers.NCP.releaseMultiClick();
                            }
                            else {
                                windowClick = this.windowClicks.poll();
                            }
                        }
                    }
                });
            }
        }
        else {
            this.windowClicks.clear();
            this.queuedSlots.clear();
        }
    }
    
    protected ItemStack setStack() {
        if (this.stackSet) {
            return null;
        }
        final ItemStack drag = AutoArmor.mc.player.inventory.getItemStack();
        if (drag.func_190926_b()) {
            this.stackSet = true;
            return drag;
        }
        final int slot = findItem(Items.field_190931_a, AutoArmor.XCARRY.isEnabled(), this.queuedSlots);
        if (slot != -1) {
            final ItemStack inSlot = InventoryUtil.get(slot);
            this.queueClick(slot, drag, inSlot);
            this.queuedSlots.add(slot);
            this.stackSet = true;
            return inSlot;
        }
        return null;
    }
    
    public static boolean curseCheck(final ItemStack stack, final boolean check) {
        return !check || !EnchantmentHelper.func_190938_b(stack);
    }
    
    boolean canAutoMend() {
        if (AutoArmor.SUICIDE.returnIfPresent(Suicide::shouldTakeOffArmor, false)) {
            return this.takeOffLoot.getValue() || AutoArmor.mc.world.getEntitiesWithinAABB((Class)EntityItem.class, RotationUtil.getRotationPlayer().getEntityBoundingBox()).isEmpty();
        }
        if (!this.autoMend.getValue() || (this.screenCheck.getValue() && AutoArmor.mc.currentScreen != null) || ((!Mouse.isButtonDown(1) || !InventoryUtil.isHolding(Items.EXPERIENCE_BOTTLE)) && (!AutoArmor.EXP_TWEAKS.isEnabled() || !AutoArmor.EXP_TWEAKS.returnIfPresent(ExpTweaks::isMiddleClick, false))) || (this.wasteLoot.getValue() && AutoArmor.EXP_TWEAKS.returnIfPresent(e -> e.isWastingLoot(AutoArmor.mc.world.loadedEntityList), Boolean.valueOf(false))) || (!this.takeOffLoot.getValue() && !AutoArmor.mc.world.getEntitiesWithinAABB((Class)EntityItem.class, RotationUtil.getRotationPlayer().getEntityBoundingBox()).isEmpty())) {
            return false;
        }
        final EntityPlayer closestPlayer = EntityUtil.getClosestEnemy();
        if (closestPlayer != null && closestPlayer.getDistanceSqToEntity((Entity)AutoArmor.mc.player) < MathUtil.square(this.closest.getValue() * 2.0f)) {
            for (final Entity entity : AutoArmor.mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderCrystal && !entity.isDead && AutoArmor.mc.player.getDistanceSqToEntity(entity) <= 144.0) {
                    final double damage = this.getDamageNoArmor(entity.posX, entity.posY, entity.posZ);
                    if (damage > EntityUtil.getHealth((EntityLivingBase)AutoArmor.mc.player) + 1.0 || damage > this.maxDmg.getValue()) {
                        return false;
                    }
                    continue;
                }
            }
            final BlockPos middle = PositionUtil.getPosition();
            for (int maxRadius = Sphere.getRadius(10.0), i = 1; i < maxRadius; ++i) {
                final BlockPos pos = middle.add(Sphere.get(i));
                if (BlockUtil.canPlaceCrystal(pos, false, this.newVer.getValue(), AutoArmor.mc.world.loadedEntityList, this.newVerEntities.getValue(), 0L) || (this.bedCheck.getValue() && BlockUtil.canPlaceBed(pos, this.newVer.getValue()))) {
                    final double damage2 = this.getDamageNoArmor(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                    if (damage2 > EntityUtil.getHealth((EntityLivingBase)AutoArmor.mc.player) + 1.0 || damage2 > this.maxDmg.getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private double getDamageNoArmor(final double x, final double y, final double z) {
        double distance = AutoArmor.mc.player.getDistance(x, y, z) / 12.0;
        if (distance > 1.0) {
            return 0.0;
        }
        final double density = DamageUtil.getBlockDensity(new Vec3d(x, y, z), AutoArmor.mc.player.getEntityBoundingBox(), (IBlockAccess)AutoArmor.mc.world, true, true, false, false);
        final double densityDistance;
        distance = (densityDistance = (1.0 - distance) * density);
        float damage = DamageUtil.getDifficultyMultiplier((float)((densityDistance * densityDistance + distance) / 2.0 * 7.0 * 12.0 + 1.0));
        damage = CombatRules.getDamageAfterAbsorb(damage, 3.0f, 2.0f);
        final PotionEffect resistance = AutoArmor.mc.player.getActivePotionEffect(MobEffects.RESISTANCE);
        if (resistance != null) {
            damage = damage * (25 - (resistance.getAmplifier() + 1) * 5) / 25.0f;
        }
        return Math.max(damage, 0.0f);
    }
    
    public static EntityEquipmentSlot fromSlot(final int slot) {
        switch (slot) {
            case 5: {
                return EntityEquipmentSlot.HEAD;
            }
            case 6: {
                return EntityEquipmentSlot.CHEST;
            }
            case 7: {
                return EntityEquipmentSlot.LEGS;
            }
            case 8: {
                return EntityEquipmentSlot.FEET;
            }
            default: {
                final ItemStack stack = InventoryUtil.get(slot);
                return getSlot(stack);
            }
        }
    }
    
    public static int fromEquipment(final EntityEquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case OFFHAND: {
                return 45;
            }
            case FEET: {
                return 8;
            }
            case LEGS: {
                return 7;
            }
            case CHEST: {
                return 6;
            }
            case HEAD: {
                return 5;
            }
            default: {
                return -1;
            }
        }
    }
    
    public static EntityEquipmentSlot getSlot(final ItemStack stack) {
        if (!stack.func_190926_b()) {
            if (stack.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor)stack.getItem();
                return armor.getEquipmentSlot();
            }
            if (stack.getItem() instanceof ItemElytra) {
                return EntityEquipmentSlot.CHEST;
            }
        }
        return null;
    }
    
    public static int findItem(final Item item, final boolean xCarry, final Set<Integer> blackList) {
        final ItemStack drag = AutoArmor.mc.player.inventory.getItemStack();
        if (!drag.func_190926_b() && drag.getItem() == item && !blackList.contains(-2)) {
            return -2;
        }
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = InventoryUtil.get(i);
            if (stack.getItem() == item && !blackList.contains(i)) {
                return i;
            }
        }
        if (xCarry) {
            for (int i = 1; i < 5; ++i) {
                final ItemStack stack = InventoryUtil.get(i);
                if (stack.getItem() == item && !blackList.contains(i)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    static {
        EXP_TWEAKS = Caches.getModule(ExpTweaks.class);
        XCARRY = Caches.getModule(XCarry.class);
        SUICIDE = Caches.getModule(Suicide.class);
    }
}
