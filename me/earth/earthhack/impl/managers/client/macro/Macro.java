// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.macro;

import me.earth.earthhack.api.config.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.managers.chat.*;
import com.google.gson.*;
import java.util.*;

public class Macro implements Jsonable, Nameable
{
    protected boolean release;
    protected final String name;
    protected String[] commands;
    protected int index;
    protected Bind bind;
    
    public Macro(final String name, final Bind bind, final String[] commands) {
        this.name = name;
        this.bind = bind;
        this.commands = commands;
    }
    
    public void execute(final CommandManager manager) throws Error {
        this.getType().execute(manager, this);
    }
    
    public String[] getCommands() {
        if (this.commands.length == 0) {
            return new String[] { "" };
        }
        return this.commands;
    }
    
    public Bind getBind() {
        return this.bind;
    }
    
    public MacroType getType() {
        return MacroType.NORMAL;
    }
    
    public boolean isRelease() {
        return this.release;
    }
    
    public void setRelease(final boolean release) {
        this.release = release;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Macro && ((Macro)o).getName().equalsIgnoreCase(this.getName());
    }
    
    @Override
    public int hashCode() {
        return this.name.toLowerCase().hashCode();
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
        final JsonObject object = new JsonObject();
        object.add("bind", Macro.PARSER.parse(this.bind.toString()));
        object.add("type", Macro.PARSER.parse(this.getType().name()));
        object.add("release", Macro.PARSER.parse(this.release + ""));
        for (int i = 0; i < this.commands.length; ++i) {
            object.add("command" + ((i == 0) ? "" : Integer.valueOf(i)), Jsonable.parse(this.commands[i]));
        }
        return object.toString();
    }
}
