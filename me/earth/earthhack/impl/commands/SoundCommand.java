//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.core.mixins.audio.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.client.audio.*;

public class SoundCommand extends Command implements Globals
{
    public SoundCommand() {
        super(new String[][] { { "sound" } });
        CommandDescriptions.register(this, "Reloads the SoundSystem.");
    }
    
    @Override
    public void execute(final String[] args) {
        try {
            final SoundManager soundManager = ((ISoundHandler)SoundCommand.mc.getSoundHandler()).getManager();
            soundManager.reloadSoundSystem();
            ChatUtil.sendMessage("§aReloaded SoundSystem.");
        }
        catch (Exception e) {
            ChatUtil.sendMessage("§cCouldn't reload sound: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
