// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render.image;

import me.earth.earthhack.api.util.interfaces.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import java.security.*;
import java.io.*;
import java.util.*;
import org.lwjgl.*;

public class GifImage implements Globals
{
    private List<BufferedImage> frames;
    private List<DynamicTexture> textures;
    private int offset;
    private int delay;
    private boolean firstUpdate;
    private long lastUpdate;
    private long timeLeft;
    
    public GifImage(final List<BufferedImage> images, final int delay) {
        this.frames = new LinkedList<BufferedImage>();
        this.textures = new LinkedList<DynamicTexture>();
        this.frames.clear();
        for (final BufferedImage image : images) {
            this.frames.add(ImageUtil.createFlipped(image));
        }
        this.offset = 0;
        this.delay = delay;
        this.firstUpdate = true;
        for (final BufferedImage image : this.frames) {
            try {
                this.textures.add(ImageUtil.cacheBufferedImage(image, "gif"));
            }
            catch (NoSuchAlgorithmException | IOException ex) {
                final Exception ex2;
                final Exception e = ex2;
                e.printStackTrace();
            }
        }
        this.reset();
    }
    
    public GifImage(final BufferedImage sheet, final int width, final int height) {
        this.frames = new LinkedList<BufferedImage>();
        this.textures = new LinkedList<DynamicTexture>();
    }
    
    public void reset() {
        this.firstUpdate = true;
        this.timeLeft = this.delay;
        this.offset = 0;
    }
    
    public BufferedImage getBufferedImage() {
        final long now = this.getTime();
        long delta = now - this.lastUpdate;
        if (this.firstUpdate) {
            delta = 0L;
            this.firstUpdate = false;
        }
        this.lastUpdate = now;
        this.timeLeft -= delta;
        if (this.timeLeft <= 0L) {
            ++this.offset;
            this.timeLeft = this.delay;
        }
        if (this.offset >= this.frames.size()) {
            this.offset = 0;
        }
        return this.frames.get(this.offset);
    }
    
    public DynamicTexture getDynamicTexture() {
        final long now = this.getTime();
        long delta = now - this.lastUpdate;
        if (this.firstUpdate) {
            delta = 0L;
            this.firstUpdate = false;
        }
        this.lastUpdate = now;
        this.timeLeft -= delta;
        if (this.timeLeft <= 0L) {
            ++this.offset;
            this.timeLeft = this.delay;
        }
        if (this.offset >= this.frames.size()) {
            this.offset = 0;
        }
        return this.textures.get(this.offset);
    }
    
    private long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
}
