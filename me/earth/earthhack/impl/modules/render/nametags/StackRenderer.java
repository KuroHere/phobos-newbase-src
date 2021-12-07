//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.nametags;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.util.*;

public class StackRenderer implements Globals
{
    private final ItemStack stack;
    private final List<String> enchantTexts;
    private final boolean damageable;
    private final int color;
    private final float percent;
    
    public StackRenderer(final ItemStack stack, final boolean damageable) {
        this.damageable = damageable;
        if (stack.isItemStackDamageable()) {
            this.percent = DamageUtil.getPercent(stack) / 100.0f;
            this.color = ColorHelper.toColor(this.percent * 120.0f, 100.0f, 50.0f, 1.0f).getRGB();
        }
        else {
            this.percent = 100.0f;
            this.color = -1;
        }
        this.stack = stack;
        final Set<Enchantment> e = EnchantmentHelper.getEnchantments(stack).keySet();
        this.enchantTexts = new ArrayList<String>(e.size());
        for (final Enchantment enchantment : e) {
            this.enchantTexts.add(this.getEnchantText(enchantment, EnchantmentHelper.getEnchantmentLevel(enchantment, stack)));
        }
    }
    
    public boolean isDamageable() {
        return this.damageable;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public void renderStack(final int x, final int y, final int enchHeight) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        StackRenderer.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        final int height = (enchHeight > 4) ? ((enchHeight - 4) * 8 / 2) : 0;
        StackRenderer.mc.getRenderItem().renderItemAndEffectIntoGUI(this.stack, x, y + height);
        StackRenderer.mc.getRenderItem().renderItemOverlays(StackRenderer.mc.fontRendererObj, this.stack, x, y + height);
        StackRenderer.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchants(this.stack, x, y - 24);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    public void renderStack2D(final int x, final int y, final int enchHeight) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        StackRenderer.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        final int height = (enchHeight > 4) ? ((enchHeight - 4) * 8 / 2) : 0;
        StackRenderer.mc.getRenderItem().renderItemAndEffectIntoGUI(this.stack, x, y + height);
        StackRenderer.mc.getRenderItem().renderItemOverlays(StackRenderer.mc.fontRendererObj, this.stack, x, y + height);
        StackRenderer.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchants(this.stack, x, y * 2 + 5);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    public void renderDurability(final float x, final float y) {
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        Managers.TEXT.drawStringWithShadow((int)(this.percent * 100.0f) + "%", x * 2.0f, y, this.color);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    public void renderText(final int y) {
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        final String name = this.stack.getDisplayName();
        Managers.TEXT.drawStringWithShadow(name, (float)(-Managers.TEXT.getStringWidth(name) / 2), (float)y, -1);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    public void renderText(final float x, final float y) {
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        final String name = this.stack.getDisplayName();
        Managers.TEXT.drawStringWithShadow(name, x + -Managers.TEXT.getStringWidth(name) / 2, y, -1);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private void renderEnchants(final ItemStack stack, final int xOffset, int yOffset) {
        for (final String enchantment : this.enchantTexts) {
            if (enchantment != null) {
                Managers.TEXT.drawStringWithShadow(enchantment, xOffset * 2.0f, (float)yOffset, -1);
                yOffset += 8;
            }
        }
        if (stack.getItem().equals(Items.GOLDEN_APPLE) && stack.hasEffect()) {
            Managers.TEXT.drawStringWithShadow("§cGod", xOffset * 2.0f, (float)yOffset, -3977919);
        }
    }
    
    private String getEnchantText(final Enchantment ench, final int lvl) {
        final ResourceLocation resource = (ResourceLocation)Enchantment.REGISTRY.getNameForObject((Object)ench);
        String name = (resource == null) ? ench.getName() : resource.toString();
        final int lvlOffset = (lvl > 1) ? 12 : 13;
        if (name.length() > lvlOffset) {
            name = name.substring(10, lvlOffset);
        }
        if (lvl > 1) {
            name += lvl;
        }
        return (name.length() < 2) ? name : TextUtil.capitalize(name);
    }
}
