// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.macro;

import java.util.function.*;
import me.earth.earthhack.impl.managers.chat.*;
import me.earth.earthhack.impl.modules.client.commands.*;

public enum MacroType
{
    NORMAL((c, m) -> {
        if (m.commands.length > 0) {
            c.applyCommand(Commands.getPrefix() + m.commands[0]);
        }
        return;
    }), 
    COMBINED((c, m) -> {
        if (m.commands.length > 0) {
            final String[] commands = m.commands;
            int i = 0;
            for (int length = commands.length; i < length; ++i) {
                final String command = commands[i];
                c.applyCommand(Commands.getPrefix() + command);
            }
        }
        return;
    }), 
    DELEGATE(MacroType.COMBINED::execute), 
    FLOW((c, m) -> {
        if (m.commands.length == 0) {
            return;
        }
        else {
            c.applyCommand(Commands.getPrefix() + m.commands[m.index]);
            ++m.index;
            if (m.index >= m.commands.length) {
                m.index = 0;
            }
            return;
        }
    });
    
    private final BiConsumer<CommandManager, Macro> behaviour;
    
    private MacroType(final BiConsumer<CommandManager, Macro> behaviour) {
        this.behaviour = behaviour;
    }
    
    public void execute(final CommandManager manager, final Macro macro) {
        this.behaviour.accept(manager, macro);
    }
    
    public static MacroType fromString(final String name) {
        final String lowerCase = name.toLowerCase();
        switch (lowerCase) {
            case "normal": {
                return MacroType.NORMAL;
            }
            case "combined": {
                return MacroType.COMBINED;
            }
            case "flow": {
                return MacroType.FLOW;
            }
            case "delegate": {
                return MacroType.DELEGATE;
            }
            default: {
                throw new IllegalArgumentException("Couldn't parse MacroType: " + name);
            }
        }
    }
}
