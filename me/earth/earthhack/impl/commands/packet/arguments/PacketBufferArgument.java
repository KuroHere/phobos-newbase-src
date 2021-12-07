// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;

public class PacketBufferArgument extends AbstractArgument<PacketBuffer>
{
    public PacketBufferArgument() {
        super(PacketBuffer.class);
    }
    
    @Override
    public PacketBuffer fromString(final String argument) throws ArgParseException {
        if (argument.equalsIgnoreCase("empty")) {
            return new PacketBuffer(Unpooled.buffer());
        }
        final String[] split = split(argument);
        final byte[] b = new byte[split.length];
        for (int i = 0; i < split.length; ++i) {
            final String s = split[i];
            try {
                final byte parsed = Byte.parseByte(s, 16);
                b[i] = parsed;
            }
            catch (Exception e) {
                throw new ArgParseException("Could not parse byte: " + s + "!");
            }
        }
        return new PacketBuffer(Unpooled.buffer().writeBytes(b));
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument != null && !argument.isEmpty() && TextUtil.startsWith("empty", argument)) {
            return new PossibleInputs(TextUtil.substring("empty", argument.length()), "");
        }
        return super.getPossibleInputs(argument);
    }
    
    private static String[] split(final String string) {
        final String[] result = new String[(string.length() + 1) / 2];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            final char c = string.charAt(i);
            builder.append(c);
            if ((i + 1) % 2 == 0) {
                result[i / 2] = builder.toString();
                builder = new StringBuilder();
            }
        }
        final String last = builder.toString();
        if (!last.isEmpty()) {
            result[result.length - 1] = last;
        }
        return result;
    }
}
