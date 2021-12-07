//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.hud;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.render.*;
import me.earth.earthhack.api.setting.*;
import java.awt.*;
import me.earth.earthhack.impl.modules.client.hud.modes.*;
import me.earth.earthhack.impl.modules.client.hud.arraylist.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.hud.util.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.potion.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.client.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.util.render.*;

public class HUD extends Module
{
    public static final TextRenderer RENDERER;
    protected final Setting<RenderMode> renderMode;
    protected final Setting<HudRainbow> colorMode;
    protected final Setting<Color> color;
    protected final Setting<Boolean> logo;
    protected final Setting<Boolean> coordinates;
    protected final Setting<Boolean> armor;
    protected final Setting<Modules> renderModules;
    protected final Setting<Potions> potions;
    protected final Setting<PotionColor> potionColor;
    protected final Setting<Boolean> shadow;
    protected final Setting<Boolean> ping;
    protected final Setting<Boolean> speed;
    protected final Setting<Boolean> fps;
    protected final Setting<Boolean> tps;
    protected final Setting<Boolean> animations;
    protected final List<Map.Entry<String, Module>> modules;
    protected final Map<Module, ArrayEntry> arrayEntries;
    protected final Map<Module, ArrayEntry> removeEntries;
    protected ScaledResolution resolution;
    protected int width;
    protected int height;
    protected float animationY;
    private final Map<Potion, Color> potionColorMap;
    
