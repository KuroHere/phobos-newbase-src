//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.combat.legswitch.modes.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;

final class ListenerSound extends SoundObserver implements Globals
{
    private final LegSwitch module;
    
    public ListenerSound(final LegSwitch module) {
        super(module.soundRemove::getValue);
        this.module = module;
    }
    
    @Override
    public void onChange(final SPacketSoundEffect value) {
        if (this.module.soundStart.getValue() && (InventoryUtil.isHolding(Items.END_CRYSTAL) || this.module.autoSwitch.getValue() != LegAutoSwitch.None) && (this.module.rotate.getValue() == ACRotate.None || this.module.rotate.getValue() == ACRotate.Break)) {
            this.module.startCalculation();
        }
    }
    
    @Override
    public boolean shouldBeNotified() {
        return this.module.soundStart.getValue();
    }
}
