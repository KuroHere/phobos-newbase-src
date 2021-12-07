//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.resource;

import net.minecraft.client.resources.data.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.io.*;

public class PluginResourceSupplier implements ResourceSupplier
{
    private final PluginResourceLocation location;
    private final MetadataSerializer metadataSerializer;
    private final ClassLoader classLoader;
    
    public PluginResourceSupplier(final PluginResourceLocation location, final MetadataSerializer metadataSerializer, final ClassLoader classLoader) {
        this.classLoader = Objects.requireNonNull(classLoader);
        this.metadataSerializer = Objects.requireNonNull(metadataSerializer);
        this.location = location;
    }
    
    @Override
    public IResource get() throws ResourceException {
        final String target = String.format("%s/%s/%s", "assets", this.location.getResourceDomain(), this.location.getResourcePath());
        try {
            final InputStream stream = this.classLoader.getResourceAsStream(target);
            if (stream == null) {
                throw new ResourceException("PluginResource: " + this.location + " had no InputStream!");
            }
            return this.location.toResource(this.location.getResourcePack(), this.location, stream, stream, this.metadataSerializer);
        }
        catch (Exception e) {
            throw new ResourceException(e);
        }
    }
}