    public HUD() {
        super("HUD", Category.Client);
        this.renderMode = this.register(new EnumSetting("RenderMode", RenderMode.Normal));
        this.colorMode = this.register(new EnumSetting("Rainbow", HudRainbow.None));
        this.color = this.register(new ColorSetting("Color", Color.WHITE));
        this.logo = this.register(new BooleanSetting("Logo", true));
        this.coordinates = this.register(new BooleanSetting("Coordinates", true));
        this.armor = this.register(new BooleanSetting("Armor", true));
        this.renderModules = this.register(new EnumSetting("Modules", Modules.Length));
        this.potions = this.register(new EnumSetting("Potions", Potions.Move));
        this.potionColor = this.register(new EnumSetting("PotionColor", PotionColor.Normal));
        this.shadow = this.register(new BooleanSetting("Shadow", true));
        this.ping = this.register(new BooleanSetting("Ping", false));
        this.speed = this.register(new BooleanSetting("Speed", false));
        this.fps = this.register(new BooleanSetting("FPS", false));
        this.tps = this.register(new BooleanSetting("TPS", false));
        this.animations = this.register(new BooleanSetting("Animations", true));
        this.modules = new ArrayList<Map.Entry<String, Module>>();
        this.arrayEntries = new HashMap<Module, ArrayEntry>();
        this.removeEntries = new HashMap<Module, ArrayEntry>();
        this.resolution = new ScaledResolution(HUD.mc);
        this.animationY = 0.0f;
        this.potionColorMap = new HashMap<Potion, Color>();
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerPostKey(this));
        this.setData(new HUDData(this));
        this.potionColorMap.put(MobEffects.SPEED, new Color(124, 175, 198));
        this.potionColorMap.put(MobEffects.SLOWNESS, new Color(90, 108, 129));
        this.potionColorMap.put(MobEffects.HASTE, new Color(217, 192, 67));
        this.potionColorMap.put(MobEffects.MINING_FATIGUE, new Color(74, 66, 23));
        this.potionColorMap.put(MobEffects.STRENGTH, new Color(147, 36, 35));
        this.potionColorMap.put(MobEffects.INSTANT_HEALTH, new Color(67, 10, 9));
        this.potionColorMap.put(MobEffects.INSTANT_DAMAGE, new Color(67, 10, 9));
        this.potionColorMap.put(MobEffects.JUMP_BOOST, new Color(34, 255, 76));
        this.potionColorMap.put(MobEffects.NAUSEA, new Color(85, 29, 74));
        this.potionColorMap.put(MobEffects.REGENERATION, new Color(205, 92, 171));
        this.potionColorMap.put(MobEffects.RESISTANCE, new Color(153, 69, 58));
        this.potionColorMap.put(MobEffects.FIRE_RESISTANCE, new Color(228, 154, 58));
        this.potionColorMap.put(MobEffects.WATER_BREATHING, new Color(46, 82, 153));
        this.potionColorMap.put(MobEffects.INVISIBILITY, new Color(127, 131, 146));
        this.potionColorMap.put(MobEffects.BLINDNESS, new Color(31, 31, 35));
        this.potionColorMap.put(MobEffects.NIGHT_VISION, new Color(31, 31, 161));
        this.potionColorMap.put(MobEffects.HUNGER, new Color(88, 118, 83));
        this.potionColorMap.put(MobEffects.WEAKNESS, new Color(72, 77, 72));
        this.potionColorMap.put(MobEffects.POISON, new Color(78, 147, 49));
        this.potionColorMap.put(MobEffects.WITHER, new Color(53, 42, 39));
        this.potionColorMap.put(MobEffects.HEALTH_BOOST, new Color(248, 125, 35));
        this.potionColorMap.put(MobEffects.ABSORPTION, new Color(37, 82, 165));
        this.potionColorMap.put(MobEffects.SATURATION, new Color(248, 36, 35));
        this.potionColorMap.put(MobEffects.GLOWING, new Color(148, 160, 97));
        this.potionColorMap.put(MobEffects.LEVITATION, new Color(206, 255, 255));
        this.potionColorMap.put(MobEffects.LUCK, new Color(51, 153, 0));
        this.potionColorMap.put(MobEffects.UNLUCK, new Color(192, 164, 77));
    }
    
    protected void renderLogo() {
        if (this.logo.getValue()) {
            this.renderText("3arthh4ck - 1.3.1", 2.0f, 2.0f);
        }
    }
    
    protected void renderModules() {
        int offset = 0;
        if (this.potions.getValue() == Potions.Text) {
            final ArrayList<Potion> sorted = new ArrayList<Potion>();
            final Iterator iterator = Potion.REGISTRY.iterator();
            Potion potion = null;
            while (iterator.hasNext()) {
                potion = (Potion)iterator.next();
                if (potion != null && HUD.mc.player.isPotionActive(potion)) {
                    sorted.add(potion);
                }
            }
            sorted.sort(Comparator.comparingDouble(potion -> {
                final TextRenderer renderer = HUD.RENDERER;
                new StringBuilder().append(I18n.format(potion.getName(), new Object[0]));
                String string;
                if (HUD.mc.player.getActivePotionEffect(potion).getAmplifier() > 0) {
                    string = " " + (HUD.mc.player.getActivePotionEffect(potion).getAmplifier() + 1);
                }
                else {
                    string = "";
                }
                final StringBuilder sb;
                return (double)(-renderer.getStringWidth(sb.append(string).append(ChatFormatting.GRAY).append(" ").append(Potion.getPotionDurationString((PotionEffect)Objects.requireNonNull(HUD.mc.player.getActivePotionEffect(potion)), 1.0f)).toString()));
            }));
            final Iterator<Potion> iterator2 = sorted.iterator();
            while (iterator2.hasNext()) {
                potion = iterator2.next();
                final PotionEffect effect = HUD.mc.player.getActivePotionEffect(potion);
                if (effect != null) {
                    final String label = I18n.format(potion.getName(), new Object[0]) + ((effect.getAmplifier() > 0) ? (" " + (effect.getAmplifier() + 1)) : "") + ChatFormatting.GRAY + " " + Potion.getPotionDurationString(effect, 1.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    final int x = this.width - 2 - HUD.RENDERER.getStringWidth(label);
                    this.renderPotionText(label, (float)x, this.height - 2 - HUD.RENDERER.getStringHeight() - offset - this.animationY, effect.getPotion());
                    offset += (int)(HUD.RENDERER.getStringHeight() + 3.0f);
                }
            }
        }
        if (this.speed.getValue()) {
            final String text = "Speed §7" + MathUtil.round(Managers.SPEED.getSpeed(), 2) + " km/h";
            this.renderText(text, (float)(this.width - 2 - HUD.RENDERER.getStringWidth(text)), this.height - 2 - HUD.RENDERER.getStringHeight() - offset - this.animationY);
            offset += (int)(HUD.RENDERER.getStringHeight() + 3.0f);
        }
        if (this.tps.getValue()) {
            final String tps = "TPS §7" + MathUtil.round(Managers.TPS.getTps(), 2);
            this.renderText(tps, (float)(this.width - 2 - HUD.RENDERER.getStringWidth(tps)), this.height - 2 - HUD.RENDERER.getStringHeight() - offset - this.animationY);
            offset += (int)(HUD.RENDERER.getStringHeight() + 3.0f);
        }
        if (this.fps.getValue()) {
            final String fps = "FPS §7" + Minecraft.getDebugFPS();
            this.renderText(fps, (float)(this.width - 2 - HUD.RENDERER.getStringWidth(fps)), this.height - 2 - HUD.RENDERER.getStringHeight() - offset - this.animationY);
            offset += (int)(HUD.RENDERER.getStringHeight() + 3.0f);
        }
        if (this.ping.getValue()) {
            final String ping = "Ping §7" + ServerUtil.getPing();
            this.renderText(ping, (float)(this.width - 2 - HUD.RENDERER.getStringWidth(ping)), this.height - 2 - HUD.RENDERER.getStringHeight() - offset - this.animationY);
        }
        if (this.coordinates.getValue()) {
            final long x2 = Math.round(HUD.mc.player.posX);
            final long y = Math.round(HUD.mc.player.posY);
            final long z = Math.round(HUD.mc.player.posZ);
            final String coords = (HUD.mc.player.dimension == -1) ? String.format("§7%s §f[%s]§8, §7%s§8, §7%s §f[%s]", x2, x2 * 8L, y, z, z * 8L) : ((HUD.mc.player.dimension == 0) ? String.format("§f%s §7[%s]§8, §f%s§8, §f%s §7[%s]", x2, x2 / 8L, y, z, z / 8L) : String.format("§f%s§8, §f%s§8, §f%s", x2, y, z));
            this.renderText(coords, 2.0f, this.height - 2 - HUD.RENDERER.getStringHeight() - this.animationY);
            final String dir = RotationUtil.getDirection4D(false);
            this.renderText(dir, 2.0f, this.height - 3 - HUD.RENDERER.getStringHeight() * 2.0f - this.animationY);
        }
        this.renderArmor();
        if (this.renderModules.getValue() != Modules.None) {
            final boolean move = this.potions.getValue() == Potions.Move && !HUD.mc.player.getActivePotionEffects().isEmpty();
            int j = move ? 2 : 0;
            final int o = move ? 5 : 2;
            if (this.animations.getValue()) {
                for (final Map.Entry<String, Module> module : this.modules) {
                    if (this.isArrayMember(module.getValue())) {
                        continue;
                    }
                    this.getArrayEntries().put(module.getValue(), new ArrayEntry(module.getValue()));
                }
                Map<Module, ArrayEntry> arrayEntriesSorted;
                if (this.renderModules.getValue() == Modules.Length) {
                    arrayEntriesSorted = this.getArrayEntries().entrySet().stream().sorted(Comparator.comparingDouble(entry -> Managers.TEXT.getStringWidth(ModuleUtil.getHudName(entry.getKey())) * -1)).collect((Collector<? super Object, ?, Map<Module, ArrayEntry>>)Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue, (e1, e2) -> e1, (Supplier<R>)LinkedHashMap::new));
                }
                else {
                    arrayEntriesSorted = this.getArrayEntries().entrySet().stream().sorted(Comparator.comparing(entry -> ModuleUtil.getHudName(entry.getKey()))).collect((Collector<? super Object, ?, Map<Module, ArrayEntry>>)Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue, (e1, e2) -> e1, (Supplier<R>)LinkedHashMap::new));
                }
                for (final ArrayEntry arrayEntry : arrayEntriesSorted.values()) {
                    arrayEntry.drawArrayEntry((float)(this.width - 2), (float)(o + j * 10));
                    ++j;
                }
                this.getRemoveEntries().forEach((key, value) -> {
                    final ArrayEntry arrayEntry6 = this.getArrayEntries().remove(key);
                    return;
                });
                this.getRemoveEntries().clear();
            }
            else {
                for (final Map.Entry<String, Module> module : this.modules) {
                    this.renderText(module.getKey(), (float)(this.width - 2 - HUD.RENDERER.getStringWidth(module.getKey())), (float)(o + j * 10));
                    ++j;
                }
            }
        }
    }
    
    private void renderArmor() {
        if (this.armor.getValue()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int x = 15;
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 3; i >= 0; --i) {
                final ItemStack stack = (ItemStack)HUD.mc.player.inventory.armorInventory.get(i);
                if (!stack.func_190926_b()) {
                    int y;
                    if (HUD.mc.player.isInsideOfMaterial(Material.WATER) && HUD.mc.player.getAir() > 0 && !HUD.mc.player.capabilities.isCreativeMode) {
                        y = 65;
                    }
                    else if (HUD.mc.player.getRidingEntity() != null && !HUD.mc.player.capabilities.isCreativeMode) {
                        if (HUD.mc.player.getRidingEntity() instanceof EntityLivingBase) {
                            final EntityLivingBase entity = (EntityLivingBase)HUD.mc.player.getRidingEntity();
                            y = (int)(45.0 + Math.ceil((entity.getMaxHealth() - 1.0f) / 20.0f) * 10.0);
                        }
                        else {
                            y = 45;
                        }
                    }
                    else if (HUD.mc.player.capabilities.isCreativeMode) {
                        y = (HUD.mc.player.isRidingHorse() ? 45 : 38);
                    }
                    else {
                        y = 55;
                    }
                    final float percent = DamageUtil.getPercent(stack) / 100.0f;
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(0.625f, 0.625f, 0.625f);
                    GlStateManager.disableDepth();
                    Managers.TEXT.drawStringWithShadow((int)(percent * 100.0f) + "%", ((this.width >> 1) + x + 1) * 1.6f, (this.height - y - 3) * 1.6f, ColorHelper.toColor(percent * 120.0f, 100.0f, 50.0f, 1.0f).getRGB());
                    GlStateManager.enableDepth();
                    GlStateManager.scale(1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    HUD.mc.getRenderItem().renderItemIntoGUI(stack, this.width / 2 + x, this.height - y);
                    HUD.mc.getRenderItem().renderItemOverlays(HUD.mc.fontRendererObj, stack, this.width / 2 + x, this.height - y);
                    GlStateManager.popMatrix();
                    x += 18;
                }
            }
            RenderHelper.disableStandardItemLighting();
        }
    }
    
    public void renderText(final String text, final float x, final float y) {
        final String colorCode = this.colorMode.getValue().getColor();
        HUD.RENDERER.drawStringWithShadow(colorCode + text, x, y, (this.colorMode.getValue() == HudRainbow.None) ? this.color.getValue().getRGB() : ((this.colorMode.getValue() == HudRainbow.Static) ? ColorUtil.staticRainbow((y + 1.0f) * 0.89f, this.color.getValue()) : -1));
    }
    
    public void renderPotionText(final String text, final float x, final float y, final Potion potion) {
        final String colorCode = (this.potionColor.getValue() == PotionColor.Normal) ? "" : this.colorMode.getValue().getColor();
        HUD.RENDERER.drawStringWithShadow(colorCode + text, x, y, (this.potionColor.getValue() == PotionColor.Normal) ? this.potionColorMap.get(potion).getRGB() : ((this.colorMode.getValue() == HudRainbow.None) ? this.color.getValue().getRGB() : ((this.colorMode.getValue() == HudRainbow.Static) ? ColorUtil.staticRainbow((y + 1.0f) * 0.89f, this.color.getValue()) : -1)));
    }
    
    public Map<Module, ArrayEntry> getArrayEntries() {
        return this.arrayEntries;
    }
    
    public Map<Module, ArrayEntry> getRemoveEntries() {
        return this.removeEntries;
    }
    
    protected boolean isArrayMember(final Module module) {
        return this.getArrayEntries().containsKey(module);
    }
    
    static {
        RENDERER = Managers.TEXT;
    }
}
