//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.command.*;

public class VClipCommand extends Command implements Globals
{
    public VClipCommand() {
        super(new String[][] { { "vclip" }, { "amount" } });
        CommandDescriptions.register(this, "Teleports you vertically.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§cPlease specify an amount to be vclipped by.");
            return;
        }
        if (VClipCommand.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be ingame to use this command.");
            return;
        }
        try {
            final double amount = Double.parseDouble(args[1]);
            final Entity entity = (Entity)((VClipCommand.mc.player.getRidingEntity() != null) ? VClipCommand.mc.player.getRidingEntity() : VClipCommand.mc.player);
            entity.setPosition(entity.posX, entity.posY + amount, entity.posZ);
            PacketUtil.doY(entity.posY + amount, VClipCommand.mc.player.onGround);
            ChatUtil.sendMessage("§aVClipped you §f" + args[1] + "§a" + " blocks.");
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
