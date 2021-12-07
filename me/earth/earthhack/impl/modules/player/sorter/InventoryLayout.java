//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.sorter;

import me.earth.earthhack.api.config.*;
import me.earth.earthhack.api.util.interfaces.*;
import com.google.gson.*;
import java.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class InventoryLayout implements Jsonable, Globals
{
    private final Map<Integer, Item> layout;
    
    public InventoryLayout() {
        this.layout = new HashMap<Integer, Item>();
    }
    
    public void setSlot(final int slot, final Item item) {
        this.layout.put(slot, item);
    }
    
    public Item getItem(final int slot) {
        return this.layout.get(slot);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            final int id = Integer.parseInt(entry.getKey());
            final Item item = Item.getItemById(entry.getValue().getAsInt());
            this.layout.put(id, item);
        }
    }
    
    @Override
    public String toJson() {
        final JsonObject object = new JsonObject();
        for (final Map.Entry<Integer, Item> entry : this.layout.entrySet()) {
            object.add(entry.getKey() + "", Jsonable.parse(Item.getIdFromItem((Item)entry.getValue()) + "", false));
        }
        return object.toString();
    }
    
    public static InventoryLayout createFromMcPlayer() {
        final NonNullList<ItemStack> inventory = InventoryUtil.getInventory();
        final InventoryLayout layout = new InventoryLayout();
        for (int i = 0; i < inventory.size(); ++i) {
            layout.setSlot(i, ((ItemStack)inventory.get(i)).getItem());
        }
        return layout;
    }
}
