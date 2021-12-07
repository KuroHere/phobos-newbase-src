// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.colors;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.setting.settings.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;

final class ListenerTick extends ModuleListener<Colors, TickEvent>
{
    public ListenerTick(final Colors module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        Managers.MODULES.getRegistered().forEach(module1 -> module1.getSettings().stream().filter(setting -> setting instanceof ColorSetting && ((ColorSetting)setting).isSync()).forEach(setting -> ((ColorSetting)setting).setValueAlpha(Managers.COLOR.getColorSetting().getValue())));
        Managers.MODULES.getRegistered().forEach(module1 -> module1.getSettings().stream().filter(setting -> setting instanceof ColorSetting && ((ColorSetting)setting).isRainbow() && !((ColorSetting)setting).isSync()).forEach(setting -> {
            final ColorSetting colorSetting = (ColorSetting)setting;
            Color rainbow = null;
            if (((ColorSetting)setting).isStaticRainbow()) {
                new(java.awt.Color.class)();
                new Color(ColorUtil.staticRainbow(0.0f, ((ColorSetting)setting).getValue()));
            }
            else {
                rainbow = ColorUtil.getRainbow((int)Math.max(((ColorSetting)setting).getRainbowSpeed() * 30.0f, 30.0f), 0, ((ColorSetting)setting).getRainbowSaturation() / 100.0f, ((ColorSetting)setting).getRainbowBrightness() / 100.0f);
            }
            colorSetting.setValueAlpha(rainbow);
        }));
    }
}
