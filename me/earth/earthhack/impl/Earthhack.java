// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.managers.thread.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.util.discord.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.core.ducks.*;
import org.apache.logging.log4j.*;

public class Earthhack implements Globals
{
    private static final Logger LOGGER;
    public static final String NAME = "3arthh4ck";
    public static final String VERSION = "1.3.1";
    
    public static void preInit() {
        GlobalExecutor.EXECUTOR.submit(() -> Sphere.cacheSphere(Earthhack.LOGGER));
    }
    
    public static void init() {
        Earthhack.LOGGER.info("\n\nInitializing 3arthh4ck.");
        Display.setTitle("3arthh4ck - 1.3.1");
        DiscordPresence.start();
        Managers.load();
        Earthhack.LOGGER.info("\n3arthh4ck initialized.\n");
    }
    
    public static void postInit() {
    }
    
    public static Logger getLogger() {
        return Earthhack.LOGGER;
    }
    
    public static boolean isRunning() {
        return ((IMinecraft)Earthhack.mc).isEarthhackRunning();
    }
    
    static {
        LOGGER = LogManager.getLogger("3arthh4ck");
    }
}
