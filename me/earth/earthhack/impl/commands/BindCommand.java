// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.util.bind.*;

public class BindCommand extends AbstractModuleCommand
{
    private static final BindSetting BIND;
    
    public BindCommand() {
        super("bind", new String[][] { { "bind" } });
        CommandDescriptions.register(this, "Sets the binds of modules.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§c Please specify a module!");
            return;
        }
        if (args.length == 2) {
            ChatUtil.sendMessage("§c Please specify a bind!");
            return;
        }
        final Module module = Managers.MODULES.getObject(args[1]);
        if (module == null) {
            ChatUtil.sendMessage("§cModule §f" + args[1] + "§c" + " not found!");
            return;
        }
        final Setting<?> bind = module.getSetting("Bind");
        if (bind == null) {
            ChatUtil.sendMessage("§c" + module.getName() + " can't be bound.");
            return;
        }
        bind.fromString(args[2]);
        ChatUtil.sendMessage(module.getName() + "§a" + " bound to " + module.getBind().toString());
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length == 3) {
            return new PossibleInputs(TextUtil.substring(BindCommand.BIND.getInputs(args[2]), args[2].length()), "");
        }
        return super.getPossibleInputs(args);
    }
    
    static {
        BIND = new BindSetting("", Bind.none());
    }
}
