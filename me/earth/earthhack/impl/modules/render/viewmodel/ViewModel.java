// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.viewmodel;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.util.*;

public class ViewModel extends Module
{
    public static final float[] DEFAULT_SCALE;
    public static final float[] DEFAULT_TRANSLATION;
    protected final Setting<Float> offX;
    protected final Setting<Float> offY;
    protected final Setting<Float> mainX;
    protected final Setting<Float> mainY;
    protected final Setting<Float> xScale;
    protected final Setting<Float> yScale;
    protected final Setting<Float> zScale;
    protected final Setting<Float> angleTranslate;
    protected final Setting<Float> xTranslate;
    protected final Setting<Float> yTranslate;
    protected final Setting<Float> zTranslate;
    
    public ViewModel() {
        super("ViewModel", Category.Render);
        this.offX = this.register(new NumberSetting("OffHand-X", 0.0f, -10.0f, 10.0f));
        this.offY = this.register(new NumberSetting("OffHand-Y", 0.0f, -10.0f, 10.0f));
        this.mainX = this.register(new NumberSetting("MainHand-X", 0.0f, -10.0f, 10.0f));
        this.mainY = this.register(new NumberSetting("MainHand-Y", 0.0f, -10.0f, 10.0f));
        this.xScale = this.register(new NumberSetting("X-Scale", 1.0f, 0.0f, 10.0f));
        this.yScale = this.register(new NumberSetting("Y-Scale", 1.0f, 0.0f, 10.0f));
        this.zScale = this.register(new NumberSetting("Z-Scale", 1.0f, 0.0f, 10.0f));
        this.angleTranslate = this.register(new NumberSetting("Angle-Translate", 0.0f, -360.0f, 360.0f));
        this.xTranslate = this.register(new NumberSetting("X-Translate", 1.0f, -10.0f, 10.0f));
        this.yTranslate = this.register(new NumberSetting("Y-Translate", 1.0f, -10.0f, 10.0f));
        this.zTranslate = this.register(new NumberSetting("Z-Translate", 1.0f, -10.0f, 10.0f));
        this.setData(new ViewModelData(this));
    }
    
    public float getX(final EnumHand hand) {
        if (!this.isEnabled()) {
            return 0.0f;
        }
        return (hand == EnumHand.MAIN_HAND) ? this.mainX.getValue() : ((float)this.offX.getValue());
    }
    
    public float getY(final EnumHand hand) {
        if (!this.isEnabled()) {
            return 0.0f;
        }
        return (hand == EnumHand.MAIN_HAND) ? this.mainY.getValue() : ((float)this.offY.getValue());
    }
    
    public float[] getScale() {
        if (!this.isEnabled()) {
            return ViewModel.DEFAULT_SCALE;
        }
        return new float[] { this.xScale.getValue(), this.yScale.getValue(), this.zScale.getValue() };
    }
    
    public float[] getTranslation() {
        if (!this.isEnabled()) {
            return ViewModel.DEFAULT_TRANSLATION;
        }
        return new float[] { this.angleTranslate.getValue(), this.xTranslate.getValue(), this.yTranslate.getValue(), this.zTranslate.getValue() };
    }
    
    static {
        DEFAULT_SCALE = new float[] { 1.0f, 1.0f, 1.0f };
        DEFAULT_TRANSLATION = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
    }
}
