// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.service;

import me.earth.earthhack.installer.version.*;
import me.earth.earthhack.api.config.*;
import com.google.gson.*;
import java.util.*;

public class InstallerUtil
{
    public static final String ASM = "org.ow2.asm:asm-debug-all:5.2";
    public static final String LAUNCH = "net.minecraft:launchwrapper:1.12";
    public static final String NAME = "earthhack:forge:1.12.2";
    public static final String VNAME = "earthhack:vanilla:1.12.2";
    
    public static void installEarthhack(final JsonObject o, final boolean forge) {
        final JsonElement args = VersionUtil.getOrElse("minecraftArguments", o, "");
        final String newArgs = args.getAsString() + " " + "--tweakClass me.earth.earthhack.tweaker.EarthhackTweaker";
        final JsonElement element = Jsonable.parse(newArgs);
        o.add("minecraftArguments", element);
        final JsonElement libs = VersionUtil.getOrElse("libraries", o, "[]");
        final JsonArray array = libs.getAsJsonArray();
        final JsonObject object = new JsonObject();
        object.add("name", Jsonable.parse(forge ? "earthhack:forge:1.12.2" : "earthhack:vanilla:1.12.2"));
        array.add((JsonElement)object);
        o.add("libraries", libs);
    }
    
    public static void installLibs(final JsonObject o) {
        final JsonElement libs = VersionUtil.getOrElse("libraries", o, "[]");
        final JsonArray array = libs.getAsJsonArray();
        boolean hasAsm = false;
        boolean hasLaunch = false;
        for (final JsonElement element : array) {
            final JsonElement name = element.getAsJsonObject().get("name");
            if (name != null) {
                if (name.getAsString().equals("org.ow2.asm:asm-debug-all:5.2")) {
                    hasAsm = true;
                }
                else {
                    if (!name.getAsString().equals("net.minecraft:launchwrapper:1.12")) {
                        continue;
                    }
                    hasLaunch = true;
                }
            }
        }
        if (!hasAsm) {
            final JsonObject asmLib = new JsonObject();
            asmLib.add("name", Jsonable.parse("org.ow2.asm:asm-debug-all:5.2"));
            final JsonObject downloads = new JsonObject();
            final JsonObject artifact = new JsonObject();
            artifact.add("path", Jsonable.parse("org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar"));
            artifact.add("url", Jsonable.parse("https://files.minecraftforge.net/maven/org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar"));
            artifact.add("sha1", Jsonable.parse("3354e11e2b34215f06dab629ab88e06aca477c19"));
            artifact.add("size", Jsonable.parse("387903", false));
            downloads.add("artifact", (JsonElement)artifact);
            asmLib.add("downloads", (JsonElement)downloads);
            asmLib.add("earthhlib", Jsonable.parse("true", false));
            array.add((JsonElement)asmLib);
        }
        if (!hasLaunch) {
            final JsonObject launchLib = new JsonObject();
            launchLib.add("name", Jsonable.parse("net.minecraft:launchwrapper:1.12"));
            launchLib.add("serverreq", Jsonable.parse("true", false));
            launchLib.add("earthhlib", Jsonable.parse("true", false));
            array.add((JsonElement)launchLib);
        }
    }
    
    public static void uninstallEarthhack(final JsonObject o) {
        final JsonElement args = o.get("minecraftArguments");
        if (args != null) {
            final String removed = args.getAsString().replace(" --tweakClass me.earth.earthhack.tweaker.EarthhackTweaker", "");
            final JsonElement element = Jsonable.parse(removed);
            o.add("minecraftArguments", element);
        }
        final JsonElement libs = o.get("libraries");
        if (libs != null) {
            final JsonArray array = libs.getAsJsonArray();
            final Iterator<JsonElement> itr = array.iterator();
            while (itr.hasNext()) {
                final JsonObject library = itr.next().getAsJsonObject();
                final JsonElement name = library.get("name");
                if (name != null && (name.getAsString().equals("earthhack:forge:1.12.2") || name.getAsString().equals("earthhack:vanilla:1.12.2"))) {
                    itr.remove();
                }
            }
        }
    }
    
    public static void uninstallLibs(final JsonObject o) {
        final JsonElement libs = o.get("libraries");
        if (libs != null) {
            final JsonArray array = libs.getAsJsonArray();
            final Iterator<JsonElement> itr = array.iterator();
            while (itr.hasNext()) {
                final JsonObject library = itr.next().getAsJsonObject();
                final JsonElement name = library.get("earthhlib");
                if (name != null) {
                    itr.remove();
                }
            }
        }
    }
}
