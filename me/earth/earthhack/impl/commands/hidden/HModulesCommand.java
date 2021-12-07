//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.hidden;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import org.lwjgl.input.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.command.*;

public class HModulesCommand extends Command implements Globals
{
    public HModulesCommand() {
        super(new String[][] { { "hiddenmodule" } }, true);
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length > 1) {
            final String name = args[1];
            final Module module = Managers.MODULES.getObject(name);
            if (module != null) {
                if (Mouse.isButtonDown(1)) {
                    HModulesCommand.mc.displayGuiScreen((GuiScreen)new GuiChat(Commands.getPrefix() + module.getName() + " "));
                }
                else {
                    module.toggle();
                }
            }
            else {
                ChatUtil.sendMessage("§cAn error occurred.");
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        return PossibleInputs.empty();
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        completer.setMcComplete(true);
        return completer;
    }
}
