//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.*;
import java.util.concurrent.*;
import net.minecraft.client.resources.*;
import me.earth.earthhack.impl.*;
import java.util.*;
import me.earth.earthhack.impl.core.ducks.*;
import me.earth.earthhack.impl.managers.client.resource.*;
import net.minecraft.client.resources.data.*;

public class PluginResourceManager implements Globals
{
    private static final PluginResourceManager INSTANCE;
    private final Map<ResourceLocation, List<ResourceSupplier>> resourceMap;
    
    private PluginResourceManager() {
        this.resourceMap = new ConcurrentHashMap<ResourceLocation, List<ResourceSupplier>>();
    }
    
    public static PluginResourceManager getInstance() {
        return PluginResourceManager.INSTANCE;
    }
    
    public ResourceSupplier getSingleResource(final ResourceLocation location) {
        final List<ResourceSupplier> suppliers = this.resourceMap.get(location);
        if (suppliers == null || suppliers.size() != 1) {
            return null;
        }
        return suppliers.get(0);
    }
    
    public List<IResource> getPluginResources(final ResourceLocation location) {
        final List<ResourceSupplier> suppliers = this.resourceMap.get(location);
        List<IResource> result;
        if (suppliers != null) {
            Earthhack.getLogger().info("Found " + suppliers.size() + " custom ResourceLocation" + ((suppliers.size() == 1) ? "" : "s") + " for " + location);
            result = new ArrayList<IResource>(suppliers.size());
            for (final ResourceSupplier supplier : suppliers) {
                if (supplier != null) {
                    try {
                        final IResource resource = supplier.get();
                        if (resource == null) {
                            continue;
                        }
                        result.add(resource);
                    }
                    catch (ResourceException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            result = Collections.emptyList();
        }
        return result;
    }
    
    public void register(final PluginResourceLocation r) {
        this.register(new ResourceLocation(r.getResourceDomain(), r.getResourcePath()), r);
    }
    
    public void register(final ResourceLocation location, final PluginResourceLocation resourceLocation) {
        Earthhack.getLogger().info("Adding custom ResourceLocation: " + location + " for: " + resourceLocation);
        final ClassLoader loader = PluginManager.getInstance().getPluginClassLoader();
        if (loader == null) {
            throw new IllegalStateException("Plugin ClassLoader was null!");
        }
        final MetadataSerializer mds = ((IMinecraft)PluginResourceManager.mc).getMetadataSerializer();
        if (mds == null) {
            throw new IllegalStateException("MetadataSerializer was null!");
        }
        final ResourceSupplier supplier = new PluginResourceSupplier(resourceLocation, mds, loader);
        this.register(location, supplier);
    }
    
    public void register(final ResourceLocation location, final ResourceSupplier... resourceSuppliers) {
        final List<ResourceSupplier> suppliers = this.resourceMap.computeIfAbsent(location, v -> new ArrayList());
        for (final ResourceSupplier supplier : resourceSuppliers) {
            if (supplier != null) {
                suppliers.add(supplier);
            }
        }
    }
    
    static {
        INSTANCE = new PluginResourceManager();
    }
}
