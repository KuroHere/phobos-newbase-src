// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

public class UnknownProtocolException extends Exception
{
    public UnknownProtocolException(final int id) {
        super("Received packet with unknown id: " + id);
    }
}
