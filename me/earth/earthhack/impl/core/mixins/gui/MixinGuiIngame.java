//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.impl.modules.player.sorter.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.hud.modes.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.hud.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.*;

@Mixin({ GuiIngame.class })
public abstract class MixinGuiIngame
{
    private static final ModuleCache<Sorter> SORTER;
    private static final SettingCache<Potions, EnumSetting<Potions>, HUD> POTS;
    private static final ModuleCache<NoRender> NO_RENDER;
    
    @Inject(method = { "renderPortal" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPortalHook(final float timeInPortal, final ScaledResolution scaledResolution, final CallbackInfo info) {
        if (MixinGuiIngame.NO_RENDER.returnIfPresent(NoRender::noPortal, false)) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderPumpkinOverlay" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPumpkinOverlayHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (MixinGuiIngame.NO_RENDER.returnIfPresent(NoRender::noPumpkin, false)) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffectsHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (MixinGuiIngame.POTS.getValue() == Potions.Hide || MixinGuiIngame.POTS.getValue() == Potions.Text) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderAttackIndicator" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderAttackIndicator(final float partialTicks, final ScaledResolution p_184045_2_, final CallbackInfo ci) {
        final CrosshairEvent event = new CrosshairEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderHotbar" }, at = { @At("HEAD") })
    private void onRenderHotbar(final ScaledResolution sr, final float partialTicks, final CallbackInfo ci) {
        MixinGuiIngame.SORTER.computeIfPresent(Sorter::updateMapping);
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode = 180))
    private int renderHotbarHook(final InventoryPlayer inventoryPlayer) {
        final int slot = inventoryPlayer.currentItem;
        return MixinGuiIngame.SORTER.returnIfPresent(s -> s.getReverseMapping(slot), Integer.valueOf(slot));
    }
    
    @Redirect(method = { "renderHotbar" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/NonNullList;get(I)Ljava/lang/Object;"))
    private Object renderHotbarHook(final NonNullList<ItemStack> nonNullList, final int p_get_1_) {
        final int slot = MixinGuiIngame.SORTER.returnIfPresent(s -> s.getHotbarMapping(p_get_1_), Integer.valueOf(p_get_1_));
        return nonNullList.get(slot);
    }
    
    static {
        SORTER = Caches.getModule(Sorter.class);
        POTS = Caches.getSetting(HUD.class, Setting.class, "Potions", Potions.Keep);
        NO_RENDER = Caches.getModule(NoRender.class);
    }
}
