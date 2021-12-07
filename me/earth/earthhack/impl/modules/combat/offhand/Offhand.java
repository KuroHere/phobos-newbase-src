//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.offhand;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautototem.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.*;
import me.earth.earthhack.impl.modules.player.xcarry.*;
import me.earth.earthhack.impl.modules.player.suicide.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.init.*;
import org.lwjgl.input.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.util.function.*;
import java.util.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.*;

public class Offhand extends Module
{
    private static final ModuleCache<PingBypass> PINGBYPASS;
    private static final ModuleCache<ServerAutoTotem> AUTOTOTEM;
    private static final ModuleCache<AutoArmor> AUTO_ARMOR;
    private static final ModuleCache<XCarry> XCARRY;
    private static final ModuleCache<Suicide> SUICIDE;
    protected final Setting<Float> health;
    protected final Setting<Float> safeH;
    protected final Setting<Bind> gappleBind;
    protected final Setting<Bind> crystalBind;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> cToTotem;
    protected final Setting<Boolean> swordGap;
    protected final Setting<Boolean> recover;
    protected final Setting<Boolean> noOGC;
    protected final Setting<Boolean> hotbarFill;
    protected final Setting<HUDMode> hudMode;
    protected final Setting<Integer> timeOut;
    protected final Setting<Boolean> crystalsIfNoTotem;
    protected final Setting<Boolean> async;
    protected final Setting<Integer> asyncCheck;
    protected final Setting<Boolean> crystalCheck;
    protected final Setting<Boolean> doubleClicks;
    protected final Setting<Boolean> noMove;
    protected final Setting<Boolean> cancelActions;
    protected final Setting<Boolean> cancelActive;
    protected final Setting<Boolean> noDoubleGapple;
    protected final Setting<Boolean> doubleGappleToCrystal;
    protected final Map<Item, Integer> lastSlots;
    protected final StopWatch setSlotTimer;
    protected final StopWatch timeOutTimer;
    protected final StopWatch asyncTimer;
    protected final StopWatch timer;
    protected OffhandMode mode;
    protected OffhandMode recovery;
    protected volatile int asyncSlot;
    protected boolean swordGapped;
    protected boolean lookedUp;
    protected boolean pulledFromHotbar;
    protected boolean sneaking;
    protected boolean sprinting;
    
