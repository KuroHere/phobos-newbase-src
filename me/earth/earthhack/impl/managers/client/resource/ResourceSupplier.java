// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.resource;

import net.minecraft.client.resources.*;

@FunctionalInterface
public interface ResourceSupplier
{
    IResource get() throws ResourceException;
}
