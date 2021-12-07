//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.mobowner;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import java.util.*;

public class MobOwner extends Module
{
    final Map<UUID, String> cache;
    
    public MobOwner() {
        super("MobOwner", Category.Misc);
        this.cache = new HashMap<UUID, String>();
        this.listeners.add(new ListenerTick(this));
        this.setData(new SimpleData(this, "Displays the name of their owners above mobs."));
    }
    
    @Override
    protected void onDisable() {
        if (MobOwner.mc.world != null) {
            for (final Entity entity : MobOwner.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityTameable)) {
                    if (!(entity instanceof AbstractHorse)) {
                        continue;
                    }
                }
                try {
                    entity.setAlwaysRenderNameTag(false);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
