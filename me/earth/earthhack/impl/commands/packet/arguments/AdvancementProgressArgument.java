// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.advancements.*;
import me.earth.earthhack.api.config.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class AdvancementProgressArgument extends AbstractArgument<AdvancementProgress>
{
    private static final AdvancementProgress.Serializer SERIALIZER;
    
    public AdvancementProgressArgument() {
        super(AdvancementProgress.class);
    }
    
    @Override
    public AdvancementProgress fromString(final String argument) throws ArgParseException {
        final JsonElement element = Jsonable.parse(argument);
        if (element == null) {
            throw new ArgParseException("Couldn't parse " + argument + " to Json! Check the log.");
        }
        try {
            return AdvancementProgressArgument.SERIALIZER.deserialize(element, (Type)null, (JsonDeserializationContext)null);
        }
        catch (Exception e) {
            throw new ArgParseException("Couldn't read AdvancementProgress from Json: " + element + ": " + e.getMessage());
        }
    }
    
    static {
        SERIALIZER = new AdvancementProgress.Serializer();
    }
}
