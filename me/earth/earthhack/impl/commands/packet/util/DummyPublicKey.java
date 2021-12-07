// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import java.security.*;

public class DummyPublicKey implements PublicKey, Dummy
{
    private static final PublicKey PUBLIC_KEY;
    
    @Override
    public String getAlgorithm() {
        return DummyPublicKey.PUBLIC_KEY.getAlgorithm();
    }
    
    @Override
    public String getFormat() {
        return DummyPublicKey.PUBLIC_KEY.getFormat();
    }
    
    @Override
    public byte[] getEncoded() {
        return DummyPublicKey.PUBLIC_KEY.getEncoded();
    }
    
    static {
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No RSA-Algorithm!");
        }
        final KeyPair pair = generator.generateKeyPair();
        PUBLIC_KEY = pair.getPublic();
    }
}
