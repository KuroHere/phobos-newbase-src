// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.modules.client.customfont.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.modules.*;

public class FontCommand extends Command
{
    private static final ModuleCache<FontMod> CUSTOM_FONT;
    private static final SettingCache<String, StringSetting, FontMod> FONT;
    
    public FontCommand() {
        super(new String[][] { { "font" }, { "list", "set", "size", "style", "alias", "metrics", "shadow" } });
        CommandDescriptions.register(this, "Manage the CustomFont. (TODO)");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§eCurrent Font is: §b" + FontCommand.FONT.getValue() + ".");
        }
        else {
            final String arg = args[1];
            final String lowerCase = arg.toLowerCase();
            switch (lowerCase) {
                case "list": {
                    FontCommand.CUSTOM_FONT.computeIfPresent(FontMod::sendFonts);
                    break;
                }
            }
        }
    }
    
    static {
        CUSTOM_FONT = Caches.getModule(FontMod.class);
        FONT = Caches.getSetting(FontMod.class, StringSetting.class, "Font", "Verdana");
    }
}
