// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import java.awt.*;
import javax.swing.*;
import com.formdev.flatlaf.util.*;
import java.awt.image.*;
import java.util.*;

public class FlatTitlePaneIcon extends ScaledImageIcon
{
    private final List<Image> images;
    
    public FlatTitlePaneIcon(final List<Image> images, final Dimension size) {
        super(null, size.width, size.height);
        this.images = images;
    }
    
    @Override
    protected Image getResolutionVariant(final int destImageWidth, final int destImageHeight) {
        final List<Image> allImages = new ArrayList<Image>();
        for (final Image image : this.images) {
            if (MultiResolutionImageSupport.isMultiResolutionImage(image)) {
                allImages.add(MultiResolutionImageSupport.getResolutionVariant(image, destImageWidth, destImageHeight));
            }
            else {
                allImages.add(image);
            }
        }
        if (allImages.size() == 1) {
            return allImages.get(0);
        }
        allImages.sort((image1, image2) -> image1.getWidth(null) - image2.getWidth(null));
        for (final Image image : allImages) {
            if (destImageWidth <= image.getWidth(null) && destImageHeight <= image.getHeight(null)) {
                return image;
            }
        }
        return allImages.get(allImages.size() - 1);
    }
}
