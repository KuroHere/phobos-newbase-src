// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import java.security.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class PublicKeyArgument extends AbstractArgument<PublicKey>
{
    public PublicKeyArgument() {
        super(PublicKey.class);
    }
    
    @Override
    public PublicKey fromString(final String argument) throws ArgParseException {
        return new DummyPublicKey();
    }
}
