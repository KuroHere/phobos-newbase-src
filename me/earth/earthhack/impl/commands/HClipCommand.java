//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.command.*;

public class HClipCommand extends Command implements Globals
{
    public HClipCommand() {
        super(new String[][] { { "hclip" }, { "amount" } });
        CommandDescriptions.register(this, "Teleports you horizontally.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§cPlease specify an amount to be teleported by.");
            return;
        }
        if (HClipCommand.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be ingame to use this command.");
            return;
        }
        try {
            final double h = Double.parseDouble(args[1]);
            final Entity entity = (Entity)((HClipCommand.mc.player.getRidingEntity() != null) ? HClipCommand.mc.player.getRidingEntity() : HClipCommand.mc.player);
            final double yaw = Math.cos(Math.toRadians(HClipCommand.mc.player.rotationYaw + 90.0f));
            final double pit = Math.sin(Math.toRadians(HClipCommand.mc.player.rotationYaw + 90.0f));
            entity.setPosition(entity.posX + h * yaw, entity.posY, entity.posZ + h * pit);
            ChatUtil.sendMessage("§aHClipped you §f" + args[1] + "§a" + " blocks.");
        }
        catch (Exception e) {
            ChatUtil.sendMessage("§cCouldn't parse §f" + args[1] + "§c" + ", a number (can be a floating point one) is required.");
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length > 1) {
            return PossibleInputs.empty();
        }
        return super.getPossibleInputs(args);
    }
}
