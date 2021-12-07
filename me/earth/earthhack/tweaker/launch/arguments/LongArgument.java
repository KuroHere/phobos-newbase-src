// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.tweaker.launch.arguments;

import com.google.gson.*;

public class LongArgument extends AbstractArgument<Long>
{
    public LongArgument(final Long value) {
        super(value);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        this.value = (T)Long.valueOf(element.getAsLong());
    }
    
    @Override
    public String toJson() {
        return ((Long)this.value).toString();
    }
}
