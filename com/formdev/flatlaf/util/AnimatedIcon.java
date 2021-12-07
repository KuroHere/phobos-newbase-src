// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

import java.awt.*;
import javax.swing.*;

public interface AnimatedIcon extends Icon
{
    default void paintIcon(final Component c, final Graphics g, final int x, final int y) {
        AnimationSupport.paintIcon(this, c, g, x, y);
    }
    
    void paintIconAnimated(final Component p0, final Graphics p1, final int p2, final int p3, final float p4);
    
    float getValue(final Component p0);
    
    default boolean isAnimationEnabled() {
        return true;
    }
    
    default int getAnimationDuration() {
        return 150;
    }
    
    default int getAnimationResolution() {
        return 10;
    }
    
    default Animator.Interpolator getAnimationInterpolator() {
        return CubicBezierEasing.STANDARD_EASING;
    }
    
    default Object getClientPropertyKey() {
        return this.getClass();
    }
    
    public static class AnimationSupport
    {
        private float startValue;
        private float targetValue;
        private float animatedValue;
        private float fraction;
        private Animator animator;
        private int x;
        private int y;
        
        public static void paintIcon(final AnimatedIcon icon, final Component c, final Graphics g, final int x, final int y) {
            if (!isAnimationEnabled(icon, c)) {
                paintIconImpl(icon, c, g, x, y, null);
                return;
            }
            final JComponent jc = (JComponent)c;
            final Object key = icon.getClientPropertyKey();
            AnimationSupport as = (AnimationSupport)jc.getClientProperty(key);
            if (as == null) {
                final AnimationSupport animationSupport3;
                final AnimationSupport animationSupport2;
                final AnimationSupport animationSupport;
                as = (animationSupport = (animationSupport2 = (animationSupport3 = new AnimationSupport())));
                final float value2 = icon.getValue(c);
                animationSupport.animatedValue = value2;
                animationSupport2.targetValue = value2;
                animationSupport3.startValue = value2;
                as.x = x;
                as.y = y;
                jc.putClientProperty(key, as);
            }
            else {
                final float value = icon.getValue(c);
                if (value != as.targetValue) {
                    if (as.animator == null) {
                        final AnimationSupport as2 = as;
                        as.animator = new Animator(icon.getAnimationDuration(), fraction -> {
                            if (!c.isDisplayable()) {
                                as2.animator.stop();
                                return;
                            }
                            else {
                                as2.animatedValue = as2.startValue + (as2.targetValue - as2.startValue) * fraction;
                                as2.fraction = fraction;
                                c.repaint(as2.x, as2.y, icon.getIconWidth(), icon.getIconHeight());
                                return;
                            }
                        }, () -> {
                            final float targetValue = as2.targetValue;
                            as2.animatedValue = targetValue;
                            as2.startValue = targetValue;
                            as2.animator = null;
                            return;
                        });
                    }
                    if (as.animator.isRunning()) {
                        as.animator.cancel();
                        final int duration2 = (int)(icon.getAnimationDuration() * as.fraction);
                        if (duration2 > 0) {
                            as.animator.setDuration(duration2);
                        }
                        as.startValue = as.animatedValue;
                    }
                    else {
                        as.animator.setDuration(icon.getAnimationDuration());
                        as.animator.setResolution(icon.getAnimationResolution());
                        as.animator.setInterpolator(icon.getAnimationInterpolator());
                        as.animatedValue = as.startValue;
                    }
                    as.targetValue = value;
                    as.animator.start();
                }
                as.x = x;
                as.y = y;
            }
            paintIconImpl(icon, c, g, x, y, as);
        }
        
        private static void paintIconImpl(final AnimatedIcon icon, final Component c, final Graphics g, final int x, final int y, final AnimationSupport as) {
            final float value = (as != null) ? as.animatedValue : icon.getValue(c);
            icon.paintIconAnimated(c, g, x, y, value);
        }
        
        private static boolean isAnimationEnabled(final AnimatedIcon icon, final Component c) {
            return Animator.useAnimation() && icon.isAnimationEnabled() && c instanceof JComponent;
        }
        
        public static void saveIconLocation(final AnimatedIcon icon, final Component c, final int x, final int y) {
            if (!isAnimationEnabled(icon, c)) {
                return;
            }
            final AnimationSupport as = (AnimationSupport)((JComponent)c).getClientProperty(icon.getClientPropertyKey());
            if (as != null) {
                as.x = x;
                as.y = y;
            }
        }
    }
}
