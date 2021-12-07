// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import javax.crypto.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class SecretKeyArgument extends AbstractArgument<SecretKey>
{
    public SecretKeyArgument() {
        super(SecretKey.class);
    }
    
    @Override
    public SecretKey fromString(final String argument) throws ArgParseException {
        return new DummySecretKey();
    }
}
