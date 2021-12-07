//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autolog;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.client.network.*;

public class AutoLog extends Module
{
    protected final Setting<Float> health;
    protected final Setting<Integer> totems;
    protected final Setting<Float> enemy;
    protected final Setting<Boolean> absorption;
    protected ServerData serverData;
    protected String message;
    protected boolean awaitScreen;
    
    public AutoLog() {
        super("AutoLog", Category.Misc);
        this.health = this.register(new NumberSetting("Health", 5.0f, 0.1f, 19.5f));
        this.totems = this.register(new NumberSetting("Totems", 0, 0, 10));
        this.enemy = this.register(new NumberSetting("Enemy", 12.0f, 0.0f, 100.0f));
        this.absorption = this.register(new BooleanSetting("Absorption", false));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerScreen(this));
        this.setData(new AutoLogData(this));
    }
    
    @Override
    protected void onDisable() {
        this.awaitScreen = false;
    }
    
    public void disconnect(final float health, final EntityPlayer closest, final int totems) {
        this.message = "AutoLogged with " + MathUtil.round(health, 1) + " health and " + totems + " Totem" + ((totems == 1) ? "" : "s") + " remaining." + ((closest == null) ? "" : (" Closest Enemy: " + closest.getName() + "."));
        this.serverData = AutoLog.mc.getCurrentServerData();
        this.awaitScreen = true;
        final NetHandlerPlayClient connection = AutoLog.mc.getConnection();
        if (connection == null) {
            AutoLog.mc.world.sendQuittingDisconnectingPacket();
        }
        else {
            ServerUtil.disconnectFromMC(this.message);
        }
    }
}
