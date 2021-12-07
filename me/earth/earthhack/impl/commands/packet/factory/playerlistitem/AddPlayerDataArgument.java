// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.factory.playerlistitem;

import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import com.mojang.authlib.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.packet.arguments.*;

public class AddPlayerDataArgument extends AbstractArgument<SPacketPlayerListItem.AddPlayerData>
{
    private static final GameProfileArgument GAME_PROFILE_ARGUMENT;
    private static final IntArgument INT_ARGUMENT;
    private static final PacketArgument<GameType> GAME_TYPE_ARGUMENT;
    private static final TextComponentArgument TEXT_COMPONENT_ARGUMENT;
    private static final SPacketPlayerListItem PACKET;
    
    public AddPlayerDataArgument() {
        super(SPacketPlayerListItem.AddPlayerData.class);
    }
    
    @Override
    public SPacketPlayerListItem.AddPlayerData fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split(",");
        if (split.length < 4) {
            throw new ArgParseException("Expected 3+ Arguments for EntityPlayerMP, but found: " + split.length + "!");
        }
        final GameProfile profile = AddPlayerDataArgument.GAME_PROFILE_ARGUMENT.fromString(split[0] + "," + split[1]);
        final int latency = AddPlayerDataArgument.INT_ARGUMENT.fromString(split[2]);
        final GameType gameType = AddPlayerDataArgument.GAME_TYPE_ARGUMENT.fromString(split[3]);
        ITextComponent component = null;
        if (split.length >= 5) {
            final StringBuilder builder = new StringBuilder(split[4]);
            for (int i = 5; i < split.length; ++i) {
                builder.append(",").append(split[i]);
            }
            component = AddPlayerDataArgument.TEXT_COMPONENT_ARGUMENT.fromString(builder.toString());
        }
        final SPacketPlayerListItem packet = AddPlayerDataArgument.PACKET;
        packet.getClass();
        return new SPacketPlayerListItem.AddPlayerData(packet, profile, latency, gameType, component);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<AddPlayerData:GameProfile,latency,GameType,TextComponent>");
        }
        final String[] split = argument.split(",");
        switch (split.length) {
            case 0: {
                return inputs.setRest("<AddPlayerData:GameProfile,latency,GameType,TextComponent>");
            }
            case 1: {
                final PossibleInputs g = AddPlayerDataArgument.GAME_PROFILE_ARGUMENT.getPossibleInputs(split[0]);
                return inputs.setCompletion(g.getCompletion()).setRest(g.getRest() + ",latency,GameType,TextComponent");
            }
            case 2: {
                final PossibleInputs g2 = AddPlayerDataArgument.GAME_PROFILE_ARGUMENT.getPossibleInputs(split[0] + split[1]);
                return inputs.setCompletion(g2.getCompletion()).setRest(g2.getRest() + ",latency,GameType,TextComponent");
            }
            case 3: {
                return inputs.setRest(",GameType,TextComponent");
            }
            case 4: {
                final PossibleInputs type = AddPlayerDataArgument.GAME_TYPE_ARGUMENT.getPossibleInputs(split[2]);
                return inputs.setCompletion(type.getCompletion()).setRest(type.getRest() + ",TextComponent");
            }
            default: {
                return inputs;
            }
        }
    }
    
    static {
        GAME_PROFILE_ARGUMENT = new GameProfileArgument();
        INT_ARGUMENT = new IntArgument();
        GAME_TYPE_ARGUMENT = new EnumArgument<GameType>(GameType.class);
        TEXT_COMPONENT_ARGUMENT = new TextComponentArgument();
        PACKET = new SPacketPlayerListItem();
    }
}
