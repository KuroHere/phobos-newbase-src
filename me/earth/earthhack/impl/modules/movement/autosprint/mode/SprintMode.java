//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.autosprint.mode;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.client.settings.*;

public enum SprintMode implements Globals
{
    Rage {
        @Override
        public void sprint() {
            SprintMode$1.mc.player.setSprinting(true);
        }
    }, 
    Legit {
        @Override
        public void sprint() {
            KeyBinding.setKeyBindState(SprintMode$2.mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }
    };
    
    public abstract void sprint();
}
