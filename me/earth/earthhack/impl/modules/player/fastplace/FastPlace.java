//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fastplace;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.core.ducks.*;

public class FastPlace extends RemovingItemAddingModule
{
    protected final Setting<Boolean> all;
    protected final Setting<Boolean> bypass;
    protected final Setting<Boolean> foodBypass;
    protected final Setting<Boolean> bypassContainers;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> doubleEat;
    
    public FastPlace() {
        super("FastPlace", Category.Player, s -> "Makes you place " + s.getName() + " faster.");
        this.all = this.register(new BooleanSetting("All", true));
        this.bypass = this.register(new BooleanSetting("Bypass", false));
        this.foodBypass = this.register(new BooleanSetting("Food-Bypass", false));
        this.bypassContainers = this.register(new BooleanSetting("Bypass-Containers", false));
        this.delay = this.register(new NumberSetting("Delay", 0, 0, 4));
        this.doubleEat = this.register(new BooleanSetting("Double-Eat", false));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerUseOnBlock(this));
        this.listeners.add(new ListenerUseItem(this));
        final SimpleData data = new SimpleData(this, "Removes the place delay when holding down right click.");
        data.register(this.all, "Ignores the White/Blacklisted items and removes the delay for all items.");
        data.register(this.delay, "The delay in ticks between using an item.");
        this.setData(data);
    }
    
    protected void onTick() {
        if (FastPlace.mc.gameSettings.keyBindUseItem.isKeyDown() && this.delay.getValue() < ((IMinecraft)FastPlace.mc).getRightClickDelay() && (this.all.getValue() || this.isStackValid(FastPlace.mc.player.getHeldItemMainhand()) || this.isStackValid(FastPlace.mc.player.getHeldItemOffhand()))) {
            ((IMinecraft)FastPlace.mc).setRightClickDelay(this.delay.getValue());
        }
    }
}
