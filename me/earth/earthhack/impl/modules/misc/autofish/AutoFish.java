//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autofish;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.core.ducks.*;

public class AutoFish extends Module
{
    protected final Setting<Boolean> openInv;
    protected final Setting<Float> delay;
    protected final Setting<Double> range;
    protected boolean splash;
    protected int delayCounter;
    protected int splashTicks;
    protected int timeout;
    
    public AutoFish() {
        super("AutoFish", Category.Misc);
        this.openInv = this.register(new BooleanSetting("OpenInventory", true));
        this.delay = this.register(new NumberSetting("Delay", 15.0f, 10.0f, 25.0f));
        this.range = this.register(new NumberSetting("SoundRange", 2.0, 0.1, 5.0));
        this.listeners.add(new ListenerSound(this));
        this.listeners.add(new ListenerTick(this));
    }
    
    @Override
    protected void onEnable() {
        this.splash = false;
        this.splashTicks = 0;
        this.delayCounter = 0;
        this.timeout = 0;
    }
    
    protected void click() {
        if ((!AutoFish.mc.player.inventory.getCurrentItem().func_190926_b() || AutoFish.mc.player.inventory.getCurrentItem().getItem() instanceof ItemFishingRod) && (this.openInv.getValue() || AutoFish.mc.currentScreen instanceof GuiChat || AutoFish.mc.currentScreen == null)) {
            ((IMinecraft)AutoFish.mc).click(IMinecraft.Click.RIGHT);
            this.delayCounter = this.delay.getValue().intValue();
            this.timeout = 0;
        }
    }
}
