//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.setting;

import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.api.setting.settings.*;

public class BindComponent extends DefaultComponent<Bind, BindSetting>
{
    public BindComponent(final BindSetting setting) {
        super(setting);
    }
    
    @Override
    public String getText() {
        return super.getText() + "§7";
    }
}
