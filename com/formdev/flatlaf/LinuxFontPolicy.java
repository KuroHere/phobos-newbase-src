// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf;

import java.io.*;
import java.util.*;
import com.formdev.flatlaf.util.*;
import java.awt.*;

class LinuxFontPolicy
{
    static Font getFont() {
        return SystemInfo.isKDE ? getKDEFont() : getGnomeFont();
    }
    
    private static Font getGnomeFont() {
        Object fontName = Toolkit.getDefaultToolkit().getDesktopProperty("gnome.Gtk/FontName");
        if (!(fontName instanceof String)) {
            fontName = "sans 10";
        }
        String family = "";
        int style = 0;
        double dsize = 10.0;
        final StringTokenizer st = new StringTokenizer((String)fontName);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            if (word.endsWith(",")) {
                word = word.substring(0, word.length() - 1).trim();
            }
            final String lword = word.toLowerCase();
            if (lword.equals("italic") || lword.equals("oblique")) {
                style |= 0x2;
            }
            else if (lword.equals("bold")) {
                style |= 0x1;
            }
            else if (Character.isDigit(word.charAt(0))) {
                try {
                    dsize = Double.parseDouble(word);
                }
                catch (NumberFormatException ex) {}
            }
            else {
                if (lword.startsWith("semi-") || lword.startsWith("demi-")) {
                    word = word.substring(0, 4) + word.substring(5);
                }
                else if (lword.startsWith("extra-") || lword.startsWith("ultra-")) {
                    word = word.substring(0, 5) + word.substring(6);
                }
                family = (family.isEmpty() ? word : (family + ' ' + word));
            }
        }
        if (family.startsWith("Ubuntu") && !SystemInfo.isJetBrainsJVM && !FlatSystemProperties.getBoolean("flatlaf.useUbuntuFont", false)) {
            family = "Liberation Sans";
        }
        dsize *= getGnomeFontScale();
        int size = (int)(dsize + 0.5);
        if (size < 1) {
            size = 1;
        }
        final String logicalFamily = mapFcName(family.toLowerCase());
        if (logicalFamily != null) {
            family = logicalFamily;
        }
        return createFontEx(family, style, size, dsize);
    }
    
    private static Font createFontEx(String family, int style, final int size, final double dsize) {
        while (true) {
            final Font font = createFont(family, style, size, dsize);
            if (!"Dialog".equals(font.getFamily()) || "Dialog".equals(family)) {
                return font;
            }
            final int index = family.lastIndexOf(32);
            if (index < 0) {
                return createFont("Dialog", style, size, dsize);
            }
            final String lastWord = family.substring(index + 1).toLowerCase();
            if (lastWord.contains("bold") || lastWord.contains("heavy") || lastWord.contains("black")) {
                style |= 0x1;
            }
            family = family.substring(0, index);
        }
    }
    
    private static Font createFont(final String family, final int style, final int size, final double dsize) {
        Font font = FlatLaf.createCompositeFont(family, style, size);
        font = font.deriveFont(style, (float)dsize);
        return font;
    }
    
    private static double getGnomeFontScale() {
        if (isSystemScaling()) {
            return 1.3333333333333333;
        }
        final Object value = Toolkit.getDefaultToolkit().getDesktopProperty("gnome.Xft/DPI");
        if (value instanceof Integer) {
            int dpi = (int)value / 1024;
            if (dpi == -1) {
                dpi = 96;
            }
            if (dpi < 50) {
                dpi = 50;
            }
            return dpi / 72.0;
        }
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getNormalizingTransform().getScaleY();
    }
    
    private static String mapFcName(final String name) {
        switch (name) {
            case "sans": {
                return "sansserif";
            }
            case "sans-serif": {
                return "sansserif";
            }
            case "serif": {
                return "serif";
            }
            case "monospace": {
                return "monospaced";
            }
            default: {
                return null;
            }
        }
    }
    
    private static Font getKDEFont() {
        final List<String> kdeglobals = readConfig("kdeglobals");
        final List<String> kcmfonts = readConfig("kcmfonts");
        final String generalFont = getConfigEntry(kdeglobals, "General", "font");
        final String forceFontDPI = getConfigEntry(kcmfonts, "General", "forceFontDPI");
        String family = "sansserif";
        int style = 0;
        int size = 10;
        if (generalFont != null) {
            final List<String> strs = StringUtils.split(generalFont, ',');
            try {
                family = strs.get(0);
                size = Integer.parseInt(strs.get(1));
                if ("75".equals(strs.get(4))) {
                    style |= 0x1;
                }
                if ("1".equals(strs.get(5))) {
                    style |= 0x2;
                }
            }
            catch (RuntimeException ex) {
                LoggingFacade.INSTANCE.logConfig("FlatLaf: Failed to parse 'font=" + generalFont + "'.", ex);
            }
        }
        int dpi = 96;
        if (forceFontDPI != null && !isSystemScaling()) {
            try {
                dpi = Integer.parseInt(forceFontDPI);
                if (dpi <= 0) {
                    dpi = 96;
                }
                if (dpi < 50) {
                    dpi = 50;
                }
            }
            catch (NumberFormatException ex2) {
                LoggingFacade.INSTANCE.logConfig("FlatLaf: Failed to parse 'forceFontDPI=" + forceFontDPI + "'.", ex2);
            }
        }
        final double fontScale = dpi / 72.0;
        final double dsize = size * fontScale;
        size = (int)(dsize + 0.5);
        if (size < 1) {
            size = 1;
        }
        return createFont(family, style, size, dsize);
    }
    
    private static List<String> readConfig(final String filename) {
        final File userHome = new File(System.getProperty("user.home"));
        final String[] configDirs = { ".config", ".kde4/share/config", ".kde/share/config" };
        File file = null;
        for (final String configDir : configDirs) {
            file = new File(userHome, configDir + "/" + filename);
            if (file.isFile()) {
                break;
            }
        }
        if (!file.isFile()) {
            return Collections.emptyList();
        }
        final ArrayList<String> lines = new ArrayList<String>(200);
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException ex) {
            LoggingFacade.INSTANCE.logConfig("FlatLaf: Failed to read '" + filename + "'.", ex);
        }
        return lines;
    }
    
    private static String getConfigEntry(final List<String> config, final String group, final String key) {
        final int groupLength = group.length();
        final int keyLength = key.length();
        boolean inGroup = false;
        for (final String line : config) {
            if (!inGroup) {
                if (line.length() < groupLength + 2 || line.charAt(0) != '[' || line.charAt(groupLength + 1) != ']' || line.indexOf(group) != 1) {
                    continue;
                }
                inGroup = true;
            }
            else {
                if (line.startsWith("[")) {
                    return null;
                }
                if (line.length() >= keyLength + 2 && line.charAt(keyLength) == '=' && line.startsWith(key)) {
                    return line.substring(keyLength + 1);
                }
                continue;
            }
        }
        return null;
    }
    
    private static boolean isSystemScaling() {
        if (GraphicsEnvironment.isHeadless()) {
            return true;
        }
        final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return UIScale.getSystemScaleFactor(gc) > 1.0;
    }
}
