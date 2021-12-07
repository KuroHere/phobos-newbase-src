//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor.util;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.item.*;
import java.util.function.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.*;

public class WindowClick implements Globals
{
    private final int slot;
    private final int drag;
    private final int target;
    private final ItemStack inSlot;
    private final ItemStack inDrag;
    private final Consumer<PlayerControllerMP> action;
    private boolean doubleClick;
    private Runnable post;
    private boolean fast;
    
    public WindowClick(final int slot, final ItemStack inSlot, final ItemStack inDrag) {
        this(slot, inSlot, inDrag, slot);
    }
    
    public WindowClick(final int slot, final ItemStack inSlot, final ItemStack inDrag, final int target) {
        this(slot, inSlot, inDrag, target, p -> p.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WindowClick.mc.player));
    }
    
    public WindowClick(final int slot, final ItemStack inSlot, final ItemStack inDrag, final int target, final Consumer<PlayerControllerMP> action) {
        this(slot, inSlot, -2, inDrag, target, action);
    }
    
    public WindowClick(final int slot, final ItemStack inSlot, final int dragSlot, final ItemStack inDrag, final int target, final Consumer<PlayerControllerMP> action) {
        this.slot = slot;
        this.inSlot = inSlot;
        this.inDrag = inDrag;
        this.target = target;
        this.action = action;
        this.drag = dragSlot;
    }
    
    public void runClick(final PlayerControllerMP controller) {
        ItemStack stack = null;
        ItemStack drag = null;
        if (this.slot != -1 && this.slot != -999) {
            stack = InventoryUtil.get(this.slot);
            drag = WindowClick.mc.player.inventory.getItemStack();
        }
        this.action.accept(controller);
        if (this.slot != -1 && this.slot != -999 && this.fast && (!InventoryUtil.equals(stack, WindowClick.mc.player.inventory.getItemStack()) || !InventoryUtil.equals(drag, InventoryUtil.get(this.slot)))) {
            InventoryUtil.put(this.slot, WindowClick.mc.player.inventory.getItemStack());
            WindowClick.mc.player.inventory.setItemStack(stack);
        }
        if (this.post != null) {
            this.post.run();
        }
    }
    
    public boolean isValid() {
        if (WindowClick.mc.player != null) {
            ItemStack stack = InventoryUtil.get(this.drag);
            if (InventoryUtil.equals(stack, this.inDrag)) {
                if (this.slot < 0) {
                    return true;
                }
                stack = InventoryUtil.get(this.slot);
                return InventoryUtil.equals(stack, this.inSlot);
            }
        }
        return false;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public int getTarget() {
        return this.target;
    }
    
    public boolean isDoubleClick() {
        return this.doubleClick;
    }
    
    public void setDoubleClick(final boolean doubleClick) {
        this.doubleClick = doubleClick;
    }
    
    public void addPost(final Runnable post) {
        this.post = post;
    }
    
    public void setFast(final boolean fast) {
        this.fast = fast;
    }
}
