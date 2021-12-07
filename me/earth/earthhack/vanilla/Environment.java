// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.vanilla;

import net.minecraft.launchwrapper.*;
import java.io.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.*;
import me.earth.earthhack.impl.core.util.*;

public enum Environment
{
    VANILLA, 
    SEARGE, 
    MCP;
    
    private static Environment environment;
    private static boolean forge;
    
    public static Environment getEnvironment() {
        return Environment.environment;
    }
    
    public static boolean hasForge() {
        return Environment.forge;
    }
    
    public static void loadEnvironment() {
        Environment env = Environment.SEARGE;
        try {
            final String fml = "net.minecraftforge.common.ForgeHooks";
            final byte[] forgeBytes = Launch.classLoader.getClassBytes(fml);
            if (forgeBytes != null) {
                Environment.forge = true;
            }
            else {
                env = Environment.VANILLA;
            }
        }
        catch (IOException e) {
            env = Environment.VANILLA;
        }
        final String world = "net.minecraft.world.World";
        byte[] bs = null;
        try {
            bs = Launch.classLoader.getClassBytes(world);
        }
        catch (IOException ex) {}
        if (bs != null) {
            final ClassNode node = new ClassNode();
            final ClassReader reader = new ClassReader(bs);
            reader.accept((ClassVisitor)node, 0);
            if (AsmUtil.findField(node, "loadedEntityList") != null) {
                env = Environment.MCP;
            }
        }
        Environment.environment = env;
    }
}
