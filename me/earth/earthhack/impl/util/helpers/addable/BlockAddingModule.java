// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable;

import me.earth.earthhack.api.module.util.*;
import java.util.function.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.item.*;

public class BlockAddingModule extends RemovingItemAddingModule
{
    public BlockAddingModule(final String name, final Category category, final Function<Setting<?>, String> settingDescription) {
        super(name, category, settingDescription);
    }
    
    @Override
    public String getItemStartingWith(final String name) {
        return ItemAddingModule.getItemStartingWithDefault(name, i -> i instanceof ItemBlock);
    }
}
