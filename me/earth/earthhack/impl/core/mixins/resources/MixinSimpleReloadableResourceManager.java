//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.resources;

import net.minecraft.client.resources.data.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.managers.client.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.managers.client.resource.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ SimpleReloadableResourceManager.class })
public abstract class MixinSimpleReloadableResourceManager
{
    @Shadow
    @Final
    private MetadataSerializer rmMetadataSerializer;
    @Shadow
    @Final
    private Set<String> setResourceDomains;
    
    @Redirect(method = { "getAllResources" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/IResourceManager;getAllResources(Lnet/minecraft/util/ResourceLocation;)Ljava/util/List;"))
    private List<IResource> getAllResourcesHook(final IResourceManager iResourceManager, final ResourceLocation location) throws IOException {
        final List<IResource> list = iResourceManager.getAllResources(location);
        list.addAll(PluginResourceManager.getInstance().getPluginResources(location));
        return list;
    }
    
    @Inject(method = { "getResource" }, at = { @At("HEAD") }, cancellable = true)
    private void getResourceHook(ResourceLocation location, final CallbackInfoReturnable<IResource> cir) {
        if (!(location instanceof PluginResourceLocation) && location.getResourceDomain().equals("earthhack")) {
            location = new PluginResourceLocation(location.getResourceDomain() + ":" + location.getResourcePath(), "earthhack");
        }
        ResourceSupplier supplier;
        if (location instanceof PluginResourceLocation) {
            final PluginResourceLocation loc = (PluginResourceLocation)location;
            final ClassLoader classLoader = PluginManager.getInstance().getPluginClassLoader();
            if (classLoader == null) {
                throw new IllegalStateException("PluginClassLoader was null!");
            }
            supplier = new PluginResourceSupplier(loc, this.rmMetadataSerializer, classLoader);
        }
        else {
            supplier = PluginResourceManager.getInstance().getSingleResource(location);
        }
        if (supplier != null) {
            Earthhack.getLogger().info("Custom Resource detected: " + location);
            try {
                final IResource resource = supplier.get();
                cir.setReturnValue(resource);
            }
            catch (ResourceException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Inject(method = { "getResourceDomains" }, at = { @At("HEAD") }, cancellable = true)
    private void getResourceDomainsHook(final CallbackInfoReturnable<Set<String>> cir) {
        final Set<String> domains = this.setResourceDomains;
        domains.add("earthhack");
    }
}
