// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.visibility;

import me.earth.earthhack.api.setting.*;
import java.util.function.*;
import java.util.*;
import me.earth.earthhack.impl.*;

public class PageBuilder<T>
{
    private final List<Map.Entry<List<Setting<?>>, VisibilitySupplier>> suppliers;
    private final SettingContainer container;
    private final Setting<T> pageSetting;
    private Function<VisibilitySupplier, VisibilitySupplier> conversion;
    private Setting<?> position;
    private boolean injectBefore;
    
    public PageBuilder(final SettingContainer container, final Setting<T> setting) {
        this.suppliers = new LinkedList<Map.Entry<List<Setting<?>>, VisibilitySupplier>>();
        this.pageSetting = Visibilities.requireNonNull(setting);
        this.conversion = (Function<VisibilitySupplier, VisibilitySupplier>)(v -> v);
        this.container = Objects.requireNonNull(container);
    }
    
    public PageBuilder<T> withConversion(final Function<VisibilitySupplier, VisibilitySupplier> conversion) {
        this.conversion = conversion;
        return this;
    }
    
    public PageBuilder<T> reapplyConversion() {
        for (final Map.Entry<List<Setting<?>>, VisibilitySupplier> entry : this.suppliers) {
            entry.setValue(this.conversion.apply(entry.getValue()));
        }
        return this;
    }
    
    public PageBuilder<T> setPagePositionBefore(final String before) {
        return this.setPagePositionBefore(Visibilities.requireNonNull(this.container.getSetting(before)));
    }
    
    public PageBuilder<T> setPagePositionBefore(final String before, final Class<?> clazz) {
        return this.setPagePositionBefore(Visibilities.requireNonNull(this.container.getSetting(before, clazz)));
    }
    
    public PageBuilder<T> setPagePositionAfter(final String after, final Class<?> clazz) {
        return this.setPagePositionAfter(Visibilities.requireNonNull(this.container.getSetting(after, clazz)));
    }
    
    public PageBuilder<T> setPagePositionAfter(final String after) {
        this.position = Visibilities.requireNonNull(this.container.getSetting(after));
        this.injectBefore = false;
        return this;
    }
    
    public PageBuilder<T> setPagePositionBefore(final Setting<?> before) {
        this.position = Visibilities.requireNonNull(before);
        this.injectBefore = true;
        return this;
    }
    
    public PageBuilder<T> setPagePositionAfter(final Setting<?> after) {
        this.position = Visibilities.requireNonNull(after);
        this.injectBefore = false;
        return this;
    }
    
    public PageBuilder<T> addVisibility(final Predicate<T> visibility, final Setting<?> setting) {
        final List<Setting<?>> list = new ArrayList<Setting<?>>(1);
        list.add(setting);
        this.suppliers.add(new AbstractMap.SimpleEntry<List<Setting<?>>, VisibilitySupplier>(list, this.toVis(visibility)));
        return this;
    }
    
    public PageBuilder<T> addPage(final Predicate<T> visibility, final Setting<?> start, final Setting<?> end) {
        final List<Setting<?>> settings = new ArrayList<Setting<?>>();
        boolean started = false;
        for (final Setting<?> setting : this.container.getSettings()) {
            if (setting.equals(start)) {
                started = true;
                settings.add(setting);
            }
            if (started) {
                settings.add(setting);
            }
            if (setting.equals(end)) {
                if (!started) {
                    Earthhack.getLogger().warn("PageBuilder: found end: " + end.getName() + " but not start!");
                    return this;
                }
                started = false;
                settings.add(setting);
            }
        }
        if (started) {
            Earthhack.getLogger().warn("PageBuilder: found start: " + start.getName() + " but not end!");
            return this;
        }
        this.suppliers.add(new AbstractMap.SimpleEntry<List<Setting<?>>, VisibilitySupplier>(settings, this.toVis(visibility)));
        return this;
    }
    
    public PageBuilder<T> addPage(final Predicate<T> visibility, final Setting<?>... settings) {
        final List<Setting<?>> toArrayList = new ArrayList<Setting<?>>(settings.length);
        for (final Setting<?> setting : settings) {
            if (setting != null) {
                toArrayList.add(setting);
            }
        }
        this.suppliers.add(new AbstractMap.SimpleEntry<List<Setting<?>>, VisibilitySupplier>(toArrayList, this.toVis(visibility)));
        return this;
    }
    
    public PageBuilder<T> register(final VisibilityManager manager) {
        for (final Map.Entry<List<Setting<?>>, VisibilitySupplier> entry : this.suppliers) {
            Visibilities.register(manager, entry.getValue(), entry.getKey());
        }
        return this;
    }
    
    public PageBuilder<T> registerPageSetting() {
        if (this.position == null) {
            this.container.register(this.pageSetting);
            return this;
        }
        if (this.injectBefore) {
            this.container.registerBefore(this.pageSetting, this.position);
        }
        else {
            this.container.registerAfter(this.pageSetting, this.position);
        }
        return this;
    }
    
    public Setting<T> getPageSetting() {
        return this.pageSetting;
    }
    
    private VisibilitySupplier toVis(final Predicate<T> predicate) {
        return this.conversion.apply(() -> predicate.test(this.pageSetting.getValue()));
    }
}
