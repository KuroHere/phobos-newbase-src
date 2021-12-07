// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.main;

import me.earth.earthhack.impl.modules.client.server.main.*;
import java.net.*;
import java.lang.reflect.*;

public class Main
{
    public static void main(final String[] args) throws Throwable {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("server")) {
                ServerMain.main(args);
                return;
            }
            if (args[0].equalsIgnoreCase("client")) {
                ClientMain.main(args);
                return;
            }
        }
        final ClassLoader bootCl = Main.class.getClassLoader();
        URL[] urls;
        if (bootCl instanceof URLClassLoader) {
            final URLClassLoader ucl = (URLClassLoader)bootCl;
            urls = ucl.getURLs();
        }
        else {
            urls = new URL[] { Main.class.getProtectionDomain().getCodeSource().getLocation() };
        }
        final LibraryClassLoader cl = new LibraryClassLoader(bootCl, urls);
        Thread.currentThread().setContextClassLoader(cl);
        final String installer = "me.earth.earthhack.installer.EarthhackInstaller";
        final Class<?> c = cl.findClass_public(installer);
        final Method m = c.getMethod("launch", cl.getClass(), String[].class);
        final Object o = c.newInstance();
        m.invoke(o, cl, args);
    }
}
