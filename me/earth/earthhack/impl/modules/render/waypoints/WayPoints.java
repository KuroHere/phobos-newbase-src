//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.waypoints;

import me.earth.earthhack.impl.util.helpers.addable.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.setting.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.modules.render.waypoints.mode.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.commands.util.*;
import java.util.*;

public class WayPoints extends RegisteringModule<BlockPos, WayPointSetting>
{
    protected final Setting<WayPointRender> render;
    protected final Setting<Boolean> ovwInNether;
    protected final Setting<Boolean> netherOvw;
    protected final Setting<Color> ovwColor;
    protected final Setting<Color> netherColor;
    protected final Setting<Color> endColor;
    protected final Setting<Float> scale;
    protected final Setting<Float> lineWidth;
    protected final Setting<Integer> range;
    
    public WayPoints() {
        super("Waypoints", Category.Render, "Add Waypoint", "name> <type> <x> <y> <z", s -> new WayPointSetting(s, BlockPos.ORIGIN), s -> "A waypoint.");
        this.render = this.register(new EnumSetting("Render", WayPointRender.None));
        this.ovwInNether = this.register(new BooleanSetting("OVW-Nether", false));
        this.netherOvw = this.register(new BooleanSetting("Nether-OVW", false));
        this.ovwColor = this.register(new ColorSetting("OVW-Color", new Color(0, 255, 0)));
        this.netherColor = this.register(new ColorSetting("Nether-Color", new Color(255, 255, 0)));
        this.endColor = this.register(new ColorSetting("End-Color", new Color(0, 255, 255)));
        this.scale = this.register(new NumberSetting("Scale", 0.003f, 0.001f, 0.01f));
        this.lineWidth = this.register(new NumberSetting("LineWidth", 1.5f, 0.1f, 5.0f));
        this.range = this.register(new NumberSetting("Range", 1000, 0, Integer.MAX_VALUE));
        this.listeners.add(new ListenerRender(this));
    }
    
    @Override
    protected PossibleInputs getInput(final String input, final String[] args) {
        if (args.length < 2) {
            final PossibleInputs inputs = super.getInput(input, args);
            if (args.length == 1 && "del".startsWith(args[0].toLowerCase())) {
                inputs.setRest(" <name>");
            }
            return inputs;
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        if (args[0].equalsIgnoreCase("del")) {
            if (args.length > 2) {
                return inputs;
            }
            return inputs.setCompletion(this.getInput(input.substring(4), false));
        }
        else {
            if (!args[0].equalsIgnoreCase("add")) {
                return inputs;
            }
            switch (args.length) {
                case 2: {
                    return inputs.setRest(" <type> <x> <y> <z>");
                }
                case 3: {
                    final Enum<?> entry = EnumHelper.getEnumStartingWith(args[2], WayPointType.class);
                    if (entry == null || entry == WayPointType.None) {
                        return inputs.setRest("§c unrecognized type!");
                    }
                    return inputs.setCompletion(TextUtil.substring(entry.toString(), args[2].length())).setRest(" <x> <y> <z>");
                }
                case 4: {
                    return inputs.setRest(" <y> <z>");
                }
                case 5: {
                    return inputs.setRest(" <z>");
                }
                default: {
                    return inputs;
                }
            }
        }
    }
    
    @Override
    public void add(final String string) {
        if (string == null || string.isEmpty()) {
            ChatUtil.sendMessage("§cNo WayPoint given!");
            return;
        }
        final String[] args = CommandUtil.toArgs(string);
        if (args.length != 5) {
            ChatUtil.sendMessage("§cExpected 5 arguments: (Name, Type, X, Y, Z), but found " + args.length + "!");
            return;
        }
        final WayPointType type = WayPointType.fromString(args[1]);
        if (type == WayPointType.None) {
            ChatUtil.sendMessage("§cCan't recognize type §f" + args[1] + "§c" + "! Try OVW, End or Nether!");
            return;
        }
        double x;
        double y;
        double z;
        try {
            x = Double.parseDouble(args[2]);
            y = Double.parseDouble(args[3]);
            z = Double.parseDouble(args[4]);
        }
        catch (Exception e) {
            ChatUtil.sendMessage("§cCouldn't parse input to X,Y,Z Coordinates!");
            return;
        }
        final WayPointSetting setting = ((RegisteringModule<I, WayPointSetting>)this).addSetting(args[0]);
        if (setting != null) {
            setting.setType(type);
            setting.setValue(new BlockPos(x, y, z));
            ChatUtil.sendMessage("§aAdded new waypoint: §f" + args[0] + "§a" + ".");
        }
        else {
            ChatUtil.sendMessage("§cA Waypoint called §f" + args[0] + "§c" + " already exists!");
        }
    }
    
    protected Color getColor(final WayPointType type) {
        switch (type) {
            case OVW: {
                return this.ovwColor.getValue();
            }
            case End: {
                return this.endColor.getValue();
            }
            case Nether: {
                return this.netherColor.getValue();
            }
            default: {
                return Color.WHITE;
            }
        }
    }
    
    protected Set<WayPointSetting> getWayPoints() {
        return (Set<WayPointSetting>)this.added;
    }
}
