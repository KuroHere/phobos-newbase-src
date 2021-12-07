// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client;

import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.hud.watermark.*;
import me.earth.earthhack.impl.hud.welcomer.*;
import java.util.function.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.register.exception.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.util.interfaces.*;

public class HudElementManager extends IterationRegister<HudElement>
{
    private int currentZ;
    
    public HudElementManager() {
        this.currentZ = 0;
    }
    
    public void init() {
        Earthhack.getLogger().info("Initializing Hud Elements.");
        this.forceRegister(new Watermark());
        this.forceRegister(new Welcomer());
    }
    
    public void load() {
        for (final HudElement element : this.getRegistered()) {
            Earthhack.getLogger().info(element.getName());
            element.load();
        }
        this.registered.sort(Comparator.comparing((Function<? super T, ? extends Comparable>)HudElement::getZ));
    }
    
    @Override
    public void unregister(final HudElement element) throws CantUnregisterException {
        super.unregister(element);
        Bus.EVENT_BUS.unsubscribe(element);
    }
    
    private void forceRegister(final HudElement element) {
        this.registered.add((T)element);
        if (element instanceof Registrable) {
            ((Registrable)element).onRegister();
        }
        element.setZ((float)this.currentZ);
        ++this.currentZ;
    }
}
