//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.nametags;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.thread.*;
import java.util.*;

public class Nametags extends Module
{
    protected final Setting<Boolean> twoD;
    protected final Setting<Boolean> health;
    protected final Setting<Boolean> ping;
    protected final Setting<Boolean> id;
    protected final Setting<Boolean> itemStack;
    protected final Setting<Boolean> armor;
    protected final Setting<Boolean> gameMode;
    protected final Setting<Boolean> durability;
    protected final Setting<Boolean> invisibles;
    protected final Setting<Boolean> pops;
    protected final Setting<Boolean> burrow;
    protected final Setting<Boolean> fov;
    protected final Setting<Boolean> sneak;
    protected final Setting<Float> scale;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> debug;
    protected final Setting<Boolean> media;
    protected final Setting<Boolean> multiThread;
    protected List<Nametag> nametags;
    protected final StopWatch timer;
    
    public Nametags() {
        super("Nametags", Category.Render);
        this.twoD = this.register(new BooleanSetting("2D", false));
        this.health = this.register(new BooleanSetting("Health", true));
        this.ping = this.register(new BooleanSetting("Ping", true));
        this.id = this.register(new BooleanSetting("Id", false));
        this.itemStack = this.register(new BooleanSetting("StackName", false));
        this.armor = this.register(new BooleanSetting("Armor", true));
        this.gameMode = this.register(new BooleanSetting("GameMode", false));
        this.durability = this.register(new BooleanSetting("Durability", true));
        this.invisibles = this.register(new BooleanSetting("Invisibles", false));
        this.pops = this.register(new BooleanSetting("Pops", true));
        this.burrow = this.register(new BooleanSetting("Burrow", true));
        this.fov = this.register(new BooleanSetting("Fov", true));
        this.sneak = this.register(new BooleanSetting("Sneak", true));
        this.scale = this.register(new NumberSetting("Scale", 0.003f, 0.001f, 0.01f));
        this.delay = this.register(new NumberSetting("Delay", 16, 0, 100));
        this.debug = this.register(new BooleanSetting("Debug", false));
        this.media = this.register(new BooleanSetting("Media", true));
        this.multiThread = this.register(new BooleanSetting("MultiThread", true));
        this.nametags = new ArrayList<Nametag>();
        this.timer = new StopWatch();
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerRender2D(this));
        this.setData(new NametagsData(this));
    }
    
    protected void updateNametags() {
        if (this.timer.passed(this.delay.getValue())) {
            final List<EntityPlayer> players = Managers.ENTITIES.getPlayers();
            if (players == null) {
                return;
            }
            final SafeRunnable runnable = () -> {
                final List<Nametag> nametags = new ArrayList<Nametag>(players.size());
                players.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final EntityPlayer player = iterator.next();
                    if (player != null && !player.isDead && !player.equals((Object)Nametags.mc.player)) {
                        nametags.add(new Nametag(this, player));
                    }
                }
                this.nametags = nametags;
                return;
            };
            if (this.multiThread.getValue()) {
                Managers.THREAD.submit(runnable);
            }
            else {
                runnable.run();
            }
            this.timer.reset();
        }
    }
    
    protected int getFontOffset(final int enchHeight) {
        int armorOffset = this.armor.getValue() ? -26 : -27;
        if (enchHeight > 4) {
            armorOffset -= (enchHeight - 4) * 8;
        }
        return armorOffset;
    }
}
