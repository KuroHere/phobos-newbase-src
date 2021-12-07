// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.norender;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.inventory.*;

public class NoRender extends Module
{
    protected final Setting<Boolean> fire;
    protected final Setting<Boolean> portal;
    protected final Setting<Boolean> pumpkin;
    protected final Setting<Boolean> totemPops;
    protected final Setting<Boolean> nausea;
    protected final Setting<Boolean> hurtCam;
    protected final Setting<Boolean> noWeather;
    protected final Setting<Boolean> barriers;
    protected final Setting<Boolean> skyLight;
    protected final Setting<Boolean> noFog;
    protected final Setting<Boolean> blocks;
    protected final Setting<Boolean> advancements;
    protected final Setting<Boolean> critParticles;
    protected final Setting<Boolean> dynamicFov;
    public final Setting<Boolean> boss;
    public final Setting<Boolean> explosions;
    public final Setting<Boolean> defaultBackGround;
    protected final Setting<Boolean> items;
    protected final Setting<Boolean> helmet;
    protected final Setting<Boolean> chestplate;
    protected final Setting<Boolean> leggings;
    protected final Setting<Boolean> boots;
    protected final Setting<Boolean> entities;
    protected final Set<Integer> ids;
    
    public NoRender() {
        super("NoRender", Category.Render);
        this.fire = this.register(new BooleanSetting("Fire", true));
        this.portal = this.register(new BooleanSetting("Portal", true));
        this.pumpkin = this.register(new BooleanSetting("Pumpkin", true));
        this.totemPops = this.register(new BooleanSetting("TotemPop", true));
        this.nausea = this.register(new BooleanSetting("Nausea", true));
        this.hurtCam = this.register(new BooleanSetting("HurtCam", true));
        this.noWeather = this.register(new BooleanSetting("Weather", true));
        this.barriers = this.register(new BooleanSetting("Barriers", false));
        this.skyLight = this.register(new BooleanSetting("SkyLight", true));
        this.noFog = this.register(new BooleanSetting("NoFog", true));
        this.blocks = this.register(new BooleanSetting("Blocks", true));
        this.advancements = this.register(new BooleanSetting("Advancements", false));
        this.critParticles = this.register(new BooleanSetting("CritParticles", false));
        this.dynamicFov = this.register(new BooleanSetting("DynamicFov", true));
        this.boss = this.register(new BooleanSetting("BossHealth", true));
        this.explosions = this.register(new BooleanSetting("Explosions", true));
        this.defaultBackGround = this.register(new BooleanSetting("DefaultGuiBackGround", false));
        this.items = this.register(new BooleanSetting("Items", false));
        this.helmet = this.register(new BooleanSetting("Helmet", false));
        this.chestplate = this.register(new BooleanSetting("Breastplate", false));
        this.leggings = this.register(new BooleanSetting("Leggings", false));
        this.boots = this.register(new BooleanSetting("Boots", false));
        this.entities = this.register(new BooleanSetting("Entities", false));
        this.ids = new HashSet<Integer>();
        this.listeners.add(new ListenerSuffocation(this));
        this.listeners.add(new ListenerAnimation(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerRenderEntities(this));
        this.setData(new NoRenderData(this));
    }
    
    public boolean noFire() {
        return this.isEnabled() && this.fire.getValue();
    }
    
    public boolean noTotems() {
        return this.isEnabled() && this.totemPops.getValue();
    }
    
    public boolean noHurtCam() {
        return this.isEnabled() && this.hurtCam.getValue();
    }
    
    public boolean noPortal() {
        return this.isEnabled() && this.portal.getValue();
    }
    
    public boolean noPumpkin() {
        return this.isEnabled() && this.pumpkin.getValue();
    }
    
    public boolean noNausea() {
        return this.isEnabled() && this.nausea.getValue();
    }
    
    public boolean noFog() {
        return this.isEnabled() && this.noFog.getValue();
    }
    
    public boolean noSkyLight() {
        return this.isEnabled() && this.skyLight.getValue();
    }
    
    public boolean noAdvancements() {
        return this.isEnabled() && this.advancements.getValue();
    }
    
    public boolean noWeather() {
        return this.isEnabled() && this.noWeather.getValue();
    }
    
    public boolean showBarriers() {
        return this.isEnabled() && this.barriers.getValue();
    }
    
    public boolean dynamicFov() {
        return this.isEnabled() && this.dynamicFov.getValue();
    }
    
    public boolean isValidArmorPiece(final EntityEquipmentSlot slot) {
        return !this.isEnabled() || ((slot != EntityEquipmentSlot.HEAD || !this.helmet.getValue()) && (slot != EntityEquipmentSlot.CHEST || !this.chestplate.getValue()) && (slot != EntityEquipmentSlot.LEGS || !this.leggings.getValue()) && (slot != EntityEquipmentSlot.FEET || !this.boots.getValue()));
    }
}
