//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components.setting;

import me.earth.earthhack.api.setting.settings.*;

public class StringComponent extends DefaultComponent<String, StringSetting>
{
    public StringComponent(final StringSetting setting) {
        super(setting);
    }
    
    @Override
    public String getText() {
        return ((StringSetting)this.setting).getName() + "§7" + " : " + "§6";
    }
}
