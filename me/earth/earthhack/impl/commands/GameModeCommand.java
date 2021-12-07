//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.util.*;
import net.minecraft.server.integrated.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.command.*;

public class GameModeCommand extends Command implements Globals
{
    public GameModeCommand() {
        super(new String[][] { { "gamemode" }, { "survival", "creative", "adventure", "spectator" }, { "fake" } });
        CommandDescriptions.register(this, "Allows you to change or, if the 3rd argument is \"fake\", fake your gamemode.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (GameModeCommand.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be ingame to use this command!");
            return;
        }
        if (args.length == 1) {
            ChatUtil.sendMessage("§cSpecify a gamemode.");
        }
        else {
            GameType gameType = this.tryToParseFromID(args[1]);
            if (gameType == null) {
                gameType = this.getGameTypeStartingWith(args[1]);
                if (gameType == null) {
                    ChatUtil.sendMessage("§cGameType §f" + args[1] + "§c" + " not found.");
                    return;
                }
            }
            boolean fake = false;
            if (args.length > 2) {
                fake = args[2].equalsIgnoreCase("fake");
            }
            if (fake && GameModeCommand.mc.playerController != null) {
                ChatUtil.sendMessage("§aSetting your Client GameType to: §b" + TextUtil.capitalize(gameType.getName()));
                GameModeCommand.mc.playerController.setGameType(gameType);
            }
            else if (GameModeCommand.mc.player != null) {
                if (GameModeCommand.mc.isSingleplayer()) {
                    final EntityPlayerMP player = Objects.requireNonNull(GameModeCommand.mc.getIntegratedServer()).getPlayerList().getPlayerByUUID(GameModeCommand.mc.player.getUniqueID());
                    if (player != null) {
                        player.setGameType(gameType);
                        ChatUtil.sendMessage("§aGamemode set to §f" + gameType.getName() + "§a" + ".");
                        return;
                    }
                }
                final String message = "/gamemode " + gameType.getName();
                ChatUtil.sendMessage("§a" + message);
                GameModeCommand.mc.player.sendChatMessage(message);
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length != 2) {
            return inputs;
        }
        boolean isNumber = true;
        GameType gameType = this.tryToParseFromID(args[1]);
        if (gameType == null) {
            isNumber = false;
            gameType = this.getGameTypeStartingWith(args[1]);
        }
        if (gameType == null) {
            return inputs.setCompletion("").setRest("§c not found");
        }
        if (isNumber) {
            return inputs.setRest(" (" + gameType.getName() + ") <fake>");
        }
        if (gameType.getName().equalsIgnoreCase(args[1])) {
            return inputs.setRest(" <fake>");
        }
        return inputs.setCompletion(TextUtil.substring(gameType.getName(), args[1].length())).setRest("");
    }
    
    private GameType getGameTypeStartingWith(final String arg) {
        for (final GameType gameType : GameType.values()) {
            if (gameType.getName().startsWith(arg.toLowerCase())) {
                return gameType;
            }
        }
        return null;
    }
    
    private GameType tryToParseFromID(final String idString) {
        try {
            final int id = Integer.parseInt(idString);
            return GameType.parseGameTypeWithDefault(id, (GameType)null);
        }
        catch (NumberFormatException ignored) {
            return null;
        }
    }
}
