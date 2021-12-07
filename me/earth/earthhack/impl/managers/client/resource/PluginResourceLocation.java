// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.resource;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.client.resources.*;

public class PluginResourceLocation extends ResourceLocation
{
    private final String resourcePack;
    
    protected PluginResourceLocation(final int unused, final String resourcePack, final String... resourceName) {
        super(unused, resourceName);
        this.resourcePack = resourcePack;
    }
    
    public PluginResourceLocation(final String resourceName, final String resourcePack) {
        super(resourceName);
        this.resourcePack = resourcePack;
    }
    
    public PluginResourceLocation(final String namespaceIn, final String pathIn, final String resourcePack) {
        super(namespaceIn, pathIn);
        this.resourcePack = resourcePack;
    }
    
    public String getResourcePack() {
        return this.resourcePack;
    }
    
    public IResource toResource(final String resourcePackNameIn, final ResourceLocation srResourceLocationIn, final InputStream resourceInputStreamIn, final InputStream mcmetaInputStreamIn, final MetadataSerializer srMetadataSerializerIn) {
        return (IResource)new PluginResource(resourcePackNameIn, srResourceLocationIn, resourceInputStreamIn, mcmetaInputStreamIn, srMetadataSerializerIn);
    }
}
