//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.macro;

import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.*;
import java.util.*;

public class MacroManager extends IterationRegister<Macro> implements Subscriber, Globals
{
    private final List<Listener<?>> listeners;
    private boolean safe;
    
    public MacroManager() {
        (this.listeners = new ArrayList<Listener<?>>()).add(new EventListener<KeyboardEvent>(KeyboardEvent.class, 100) {
            @Override
            public void invoke(final KeyboardEvent event) {
                for (final Macro macro : MacroManager.this.getRegistered()) {
                    if (macro.getType() != MacroType.DELEGATE && macro.getBind().getKey() == event.getKey() && macro.isRelease() != event.getEventState()) {
                        try {
                            MacroManager.this.safe = true;
                            macro.execute(Managers.COMMANDS);
                        }
                        catch (Throwable t) {
                            ChatUtil.sendMessage("§cAn error occurred while executing macro §f" + macro.getName() + "§c" + ": " + ((t.getMessage() == null) ? t.getClass().getName() : t.getMessage()) + ". I strongly recommend deleting it for now and checking your logic!");
                            t.printStackTrace();
                        }
                        finally {
                            MacroManager.this.safe = false;
                        }
                    }
                }
            }
        });
    }
    
    public void validateAll() {
        this.getRegistered().removeIf(macro -> {
            if (macro instanceof DelegateMacro && !((DelegateMacro)macro).isReferenced(this)) {
                Earthhack.getLogger().info("Deleting DelegateMacro " + macro.getName() + " it's not being referenced anymore.");
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    @Override
    public Collection<Listener<?>> getListeners() {
        return this.listeners;
    }
    
    public boolean isSafe() {
        return MacroManager.mc.isCallingFromMinecraftThread() && this.safe;
    }
}
