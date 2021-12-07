//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import me.earth.earthhack.impl.util.helpers.*;
import java.util.concurrent.atomic.*;
import javax.annotation.*;
import net.minecraft.client.renderer.texture.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class ScreenShotRunnable extends SafeFinishable
{
    private static final DateFormat DATE_FORMAT;
    private final AtomicReference<String> finishedString;
    private final AtomicReference<File> fileReference;
    private final String screenShotName;
    private final File dir;
    private final int width;
    private final int height;
    private final int[] pixels;
    
    public ScreenShotRunnable(final AtomicReference<String> finishedString, final AtomicReference<File> fileReference, final AtomicBoolean finished, final int width, final int height, final int[] pixels, final File dir, @Nullable final String screenshotName) {
        super(finished);
        this.fileReference = fileReference;
        this.finishedString = finishedString;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.dir = dir;
        this.screenShotName = screenshotName;
    }
    
    @Override
    public void runSafely() throws IOException {
        TextureUtil.processPixelValues(this.pixels, this.width, this.height);
        final BufferedImage bufferedimage = new BufferedImage(this.width, this.height, 1);
        bufferedimage.setRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
        final File file = makeScreenshotFile(this.dir, this.screenShotName);
        ImageIO.write(bufferedimage, "png", file);
        this.finishedString.set("Screenshot: " + file.getName());
        this.fileReference.set(file);
    }
    
    @Override
    public void handle(final Throwable t) {
        this.finishedString.set("Screenshot Error: " + t.getMessage());
        super.handle(t);
    }
    
    private static File makeScreenshotFile(final File gameDirectory, @Nullable final String screenshotName) throws IOException {
        final File directory = new File(gameDirectory, "screenshots");
        directory.mkdir();
        File file;
        if (screenshotName == null) {
            file = getTimestampedPNGFileForDirectory(directory);
        }
        else {
            file = new File(directory, screenshotName);
        }
        file = file.getCanonicalFile();
        return file;
    }
    
    private static File getTimestampedPNGFileForDirectory(final File gameDirectory) {
        final String s = ScreenShotRunnable.DATE_FORMAT.format(new Date());
        int i = 1;
        File file;
        while (true) {
            file = new File(gameDirectory, s + ((i == 1) ? "" : ("_" + i)) + ".png");
            if (!file.exists()) {
                break;
            }
            ++i;
        }
        return file;
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
}
