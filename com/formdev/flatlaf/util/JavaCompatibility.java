// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

import javax.swing.*;
import java.lang.reflect.*;
import java.awt.*;

public class JavaCompatibility
{
    private static Method drawStringUnderlineCharAtMethod;
    private static Method getClippedStringMethod;
    
    public static void drawStringUnderlineCharAt(final JComponent c, final Graphics g, final String text, final int underlinedIndex, final int x, final int y) {
        synchronized (JavaCompatibility.class) {
            if (JavaCompatibility.drawStringUnderlineCharAtMethod == null) {
                try {
                    final Class<?> cls = Class.forName(SystemInfo.isJava_9_orLater ? "javax.swing.plaf.basic.BasicGraphicsUtils" : "sun.swing.SwingUtilities2");
                    JavaCompatibility.drawStringUnderlineCharAtMethod = cls.getMethod("drawStringUnderlineCharAt", (Class<?>[])(SystemInfo.isJava_9_orLater ? new Class[] { JComponent.class, Graphics2D.class, String.class, Integer.TYPE, Float.TYPE, Float.TYPE } : new Class[] { JComponent.class, Graphics.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE }));
                }
                catch (Exception ex) {
                    LoggingFacade.INSTANCE.logSevere(null, ex);
                    throw new RuntimeException(ex);
                }
            }
        }
        try {
            if (SystemInfo.isJava_9_orLater) {
                JavaCompatibility.drawStringUnderlineCharAtMethod.invoke(null, c, g, text, underlinedIndex, Float.valueOf(x), Float.valueOf(y));
            }
            else {
                JavaCompatibility.drawStringUnderlineCharAtMethod.invoke(null, c, g, text, underlinedIndex, x, y);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex3) {
            final Exception ex4;
            final Exception ex2 = ex4;
            LoggingFacade.INSTANCE.logSevere(null, ex2);
            throw new RuntimeException(ex2);
        }
    }
    
    public static String getClippedString(final JComponent c, final FontMetrics fm, final String string, final int availTextWidth) {
        synchronized (JavaCompatibility.class) {
            if (JavaCompatibility.getClippedStringMethod == null) {
                try {
                    final Class<?> cls = Class.forName(SystemInfo.isJava_9_orLater ? "javax.swing.plaf.basic.BasicGraphicsUtils" : "sun.swing.SwingUtilities2");
                    JavaCompatibility.getClippedStringMethod = cls.getMethod(SystemInfo.isJava_9_orLater ? "getClippedString" : "clipStringIfNecessary", JComponent.class, FontMetrics.class, String.class, Integer.TYPE);
                }
                catch (Exception ex) {
                    LoggingFacade.INSTANCE.logSevere(null, ex);
                    throw new RuntimeException(ex);
                }
            }
        }
        try {
            return (String)JavaCompatibility.getClippedStringMethod.invoke(null, c, fm, string, availTextWidth);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex3) {
            final Exception ex4;
            final Exception ex2 = ex4;
            LoggingFacade.INSTANCE.logSevere(null, ex2);
            throw new RuntimeException(ex2);
        }
    }
}
