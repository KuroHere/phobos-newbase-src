//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.mcp;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.mcf.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.*;

public class MiddleClickPearl extends Module
{
    private static final ModuleCache<MCF> MCFRIENDS;
    protected final Setting<Boolean> preferMCF;
    protected final Setting<Boolean> cancelMCF;
    protected final Setting<Boolean> cancelBlock;
    protected Runnable runnable;
    
    public MiddleClickPearl() {
        super("MCP", Category.Player);
        this.preferMCF = this.register(new BooleanSetting("PrioMCF", false));
        this.cancelMCF = this.register(new BooleanSetting("CancelMCF", true));
        this.cancelBlock = this.register(new BooleanSetting("CancelBlock", false));
        this.listeners.add(new ListenerMiddleClick(this));
        this.listeners.add(new ListenerMotion(this));
    }
    
    public void onEnable() {
        this.runnable = null;
    }
    
    @Override
    protected void onDisable() {
        this.runnable = null;
    }
    
    protected boolean prioritizeMCF() {
        return this.preferMCF.getValue() && MiddleClickPearl.MCFRIENDS.isEnabled() && MiddleClickPearl.mc.objectMouseOver != null && MiddleClickPearl.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY && MiddleClickPearl.mc.objectMouseOver.entityHit instanceof EntityPlayer;
    }
    
    static {
        MCFRIENDS = Caches.getModule(MCF.class);
    }
}
