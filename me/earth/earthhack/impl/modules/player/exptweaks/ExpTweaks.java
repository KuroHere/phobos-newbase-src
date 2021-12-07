//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.exptweaks;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.thread.*;
import org.lwjgl.input.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import java.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.item.*;

public class ExpTweaks extends Module
{
    protected final Setting<Boolean> feetExp;
    protected final Setting<Integer> expPackets;
    protected final Setting<Boolean> wasteStop;
    protected final Setting<Integer> stopDura;
    protected final Setting<Integer> wasteIf;
    protected final Setting<Boolean> wasteLoot;
    protected final Setting<Boolean> packetsInLoot;
    protected final Setting<Double> grow;
    protected final Setting<Boolean> middleClickExp;
    protected final Setting<Integer> mcePackets;
    protected final Setting<Boolean> silent;
    protected final Setting<Boolean> whileEating;
    protected final Setting<Bind> mceBind;
    protected boolean justCancelled;
    protected boolean isMiddleClick;
    protected int lastSlot;
    
    public ExpTweaks() {
        super("ExpTweaks", Category.Player);
        this.feetExp = this.register(new BooleanSetting("FeetExp", false));
        this.expPackets = this.register(new NumberSetting("ExpPackets", 0, 0, 64));
        this.wasteStop = this.register(new BooleanSetting("WasteStop", false));
        this.stopDura = this.register(new NumberSetting("Stop-Dura", 100, 0, 100));
        this.wasteIf = this.register(new NumberSetting("WasteIf", 30, 0, 100));
        this.wasteLoot = this.register(new BooleanSetting("WasteLoot", true));
        this.packetsInLoot = this.register(new BooleanSetting("PacketsInLoot", true));
        this.grow = this.register(new NumberSetting("Grow", 0.0, 0.0, 5.0));
        this.middleClickExp = this.register(new BooleanSetting("MiddleClickExp", false));
        this.mcePackets = this.register(new NumberSetting("MCE-Packets", 0, 0, 64));
        this.silent = this.register(new BooleanSetting("Silent", true));
        this.whileEating = this.register(new BooleanSetting("WhileEating", true));
        this.mceBind = this.register(new BindSetting("MCE-Bind", Bind.none()));
        this.lastSlot = -1;
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerUseItem(this));
        this.listeners.add(new ListenerMiddleClick(this));
        final SimpleData data = new SimpleData(this, "Tweaks for Experience Orbs/Bottles.");
        data.register(this.feetExp, "Will silently look at your feet when you are mending.");
        data.register(this.expPackets, "Sends more packets to make mending faster. 10 is a good value, but can waste exp!");
        data.register(this.wasteStop, "Will stop you from throwing Experience if your Armor has full durability.");
        data.register(this.wasteIf, "Will not use WasteStop if you one of your armor pieces has less durability (%) than this value.");
        data.register(this.wasteLoot, "Wastes Exp when you are standing in Exp Bottles.");
        this.setData(data);
    }
    
    @Override
    protected void onEnable() {
        this.isMiddleClick = false;
        this.justCancelled = false;
        this.lastSlot = -1;
    }
    
    @Override
    protected void onDisable() {
        if (this.lastSlot != -1) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> InventoryUtil.switchTo(this.lastSlot));
            this.lastSlot = -1;
        }
    }
    
    public boolean isMiddleClick() {
        return this.middleClickExp.getValue() && ((Mouse.isButtonDown(2) && this.mceBind.getValue().getKey() == -1) || KeyBoardUtil.isKeyDown(this.mceBind));
    }
    
    public boolean isWastingLoot(final List<Entity> entities) {
        if (entities != null) {
            final AxisAlignedBB bb = RotationUtil.getRotationPlayer().getEntityBoundingBox().expand((double)this.grow.getValue(), (double)this.grow.getValue(), (double)this.grow.getValue());
            for (final Entity entity : entities) {
                if (entity instanceof EntityItem && !entity.isDead && ((EntityItem)entity).getEntityItem().getItem() == Items.EXPERIENCE_BOTTLE && entity.getEntityBoundingBox().intersectsWith(bb)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isWasting() {
        if (this.wasteLoot.getValue()) {
            final List<Entity> entities = Managers.ENTITIES.getEntitiesAsync();
            if (this.isWastingLoot(entities)) {
                return false;
            }
        }
        boolean empty = true;
        boolean full = false;
        for (int i = 5; i < 9; ++i) {
            final ItemStack stack = ExpTweaks.mc.player.inventoryContainer.getSlot(i).getStack();
            if (!stack.func_190926_b()) {
                empty = false;
                final float percent = DamageUtil.getPercent(stack);
                if (percent >= this.stopDura.getValue()) {
                    full = true;
                }
                else if (percent <= this.wasteIf.getValue()) {
                    return false;
                }
            }
        }
        return empty || full;
    }
    
    public boolean cancelShrink() {
        final boolean just = this.justCancelled;
        this.justCancelled = false;
        return this.isEnabled() && this.wasteStop.getValue() && just;
    }
}
