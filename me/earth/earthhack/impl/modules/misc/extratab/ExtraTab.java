//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.extratab;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.media.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.*;

public class ExtraTab extends Module
{
    private static final ModuleCache<Media> MEDIA;
    protected final Setting<Integer> size;
    
    public ExtraTab() {
        super("ExtraTab", Category.Misc);
        this.size = this.register(new NumberSetting("Size", 250, 0, 500));
        this.register(new BooleanSetting("Download-Threads", false));
    }
    
    public int getSize(final int defaultSize) {
        return this.isEnabled() ? this.size.getValue() : defaultSize;
    }
    
    public String getName(final NetworkPlayerInfo info) {
        final String finalName;
        String name = finalName = ((info.getDisplayName() != null) ? info.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)info.getPlayerTeam(), info.getGameProfile().getName()));
        name = ExtraTab.MEDIA.returnIfPresent(m -> m.convert(finalName), name);
        if (this.isEnabled()) {
            if (Managers.FRIENDS.contains(finalName)) {
                return "§b" + name;
            }
            if (Managers.ENEMIES.contains(finalName)) {
                return "§c" + name;
            }
        }
        return name;
    }
    
    static {
        MEDIA = Caches.getModule(Media.class);
    }
}
