//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import net.minecraft.client.settings.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import org.lwjgl.input.*;

public class KeyBoardUtil
{
    public static boolean isKeyDown(final KeyBinding binding) {
        return isKeyDown(binding.getKeyCode());
    }
    
    public static boolean isKeyDown(final Setting<Bind> setting) {
        return isKeyDown(setting.getValue());
    }
    
    public static boolean isKeyDown(final Bind bind) {
        return isKeyDown(bind.getKey());
    }
    
    public static boolean isKeyDown(final int key) {
        return key != 0 && key != -1 && ((key >= 0) ? Keyboard.isKeyDown(key) : Mouse.isButtonDown(key + 100));
    }
}
