// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.newdawn.slick.util;

import org.newdawn.slick.*;

public class Bootstrap
{
    public static void runAsApplication(final Game game, final int width, final int height, final boolean fullscreen) {
        try {
            final AppGameContainer container = new AppGameContainer(game, width, height, fullscreen);
            container.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
