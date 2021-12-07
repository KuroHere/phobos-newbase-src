//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.setting;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.impl.gui.chat.components.values.*;
import net.minecraft.util.text.*;

public class DefaultComponent<T, S extends Setting<T>> extends SettingComponent<T, S>
{
    public DefaultComponent(final S setting) {
        super(setting);
        final ValueComponent value = new ValueComponent(setting);
        value.getStyle().setClickEvent(this.getStyle().getClickEvent());
        value.getStyle().setHoverEvent(this.getStyle().getHoverEvent());
        this.appendSibling((ITextComponent)value);
    }
}