    public Offhand() {
        super("Offhand", Category.Combat);
        this.health = this.register(new NumberSetting("Health", 14.5f, 0.0f, 36.0f));
        this.safeH = this.register(new NumberSetting("SafeHealth", 3.0f, 0.0f, 36.0f));
        this.gappleBind = this.register(new BindSetting("GappleBind", Bind.none()));
        this.crystalBind = this.register(new BindSetting("CrystalBind", Bind.none()));
        this.delay = this.register(new NumberSetting("Delay", 25, 0, 500));
        this.cToTotem = this.register(new BooleanSetting("Crystal-Totem", true));
        this.swordGap = this.register(new BooleanSetting("Sword-Gapple", false));
        this.recover = this.register(new BooleanSetting("RecoverSwitch", true));
        this.noOGC = this.register(new BooleanSetting("AntiPlace", true));
        this.hotbarFill = this.register(new BooleanSetting("Totem-Hotbar", false));
        this.hudMode = this.register(new EnumSetting("HUDMode", HUDMode.Info));
        this.timeOut = this.register(new NumberSetting("TimeOut", 600, 0, 1000));
        this.crystalsIfNoTotem = this.register(new BooleanSetting("CrystalsIfNoTotem", false));
        this.async = this.register(new BooleanSetting("Async-Totem", false));
        this.asyncCheck = this.register(new NumberSetting("Async-Check", 100, 0, 1000));
        this.crystalCheck = this.register(new BooleanSetting("CrystalCheck", true));
        this.doubleClicks = this.register(new BooleanSetting("DoubleClicks", false));
        this.noMove = this.register(new BooleanSetting("NoMove", false));
        this.cancelActions = this.register(new BooleanSetting("CancelActions", false));
        this.cancelActive = this.register(new BooleanSetting("CancelActive", false));
        this.noDoubleGapple = this.register(new BooleanSetting("NoDoubleGapple", false));
        this.doubleGappleToCrystal = this.register(new BooleanSetting("DoubleGappleToCrystal", false));
        this.lastSlots = new HashMap<Item, Integer>();
        this.setSlotTimer = new StopWatch();
        this.timeOutTimer = new StopWatch();
        this.asyncTimer = new StopWatch();
        this.timer = new StopWatch();
        this.mode = OffhandMode.TOTEM;
        this.recovery = null;
        this.asyncSlot = -1;
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerKeyboard(this));
        this.listeners.add(new ListenerRightClick(this));
        this.listeners.add(new ListenerTotem(this));
        this.listeners.add(new ListenerSetSlot(this));
        this.setData(new OffhandData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        switch (this.hudMode.getValue()) {
            case Info: {
                return this.mode.getName();
            }
            case Name: {
                return InventoryUtil.getCount(this.mode.getItem()) + "";
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public String getDisplayName() {
        if (this.hudMode.getValue() != HUDMode.Name) {
            return super.getDisplayName();
        }
        if (OffhandMode.TOTEM.equals(this.mode)) {
            return "AutoTotem";
        }
        return "Offhand" + this.mode.getName();
    }
    
    public void setMode(final OffhandMode mode) {
        this.mode = mode;
        this.recovery = (mode.equals(OffhandMode.TOTEM) ? this.recovery : null);
        this.swordGapped = false;
    }
    
    public OffhandMode getMode() {
        return this.mode;
    }
    
    public void doOffhand() {
        if (Offhand.mc.player != null && this.timer.passed(this.delay.getValue()) && InventoryUtil.validScreen() && !Offhand.SUICIDE.returnIfPresent(Suicide::deactivateOffhand, false)) {
            if ((Offhand.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || Offhand.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe) && this.swordGap.getValue() && (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE || Offhand.mc.player.getHeldItemOffhand().getItem() == Items.field_190929_cY)) {
                if (Mouse.isButtonDown(1) && OffhandMode.TOTEM.equals(this.mode)) {
                    this.mode = OffhandMode.GAPPLE;
                    this.swordGapped = true;
                }
                else if (this.swordGapped && !Mouse.isButtonDown(1) && OffhandMode.GAPPLE.equals(this.mode)) {
                    this.setMode(OffhandMode.TOTEM);
                }
            }
            if (!this.isSafe()) {
                this.setRecovery(this.mode);
                this.mode = OffhandMode.TOTEM;
            }
            else if (this.recover.getValue() && this.recovery != null && this.timeOutTimer.passed(this.timeOut.getValue())) {
                this.setMode(this.recovery);
            }
            final int tSlot = InventoryUtil.findItem(Items.field_190929_cY, true);
            final int hotbar = InventoryUtil.findHotbarItem(Items.field_190929_cY, new Item[0]);
            if (this.pulledFromHotbar && this.hotbarFill.getValue() && InventoryUtil.findEmptyHotbarSlot() != -1 && (hotbar == -1 || hotbar == -2) && tSlot != -1 && this.timer.passed(this.timeOut.getValue())) {
                Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                    if (InventoryUtil.get(tSlot).getItem() == Items.field_190929_cY) {
                        Offhand.mc.playerController.windowClick(0, tSlot, 0, ClickType.QUICK_MOVE, (EntityPlayer)Offhand.mc.player);
                    }
                    return;
                });
                this.postWindowClick();
                this.pulledFromHotbar = false;
            }
            if (this.crystalsIfNoTotem.getValue() && this.mode == OffhandMode.TOTEM && InventoryUtil.getCount(Items.field_190929_cY) == 0 && InventoryUtil.getCount(Items.END_CRYSTAL) != 0 && this.setSlotTimer.passed(250L)) {
                this.mode = OffhandMode.CRYSTAL;
                this.swordGapped = false;
            }
            if (this.noDoubleGapple.getValue() && this.mode == OffhandMode.GAPPLE && Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && Offhand.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE) {
                if (this.doubleGappleToCrystal.getValue()) {
                    this.mode = OffhandMode.CRYSTAL;
                }
                else {
                    this.mode = OffhandMode.TOTEM;
                }
                this.swordGapped = false;
            }
            this.switchToItem(this.mode.getItem());
        }
    }
    
    public void setRecovery(final OffhandMode recoveryIn) {
        if (this.recover.getValue() && recoveryIn != null && !recoveryIn.equals(OffhandMode.TOTEM)) {
            this.recovery = recoveryIn;
            this.timeOutTimer.reset();
        }
    }
    
    private void switchToItem(final Item item) {
        final ItemStack drag = Offhand.mc.player.inventory.getItemStack();
        final Item dragItem = drag.getItem();
        final Item offhandItem = Offhand.mc.player.getHeldItemOffhand().getItem();
        if (offhandItem != item) {
            if (dragItem == item) {
                this.preWindowClick();
                InventoryUtil.clickLocked(-2, 45, dragItem, offhandItem);
                this.postWindowClick();
                this.lookedUp = false;
            }
            else {
                final Integer last = this.lastSlots.get(item);
                int slot;
                if (last != null && InventoryUtil.get(last).getItem() == item) {
                    slot = last;
                }
                else {
                    slot = this.findItem(item);
                }
                if (slot != -1 && slot != -2) {
                    this.lastSlots.put(item, slot);
                    this.lookedUp = false;
                    final Item slotItem = InventoryUtil.get(slot).getItem();
                    this.preWindowClick();
                    if (this.doubleClicks.getValue()) {
                        InventoryUtil.clickLocked(slot, 45, slotItem, offhandItem);
                    }
                    else {
                        InventoryUtil.clickLocked(-1, slot, null, slotItem);
                    }
                    this.postWindowClick();
                }
            }
        }
        else if (!drag.func_190926_b() && !this.lookedUp) {
            final Integer lastSlot = this.lastSlots.get(dragItem);
            if (lastSlot != null && InventoryUtil.get(lastSlot).func_190926_b()) {
                this.preWindowClick();
                InventoryUtil.clickLocked(-2, lastSlot, dragItem, InventoryUtil.get(lastSlot).getItem());
                this.postWindowClick();
            }
            else {
                final int slot2 = this.findItem(Items.field_190931_a);
                if (slot2 != -1 && slot2 != -2) {
                    this.lastSlots.put(dragItem, slot2);
                    this.preWindowClick();
                    InventoryUtil.clickLocked(-2, slot2, dragItem, InventoryUtil.get(slot2).getItem());
                    this.postWindowClick();
                }
            }
            this.lookedUp = true;
        }
    }
    
    private int findItem(final Item item) {
        return InventoryUtil.findItem(item, Offhand.XCARRY.isEnabled());
    }
    
    public boolean isSafe() {
        float playerHealth = EntityUtil.getHealth((EntityLivingBase)Offhand.mc.player);
        if (this.crystalCheck.getValue() && Offhand.mc.player != null && Offhand.mc.world != null) {
            final float highestDamage = (float)Offhand.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).map(DamageUtil::calculate).max(Comparator.comparing(damage -> damage)).orElse(0.0f);
            playerHealth -= highestDamage;
        }
        return (Offhand.PINGBYPASS.isEnabled() && Offhand.AUTOTOTEM.isEnabled()) || (Managers.SAFETY.isSafe() && playerHealth >= this.safeH.getValue()) || playerHealth >= this.health.getValue();
    }
    
