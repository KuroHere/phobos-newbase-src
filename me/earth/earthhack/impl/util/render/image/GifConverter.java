// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render.image;

import javax.imageio.*;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.*;
import javax.imageio.metadata.*;
import java.awt.*;
import java.awt.image.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;

public class GifConverter
{
    public static Animation readAnimation(final InputStream stream) throws IOException {
        final ArrayList<ImageFrame> frames = new ArrayList<ImageFrame>(2);
        final ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        reader.setInput(ImageIO.createImageInputStream(stream));
        int lastx = 0;
        int lasty = 0;
        int width = -1;
        int height = -1;
        final IIOMetadata metadata = reader.getStreamMetadata();
        Color backgroundColor = null;
        if (metadata != null) {
            final IIOMetadataNode globalRoot = (IIOMetadataNode)metadata.getAsTree(metadata.getNativeMetadataFormatName());
            final NodeList globalColorTable = globalRoot.getElementsByTagName("GlobalColorTable");
            final NodeList globalScreeDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");
            if (globalScreeDescriptor != null && globalScreeDescriptor.getLength() > 0) {
                final IIOMetadataNode screenDescriptor = (IIOMetadataNode)globalScreeDescriptor.item(0);
                if (screenDescriptor != null) {
                    width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
                    height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
                }
            }
            if (globalColorTable != null && globalColorTable.getLength() > 0) {
                final IIOMetadataNode colorTable = (IIOMetadataNode)globalColorTable.item(0);
                if (colorTable != null) {
                    final String bgIndex = colorTable.getAttribute("backgroundColorIndex");
                    for (IIOMetadataNode colorEntry = (IIOMetadataNode)colorTable.getFirstChild(); colorEntry != null; colorEntry = (IIOMetadataNode)colorEntry.getNextSibling()) {
                        if (colorEntry.getAttribute("index").equals(bgIndex)) {
                            final int red = Integer.parseInt(colorEntry.getAttribute("red"));
                            final int green = Integer.parseInt(colorEntry.getAttribute("green"));
                            final int blue = Integer.parseInt(colorEntry.getAttribute("blue"));
                            backgroundColor = new Color(red, green, blue);
                            break;
                        }
                    }
                }
            }
        }
        BufferedImage master = null;
        boolean hasBackround = false;
        int frameIndex = 0;
        BufferedImage image;
        ImageFrame[] framez;
        int totalWidth;
        int maxHeight;
        BufferedImage finalImage;
        Graphics graphics;
        int offset;
        int delay;
        ByteArrayOutputStream baos;
        InputStream is;
        org.newdawn.slick.Image image2;
        int x;
        int y;
        int nodeIndex;
        IIOMetadataNode root;
        IIOMetadataNode gce;
        NodeList children;
        int delay2;
        String disposal;
        int i;
        Node nodeItem;
        BufferedImage from;
        ColorModel model;
        boolean alpha;
        WritableRaster raster;
        ColorModel model2;
        boolean alpha2;
        WritableRaster raster2;
        BufferedImage copy;
        NamedNodeMap map;
        Label_0503_Outer:Label_0336_Outer:Label_0594_Outer:
        while (true) {
            Label_0310: {
                try {
                    image = reader.read(frameIndex);
                    break Label_0310;
                }
                catch (IndexOutOfBoundsException io) {
                    reader.dispose();
                    framez = frames.toArray(new ImageFrame[frames.size()]);
                    totalWidth = 0;
                    maxHeight = framez[0].getHeight();
                    for (final ImageFrame frame : framez) {
                        totalWidth += frame.getWidth();
                        if (frame.getHeight() > maxHeight) {
                            maxHeight = frame.getHeight();
                        }
                    }
                    finalImage = new BufferedImage(totalWidth, maxHeight, 1);
                    graphics = finalImage.getGraphics();
                    offset = 0;
                    delay = 0;
                    for (final ImageFrame frame2 : framez) {
                        graphics.drawImage(frame2.getImage(), offset, 0, null);
                        offset += frame2.getWidth();
                        delay += frame2.getDelay() * 10;
                    }
                    delay /= framez.length;
                    graphics.dispose();
                    baos = new ByteArrayOutputStream();
                    ImageIO.write(finalImage, "gif", baos);
                    is = new ByteArrayInputStream(baos.toByteArray());
                    image2 = new org.newdawn.slick.Image(TextureLoader.getTexture("GIF", is));
                    return new Animation(new SpriteSheet(image2, width, height), delay);
                    Label_0494: {
                        x = 0;
                    }
                    y = 0;
                    nodeIndex = 0;
                    while (true) {
                        Block_17: {
                            Label_0801: {
                            Block_19_Outer:
                                while (true) {
                                    Label_0322:Block_18_Outer:
                                    while (true) {
                                        Label_0619: {
                                            Label_0778: {
                                                while (true) {
                                                    while (true) {
                                                        break Label_0503;
                                                        root = (IIOMetadataNode)reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
                                                        gce = (IIOMetadataNode)root.getElementsByTagName("GraphicControlExtension").item(0);
                                                        children = root.getChildNodes();
                                                        delay2 = Integer.valueOf(gce.getAttribute("delayTime"));
                                                        disposal = gce.getAttribute("disposalMethod");
                                                        iftrue(Label_0494:)(master != null);
                                                        Block_13: {
                                                            break Block_13;
                                                            while (true) {
                                                                Label_0649: {
                                                                    while (true) {
                                                                        master.createGraphics().fillRect(lastx, lasty, frames.get(frameIndex - 1).getWidth(), frames.get(frameIndex - 1).getHeight());
                                                                        break Label_0778;
                                                                        iftrue(Label_0666:)(frames.get(i).getDisposal().equals("restoreToPrevious") && frameIndex != 0);
                                                                        break Label_0649;
                                                                        Label_0713:
                                                                        iftrue(Label_0778:)(!disposal.equals("restoreToBackgroundColor") || backgroundColor == null || (hasBackround && frameIndex <= 1));
                                                                        continue Block_19_Outer;
                                                                    }
                                                                    nodeItem = children.item(nodeIndex);
                                                                    iftrue(Label_0594:)(!nodeItem.getNodeName().equals("ImageDescriptor"));
                                                                    break Block_17;
                                                                    iftrue(Label_0336:)(width != -1 && height != -1);
                                                                    break Label_0322;
                                                                }
                                                                from = frames.get(i).getImage();
                                                                Label_0672:
                                                                model = from.getColorModel();
                                                                alpha = from.isAlphaPremultiplied();
                                                                raster = from.copyData(null);
                                                                master = new BufferedImage(model, raster, alpha, null);
                                                                break Label_0778;
                                                                iftrue(Label_0600:)(nodeIndex >= children.getLength());
                                                                continue Block_18_Outer;
                                                            }
                                                        }
                                                        master = new BufferedImage(width, height, 2);
                                                        master.createGraphics().setColor(backgroundColor);
                                                        master.createGraphics().fillRect(0, 0, master.getWidth(), master.getHeight());
                                                        hasBackround = (image.getWidth() == width && image.getHeight() == height);
                                                        master.createGraphics().drawImage(image, 0, 0, null);
                                                        break Label_0801;
                                                        from = null;
                                                        i = frameIndex - 1;
                                                        break Label_0619;
                                                        ++nodeIndex;
                                                        continue Label_0336_Outer;
                                                    }
                                                    Label_0600:
                                                    iftrue(Label_0713:)(!disposal.equals("restoreToPrevious"));
                                                    continue Label_0594_Outer;
                                                }
                                            }
                                            master.createGraphics().drawImage(image, x, y, null);
                                            lastx = x;
                                            lasty = y;
                                            break Label_0801;
                                            Label_0666:
                                            --i;
                                        }
                                        iftrue(Label_0672:)(i < 0);
                                        continue Label_0594_Outer;
                                    }
                                    width = image.getWidth();
                                    height = image.getHeight();
                                    continue Block_19_Outer;
                                }
                            }
                            model2 = master.getColorModel();
                            alpha2 = master.isAlphaPremultiplied();
                            raster2 = master.copyData(null);
                            copy = new BufferedImage(model2, raster2, alpha2, null);
                            frames.add(new ImageFrame(copy, delay2, disposal, image.getWidth(), image.getHeight()));
                            master.flush();
                            ++frameIndex;
                            continue Label_0503_Outer;
                        }
                        map = nodeItem.getAttributes();
                        x = Integer.valueOf(map.getNamedItem("imageLeftPosition").getNodeValue());
                        y = Integer.valueOf(map.getNamedItem("imageTopPosition").getNodeValue());
                        continue;
                    }
                }
            }
            break;
        }
    }
    
