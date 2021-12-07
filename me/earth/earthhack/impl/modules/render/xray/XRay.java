//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.xray;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.fullbright.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.vanilla.*;
import java.lang.reflect.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.modules.*;

public class XRay extends BlockAddingModule
{
    private static final ModuleCache<Fullbright> FULL_BRIGHT;
    protected final Setting<XrayMode> mode;
    protected final Setting<Boolean> soft;
    protected final Setting<Integer> opacity;
    protected boolean lightPipeLine;
    
    public XRay() {
        super("XRay", Category.Render, s -> "Black/Whitelist " + s.getName() + " from being displayed.");
        this.mode = this.register(new EnumSetting("Mode", XrayMode.Simple));
        this.soft = this.register(new BooleanSetting("Soft-Reload", false));
        this.opacity = this.register(new NumberSetting("Opacity", 120, 0, 255));
        this.listeners.add(new ListenerBlockLayer(this));
        this.listeners.add(new ListenerTick(this));
        this.mode.addObserver(event -> {
            if (this.isEnabled() && !event.isCancelled()) {
                this.toggle();
                Scheduler.getInstance().schedule(this::toggle);
            }
            return;
        });
        this.listType.addObserver(event -> {
            if (this.isEnabled()) {
                this.loadRenderers();
            }
            return;
        });
        this.setData(new XRayData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
    
    public void onEnable() {
        if (this.mode.getValue() == XrayMode.Opacity) {
            if (Environment.hasForge()) {
                try {
                    final Field field = Class.forName("net.minecraftforge.common.ForgeModContainer", true, this.getClass().getClassLoader()).getDeclaredField("forgeLightPipelineEnabled");
                    final boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    this.lightPipeLine = field.getBoolean(null);
                    field.set(null, false);
                    field.setAccessible(accessible);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!XRay.FULL_BRIGHT.isEnabled()) {
                XRay.mc.gameSettings.gammaSetting = 1.0f;
            }
            XRay.mc.renderChunksMany = false;
        }
        Scheduler.getInstance().schedule(this::loadRenderers);
    }
    
    public void onDisable() {
        if (this.mode.getValue() == XrayMode.Opacity) {
            if (Environment.hasForge()) {
                try {
                    final Field field = Class.forName("net.minecraftforge.common.ForgeModContainer", true, this.getClass().getClassLoader()).getDeclaredField("forgeLightPipelineEnabled");
                    final boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(null, this.lightPipeLine);
                    field.setAccessible(accessible);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!XRay.FULL_BRIGHT.isEnabled()) {
                XRay.mc.gameSettings.gammaSetting = 1.0f;
            }
            XRay.mc.renderChunksMany = true;
        }
        this.loadRenderers();
    }
    
    @Override
    protected SimpleRemovingSetting addSetting(final String string) {
        final SimpleRemovingSetting s = super.addSetting(string);
        if (s != null) {
            this.loadRenderers();
        }
        return s;
    }
    
    @Override
    public Setting<?> unregister(final Setting<?> setting) {
        final Setting<?> s = super.unregister(setting);
        if (s != null) {
            this.loadRenderers();
        }
        return s;
    }
    
    public boolean shouldRender(final Block block) {
        return this.isValid(block.getLocalizedName());
    }
    
    public XrayMode getMode() {
        return this.mode.getValue();
    }
    
    public int getOpacity() {
        return this.opacity.getValue();
    }
    
    public void loadRenderers() {
        if (XRay.mc.world != null && XRay.mc.player != null && XRay.mc.renderGlobal != null && XRay.mc.gameSettings != null) {
            WorldRenderUtil.reload(this.soft.getValue());
        }
    }
    
    static {
        FULL_BRIGHT = Caches.getModule(Fullbright.class);
    }
}
