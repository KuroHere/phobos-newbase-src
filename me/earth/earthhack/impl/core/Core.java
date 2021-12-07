// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core;

import me.earth.earthhack.tweaker.*;
import me.earth.earthhack.vanilla.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.core.util.*;
import me.earth.earthhack.impl.managers.client.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.plugin.*;
import java.nio.file.*;
import java.util.*;
import me.earth.earthhack.impl.core.transfomer.*;
import org.apache.logging.log4j.*;

public class Core implements TweakerCore
{
    public static final Logger LOGGER;
    
    @Override
    public void init(final ClassLoader pluginClassLoader) {
        Core.LOGGER.info("Initializing 3arthh4cks Core.");
        Core.LOGGER.info("Found Environment: " + Environment.getEnvironment());
        Bus.EVENT_BUS.subscribe(Scheduler.getInstance());
        final Path path = Paths.get("earthhack", new String[0]);
        FileUtil.createDirectory(path);
        FileUtil.getDirectory(path, "util");
        FileUtil.getDirectory(path, "plugins");
        final MixinHelper helper = MixinHelper.getHelper();
        PluginManager.getInstance().createPluginConfigs(pluginClassLoader);
        MixinBootstrap.init();
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT).setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.PREINIT).setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.INIT).setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT).setSide(MixinEnvironment.Side.CLIENT);
        String extraMixin;
        if (Environment.hasForge()) {
            Core.LOGGER.info("Forge detected!");
            extraMixin = "mixins.forge.json";
        }
        else {
            Core.LOGGER.info("No Forge!");
            extraMixin = "mixins.vanilla.json";
        }
        Mixins.addConfiguration(extraMixin);
        for (final PluginConfig config : PluginManager.getInstance().getConfigs().values()) {
            if (config.getMixinConfig() != null) {
                Core.LOGGER.info("Adding " + config.getName() + "'s MixinConfig: " + config.getMixinConfig());
                helper.addConfigExclusion(config.getMixinConfig());
                Mixins.addConfiguration(config.getMixinConfig());
            }
        }
        helper.addConfigExclusion("mixins.earth.json");
        Mixins.addConfiguration("mixins.earth.json");
        String obfuscationContext = "searge";
        if (Environment.getEnvironment() == Environment.VANILLA) {
            obfuscationContext = "notch";
        }
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext(obfuscationContext);
    }
    
    @Override
    public String[] getTransformers() {
        return new String[] { EarthhackTransformer.class.getName() };
    }
    
    static {
        LOGGER = LogManager.getLogger("3arthh4ck-Core");
    }
}
