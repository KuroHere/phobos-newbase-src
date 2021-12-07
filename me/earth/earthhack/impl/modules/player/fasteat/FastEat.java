//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fasteat;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.player.fasteat.mode.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.item.*;

public class FastEat extends Module
{
    protected final Setting<FastEatMode> mode;
    protected final Setting<Float> speed;
    protected final Setting<Boolean> cancel;
    
    public FastEat() {
        super("FastEat", Category.Player);
        this.mode = this.register(new EnumSetting("Mode", FastEatMode.Packet));
        this.speed = this.register(new NumberSetting("Speed", 15.0f, 1.0f, 25.0f));
        this.cancel = this.register(new BooleanSetting("Cancel-Digging", false));
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerTryUseItem(this));
        this.listeners.add(new ListenerDigging(this));
        final SimpleData data = new SimpleData(this, "Exploits that make you fat.");
        data.register(this.mode, "Different Modes. NoDelay won't lagback, the others might.");
        data.register(this.speed, "Speed for mode Packet.");
        data.register(this.cancel, "Makes it so that you just need to click once and the item will be eaten.");
        this.setData(data);
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
    
    public FastEatMode getMode() {
        return this.mode.getValue();
    }
    
    protected boolean isValid(final ItemStack stack) {
        return stack != null && FastEat.mc.player.isHandActive() && (stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemPotion || stack.getItem() instanceof ItemBucketMilk);
    }
}