    public void preWindowClick() {
        if (this.noMove.getValue() && Managers.POSITION.isOnGround()) {
            PacketUtil.doPosition(Managers.POSITION.getX(), Managers.POSITION.getY(), Managers.POSITION.getZ(), Managers.POSITION.isOnGround());
        }
        this.sneaking = Managers.ACTION.isSneaking();
        this.sprinting = Managers.ACTION.isSprinting();
        if (this.cancelActions.getValue()) {
            if (this.sneaking) {
                PacketUtil.sendAction(CPacketEntityAction.Action.STOP_SNEAKING);
            }
            if (this.sprinting) {
                PacketUtil.sendAction(CPacketEntityAction.Action.STOP_SPRINTING);
            }
        }
        if (this.cancelActive.getValue()) {
            NetworkUtil.send((Packet<?>)new CPacketHeldItemChange(Offhand.mc.player.inventory.currentItem));
        }
    }
    
    public void postWindowClick() {
        Offhand.AUTO_ARMOR.computeIfPresent(AutoArmor::resetTimer);
        this.timer.reset();
        if (this.cancelActions.getValue()) {
            if (this.sneaking) {
                PacketUtil.sendAction(CPacketEntityAction.Action.START_SNEAKING);
            }
            if (this.sprinting) {
                PacketUtil.sendAction(CPacketEntityAction.Action.START_SPRINTING);
            }
        }
    }
    
    public StopWatch getTimer() {
        return this.timer;
    }
    
    static {
        PINGBYPASS = Caches.getModule(PingBypass.class);
        AUTOTOTEM = Caches.getModule(ServerAutoTotem.class);
        AUTO_ARMOR = Caches.getModule(AutoArmor.class);
        XCARRY = Caches.getModule(XCarry.class);
        SUICIDE = Caches.getModule(Suicide.class);
    }
}
