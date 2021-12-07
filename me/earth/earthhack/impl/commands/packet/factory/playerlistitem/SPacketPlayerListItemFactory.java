//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.factory.playerlistitem;

import me.earth.earthhack.impl.commands.packet.factory.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.commands.packet.generic.*;
import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.misc.*;
import java.lang.reflect.*;
import me.earth.earthhack.impl.commands.packet.arguments.*;
import me.earth.earthhack.impl.commands.packet.array.*;
import java.util.*;

public class SPacketPlayerListItemFactory extends DefaultFactory
{
    private static final PacketArgument<SPacketPlayerListItem.AddPlayerData> ARGUMENT;
    private static final PacketArgument<SPacketPlayerListItem.Action> ACTION_ARGUMENT;
    private static final PacketArgument<SPacketPlayerListItem.AddPlayerData[]> ARRAY_ARGUMENT;
    private static final GenericIterableArgument<SPacketPlayerListItem.AddPlayerData> ITERABLE_ARGUMENT;
    private static final List<GenericArgument<?>> GENERICS;
    
    public SPacketPlayerListItemFactory(final PacketCommand command) {
        super(command);
    }
    
    @Override
    public Packet<?> create(final Class<? extends Packet<?>> clazz, final String[] args) throws ArgParseException {
        if (!SPacketPlayerListItem.class.isAssignableFrom(clazz)) {
            throw new IllegalStateException("This definitely shouldn't happen! SPacketPlayerListFactory got: " + clazz.getName());
        }
        return super.create(clazz, args);
    }
    
    @Override
    public PossibleInputs getInputs(final Class<? extends Packet<?>> clazz, final String[] args) {
        if (!SPacketPlayerListItem.class.isAssignableFrom(clazz)) {
            throw new IllegalStateException("This definitely shouldn't happen! SPacketPlayerListFactory got: " + clazz.getName());
        }
        return super.getInputs(clazz, args);
    }
    
    @Override
    protected Packet<?> instantiate(final Constructor<? extends Packet<?>> ctr, final Object[] parameters) throws NoSuchFieldException, IllegalAccessException {
        final SPacketPlayerListItem packet = new SPacketPlayerListItem();
        final Field action = ReflectionUtil.getField(SPacketPlayerListItem.class, "action", "action", "a");
        action.setAccessible(true);
        action.set(packet, parameters[0]);
        for (int i = 1; i < parameters.length; ++i) {
            final Object o = parameters[i];
            if (o != null) {
                if (o.getClass().isArray()) {
                    for (int j = 0; j < Array.getLength(o); ++j) {
                        packet.getEntries().add(Array.get(o, j));
                    }
                }
                else if (o instanceof Iterable) {
                    for (final Object data : (Iterable)o) {
                        packet.getEntries().add(data);
                    }
                }
            }
        }
        return (Packet<?>)packet;
    }
    
    @Override
    protected PacketArgument<?> getArgument(final Constructor<?> ctr, final Class<?> type, final Class<? extends Packet<?>> clazz, final int i) {
        final List<GenericArgument<?>> gs = this.getGeneric(clazz);
        if (gs != null) {
            for (final GenericArgument<?> g : gs) {
                if (i == g.getArgIndex()) {
                    return g;
                }
            }
        }
        return this.getArgumentForType(type);
    }
    
    @Override
    protected List<GenericArgument<?>> getGeneric(final Class<? extends Packet<?>> clazz) {
        return SPacketPlayerListItemFactory.GENERICS;
    }
    
    @Override
    protected PacketArgument<?> getArgumentForType(final Class<?> type) {
        if (SPacketPlayerListItem.Action.class.isAssignableFrom(type)) {
            return SPacketPlayerListItemFactory.ACTION_ARGUMENT;
        }
        if (Iterable.class.isAssignableFrom(type)) {
            return SPacketPlayerListItemFactory.ITERABLE_ARGUMENT;
        }
        if (SPacketPlayerListItem.AddPlayerData[].class.isAssignableFrom(type)) {
            return SPacketPlayerListItemFactory.ARRAY_ARGUMENT;
        }
        return null;
    }
    
    static {
        ARGUMENT = new AddPlayerDataArgument();
        ACTION_ARGUMENT = new EnumArgument<SPacketPlayerListItem.Action>(SPacketPlayerListItem.Action.class);
        ARRAY_ARGUMENT = new FunctionArrayArgument((Class<Object[]>)SPacketPlayerListItem.AddPlayerData[].class, (PacketArgument<Object>)SPacketPlayerListItemFactory.ARGUMENT, x$0 -> new SPacketPlayerListItem.AddPlayerData[x$0]);
        try {
            final Constructor<SPacketPlayerListItem> ctr = SPacketPlayerListItem.class.getDeclaredConstructor(SPacketPlayerListItem.Action.class, Iterable.class);
            ITERABLE_ARGUMENT = new GenericIterableArgument<SPacketPlayerListItem.AddPlayerData>(ctr, 1, SPacketPlayerListItemFactory.ARGUMENT);
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException("Couldn't find Iterable Constructor in SPacketPlayerListItem!");
        }
        (GENERICS = new ArrayList<GenericArgument<?>>(1)).add(SPacketPlayerListItemFactory.ITERABLE_ARGUMENT);
    }
}
