// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.util;

import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.config.*;
import me.earth.earthhack.impl.managers.client.macro.*;
import me.earth.earthhack.api.register.exception.*;
import java.util.*;
import me.earth.earthhack.api.register.*;

public class MacroConfig extends IdentifiedNameable implements Config
{
    private final List<Macro> macros;
    private final MacroManager manager;
    
    public MacroConfig(final String name, final MacroManager manager) {
        super(name);
        this.macros = new ArrayList<Macro>();
        this.manager = manager;
    }
    
    public void add(final Macro macro) {
        this.macros.add(macro);
    }
    
    public List<Macro> getMacros() {
        return this.macros;
    }
    
    @Override
    public void apply() {
        for (final Macro macro : new ArrayList(((IterationRegister<? extends E>)this.manager).getRegistered())) {
            try {
                this.manager.unregister(macro);
            }
            catch (CantUnregisterException e) {
                e.printStackTrace();
            }
        }
        for (final Macro macro : this.macros) {
            try {
                this.manager.register(macro);
            }
            catch (AlreadyRegisteredException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public static MacroConfig create(final String name, final MacroManager manager) {
        final MacroConfig config = new MacroConfig(name, manager);
        for (final Macro macro : manager.getRegistered()) {
            config.add(macro);
        }
        return config;
    }
}
