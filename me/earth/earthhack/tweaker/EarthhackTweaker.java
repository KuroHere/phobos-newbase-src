// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.tweaker;

import org.spongepowered.asm.launch.*;
import java.io.*;
import java.lang.reflect.*;
import net.minecraft.launchwrapper.*;
import org.apache.logging.log4j.*;
import java.util.*;
import me.earth.earthhack.tweaker.launch.arguments.*;
import me.earth.earthhack.tweaker.launch.*;

public class EarthhackTweaker implements ITweaker
{
    private Map<String, String> launchArgs;
    private final MixinTweaker wrapped;
    
    public EarthhackTweaker() {
        this.wrapped = new MixinTweaker();
    }
    
    public void acceptOptions(final List<String> args, final File gameDir, final File assetsDir, final String profile) {
        try {
            final String className = "me.earth.earthhack.vanilla.Environment";
            final Class<?> env = Class.forName(className, true, (ClassLoader)Launch.classLoader);
            final Method load = env.getDeclaredMethod("loadEnvironment", (Class<?>[])new Class[0]);
            load.setAccessible(true);
            load.invoke(null, new Object[0]);
        }
        catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            final ReflectiveOperationException ex2;
            final ReflectiveOperationException e = ex2;
            throw new RuntimeException(e);
        }
        final Object obj = Launch.blackboard.get("launchArgs");
        if (obj == null) {
            this.launchArgs = new HashMap<String, String>();
            String classifier = null;
            for (final String arg : args) {
                if (arg.startsWith("-")) {
                    if (classifier != null) {
                        classifier = this.launchArgs.put(classifier, "");
                    }
                    else if (arg.contains("=")) {
                        classifier = this.launchArgs.put(arg.substring(0, arg.indexOf(61)), arg.substring(arg.indexOf(61) + 1));
                    }
                    else {
                        classifier = arg;
                    }
                }
                else {
                    if (classifier == null) {
                        continue;
                    }
                    classifier = this.launchArgs.put(classifier, arg);
                }
            }
            if (!this.launchArgs.containsKey("--version")) {
                this.launchArgs.put("--version", (profile != null) ? profile : "3arthh4ck-Profile");
            }
            if (!this.launchArgs.containsKey("--gameDir") && gameDir != null) {
                this.launchArgs.put("--gameDir", gameDir.getAbsolutePath());
            }
            if (!this.launchArgs.containsKey("--assetsDir") && assetsDir != null) {
                this.launchArgs.put("--assetsDir", assetsDir.getAbsolutePath());
            }
        }
        this.wrapped.acceptOptions(args, gameDir, assetsDir, profile);
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader classLoader) {
        this.wrapped.injectIntoClassLoader(classLoader);
        try {
            final String className = "me.earth.earthhack.impl.core.Core";
            classLoader.addTransformerExclusion(className);
            final Class<?> coreClass = Class.forName(className, true, (ClassLoader)classLoader);
            final TweakerCore core = (TweakerCore)coreClass.newInstance();
            final Logger logger = LogManager.getLogger("3arthh4ck-Core");
            logger.info("\n\n");
            this.loadDevArguments();
            core.init((ClassLoader)classLoader);
            for (final String transformer : core.getTransformers()) {
                classLoader.registerTransformer(transformer);
            }
            logger.info("\n\n");
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            final ReflectiveOperationException ex2;
            final ReflectiveOperationException e = ex2;
            throw new IllegalStateException(e);
        }
    }
    
    public String getLaunchTarget() {
        return this.wrapped.getLaunchTarget();
    }
    
    public String[] getLaunchArguments() {
        final List<String> al = Launch.blackboard.get("ArgumentList");
        if (al.isEmpty() && this.launchArgs != null && Launch.blackboard.get("launchArgs") == null) {
            final List<String> args = new ArrayList<String>(this.launchArgs.size() * 2);
            for (final Map.Entry<String, String> arg : this.launchArgs.entrySet()) {
                args.add(arg.getKey());
                args.add(arg.getValue());
            }
            return args.toArray(new String[0]);
        }
        return this.wrapped.getLaunchArguments();
    }
    
    private void loadDevArguments() {
        final ArgumentManager dev = DevArguments.getInstance();
        dev.addArgument("inventory", new BooleanArgument());
        dev.addArgument("inventorymp", new BooleanArgument());
        dev.addArgument("totems", new BooleanArgument());
        dev.addArgument("dead", new BooleanArgument(Boolean.FALSE));
        dev.addArgument("jsrn", new BooleanArgument());
        dev.addArgument("jsnull", new BooleanArgument(Boolean.FALSE));
        dev.addArgument("connection", new LongArgument(800L));
        dev.addArgument("leijurvpos", new BooleanArgument(Boolean.TRUE));
        dev.loadArguments();
    }
}
