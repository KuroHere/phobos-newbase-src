//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;

public class PlayerCapabilitiesArgument extends AbstractArgument<PlayerCapabilities> implements Globals
{
    private static final BooleanArgument BOOLEAN;
    
    public PlayerCapabilitiesArgument() {
        super(PlayerCapabilities.class);
    }
    
    @Override
    public PlayerCapabilities fromString(final String argument) throws ArgParseException {
        if (PlayerCapabilitiesArgument.mc.player == null) {
            throw new ArgParseException("Minecraft.Player was null!");
        }
        final String[] split = argument.split(",");
        final boolean damage = (split.length > 0) ? Boolean.parseBoolean(split[0]) : PlayerCapabilitiesArgument.mc.player.capabilities.disableDamage;
        final boolean flying = (split.length > 1) ? Boolean.parseBoolean(split[1]) : PlayerCapabilitiesArgument.mc.player.capabilities.isFlying;
        final boolean allow = (split.length > 2) ? Boolean.parseBoolean(split[2]) : PlayerCapabilitiesArgument.mc.player.capabilities.allowFlying;
        final boolean creative = (split.length > 3) ? Boolean.parseBoolean(split[3]) : PlayerCapabilitiesArgument.mc.player.capabilities.isCreativeMode;
        final boolean edit = (split.length > 4) ? Boolean.parseBoolean(split[4]) : PlayerCapabilitiesArgument.mc.player.capabilities.allowEdit;
        final float flySpeed = (split.length > 5) ? ((float)ArgParseException.tryDouble(split[5], "speed")) : PlayerCapabilitiesArgument.mc.player.capabilities.getFlySpeed();
        final float walkSpeed = (split.length > 6) ? ((float)ArgParseException.tryDouble(split[5], "walk")) : PlayerCapabilitiesArgument.mc.player.capabilities.getWalkSpeed();
        final PlayerCapabilities playerCapabilities = new PlayerCapabilities();
        playerCapabilities.disableDamage = damage;
        playerCapabilities.isFlying = flying;
        playerCapabilities.allowFlying = allow;
        playerCapabilities.isCreativeMode = creative;
        playerCapabilities.allowEdit = edit;
        playerCapabilities.setFlySpeed(flySpeed);
        playerCapabilities.setPlayerWalkSpeed(walkSpeed);
        return playerCapabilities;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<PlayerCapabilities:damage,flying,allow,creative,edit,speed,walk>");
        }
        final String[] split = argument.split(",");
        if (split.length == 0) {
            return inputs.setRest("<PlayerCapabilities:damage,flying,allow,creative,edit,speed,walk>");
        }
        int length = split.length;
        if (split[split.length - 1].isEmpty()) {
            --length;
        }
        switch (length) {
            case 0: {
                return inputs.setRest("damage,flying,allow,creative,edit,speed,walk>");
            }
            case 1: {
                inputs.setRest("flying,allow,creative,edit,speed,walk>");
                break;
            }
            case 2: {
                inputs.setRest("allow,creative,edit,speed,walk>");
                break;
            }
            case 3: {
                inputs.setRest("creative,edit,speed,walk>");
                break;
            }
            case 4: {
                inputs.setRest("edit,speed,walk");
                break;
            }
            case 5: {
                inputs.setRest("speed,walk");
            }
            case 6: {
                inputs.setRest("walk");
                break;
            }
        }
        if (split.length < 6) {
            final String last = split[split.length - 1];
            if (last.isEmpty()) {
                return inputs;
            }
            final PossibleInputs bool = PlayerCapabilitiesArgument.BOOLEAN.getPossibleInputs(last);
            inputs.setCompletion(bool.getCompletion() + ",");
        }
        else if (length < 7) {
            inputs.setCompletion(",");
        }
        return inputs;
    }
    
    static {
        BOOLEAN = new BooleanArgument();
    }
}
