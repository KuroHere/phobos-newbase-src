//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.nohunger;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.managers.*;

public class NoHunger extends Module
{
    protected final Setting<Boolean> sprint;
    protected final Setting<Boolean> ground;
    boolean onGround;
    
    public NoHunger() {
        super("NoHunger", Category.Player);
        this.sprint = this.register(new BooleanSetting("Sprint", true));
        this.ground = this.register(new BooleanSetting("Ground", true));
        this.listeners.add(new ListenerEntityAction(this));
        this.listeners.addAll(new ListenerPlayerPacket(this).getListeners());
        final SimpleData data = new SimpleData(this, "Makes you not get hungry.");
        data.register(this.sprint, "Will cancel sprint packets send to the server.");
        data.register(this.ground, "Will make the server think you are not on the ground, which makes it no apply hunger to you. Will build up falldamage over time so watch out.");
        this.setData(data);
    }
    
    @Override
    protected void onEnable() {
        if (this.sprint.getValue() && NoHunger.mc.player != null) {
            NoHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoHunger.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
    }
    
    @Override
    protected void onDisable() {
        if (this.sprint.getValue() && NoHunger.mc.player != null && !Managers.ACTION.isSprinting() && NoHunger.mc.player.isSprinting()) {
            NoHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoHunger.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
}
