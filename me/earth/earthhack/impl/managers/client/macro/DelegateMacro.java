// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.macro;

import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.managers.chat.*;
import com.google.gson.*;
import java.util.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.api.config.*;

public class DelegateMacro extends Macro
{
    private MacroType delegated;
    
    public DelegateMacro(final String name, final String macro) {
        super(name, Bind.none(), new String[] { "macro use " + macro });
        this.delegated = MacroType.DELEGATE;
    }
    
    public boolean isReferenced(final MacroManager macros) {
        return this.isReferenced(macros, new HashSet<Macro>());
    }
    
    private boolean isReferenced(final MacroManager macros, final Set<Macro> checked) {
        checked.add(this);
        for (final Macro m : macros.getRegistered()) {
            if (checked.contains(m)) {
                continue;
            }
            for (final String command : m.commands) {
                if (command.toLowerCase().contains(this.getName().toLowerCase())) {
                    if (!(m instanceof DelegateMacro)) {
                        return true;
                    }
                    if (((DelegateMacro)m).isReferenced(macros, checked)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void execute(final CommandManager manager) throws Error {
        this.delegated.execute(manager, this);
    }
    
    @Override
    public MacroType getType() {
        return MacroType.DELEGATE;
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<String> commands = new ArrayList<String>();
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            final String lowerCase = entry.getKey().toLowerCase();
            switch (lowerCase) {
                case "bind": {
                    this.bind = Bind.fromString(entry.getValue().getAsString());
                    continue;
                }
                case "delegated": {
                    this.delegated = MacroType.fromString(entry.getValue().getAsString());
                    continue;
                }
                case "release": {
                    this.release = Boolean.parseBoolean(entry.getValue().getAsString());
                }
                case "type": {
                    continue;
                }
                default: {
                    commands.add(entry.getValue().getAsString());
                    continue;
                }
            }
        }
        this.commands = commands.toArray(new String[0]);
    }
    
    @Override
    public String toJson() {
        return delegateToJson(this.delegated, this.commands);
    }
    
    public static DelegateMacro delegate(final String name, final Macro macro) {
        return new DelegateMacro(name, "") {
            @Override
            public void execute(final CommandManager manager) throws Error {
                macro.execute(manager);
            }
            
            @Override
            public String[] getCommands() {
                return macro.getCommands();
            }
            
            @Override
            public void fromJson(final JsonElement element) {
                Earthhack.getLogger().info("Anonymous delegates " + this.getName() + " fromJson method was called. This shouldn't happen.");
            }
            
            @Override
            public String toJson() {
                return delegateToJson(macro.getType(), macro.commands);
            }
        };
    }
    
    private static String delegateToJson(final MacroType type, final String[] commands) {
        final JsonObject object = new JsonObject();
        object.add("bind", DelegateMacro.PARSER.parse("NONE"));
        object.add("type", DelegateMacro.PARSER.parse("DELEGATE"));
        object.add("delegated", DelegateMacro.PARSER.parse(type.name()));
        for (int i = 0; i < commands.length; ++i) {
            object.add("command" + ((i == 0) ? "" : Integer.valueOf(i)), Jsonable.parse(commands[i]));
        }
        return object.toString();
    }
}
