//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.mcf;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import com.mojang.authlib.*;

public class MCF extends Module
{
    public MCF() {
        super("MCF", Category.Misc);
        this.listeners.add(new MiddleClickListener(this));
        this.setData(new MCFData(this));
    }
    
    protected void onMiddleClick() {
        if (this.isEnabled() && MCF.mc.objectMouseOver != null && MCF.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
            final Entity entity = MCF.mc.objectMouseOver.entityHit;
            if (entity instanceof EntityPlayer) {
                if (Managers.FRIENDS.contains((EntityPlayer)entity)) {
                    Managers.FRIENDS.remove(entity);
                    Managers.CHAT.sendDeleteMessage("§c" + entity.getName() + " unfriended.", entity.getName(), 4000);
                }
                else {
                    final GameProfile profile = ((EntityPlayer)entity).getGameProfile();
                    Managers.FRIENDS.add(profile.getName(), profile.getId());
                    Managers.CHAT.sendDeleteMessage("§b" + entity.getName() + " friended.", entity.getName(), 4000);
                }
            }
        }
    }
}
