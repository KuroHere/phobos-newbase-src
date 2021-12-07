//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.values;

import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.setting.*;

public class EnumHoverComponent<E extends Enum<E>> extends SettingComponent<E, EnumSetting<E>>
{
    public EnumHoverComponent(final EnumSetting<E> setting) {
        super(setting);
    }
    
    @Override
    public String getText() {
        return "§b" + ((Setting<Enum>)this.setting).getValue().name() + "§7" + " -> " + "§f" + EnumHelper.next(((Setting<Enum<?>>)this.setting).getValue()).name();
    }
}
