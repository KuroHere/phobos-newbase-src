// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable;

import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.api.module.util.*;
import java.util.function.*;
import me.earth.earthhack.api.setting.*;

public class RemovingItemAddingModule extends ItemAddingModule<Boolean, SimpleRemovingSetting>
{
    public RemovingItemAddingModule(final String name, final Category category, final Function<Setting<?>, String> settingDescription) {
        super(name, category, SimpleRemovingSetting::new, settingDescription);
    }
}
