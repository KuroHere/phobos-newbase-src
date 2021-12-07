//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.management;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import com.mojang.authlib.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.setting.event.*;

public class Management extends Module
{
    protected final Setting<Boolean> clear;
    protected final Setting<Boolean> logout;
    protected final Setting<Boolean> friend;
    protected final Setting<Boolean> soundRemove;
    protected final Setting<Integer> deathTime;
    protected final Setting<Integer> time;
    protected final Setting<Boolean> aspectRatio;
    protected final Setting<Integer> aspectRatioWidth;
    protected final Setting<Integer> aspectRatioHeight;
    protected final Setting<Boolean> pooledScreenShots;
    protected final Setting<Boolean> pauseOnLeftFocus;
    protected GameProfile lastProfile;
    
    public Management() {
        super("Management", Category.Client);
        this.clear = this.register(new BooleanSetting("ClearPops", false));
        this.logout = this.register(new BooleanSetting("LogoutPops", false));
        this.friend = this.register(new BooleanSetting("SelfFriend", true));
        this.soundRemove = this.register(new BooleanSetting("SoundRemove", true));
        this.deathTime = this.register(new NumberSetting("DeathTime", 250, 0, 1000));
        this.time = this.register(new NumberSetting("Time", 0, 0, 24000));
        this.aspectRatio = this.register(new BooleanSetting("ChangeAspectRatio", false));
        this.aspectRatioWidth = this.register(new NumberSetting("AspectRatioWidth", Management.mc.displayWidth, 0, Management.mc.displayWidth));
        this.aspectRatioHeight = this.register(new NumberSetting("AspectRatioHeight", Management.mc.displayHeight, 0, Management.mc.displayHeight));
        this.pooledScreenShots = this.register(new BooleanSetting("Pooled-Screenshots", false));
        this.pauseOnLeftFocus = this.register(new BooleanSetting("PauseOnLeftFocus", Management.mc.gameSettings.pauseOnLostFocus));
        Bus.EVENT_BUS.register(new ListenerLogout(this));
        Bus.EVENT_BUS.register(new ListenerGameLoop(this));
        Bus.EVENT_BUS.register(new ListenerAspectRatio(this));
        Bus.EVENT_BUS.register(new ListenerTick(this));
        this.setData(new ManagementData(this));
        this.clear.addObserver(event -> {
            event.setValue(false);
            ChatUtil.sendMessage("Clearing TotemPops...");
            Managers.COMBAT.reset();
            return;
        });
        this.pauseOnLeftFocus.addObserver(e -> Management.mc.gameSettings.pauseOnLostFocus = e.getValue());
        this.register(new BooleanSetting("IgnoreForgeRegistries", false));
    }
    
    @Override
    protected void onLoad() {
        if (this.friend.getValue()) {
            this.lastProfile = Management.mc.getSession().getProfile();
            Managers.FRIENDS.add(this.lastProfile.getName(), this.lastProfile.getId());
        }
    }
}
