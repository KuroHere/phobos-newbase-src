// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.offhand.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerKeyboard extends ModuleListener<AutoCrystal, KeyboardEvent>
{
    private static final ModuleCache<Offhand> OFFHAND;
    
    public ListenerKeyboard(final AutoCrystal module) {
        super(module, (Class<? super Object>)KeyboardEvent.class);
    }
    
    public void invoke(final KeyboardEvent event) {
        if (event.getEventState() && event.getKey() == ((AutoCrystal)this.module).switchBind.getValue().getKey()) {
            if (((AutoCrystal)this.module).useAsOffhand.getValue() || ((AutoCrystal)this.module).isPingBypass()) {
                final OffhandMode m = ListenerKeyboard.OFFHAND.returnIfPresent(Offhand::getMode, null);
                if (m != null) {
                    if (m.equals(OffhandMode.CRYSTAL)) {
                        ListenerKeyboard.OFFHAND.computeIfPresent(o -> o.setMode(OffhandMode.TOTEM));
                    }
                    else {
                        ListenerKeyboard.OFFHAND.computeIfPresent(o -> o.setMode(OffhandMode.CRYSTAL));
                    }
                }
                ((AutoCrystal)this.module).switching = false;
            }
            else if (((AutoCrystal)this.module).autoSwitch.getValue() == AutoSwitch.Bind) {
                ((AutoCrystal)this.module).switching = !((AutoCrystal)this.module).switching;
            }
        }
    }
    
    static {
        OFFHAND = Caches.getModule(Offhand.class);
    }
}
