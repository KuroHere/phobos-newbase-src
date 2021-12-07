//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.misc.tooltips.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

public class PeekCommand extends Command implements Globals
{
    private static final ModuleCache<ToolTips> TOOLTIPS;
    private static final SettingCache<Boolean, BooleanSetting, ToolTips> SPY;
    
    public PeekCommand() {
        super(new String[][] { { "peek" }, { "player" } });
        CommandDescriptions.register(this, "Type peek to view the shulker you are currently holding. Specify a player name to view that players last held shulker (The Tooltips module needs to be enabled for this).");
    }
    
    @Override
    public void execute(final String[] args) {
        if (!PeekCommand.TOOLTIPS.isPresent()) {
            ChatUtil.sendMessage("§c<ToolTips> A critical error occurred! Please contact the dev.");
            return;
        }
        if (PeekCommand.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be ingame to use this command!");
            return;
        }
        if (args.length == 1 || args[1].equalsIgnoreCase(PeekCommand.mc.getSession().getProfile().getName())) {
            ItemStack stack = PeekCommand.mc.player.getHeldItemMainhand();
            if (!(stack.getItem() instanceof ItemShulkerBox)) {
                stack = PeekCommand.mc.player.getHeldItemOffhand();
                if (!(stack.getItem() instanceof ItemShulkerBox)) {
                    stack = PeekCommand.TOOLTIPS.get().getStack(PeekCommand.mc.getSession().getProfile().getName());
                }
            }
            if (stack != null && stack.getItem() instanceof ItemShulkerBox) {
                final ItemStack finalStack = stack;
                Scheduler.getInstance().schedule(() -> PeekCommand.TOOLTIPS.get().displayInventory(finalStack, null));
                return;
            }
            ChatUtil.sendMessage("§cYou need to hold a Shulker for this.");
        }
        else {
            if (!PeekCommand.TOOLTIPS.isEnabled() || !PeekCommand.SPY.getValue()) {
                ChatUtil.sendMessage("§cPlease enable ToolTips and ToolTips - ShulkerSpy in order to view other players.");
                return;
            }
            final String name = args[1];
            final ItemStack stack2 = PeekCommand.TOOLTIPS.get().getStack(name);
            if (stack2 == null) {
                ChatUtil.sendMessage("§cNo Shulker found for " + name + ".");
                return;
            }
            final ItemStack stack;
            Scheduler.getInstance().schedule(() -> PeekCommand.TOOLTIPS.get().displayInventory(stack, name));
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length == 1) {
            return inputs;
        }
        if (args.length != 2) {
            return PossibleInputs.empty();
        }
        final String name = args[1];
        final String full = this.findName(name);
        if (full == null) {
            return inputs.setCompletion("").setRest("§c no data found.");
        }
        return inputs.setCompletion(TextUtil.substring(full, name.length()));
    }
    
    private String findName(final String input) {
        for (final String string : PeekCommand.TOOLTIPS.get().getPlayers()) {
            if (string.startsWith(input.toLowerCase())) {
                return string;
            }
        }
        return null;
    }
    
    static {
        TOOLTIPS = Caches.getModule(ToolTips.class);
        SPY = Caches.getSetting(ToolTips.class, BooleanSetting.class, "ShulkerSpy", true);
    }
}
