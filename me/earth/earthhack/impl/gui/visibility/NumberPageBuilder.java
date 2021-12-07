// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.visibility;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;

public class NumberPageBuilder extends PageBuilder<Integer>
{
    public NumberPageBuilder(final SettingContainer container, final String name, final int pages) {
        super(container, new NumberSetting(name, 1, 1, pages));
    }
    
    public NumberPageBuilder addPage(final int page, final Setting<?> from, final Setting<?> to) {
        return (NumberPageBuilder)super.addPage(v -> v == page, from, to);
    }
    
    public NumberPageBuilder addPage(final int page, final Setting<?>... settings) {
        return (NumberPageBuilder)super.addPage(v -> v == page, settings);
    }
    
    public static NumberPageBuilder autoPage(final SettingContainer container, final String name, final int settingsPerPage, final Iterable<? extends Setting<?>> settings) {
        if (settingsPerPage <= 0) {
            throw new IllegalArgumentException("SettingsPerPage needs to be an integer bigger than 0!");
        }
        final Map<Integer, Setting<?>[]> pages = new HashMap<Integer, Setting<?>[]>();
        int i = 0;
        int page = 1;
        Setting<?>[] current = new Setting[settingsPerPage];
        for (final Setting<?> setting : settings) {
            current[i] = setting;
            if (++i == settingsPerPage) {
                pages.put(page++, current);
                current = new Setting[settingsPerPage];
                i = 0;
            }
        }
        if (current[0] != null) {
            pages.put(page, current);
        }
        final NumberPageBuilder pageBuilder = new NumberPageBuilder(container, name, pages.size());
        for (final Map.Entry<Integer, Setting<?>[]> entry : pages.entrySet()) {
            pageBuilder.addPage(entry.getKey(), (Setting<?>[])entry.getValue());
        }
        return pageBuilder;
    }
}
