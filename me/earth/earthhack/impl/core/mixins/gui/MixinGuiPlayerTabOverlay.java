//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.extratab.*;
import java.util.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.scoreboard.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ GuiPlayerTabOverlay.class })
public abstract class MixinGuiPlayerTabOverlay
{
    private static final ModuleCache<ExtraTab> EXTRA_TAB;
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/util/List;subList(II)Ljava/util/List;", remap = false))
    public List<NetworkPlayerInfo> subListHook(final List<NetworkPlayerInfo> list, final int from, final int to) {
        return list.subList(from, MixinGuiPlayerTabOverlay.EXTRA_TAB.returnIfPresent(e -> Math.min(e.getSize(to), list.size()), Integer.valueOf(to)));
    }
    
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerNameHook(final NetworkPlayerInfo playerInfo, final CallbackInfoReturnable<String> info) {
        info.setReturnValue(MixinGuiPlayerTabOverlay.EXTRA_TAB.returnIfPresent(e -> e.getName(playerInfo), this.getPlayerNameDefault(playerInfo)));
    }
    
    private String getPlayerNameDefault(final NetworkPlayerInfo info) {
        return (info.getDisplayName() != null) ? info.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)info.getPlayerTeam(), info.getGameProfile().getName());
    }
    
    static {
        EXTRA_TAB = Caches.getModule(ExtraTab.class);
    }
}
