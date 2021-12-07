//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.freecam;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.player.freecam.mode.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.minecraft.*;

public class Freecam extends DisablingModule
{
    protected final Setting<CamMode> mode;
    protected final Setting<Float> speed;
    private EntityOtherPlayerMP fakePlayer;
    
    public Freecam() {
        super("Freecam", Category.Player);
        this.mode = this.register(new EnumSetting("Mode", CamMode.Position));
        this.speed = this.register(new NumberSetting("Speed", 0.5f, 0.1f, 5.0f));
        this.listeners.add(new ListenerPacket(this));
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerOverlay(this));
        this.listeners.add(new ListenerPush(this));
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerMotion(this));
        final SimpleData data = new SimpleData(this, "Allows you to look around freely.");
        data.register(this.mode, "-Cancel cancels movement packets.\n-Spanish good for dupes.\n-Position very legit freecam and, unless you toggle freecam while standing in the air, almost undetectable.");
        data.register(this.speed, "The speed you want to move with while in freecam.");
        this.setData(data);
    }
    
    public CamMode getMode() {
        return this.mode.getValue();
    }
    
    @Override
    protected void onEnable() {
        if (Freecam.mc.player == null) {
            this.disable();
            return;
        }
        Freecam.mc.player.dismountRidingEntity();
        this.fakePlayer = PlayerUtil.createFakePlayerAndAddToWorld(Freecam.mc.player.getGameProfile());
        this.fakePlayer.onGround = Freecam.mc.player.onGround;
    }
    
    @Override
    protected void onDisable() {
        if (Freecam.mc.player == null) {
            return;
        }
        Freecam.mc.player.setPosition(this.fakePlayer.posX, this.fakePlayer.posY, this.fakePlayer.posZ);
        Freecam.mc.player.noClip = false;
        PlayerUtil.removeFakePlayer(this.fakePlayer);
        this.fakePlayer = null;
    }
    
    public EntityOtherPlayerMP getPlayer() {
        return this.fakePlayer;
    }
    
    public void rotate(final float yaw, final float pitch) {
        if (this.fakePlayer != null) {
            this.fakePlayer.rotationYawHead = yaw;
            this.fakePlayer.setPositionAndRotation(this.fakePlayer.posX, this.fakePlayer.posY, this.fakePlayer.posZ, yaw, pitch);
            this.fakePlayer.setPositionAndRotationDirect(this.fakePlayer.posX, this.fakePlayer.posY, this.fakePlayer.posZ, yaw, pitch, 3, false);
        }
    }
}
