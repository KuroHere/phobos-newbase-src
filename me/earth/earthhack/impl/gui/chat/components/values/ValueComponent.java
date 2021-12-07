//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.values;

import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.util.text.*;
import java.util.*;

public class ValueComponent extends SuppliedComponent
{
    private final Setting<?> setting;
    
    public ValueComponent(final Setting<?> setting) {
        super(() -> {
            if (setting.getValue() == null) {
                return "null";
            }
            else if (setting instanceof StringSetting && setting.getValue().toString().isEmpty()) {
                return "<...>";
            }
            else {
                return setting.getValue().toString();
            }
        });
        this.setting = setting;
    }
    
    @Override
    public TextComponentString createCopy() {
        final ValueComponent copy = new ValueComponent(this.setting);
        copy.setStyle(this.getStyle().createShallowCopy());
        for (final ITextComponent sibling : this.getSiblings()) {
            copy.appendSibling(sibling.createCopy());
        }
        return copy;
    }
}
