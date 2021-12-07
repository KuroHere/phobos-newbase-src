//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.holeesp;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.thread.holes.*;
import me.earth.earthhack.api.setting.*;
import java.awt.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.render.*;
import java.util.*;
import me.earth.earthhack.impl.util.math.rotation.*;

public class HoleESP extends Module implements HoleObserver
{
    protected final Setting<Float> range;
    protected final Setting<Integer> holes;
    protected final Setting<Integer> safeHole;
    protected final Setting<Integer> wide;
    protected final Setting<Integer> big;
    protected final Setting<Boolean> fov;
    protected final Setting<Boolean> own;
    protected final Setting<Boolean> fade;
    protected final Setting<Float> fadeRange;
    protected final Setting<Float> minFade;
    protected final Setting<Double> alphaFactor;
    protected final Setting<Float> height;
    protected final Setting<Float> unsafeHeight;
    protected final Setting<Float> wideHeight;
    protected final Setting<Float> bigHeight;
    protected final Setting<Color> unsafeColor;
    protected final Setting<Color> safeColor;
    protected final Setting<Color> wideColor;
    protected final Setting<Color> bigColor;
    private final BlockPos.MutableBlockPos mPos;
    
    public HoleESP() {
        super("HoleESP", Category.Render);
        this.range = this.register(new NumberSetting("Range", 6.0f, 0.0f, 100.0f));
        this.holes = this.register(new NumberSetting("Holes", 10, 0, 1000));
        this.safeHole = this.register(new NumberSetting("S-Holes", 10, 0, 1000));
        this.wide = this.register(new NumberSetting("2x1-Holes", 1, 0, 1000));
        this.big = this.register(new NumberSetting("2x2-Holes", 1, 0, 1000));
        this.fov = this.register(new BooleanSetting("Fov", true));
        this.own = this.register(new BooleanSetting("Own", false));
        this.fade = this.register(new BooleanSetting("Fade", false));
        this.fadeRange = this.register(new NumberSetting("Fade-Range", 4.0f, 0.0f, 100.0f));
        this.minFade = this.register(new NumberSetting("Min-Fade", 3.0f, 0.0f, 100.0f));
        this.alphaFactor = this.register(new NumberSetting("AlphaFactor", 0.3, 0.0, 1.0));
        this.height = this.register(new NumberSetting("SafeHeight", 1.0f, -1.0f, 1.0f));
        this.unsafeHeight = this.register(new NumberSetting("UnsafeHeight", 1.0f, -1.0f, 1.0f));
        this.wideHeight = this.register(new NumberSetting("2x1-Height", 0.0f, -1.0f, 1.0f));
        this.bigHeight = this.register(new NumberSetting("2x2-Height", 0.0f, -1.0f, 1.0f));
        this.unsafeColor = this.register(new ColorSetting("UnsafeColor", Color.RED));
        this.safeColor = this.register(new ColorSetting("SafeColor", Color.GREEN));
        this.wideColor = this.register(new ColorSetting("2x1-Color", new Color(90, 9, 255)));
        this.bigColor = this.register(new ColorSetting("2x2-Color", new Color(0, 80, 255)));
        this.mPos = new BlockPos.MutableBlockPos();
        this.listeners.add(new ListenerRender(this));
        this.setData(new HoleESPData(this));
    }
    
    public void onLoad() {
        if (this.isEnabled()) {
            Managers.HOLES.register(this);
        }
    }
    
    public void onEnable() {
        Managers.HOLES.register(this);
    }
    
    public void onDisable() {
        Managers.HOLES.unregister(this);
    }
    
