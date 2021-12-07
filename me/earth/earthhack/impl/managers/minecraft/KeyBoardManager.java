// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft;

import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.module.*;
import java.util.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.minecraft.*;

public class KeyBoardManager extends SubscriberImpl
{
    public KeyBoardManager() {
        this.listeners.add(new EventListener<KeyboardEvent>(KeyboardEvent.class) {
            @Override
            public void invoke(final KeyboardEvent event) {
                if (event.getEventState()) {
                    for (final Module module : Managers.MODULES.getRegistered()) {
                        if (module.getBind().getKey() == event.getKey()) {
                            module.toggle();
                        }
                    }
                }
                else {
                    KeyBoardManager.this.onRelease(event.getKey());
                }
            }
        });
        this.listeners.add(new EventListener<GuiScreenEvent<?>>(GuiScreenEvent.class, -2147483638) {
            @Override
            public void invoke(final GuiScreenEvent<?> event) {
                if (event.isCancelled() || event.getScreen() == null) {
                    return;
                }
                for (final Module module : Managers.MODULES.getRegistered()) {
                    if (KeyBoardUtil.isKeyDown(module.getBind())) {
                        switch (module.getBindMode()) {
                            case Hold: {
                                module.toggle();
                                continue;
                            }
                            case Disable: {
                                module.disable();
                                continue;
                            }
                        }
                    }
                }
            }
        });
    }
    
    private void onRelease(final int keyCode) {
        for (final Module module : Managers.MODULES.getRegistered()) {
            if (module.getBind().getKey() == keyCode) {
                switch (module.getBindMode()) {
                    case Hold: {
                        module.toggle();
                        continue;
                    }
                    case Disable: {
                        module.disable();
                        continue;
                    }
                }
            }
        }
    }
}
