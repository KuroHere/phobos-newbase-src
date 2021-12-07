// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

public class CubicBezierEasing implements Animator.Interpolator
{
    public static final CubicBezierEasing STANDARD_EASING;
    public static final CubicBezierEasing EASE;
    public static final CubicBezierEasing EASE_IN;
    public static final CubicBezierEasing EASE_IN_OUT;
    public static final CubicBezierEasing EASE_OUT;
    private final float x1;
    private final float y1;
    private final float x2;
    private final float y2;
    
    public CubicBezierEasing(final float x1, final float y1, final float x2, final float y2) {
        if (x1 < 0.0f || x1 > 1.0f || y1 < 0.0f || y1 > 1.0f || x2 < 0.0f || x2 > 1.0f || y2 < 0.0f || y2 > 1.0f) {
            throw new IllegalArgumentException("control points must be in range [0, 1]");
        }
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    @Override
    public float interpolate(final float fraction) {
        if (fraction <= 0.0f || fraction >= 1.0f) {
            return fraction;
        }
        float low = 0.0f;
        float high = 1.0f;
        float mid;
        while (true) {
            mid = (low + high) / 2.0f;
            final float estimate = cubicBezier(mid, this.x1, this.x2);
            if (Math.abs(fraction - estimate) < 5.0E-4f) {
                break;
            }
            if (estimate < fraction) {
                low = mid;
            }
            else {
                high = mid;
            }
        }
        return cubicBezier(mid, this.y1, this.y2);
    }
    
    private static float cubicBezier(final float t, final float xy1, final float xy2) {
        final float invT = 1.0f - t;
        final float b1 = 3.0f * t * (invT * invT);
        final float b2 = 3.0f * (t * t) * invT;
        final float b3 = t * t * t;
        return b1 * xy1 + b2 * xy2 + b3;
    }
    
    static {
        STANDARD_EASING = new CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f);
        EASE = new CubicBezierEasing(0.25f, 0.1f, 0.25f, 1.0f);
        EASE_IN = new CubicBezierEasing(0.42f, 0.0f, 1.0f, 1.0f);
        EASE_IN_OUT = new CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f);
        EASE_OUT = new CubicBezierEasing(0.0f, 0.0f, 0.58f, 1.0f);
    }
}
