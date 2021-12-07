//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.util.*;
import java.util.function.*;
import net.minecraft.item.*;
import java.util.*;

public class ItemAddingModule<I, E extends Setting<I> & Removable> extends RegisteringModule<I, E>
{
    public ItemAddingModule(final String name, final Category category, final Function<String, E> create, final Function<Setting<?>, String> settingDescription) {
        super(name, category, "Add_Block", "item/block", create, settingDescription);
    }
    
    @Override
    public String getInput(final String input, final boolean add) {
        if (!add) {
            return super.getInput(input, false);
        }
        final String itemName = this.getItemStartingWith(input);
        if (itemName != null) {
            return TextUtil.substring(itemName, input.length());
        }
        return "";
    }
    
    public boolean isStackValid(final ItemStack stack) {
        return stack != null && this.isValid(stack.getItem().getItemStackDisplayName(stack));
    }
    
    public String getItemStartingWith(final String name) {
        return getItemStartingWithDefault(name, i -> true);
    }
    
    public static String getItemStartingWithDefault(final String name, final Predicate<Item> accept) {
        final Item item = getItemStartingWith(name, accept);
        if (item != null) {
            return item.getItemStackDisplayName(new ItemStack(item));
        }
        return null;
    }
    
    public static Item getItemStartingWith(String name, final Predicate<Item> accept) {
        if (name == null) {
            return null;
        }
        name = name.toLowerCase();
        for (final Item item : Item.REGISTRY) {
            final String itemName = item.getItemStackDisplayName(new ItemStack(item));
            if (itemName.toLowerCase().startsWith(name) && accept.test(item)) {
                return item;
            }
        }
        return null;
    }
}
