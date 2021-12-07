// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.client;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.managers.*;

public class ModuleUtil
{
    public static String getHudName(final Module module) {
        return module.getDisplayName() + ((module.getDisplayInfo() == null || module.isHidden() == Hidden.Info) ? "" : ("§7 [§f" + module.getDisplayInfo() + "§7" + "]"));
    }
    
    public static void disableRed(final Module module, final String message) {
        disable(module, "§c" + message);
    }
    
    public static void disable(final Module module, final String message) {
        module.disable();
        sendMessage(module, message);
    }
    
    public static void sendMessage(final Module module, final String message) {
        sendMessage(module, message, "");
    }
    
    public static void sendMessage(final Module module, final String message, final String append) {
        Managers.CHAT.sendDeleteMessage("<" + module.getDisplayName() + "> " + message, module.getName() + append, 2000);
    }
}
