//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.resource;

import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.data.*;
import javax.annotation.*;

public class PluginResource extends SimpleResource
{
    public PluginResource(final String resourcePackNameIn, final ResourceLocation srResourceLocationIn, final InputStream resourceInputStreamIn, final InputStream mcmetaInputStreamIn, final MetadataSerializer srMetadataSerializerIn) {
        super(resourcePackNameIn, srResourceLocationIn, resourceInputStreamIn, mcmetaInputStreamIn, srMetadataSerializerIn);
    }
    
    @Nullable
    public <T extends IMetadataSection> T getMetadata(final String sectionName) {
        return null;
    }
}
