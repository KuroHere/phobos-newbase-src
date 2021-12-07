// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.tweaker.launch.arguments;

import com.google.gson.*;

public class BooleanArgument extends AbstractArgument<Boolean>
{
    public BooleanArgument() {
        this(Boolean.TRUE);
    }
    
    public BooleanArgument(final Boolean initial) {
        super(initial);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        this.value = (T)Boolean.valueOf(element.getAsBoolean());
    }
    
    @Override
    public String toJson() {
        return ((Boolean)this.value).toString();
    }
}
