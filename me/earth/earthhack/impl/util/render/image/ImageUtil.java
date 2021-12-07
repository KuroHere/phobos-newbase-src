//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render.image;

import me.earth.earthhack.api.util.interfaces.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import java.security.*;
import javax.imageio.stream.*;
import java.util.*;
import javax.imageio.*;
import org.newdawn.slick.opengl.*;
import java.io.*;
import org.newdawn.slick.imageout.*;
import org.newdawn.slick.*;

public class ImageUtil implements Globals
{
    public static BufferedImage createFlipped(final BufferedImage image) {
        final AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1.0, -1.0));
        at.concatenate(AffineTransform.getTranslateInstance(0.0, -image.getHeight()));
        return createTransformed(image, at);
    }
    
    public static BufferedImage createTransformed(final BufferedImage image, final AffineTransform at) {
        final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        final Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    
    public static DynamicTexture cacheBufferedImage(final BufferedImage image, final String format) throws NoSuchAlgorithmException, IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, outputStream);
        final byte[] data = outputStream.toByteArray();
        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        final byte[] hash = md.digest();
        final String name = new String(hash);
        final DynamicTexture texture = new DynamicTexture(image);
        final ResourceLocation location = ImageUtil.mc.getTextureManager().getDynamicTextureLocation(name, texture);
        ImageUtil.mc.getTextureManager().loadTexture(location, (ITextureObject)texture);
        return texture;
    }
    
    public static void bindImage(final BufferedImage bufferedImage) throws IOException, NoSuchAlgorithmException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final String format = getImageFormat(bufferedImage);
        if (format == null) {
            throw new IOException();
        }
        ImageIO.write(bufferedImage, format, outputStream);
        final byte[] data = outputStream.toByteArray();
        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        final byte[] hash = md.digest();
        final String name = new String(hash);
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final ResourceLocation location = ImageUtil.mc.getTextureManager().getDynamicTextureLocation(name, texture);
        ImageUtil.mc.getTextureManager().bindTexture(location);
    }
    
    public static void bindImage(final BufferedImage bufferedImage, final String format) throws IOException, NoSuchAlgorithmException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (format == null) {
            throw new IOException();
        }
        ImageIO.write(bufferedImage, format, outputStream);
        final byte[] data = outputStream.toByteArray();
        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        final byte[] hash = md.digest();
        final String name = new String(hash);
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final ResourceLocation location = ImageUtil.mc.getTextureManager().getDynamicTextureLocation(name, texture);
        ImageUtil.mc.getTextureManager().bindTexture(location);
    }
    
    public static String getImageFormat(final BufferedImage image) throws IOException {
        final ImageInputStream stream = ImageIO.createImageInputStream(image);
        final Iterator iter = ImageIO.getImageReaders(stream);
        if (!iter.hasNext()) {
            return null;
        }
        final ImageReader reader = iter.next();
        final ImageReadParam param = reader.getDefaultReadParam();
        reader.setInput(stream, true, true);
        try {
            final BufferedImage bi = reader.read(0, param);
            return reader.getFormatName();
        }
        finally {
            reader.dispose();
            stream.close();
        }
    }
    
    public static org.newdawn.slick.Image bufferedImageToSlickImage(final BufferedImage image, final String format) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        final InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return new org.newdawn.slick.Image(TextureLoader.getTexture(format, is));
    }
    
    public static BufferedImage slickImageToBufferedImage(final org.newdawn.slick.Image image, final String format) throws SlickException, IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageWriterFactory.getWriterForFormat(format).saveImage(image, format, baos, true);
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return ImageIO.read(bais);
    }
    
    public static BufferedImage getCurrentAnimationFrame(final Animation animation) throws SlickException, IOException {
        return slickImageToBufferedImage(animation.getCurrentFrame(), "GIF");
    }
}
