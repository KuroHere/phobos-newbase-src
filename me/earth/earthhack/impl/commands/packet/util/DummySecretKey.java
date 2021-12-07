// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import javax.crypto.*;
import java.security.*;

public class DummySecretKey implements SecretKey, Dummy
{
    protected static final SecretKey SECRET_KEY;
    
    @Override
    public String getAlgorithm() {
        return DummySecretKey.SECRET_KEY.getAlgorithm();
    }
    
    @Override
    public String getFormat() {
        return DummySecretKey.SECRET_KEY.getFormat();
    }
    
    @Override
    public byte[] getEncoded() {
        return DummySecretKey.SECRET_KEY.getEncoded();
    }
    
    static {
        KeyGenerator generator;
        try {
            generator = KeyGenerator.getInstance("AES");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No RSA-Algorithm!");
        }
        SECRET_KEY = generator.generateKey();
    }
}