    public static GifImage readGifImage(final InputStream stream) throws IOException {
        final ArrayList<ImageFrame> frames = new ArrayList<ImageFrame>(2);
        final ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
        reader.setInput(ImageIO.createImageInputStream(stream));
        int lastx = 0;
        int lasty = 0;
        int width = -1;
        int height = -1;
        final IIOMetadata metadata = reader.getStreamMetadata();
        Color backgroundColor = null;
        if (metadata != null) {
            final IIOMetadataNode globalRoot = (IIOMetadataNode)metadata.getAsTree(metadata.getNativeMetadataFormatName());
            final NodeList globalColorTable = globalRoot.getElementsByTagName("GlobalColorTable");
            final NodeList globalScreeDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");
            if (globalScreeDescriptor != null && globalScreeDescriptor.getLength() > 0) {
                final IIOMetadataNode screenDescriptor = (IIOMetadataNode)globalScreeDescriptor.item(0);
                if (screenDescriptor != null) {
                    width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
                    height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
                }
            }
            if (globalColorTable != null && globalColorTable.getLength() > 0) {
                final IIOMetadataNode colorTable = (IIOMetadataNode)globalColorTable.item(0);
                if (colorTable != null) {
                    final String bgIndex = colorTable.getAttribute("backgroundColorIndex");
                    for (IIOMetadataNode colorEntry = (IIOMetadataNode)colorTable.getFirstChild(); colorEntry != null; colorEntry = (IIOMetadataNode)colorEntry.getNextSibling()) {
                        if (colorEntry.getAttribute("index").equals(bgIndex)) {
                            final int red = Integer.parseInt(colorEntry.getAttribute("red"));
                            final int green = Integer.parseInt(colorEntry.getAttribute("green"));
                            final int blue = Integer.parseInt(colorEntry.getAttribute("blue"));
                            backgroundColor = new Color(red, green, blue);
                            break;
                        }
                    }
                }
            }
        }
        BufferedImage master = null;
        boolean hasBackround = false;
        int frameIndex = 0;
        BufferedImage image;
        ImageFrame[] framez;
        int totalWidth;
        int maxHeight;
        BufferedImage finalImage;
        Graphics graphics;
        int offset;
        int delay;
        List<BufferedImage> images;
        ByteArrayOutputStream baos;
        InputStream is;
        BufferedImage from;
        int i;
        IIOMetadataNode root;
        IIOMetadataNode gce;
        NodeList children;
        int delay2;
        String disposal;
        ColorModel model;
        boolean alpha;
        WritableRaster raster;
        Node nodeItem;
        NamedNodeMap map;
        int x;
        int y;
        int nodeIndex;
        ColorModel model2;
        boolean alpha2;
        WritableRaster raster2;
        BufferedImage copy;
        Block_18_Outer:Label_0336_Outer:Label_0778_Outer:Block_19_Outer:
        while (true) {
            Label_0310: {
                try {
                    image = reader.read(frameIndex);
                    break Label_0310;
                }
                catch (IndexOutOfBoundsException io) {
                    reader.dispose();
                    framez = frames.toArray(new ImageFrame[frames.size()]);
                    totalWidth = 0;
                    maxHeight = framez[0].getHeight();
                    for (final ImageFrame frame : framez) {
                        totalWidth += frame.getWidth();
                        if (frame.getHeight() > maxHeight) {
                            maxHeight = frame.getHeight();
                        }
                    }
                    finalImage = new BufferedImage(totalWidth, maxHeight, 1);
                    graphics = finalImage.getGraphics();
                    offset = 0;
                    delay = 0;
                    images = new ArrayList<BufferedImage>();
                    for (final ImageFrame frame2 : framez) {
                        delay += frame2.getDelay() * 10;
                        images.add(frame2.getImage());
                    }
                    delay /= framez.length;
                    graphics.dispose();
                    baos = new ByteArrayOutputStream();
                    ImageIO.write(finalImage, "gif", baos);
                    is = new ByteArrayInputStream(baos.toByteArray());
                    return new GifImage(images, delay);
                    while (true) {
                        Label_0778:Block_16_Outer:Label_0801_Outer:
                        while (true) {
                            Label_0739: {
                                while (true) {
                                Label_0801:
                                    while (true) {
                                        Block_13: {
                                            while (true) {
                                                Label_0503: {
                                                    while (true) {
                                                    Label_0322:
                                                        while (true) {
                                                            from = null;
                                                            i = frameIndex - 1;
                                                            break Label_0778;
                                                            iftrue(Label_0336:)(width != -1 && height != -1);
                                                            break Label_0322;
                                                            root = (IIOMetadataNode)reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
                                                            gce = (IIOMetadataNode)root.getElementsByTagName("GraphicControlExtension").item(0);
                                                            children = root.getChildNodes();
                                                            delay2 = Integer.valueOf(gce.getAttribute("delayTime"));
                                                            disposal = gce.getAttribute("disposalMethod");
                                                            iftrue(Label_0494:)(master != null);
                                                            break Block_13;
                                                            Label_0594: {
                                                                while (true) {
                                                                    model = from.getColorModel();
                                                                    alpha = from.isAlphaPremultiplied();
                                                                    raster = from.copyData(null);
                                                                    master = new BufferedImage(model, raster, alpha, null);
                                                                    break Label_0778;
                                                                    Label_0713: {
                                                                        iftrue(Label_0778:)(!disposal.equals("restoreToBackgroundColor") || backgroundColor == null || (hasBackround && frameIndex <= 1));
                                                                    }
                                                                    break Label_0739;
                                                                    while (true) {
                                                                        map = nodeItem.getAttributes();
                                                                        x = Integer.valueOf(map.getNamedItem("imageLeftPosition").getNodeValue());
                                                                        y = Integer.valueOf(map.getNamedItem("imageTopPosition").getNodeValue());
                                                                        break Label_0594;
                                                                        nodeItem = children.item(nodeIndex);
                                                                        iftrue(Label_0594:)(!nodeItem.getNodeName().equals("ImageDescriptor"));
                                                                        continue Block_16_Outer;
                                                                    }
                                                                    model2 = master.getColorModel();
                                                                    alpha2 = master.isAlphaPremultiplied();
                                                                    raster2 = master.copyData(null);
                                                                    copy = new BufferedImage(model2, raster2, alpha2, null);
                                                                    frames.add(new ImageFrame(copy, delay2, disposal, image.getWidth(), image.getHeight()));
                                                                    master.flush();
                                                                    ++frameIndex;
                                                                    continue Block_18_Outer;
                                                                    from = frames.get(i).getImage();
                                                                    continue Label_0778_Outer;
                                                                }
                                                                master.createGraphics().drawImage(image, x, y, null);
                                                                lastx = x;
                                                                lasty = y;
                                                                continue Label_0801;
                                                                Label_0666: {
                                                                    --i;
                                                                }
                                                                break Label_0778;
                                                            }
                                                            ++nodeIndex;
                                                            break Label_0503;
                                                            Label_0600: {
                                                                iftrue(Label_0713:)(!disposal.equals("restoreToPrevious"));
                                                            }
                                                            continue Label_0336_Outer;
                                                        }
                                                        width = image.getWidth();
                                                        height = image.getHeight();
                                                        continue Label_0778_Outer;
                                                    }
                                                    Label_0494: {
                                                        x = 0;
                                                    }
                                                    y = 0;
                                                    nodeIndex = 0;
                                                }
                                                iftrue(Label_0600:)(nodeIndex >= children.getLength());
                                                continue Label_0801_Outer;
                                            }
                                        }
                                        master = new BufferedImage(width, height, 2);
                                        master.createGraphics().setColor(backgroundColor);
                                        master.createGraphics().fillRect(0, 0, master.getWidth(), master.getHeight());
                                        hasBackround = (image.getWidth() == width && image.getHeight() == height);
                                        master.createGraphics().drawImage(image, 0, 0, null);
                                        continue Label_0801;
                                    }
                                    iftrue(Label_0666:)(frames.get(i).getDisposal().equals("restoreToPrevious") && frameIndex != 0);
                                    continue Block_19_Outer;
                                }
                            }
                            master.createGraphics().fillRect(lastx, lasty, frames.get(frameIndex - 1).getWidth(), frames.get(frameIndex - 1).getHeight());
                            continue Label_0778;
                        }
                        iftrue(Label_0672:)(i < 0);
                        continue;
                    }
                }
            }
            break;
        }
    }
}
