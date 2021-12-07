// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.module;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.event.bus.api.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.event.bus.instance.*;
import java.util.*;
import me.earth.earthhack.api.setting.event.*;

public abstract class Module extends SettingContainer implements Globals, Subscriber, Hideable, Displayable, Nameable
{
    protected final List<Listener<?>> listeners;
    private final AtomicBoolean enableCheck;
    private final AtomicBoolean inOnEnable;
    private final Setting<String> name;
    private final Setting<Bind> bind;
    private final Setting<Hidden> hidden;
    private final Setting<Boolean> enabled;
    private final Setting<Toggle> bindMode;
    private final Category category;
    private ModuleData data;
    
    public Module(final String name, final Category category) {
        this.listeners = new ArrayList<Listener<?>>();
        this.enableCheck = new AtomicBoolean();
        this.inOnEnable = new AtomicBoolean();
        this.bind = this.register(new BindSetting("Bind", Bind.none()));
        this.hidden = this.register(new EnumSetting("Hidden", Hidden.Visible));
        this.enabled = this.register(new BooleanSetting("Enabled", false));
        this.bindMode = this.register(new EnumSetting("Toggle", Toggle.Normal));
        this.name = this.register(new StringSetting("Name", name));
        this.category = category;
        this.data = new DefaultData<Object>(this);
        this.enabled.addObserver(event -> {
            if (!event.isCancelled()) {
                this.enableCheck.set(event.getValue());
                if (event.getValue() && !Bus.EVENT_BUS.isSubscribed(this)) {
                    this.inOnEnable.set(true);
                    this.onEnable();
                    this.inOnEnable.set(false);
                    if (this.enableCheck.get()) {
                        Bus.EVENT_BUS.subscribe(this);
                    }
                }
                else if (!event.getValue() && (Bus.EVENT_BUS.isSubscribed(this) || this.inOnEnable.get())) {
                    Bus.EVENT_BUS.unsubscribe(this);
                    this.onDisable();
                }
            }
        });
    }
    
    @Override
    public String getName() {
        return this.name.getInitial();
    }
    
    @Override
    public String getDisplayName() {
        return this.name.getValue();
    }
    
    @Override
    public void setDisplayName(final String name) {
        this.name.setValue(name);
    }
    
    public final void toggle() {
        if (this.isEnabled()) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    public final void enable() {
        if (!this.isEnabled()) {
            this.enabled.setValue(true);
        }
    }
    
    public final void disable() {
        if (this.isEnabled()) {
            this.enabled.setValue(false);
        }
    }
    
    public final void load() {
        if (this.isEnabled() && !Bus.EVENT_BUS.isSubscribed(this)) {
            Bus.EVENT_BUS.subscribe(this);
        }
        this.onLoad();
    }
    
    public boolean isEnabled() {
        return this.enableCheck.get();
    }
    
    public String getDisplayInfo() {
        return null;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public ModuleData getData() {
        return this.data;
    }
    
    public void setData(final ModuleData data) {
        if (data != null) {
            this.data = data;
        }
    }
    
    public Bind getBind() {
        return this.bind.getValue();
    }
    
    public void setBind(final Bind bind) {
        this.bind.setValue(bind);
    }
    
    public Toggle getBindMode() {
        return this.bindMode.getValue();
    }
    
    @Override
    public void setHidden(final Hidden hidden) {
        this.hidden.setValue(hidden);
    }
    
    @Override
    public Hidden isHidden() {
        return this.hidden.getValue();
    }
    
    protected void onEnable() {
    }
    
    protected void onDisable() {
    }
    
    protected void onLoad() {
    }
    
    @Override
    public Collection<Listener<?>> getListeners() {
        return this.listeners;
    }
    
    @Override
    public int hashCode() {
        return this.name.getInitial().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Module) {
            final String name = this.name.getInitial();
            return name != null && name.equals(((Module)o).name.getInitial());
        }
        return false;
    }
}
