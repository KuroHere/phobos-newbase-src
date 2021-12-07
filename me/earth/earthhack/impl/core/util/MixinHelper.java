// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.util;

import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.*;
import java.lang.reflect.*;
import java.util.*;
import me.earth.earthhack.impl.core.*;

public class MixinHelper
{
    private static final MixinHelper INSTANCE;
    private static final IMixinConfigPlugin PLUGIN;
    private final Set<String> exclusions;
    
    private MixinHelper() {
        this.exclusions = new HashSet<String>();
    }
    
    public static MixinHelper getHelper() {
        return MixinHelper.INSTANCE;
    }
    
    public void addConfigExclusion(final String exclusion) {
        this.exclusions.add(exclusion);
    }
    
    public void establishDominance() throws NoSuchFieldException, IllegalAccessException {
        final MixinEnvironment env = MixinEnvironment.getCurrentEnvironment();
        final Object trns = env.getActiveTransformer();
        if (trns instanceof MixinTransformer) {
            final Field configsField = trns.getClass().getDeclaredField("configs");
            configsField.setAccessible(true);
            final List<?> configs = (List<?>)configsField.get(trns);
            for (final Object config : configs) {
                if (config == null) {
                    continue;
                }
                final Field nameField = config.getClass().getDeclaredField("name");
                nameField.setAccessible(true);
                final String name = (String)nameField.get(config);
                if (this.exclusions.contains(name)) {
                    continue;
                }
                this.setPriorityAndPlugin(config);
                final Field mixinMappingField = config.getClass().getDeclaredField("mixinMapping");
                mixinMappingField.setAccessible(true);
                final Map<String, List<?>> mixinMapping = (Map<String, List<?>>)mixinMappingField.get(config);
                for (final List<?> mixins : mixinMapping.values()) {
                    for (final Object mixin : mixins) {
                        if (mixin != null) {
                            this.setPriorityAndPlugin(mixin);
                        }
                    }
                }
            }
        }
    }
    
    private void setPriorityAndPlugin(final Object object) throws IllegalAccessException, NoSuchFieldException {
        final Field pluginField = object.getClass().getDeclaredField("plugin");
        pluginField.setAccessible(true);
        final Object plugin = pluginField.get(object);
        if (plugin != null && plugin != MixinHelper.PLUGIN) {
            Core.LOGGER.info("Replacing Plugin : ");
            pluginField.set(object, MixinHelper.PLUGIN);
        }
        final Field priority = object.getClass().getDeclaredField("priority");
        priority.setAccessible(true);
        final int prio = (int)priority.get(object);
        if (prio > 2147483644) {
            priority.set(object, prio - 4);
        }
    }
    
    static {
        INSTANCE = new MixinHelper();
        PLUGIN = new ReplacePlugin();
    }
}
