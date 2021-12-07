//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.disabling;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.event.events.client.*;

public abstract class DisablingModule extends Module implements IDisablingModule
{
    public DisablingModule(final String name, final Category category) {
        super(name, category);
        this.listeners.add(newDisconnectDisabler(this));
        this.listeners.add(newDeathDisabler(this));
        this.listeners.add(newShutDownDisabler(this));
    }
    
    @Override
    public void onShutDown() {
        this.disable();
    }
    
    @Override
    public void onDeath() {
        this.disable();
    }
    
    @Override
    public void onDisconnect() {
        this.disable();
    }
    
    public static void makeDisablingModule(final Module module) {
        module.getListeners().add(newDisconnectDisabler(module));
        module.getListeners().add(newDeathDisabler(module));
        module.getListeners().add(newShutDownDisabler(module));
    }
    
    public static Listener<?> newDisconnectDisabler(final Module module) {
        if (module instanceof IDisablingModule) {
            final IDisablingModule disabling = (IDisablingModule)module;
            return new EventListener<DisconnectEvent>(DisconnectEvent.class) {
                @Override
                public void invoke(final DisconnectEvent event) {
                    Globals.mc.addScheduledTask(disabling::onDisconnect);
                }
            };
        }
        return new EventListener<DisconnectEvent>(DisconnectEvent.class) {
            @Override
            public void invoke(final DisconnectEvent event) {
                Globals.mc.addScheduledTask(module::disable);
            }
        };
    }
    
    public static Listener<?> newDeathDisabler(final Module module) {
        if (module instanceof IDisablingModule) {
            final IDisablingModule disabling = (IDisablingModule)module;
            return new EventListener<DeathEvent>(DeathEvent.class) {
                @Override
                public void invoke(final DeathEvent event) {
                    if (event.getEntity() != null && event.getEntity().equals((Object)Globals.mc.player)) {
                        Globals.mc.addScheduledTask(disabling::onDeath);
                    }
                }
            };
        }
        return new EventListener<DeathEvent>(DeathEvent.class) {
            @Override
            public void invoke(final DeathEvent event) {
                if (event.getEntity() != null && event.getEntity().equals((Object)Globals.mc.player)) {
                    Globals.mc.addScheduledTask(module::disable);
                }
            }
        };
    }
    
    public static Listener<?> newShutDownDisabler(final Module module) {
        if (module instanceof IDisablingModule) {
            final IDisablingModule disabling = (IDisablingModule)module;
            return new EventListener<ShutDownEvent>(ShutDownEvent.class) {
                @Override
                public void invoke(final ShutDownEvent event) {
                    Globals.mc.addScheduledTask(disabling::onDisconnect);
                }
            };
        }
        return new EventListener<ShutDownEvent>(ShutDownEvent.class) {
            @Override
            public void invoke(final ShutDownEvent event) {
                Globals.mc.addScheduledTask(module::disable);
            }
        };
    }
}
