// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers;

import me.earth.earthhack.impl.managers.client.macro.*;
import me.earth.earthhack.impl.managers.minecraft.timer.*;
import me.earth.earthhack.impl.managers.thread.holes.*;
import me.earth.earthhack.impl.managers.thread.safety.*;
import me.earth.earthhack.impl.managers.thread.connection.*;
import me.earth.earthhack.impl.managers.render.*;
import me.earth.earthhack.impl.managers.chat.*;
import me.earth.earthhack.impl.managers.thread.lookup.*;
import me.earth.earthhack.impl.managers.config.*;
import me.earth.earthhack.impl.managers.thread.*;
import me.earth.earthhack.impl.managers.minecraft.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.managers.minecraft.movement.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.managers.minecraft.combat.*;
import me.earth.earthhack.impl.managers.client.*;
import me.earth.earthhack.api.plugin.*;
import java.io.*;
import java.util.*;

public class Managers
{
    public static final ThreadManager THREAD;
    public static final HudElementManager ELEMENTS;
    public static final MacroManager MACRO;
    public static final ChatManager CHAT;
    public static final CommandManager COMMANDS;
    public static final PlayerManager FRIENDS;
    public static final PlayerManager ENEMIES;
    public static final ModuleManager MODULES;
    public static final CombatManager COMBAT;
    public static final PositionManager POSITION;
    public static final RotationManager ROTATION;
    public static final ServerManager SERVER;
    public static final ActionManager ACTION;
    public static final SpeedManager SPEED;
    public static final SwitchManager SWITCH;
    public static final TimerManager TIMER;
    public static final TPSManager TPS;
    public static final TextRenderer TEXT;
    public static final HoleManager HOLES;
    public static final SafetyManager SAFETY;
    public static final ConnectionManager CONNECT;
    public static final KeyBoardManager KEYBOARD;
    public static final ColorManager COLOR;
    public static final WrapManager WRAP;
    public static final NCPManager NCP;
    public static final SetDeadManager SET_DEAD;
    public static final LookUpManager LOOK_UP;
    public static final ConfigManager CONFIG;
    public static final BlockStateManager BLOCKS;
    public static final TargetManager TARGET;
    public static final EntityProvider ENTITIES;
    public static final HealthManager HEALTH;
    public static final ServerTickManager TICK;
    
    public static void load() {
        Earthhack.getLogger().info("Subscribing Managers.");
        subscribe(Managers.TIMER, Managers.CONNECT, Managers.CHAT, Managers.COMBAT, Managers.POSITION, Managers.ROTATION, Managers.SERVER, Managers.ACTION, Managers.SPEED, Managers.SWITCH, Managers.TPS, Managers.HOLES, Managers.SAFETY, Managers.KEYBOARD, Managers.COLOR, Managers.WRAP, Managers.MACRO, Managers.NCP, Managers.SET_DEAD, Managers.BLOCKS, Managers.ENTITIES, Managers.HEALTH, Managers.TICK, new NoMotionUpdateService(), new PotionService());
        TotemDebugService.trySubscribe(Bus.EVENT_BUS);
        Managers.COMMANDS.init();
        subscribe(Managers.COMMANDS);
        Managers.ELEMENTS.init();
        Managers.MODULES.init();
        PluginManager.getInstance().instantiatePlugins();
        for (final Plugin plugin : PluginManager.getInstance().getPlugins().values()) {
            plugin.load();
        }
        try {
            Managers.CONFIG.refreshAll();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Managers.ELEMENTS.load();
        Managers.MODULES.load();
        Managers.MACRO.validateAll();
    }
    
    public static void subscribe(final Object... subscribers) {
        for (final Object subscriber : subscribers) {
            Bus.EVENT_BUS.subscribe(subscriber);
        }
    }
    
    static {
        THREAD = new ThreadManager();
        ELEMENTS = new HudElementManager();
        MACRO = new MacroManager();
        CHAT = new ChatManager();
        COMMANDS = new CommandManager();
        FRIENDS = new PlayerManager();
        ENEMIES = new PlayerManager();
        MODULES = new ModuleManager();
        COMBAT = new CombatManager();
        POSITION = new PositionManager();
        ROTATION = new RotationManager();
        SERVER = new ServerManager();
        ACTION = new ActionManager();
        SPEED = new SpeedManager();
        SWITCH = new SwitchManager();
        TIMER = new TimerManager();
        TPS = new TPSManager();
        TEXT = new TextRenderer();
        HOLES = new HoleManager();
        SAFETY = new SafetyManager();
        CONNECT = new ConnectionManager();
        KEYBOARD = new KeyBoardManager();
        COLOR = new ColorManager();
        WRAP = new WrapManager();
        NCP = new NCPManager();
        SET_DEAD = new SetDeadManager();
        LOOK_UP = new LookUpManager();
        CONFIG = new ConfigManager();
        BLOCKS = new BlockStateManager();
        TARGET = new TargetManager();
        ENTITIES = new EntityProvider();
        HEALTH = new HealthManager();
        TICK = new ServerTickManager();
    }
}
