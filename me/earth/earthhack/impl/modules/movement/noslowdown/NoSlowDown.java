//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.client.settings.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.gui.click.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.network.play.client.*;

public class NoSlowDown extends Module
{
    protected final Setting<Boolean> guiMove;
    protected final Setting<Boolean> items;
    protected final Setting<Boolean> legit;
    protected final Setting<Boolean> sprint;
    protected final Setting<Boolean> input;
    protected final Setting<Boolean> sneakPacket;
    protected final Setting<Double> websY;
    protected final Setting<Double> websXZ;
    protected final Setting<Boolean> sneak;
    protected final Setting<Boolean> useTimerWeb;
    protected final Setting<Double> timerSpeed;
    protected final Setting<Boolean> onGroundSpoof;
    protected final Setting<Boolean> superStrict;
    protected final Setting<Boolean> phobosGui;
    protected final List<Class<? extends GuiScreen>> screens;
    protected final KeyBinding[] keys;
    protected boolean spoof;
    
    public NoSlowDown() {
        super("NoSlowDown", Category.Movement);
        this.guiMove = this.register(new BooleanSetting("GuiMove", true));
        this.items = this.register(new BooleanSetting("Items", true));
        this.legit = this.register(new BooleanSetting("Legit", false));
        this.sprint = this.register(new BooleanSetting("Sprint", true));
        this.input = this.register(new BooleanSetting("Input", true));
        this.sneakPacket = this.register(new BooleanSetting("SneakPacket", false));
        this.websY = this.register(new NumberSetting("WebsVertical", 2.0, 1.0, 100.0));
        this.websXZ = this.register(new NumberSetting("WebsHorizontal", 1.1, 1.0, 100.0));
        this.sneak = this.register(new BooleanSetting("WebsSneak", false));
        this.useTimerWeb = this.register(new BooleanSetting("UseTimerInWeb", false));
        this.timerSpeed = this.register(new NumberSetting("Timer", 8.0, 0.1, 20.0));
        this.onGroundSpoof = this.register(new BooleanSetting("OnGroundSpoof", false));
        this.superStrict = this.register(new BooleanSetting("SuperStrict", false));
        this.phobosGui = this.register(new BooleanSetting("PhobosGui", false));
        this.screens = new ArrayList<Class<? extends GuiScreen>>();
        this.spoof = true;
        this.register(new BooleanSetting("SoulSand", true));
        this.keys = new KeyBinding[] { NoSlowDown.mc.gameSettings.keyBindForward, NoSlowDown.mc.gameSettings.keyBindBack, NoSlowDown.mc.gameSettings.keyBindLeft, NoSlowDown.mc.gameSettings.keyBindRight, NoSlowDown.mc.gameSettings.keyBindJump, NoSlowDown.mc.gameSettings.keyBindSprint };
        this.screens.add((Class<? extends GuiScreen>)GuiOptions.class);
        this.screens.add((Class<? extends GuiScreen>)GuiVideoSettings.class);
        this.screens.add((Class<? extends GuiScreen>)GuiScreenOptionsSounds.class);
        this.screens.add((Class<? extends GuiScreen>)GuiContainer.class);
        this.screens.add((Class<? extends GuiScreen>)GuiIngameMenu.class);
        this.listeners.add(new ListenerSprint(this));
        this.listeners.add(new ListenerInput(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerPostKeys(this));
        this.listeners.add(new ListenerRightClickItem(this));
        this.listeners.add(new ListenerTryUseItem(this));
        this.listeners.add(new ListenerTryUseItemOnBlock(this));
        this.setData(new NoSlowDownData(this));
    }
    
    @Override
    protected void onDisable() {
        Managers.NCP.setStrict(false);
    }
    
    protected void updateKeyBinds() {
        if (this.guiMove.getValue()) {
            if (this.screens.stream().anyMatch(screen -> screen.isInstance(NoSlowDown.mc.currentScreen)) || (this.phobosGui.getValue() && NoSlowDown.mc.currentScreen instanceof Click)) {
                for (final KeyBinding key : this.keys) {
                    KeyBinding.setKeyBindState(key.getKeyCode(), KeyBoardUtil.isKeyDown(key));
                }
            }
            else if (NoSlowDown.mc.currentScreen == null) {
                for (final KeyBinding key : this.keys) {
                    if (!KeyBoardUtil.isKeyDown(key)) {
                        KeyBinding.setKeyBindState(key.getKeyCode(), false);
                    }
                }
            }
        }
    }
    
    protected void onPacket(final CPacketPlayer packet) {
    }
}
