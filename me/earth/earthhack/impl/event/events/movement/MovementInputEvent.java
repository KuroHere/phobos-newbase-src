// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.movement;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.util.*;

public class MovementInputEvent extends Event
{
    private final MovementInput input;
    
    public MovementInputEvent(final MovementInput input) {
        this.input = input;
    }
    
    public MovementInput getInput() {
        return this.input;
    }
}
