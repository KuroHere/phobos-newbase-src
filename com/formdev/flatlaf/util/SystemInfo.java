// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

import java.util.*;

public class SystemInfo
{
    public static final boolean isWindows;
    public static final boolean isMacOS;
    public static final boolean isLinux;
    public static final long osVersion;
    public static final boolean isWindows_10_orLater;
    public static final boolean isMacOS_10_11_ElCapitan_orLater;
    public static final boolean isMacOS_10_14_Mojave_orLater;
    public static final boolean isMacOS_10_15_Catalina_orLater;
    public static final boolean isX86_64;
    public static final long javaVersion;
    public static final boolean isJava_9_orLater;
    public static final boolean isJava_11_orLater;
    public static final boolean isJava_15_orLater;
    public static final boolean isJetBrainsJVM;
    public static final boolean isJetBrainsJVM_11_orLater;
    public static final boolean isKDE;
    public static final boolean isProjector;
    public static final boolean isWebswing;
    public static final boolean isWinPE;
    
    public static long scanVersion(final String version) {
        int major = 1;
        int minor = 0;
        int micro = 0;
        int patch = 0;
        try {
            final StringTokenizer st = new StringTokenizer(version, "._-+");
            major = Integer.parseInt(st.nextToken());
            minor = Integer.parseInt(st.nextToken());
            micro = Integer.parseInt(st.nextToken());
            patch = Integer.parseInt(st.nextToken());
        }
        catch (Exception ex) {}
        return toVersion(major, minor, micro, patch);
    }
    
    public static long toVersion(final int major, final int minor, final int micro, final int patch) {
        return ((long)major << 48) + ((long)minor << 32) + ((long)micro << 16) + patch;
    }
    
    static {
        final String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        isWindows = osName.startsWith("windows");
        isMacOS = osName.startsWith("mac");
        isLinux = osName.startsWith("linux");
        osVersion = scanVersion(System.getProperty("os.version"));
        isWindows_10_orLater = (SystemInfo.isWindows && SystemInfo.osVersion >= toVersion(10, 0, 0, 0));
        isMacOS_10_11_ElCapitan_orLater = (SystemInfo.isMacOS && SystemInfo.osVersion >= toVersion(10, 11, 0, 0));
        isMacOS_10_14_Mojave_orLater = (SystemInfo.isMacOS && SystemInfo.osVersion >= toVersion(10, 14, 0, 0));
        isMacOS_10_15_Catalina_orLater = (SystemInfo.isMacOS && SystemInfo.osVersion >= toVersion(10, 15, 0, 0));
        final String osArch = System.getProperty("os.arch");
        isX86_64 = (osArch.equals("amd64") || osArch.equals("x86_64"));
        javaVersion = scanVersion(System.getProperty("java.version"));
        isJava_9_orLater = (SystemInfo.javaVersion >= toVersion(9, 0, 0, 0));
        isJava_11_orLater = (SystemInfo.javaVersion >= toVersion(11, 0, 0, 0));
        isJava_15_orLater = (SystemInfo.javaVersion >= toVersion(15, 0, 0, 0));
        isJetBrainsJVM = System.getProperty("java.vm.vendor", "Unknown").toLowerCase(Locale.ENGLISH).contains("jetbrains");
        isJetBrainsJVM_11_orLater = (SystemInfo.isJetBrainsJVM && SystemInfo.isJava_11_orLater);
        isKDE = (SystemInfo.isLinux && System.getenv("KDE_FULL_SESSION") != null);
        isProjector = Boolean.getBoolean("org.jetbrains.projector.server.enable");
        isWebswing = (System.getProperty("webswing.rootDir") != null);
        isWinPE = (SystemInfo.isWindows && "X:\\Windows\\System32".equalsIgnoreCase(System.getProperty("user.dir")));
    }
}
