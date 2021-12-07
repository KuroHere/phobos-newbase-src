//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.impl.modules.render.sounds.util.*;
import me.earth.earthhack.api.module.util.*;
import java.awt.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.visibility.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.core.mixins.audio.*;
import net.minecraft.client.audio.*;
import java.util.*;
import net.minecraft.util.text.*;

public class Sounds extends RegisteringModule<Boolean, SimpleRemovingSetting>
{
    protected final Setting<SoundPages> pages;
    protected final Setting<Boolean> render;
    protected final Setting<Boolean> custom;
    protected final Setting<Boolean> packet;
    protected final Setting<Boolean> client;
    protected final ColorSetting color;
    protected final Setting<Integer> remove;
    protected final Setting<Boolean> fade;
    protected final Setting<Boolean> rect;
    protected final Setting<Float> scale;
    protected final Setting<Boolean> cancelled;
    protected final Setting<CoordLogger> coordLogger;
    protected final Setting<Boolean> chat;
    protected final Setting<Boolean> thunder;
    protected final Setting<Boolean> dragon;
    protected final Setting<Boolean> wither;
    protected final Setting<Boolean> portal;
    protected final Setting<Boolean> slimes;
    protected final Map<SoundPosition, Long> sounds;
    
    public Sounds() {
        super("Sounds", Category.Render, "Add_Sound", "sound", SimpleRemovingSetting::new, s -> "White/Blacklist " + s.getName() + "sounds.");
        this.pages = this.register(new EnumSetting("Page", SoundPages.ESP));
        this.render = this.register(new BooleanSetting("Render", true));
        this.custom = this.register(new BooleanSetting("Custom", true));
        this.packet = this.register(new BooleanSetting("Packet", true));
        this.client = this.register(new BooleanSetting("Client-Side", false));
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
        this.remove = this.register(new NumberSetting("Remove", 750, 0, 20000));
        this.fade = this.register(new BooleanSetting("Fade", false));
        this.rect = this.register(new BooleanSetting("Rectangle", false));
        this.scale = this.register(new NumberSetting("Scale", 0.003f, 0.001f, 0.01f));
        this.cancelled = this.register(new BooleanSetting("Cancelled", true));
        this.coordLogger = this.register(new EnumSetting("Coord-Logger", CoordLogger.Vanilla));
        this.chat = this.register(new BooleanSetting("Chat", false));
        this.thunder = this.register(new BooleanSetting("Thunder", false));
        this.dragon = this.register(new BooleanSetting("Dragon", false));
        this.wither = this.register(new BooleanSetting("Wither", false));
        this.portal = this.register(new BooleanSetting("Portal", false));
        this.slimes = this.register(new BooleanSetting("Slimes", false));
        this.sounds = new ConcurrentHashMap<SoundPosition, Long>();
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerSound(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerCustomSound(this));
        this.listeners.add(new ListenerClientSound(this));
        this.listeners.add(new ListenerEffect(this));
        this.listeners.add(new ListenerSpawnMob(this));
        new PageBuilder<SoundPages>(this, this.pages).addPage(v -> v == SoundPages.ESP, this.render, this.cancelled).addPage(v -> v == SoundPages.CoordLogger, this.coordLogger, this.slimes).register(Visibilities.VISIBILITY_MANAGER);
        super.listType.setValue(ListType.BlackList);
    }
    
    @Override
    public String getInput(final String input, final boolean add) {
        if (!add) {
            return super.getInput(input, false);
        }
        final String itemName = getSoundStartingWith(input);
        if (itemName != null) {
            return TextUtil.substring(itemName, input.length());
        }
        return "";
    }
    
    public void log(final String s) {
        if (this.chat.getValue()) {
            Sounds.mc.addScheduledTask(() -> ChatUtil.sendMessage(s));
        }
        else {
            Earthhack.getLogger().info(s);
        }
    }
    
    public static String getSoundStartingWith(final String prefix) {
        final ISoundHandler handler = (ISoundHandler)Sounds.mc.getSoundHandler();
        for (final SoundEventAccessor soundEventAccessor : handler.getRegistry()) {
            if (TextUtil.startsWith(soundEventAccessor.getLocation().toString(), prefix)) {
                return soundEventAccessor.getLocation().toString();
            }
            final ITextComponent component = soundEventAccessor.getSubtitle();
            if (component == null) {
                continue;
            }
            if (TextUtil.startsWith(component.getUnformattedComponentText(), prefix)) {
                return component.getUnformattedComponentText();
            }
        }
        return null;
    }
}
