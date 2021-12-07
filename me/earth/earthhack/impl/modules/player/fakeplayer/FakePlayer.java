//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fakeplayer;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import java.util.*;
import com.mojang.authlib.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.modules.player.fakeplayer.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.util.function.*;
import net.minecraft.world.*;
import net.minecraft.client.entity.*;

public class FakePlayer extends DisablingModule
{
    protected final Setting<Boolean> playRecording;
    protected final Setting<Boolean> record;
    protected final Setting<Boolean> loop;
    protected final Setting<Boolean> gapple;
    protected final Setting<Integer> gappleDelay;
    protected final Setting<Boolean> damage;
    protected final Setting<String> name;
    protected final List<Position> positions;
    protected final StopWatch timer;
    protected EntityPlayerAttack fakePlayer;
    protected int index;
    
    public FakePlayer() {
        super("FakePlayer", Category.Player);
        this.playRecording = this.register(new BooleanSetting("Play-Recording", false));
        this.record = this.register(new BooleanSetting("Record", false));
        this.loop = this.register(new BooleanSetting("Loop", false));
        this.gapple = this.register(new BooleanSetting("Gapple", true));
        this.gappleDelay = this.register(new NumberSetting("Gapple-Delay", 1600, 1500, 2000));
        this.damage = this.register(new BooleanSetting("Damage", true));
        this.name = this.register(new StringSetting("Name", "FakePlayer"));
        this.positions = new ArrayList<Position>();
        this.timer = new StopWatch();
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerAttack(this));
        this.listeners.add(new ListenerExplosion(this));
        this.setData(new SimpleData(this, "Spawns in a FakePlayer for testing purposes."));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.record.getValue() ? "Recording" : (this.playRecording.getValue() ? "Playing" : null);
    }
    
    @Override
    protected void onEnable() {
        GameProfile profile = new GameProfile(new UUID(1L, 1L), "FakePlayer");
        if (!this.name.getValue().equalsIgnoreCase("FakePlayer")) {
            final UUID uuid = LookUpUtil.getUUIDSimple(this.name.getValue());
            if (uuid != null) {
                profile = new GameProfile(uuid, (String)this.name.getValue());
            }
        }
        this.index = 0;
        (this.fakePlayer = (EntityPlayerAttack)PlayerUtil.createFakePlayerAndAddToWorld(profile, (BiFunction<World, GameProfile, EntityOtherPlayerMP>)EntityPlayerPop::new)).setRemoteSupplier(this.damage::getValue);
    }
    
    @Override
    protected void onDisable() {
        PlayerUtil.removeFakePlayer(this.fakePlayer);
        this.fakePlayer.isDead = true;
        this.playRecording.setValue(false);
        this.record.setValue(false);
        this.fakePlayer = null;
    }
}
