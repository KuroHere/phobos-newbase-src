// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.factory;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import me.earth.earthhack.api.module.data.*;
import java.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.gui.chat.components.setting.*;

public class ComponentFactory
{
    private static final Map<Class<? extends Setting>, IComponentFactory<?, ?>> FACTORIES;
    
    public static <E, T extends Setting<E>> IComponentFactory<?, ?> register(final Class<T> clazz, final IComponentFactory<E, T> factory) {
        return ComponentFactory.FACTORIES.put(clazz, factory);
    }
    
    public static <T, S extends Setting<T>> SettingComponent<T, S> create(final S setting) {
        final IComponentFactory<T, S> factory = (IComponentFactory<T, S>)ComponentFactory.FACTORIES.get(setting.getClass());
        if (factory == null) {
            return new DefaultComponent<T, S>(setting);
        }
        return factory.create(setting);
    }
    
    public static HoverEvent getHoverEvent(final Setting<?> setting) {
        if (setting == null) {
            return new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("null"));
        }
        ModuleData data = null;
        if (setting.getContainer() instanceof Module) {
            data = ((Module)setting.getContainer()).getData();
        }
        String description = "A Setting. (" + setting.getInitial().getClass().getSimpleName() + ")";
        if (data != null) {
            final String dataDescription = data.settingDescriptions().get(setting);
            if (dataDescription != null) {
                description = dataDescription;
            }
        }
        return ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(description)));
    }
    
    static {
        FACTORIES = new HashMap<Class<? extends Setting>, IComponentFactory<?, ?>>();
        register(ColorSetting.class, ColorComponent::new);
        register(BindSetting.class, BindComponent::new);
        register(BooleanSetting.class, BooleanComponent::new);
        register(StringSetting.class, StringComponent::new);
        ComponentFactory.FACTORIES.put(EnumSetting.class, EnumComponent.FACTORY);
        ComponentFactory.FACTORIES.put(NumberSetting.class, NumberComponent.FACTORY);
    }
}
