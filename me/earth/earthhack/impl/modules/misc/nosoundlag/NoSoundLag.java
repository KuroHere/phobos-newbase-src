//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nosoundlag;

import me.earth.earthhack.api.module.*;
import java.util.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class NoSoundLag extends Module
{
    protected static final Set<SoundEvent> SOUNDS;
    protected final Setting<Boolean> sounds;
    protected final Setting<Boolean> crystals;
    protected final SoundObserver observer;
    
    public NoSoundLag() {
        super("NoSoundLag", Category.Misc);
        this.sounds = this.register(new BooleanSetting("Sounds", true));
        this.crystals = this.register(new BooleanSetting("Crystals", false));
        this.observer = new SimpleSoundObserver(this.crystals::getValue);
        this.listeners.add(new ListenerSound(this));
        this.setData(new SimpleData(this, "Prevents lag caused by spamming certain sounds."));
    }
    
    @Override
    protected void onEnable() {
        Managers.SET_DEAD.addObserver(this.observer);
    }
    
    @Override
    protected void onDisable() {
        Managers.SET_DEAD.removeObserver(this.observer);
    }
    
    static {
        SOUNDS = Sets.newHashSet((Object[])new SoundEvent[] { SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundEvents.field_191258_p, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER });
    }
}
