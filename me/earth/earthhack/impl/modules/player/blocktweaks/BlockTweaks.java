//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.blocktweaks;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import org.lwjgl.input.*;

public class BlockTweaks extends RemovingItemAddingModule
{
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> noBreakAnim;
    protected final Setting<Boolean> entityMine;
    protected final Setting<Boolean> m1Attack;
    protected final Setting<Boolean> ignoreFalling;
    protected final Setting<Boolean> newVerEntities;
    
    public BlockTweaks() {
        super("BlockTweaks", Category.Player, s -> "Lets you mine through entities while holding " + s.getName());
        this.delay = this.register(new NumberSetting("BreakDelay", 0, 0, 5));
        this.noBreakAnim = this.register(new BooleanSetting("NoBreakAnim", false));
        this.entityMine = this.register(new BooleanSetting("EntityMine", true));
        this.m1Attack = this.register(new BooleanSetting("RightAttack", false));
        this.ignoreFalling = this.register(new BooleanSetting("IgnoreFalling", false));
        this.newVerEntities = this.register(new BooleanSetting("1.13-Entities", false));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerPacket(this));
        this.setData(new BlockTweaksData(this));
    }
    
    public boolean areNewVerEntitiesActive() {
        return this.isEnabled() && this.newVerEntities.getValue();
    }
    
    public boolean isIgnoreFallingActive() {
        return this.isEnabled() && this.ignoreFalling.getValue();
    }
    
    public boolean noMiningTrace() {
        return this.isEnabled() && this.entityMine.getValue() && this.isStackValid(BlockTweaks.mc.player.getHeldItemMainhand()) && (!this.m1Attack.getValue() || !Mouse.isButtonDown(1));
    }
}