    protected void onRender3D() {
        this.renderList(Managers.HOLES.getUnsafe(), this.unsafeColor.getValue(), this.unsafeHeight.getValue(), this.holes.getValue());
        this.renderList(Managers.HOLES.getSafe(), this.safeColor.getValue(), this.height.getValue(), this.safeHole.getValue());
        this.renderList(Managers.HOLES.getLongHoles(), this.wideColor.getValue(), this.wideHeight.getValue(), this.wide.getValue());
        final BlockPos playerPos = new BlockPos((Entity)HoleESP.mc.player);
        if (this.big.getValue() != 0 && !Managers.HOLES.getBigHoles().isEmpty()) {
            int i = 1;
            for (final BlockPos pos : Managers.HOLES.getBigHoles()) {
                if (i > this.big.getValue()) {
                    return;
                }
                if (!this.checkPos(pos, playerPos)) {
                    continue;
                }
                final Color bC = this.bigColor.getValue();
                final float bH = this.bigHeight.getValue();
                if (this.fade.getValue()) {
                    final double distance = HoleESP.mc.player.getDistanceSq((double)(pos.getX() + 1), (double)pos.getY(), (double)(pos.getZ() + 1));
                    final double alpha = (MathUtil.square(this.fadeRange.getValue()) + MathUtil.square(this.minFade.getValue()) - distance) / MathUtil.square(this.fadeRange.getValue());
                    if (alpha > 0.0 && alpha < 1.0) {
                        final int alphaInt = MathUtil.clamp((int)(alpha * 255.0), 0, 255);
                        final Color bC2 = new Color(bC.getRed(), bC.getGreen(), bC.getBlue(), alphaInt);
                        final int boxInt = (int)(alphaInt * this.alphaFactor.getValue());
                        RenderUtil.renderBox(pos, bC2, bH, boxInt);
                        this.mPos.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
                        RenderUtil.renderBox((BlockPos)this.mPos, bC2, bH, boxInt);
                        this.mPos.setPos(pos.getX() + 1, pos.getY(), pos.getZ());
                        RenderUtil.renderBox((BlockPos)this.mPos, bC2, bH, boxInt);
                        this.mPos.setPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
                        RenderUtil.renderBox((BlockPos)this.mPos, bC2, bH, boxInt);
                    }
                    else if (alpha < 0.0) {
                        continue;
                    }
                }
                RenderUtil.renderBox(pos, bC, bH);
                this.mPos.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
                RenderUtil.renderBox((BlockPos)this.mPos, bC, bH);
                this.mPos.setPos(pos.getX() + 1, pos.getY(), pos.getZ());
                RenderUtil.renderBox((BlockPos)this.mPos, bC, bH);
                this.mPos.setPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
                RenderUtil.renderBox((BlockPos)this.mPos, bC, bH);
                ++i;
            }
        }
    }
    
    private void renderList(final List<BlockPos> positions, final Color color, final float height, final int max) {
        final BlockPos playerPos = new BlockPos((Entity)HoleESP.mc.player);
        if (max != 0 && !positions.isEmpty()) {
            int i = 1;
            for (final BlockPos pos : positions) {
                if (i > max) {
                    return;
                }
                if (!this.checkPos(pos, playerPos)) {
                    continue;
                }
                if (this.fade.getValue()) {
                    final double alpha = (MathUtil.square(this.fadeRange.getValue()) + MathUtil.square(this.minFade.getValue()) - HoleESP.mc.player.getDistanceSq(pos)) / MathUtil.square(this.fadeRange.getValue());
                    if (alpha > 0.0 && alpha < 1.0) {
                        final int alphaInt = MathUtil.clamp((int)(alpha * 255.0), 0, 255);
                        final Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), alphaInt);
                        RenderUtil.renderBox(pos, color2, height, (int)(alphaInt * this.alphaFactor.getValue()));
                    }
                    else {
                        if (alpha < 1.0) {
                            continue;
                        }
                        RenderUtil.renderBox(pos, color, height);
                    }
                }
                else {
                    RenderUtil.renderBox(pos, color, height);
                    ++i;
                }
            }
        }
    }
    
    private boolean checkPos(final BlockPos pos, final BlockPos playerPos) {
        return (!this.fov.getValue() || RotationUtil.inFov(pos)) && (this.own.getValue() || !pos.equals((Object)playerPos));
    }
    
    @Override
    public double getRange() {
        return this.range.getValue();
    }
    
    @Override
    public int getSafeHoles() {
        return this.safeHole.getValue();
    }
    
    @Override
    public int getUnsafeHoles() {
        return this.holes.getValue();
    }
    
    @Override
    public int get2x1Holes() {
        return this.wide.getValue();
    }
    
    @Override
    public int get2x2Holes() {
        return this.big.getValue();
    }
}
