// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.factory;

import net.minecraft.network.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import java.lang.reflect.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.generic.*;
import java.util.*;

public class DefaultFactory implements PacketFactory
{
    private final PacketCommand command;
    
    public DefaultFactory(final PacketCommand command) {
        this.command = command;
    }
    
    @Override
    public Packet<?> create(final Class<? extends Packet<?>> clazz, final String[] args) throws ArgParseException {
        int ctrIndex;
        try {
            ctrIndex = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
            throw new ArgParseException("Couldn't parse constructor index from " + args[2] + "!");
        }
        final Constructor<?>[] ctrs = clazz.getDeclaredConstructors();
        if (ctrIndex < 0 || ctrIndex >= ctrs.length) {
            throw new ArgParseException("Constructor index out of bounds! Expected 0" + ((ctrs.length == 0) ? "" : ("-" + (ctrs.length - 1))) + " but found: " + ctrIndex);
        }
        final Constructor<? extends Packet<?>> ctr = (Constructor<? extends Packet<?>>)ctrs[ctrIndex];
        final Class<?>[] types = ctr.getParameterTypes();
        if (args.length - 3 != types.length) {
            throw new ArgParseException("Expected " + types.length + " parameters but found: " + (args.length - 3) + "!");
        }
        final Object[] parameters = this.parseParameters(ctr, clazz, args, types);
        try {
            return this.instantiate(ctr, parameters);
        }
        catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException ex) {
            final Exception ex2;
            final Exception e2 = ex2;
            BufferUtil.release(parameters);
            throw new ArgParseException("Couldn't instantiate Packet: " + e2.getMessage());
        }
    }
    
    @Override
    public PossibleInputs getInputs(final Class<? extends Packet<?>> clazz, final String[] args) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (args.length == 2) {
            final String name = this.command.getName(clazz);
            return inputs.setRest(" <index>").setCompletion(TextUtil.substring(name, args[1].length()));
        }
        final Constructor<?>[] ctrs = clazz.getDeclaredConstructors();
        if (args[2].isEmpty()) {
            int index = -1;
            for (int i = 0; i < ctrs.length; ++i) {
                if (index == -1 || (ctrs[i].getParameterTypes().length != 0 && ctrs[index].getParameterTypes().length == 0)) {
                    index = i;
                }
            }
            return inputs.setCompletion(index + "").setRest(this.getRest(args, ctrs[index], clazz));
        }
        int index;
        try {
            index = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
            return inputs.setRest("§c <Can't parse constructor index!>");
        }
        if (index < 0 || index >= ctrs.length) {
            return inputs.setRest("§c <Constructor index out of bounds! Expected 0" + ((ctrs.length == 0) ? "" : ("-" + (ctrs.length - 1))) + " but found: " + index + ">");
        }
        final Constructor<?> ctr = ctrs[index];
        final int j = args.length - 3;
        final Class<?>[] types = ctr.getParameterTypes();
        if (j > types.length) {
            return inputs.setRest("§c <Too many parameters! Expected " + types.length + " but found: " + j + ">");
        }
        final PossibleInputs compl = this.getCompletion(args, ctr, clazz);
        return inputs.setCompletion(compl.getCompletion()).setRest(compl.getRest() + this.getRest(args, ctr, clazz));
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
    
    protected Packet<?> instantiate(final Constructor<? extends Packet<?>> ctr, final Object[] parameters) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        ctr.setAccessible(true);
        return (Packet<?>)ctr.newInstance(parameters);
    }
    
    protected Object[] parseParameters(final Constructor<? extends Packet<?>> ctr, final Class<? extends Packet<?>> clazz, final String[] args, final Class<?>... types) throws ArgParseException {
        final Object[] parameters = new Object[types.length];
        for (int i = 0; i < types.length; ++i) {
            final PacketArgument<?> argument = this.getArgument(ctr, types[i], clazz, i);
            if (argument == null) {
                throw new ArgParseException("Couldn't find Argument for " + types[i].getName() + "!");
            }
            try {
                final Object o = argument.fromString(args[i + 3]);
                parameters[i] = o;
            }
            catch (ArgParseException e) {
                BufferUtil.release(parameters);
                throw e;
            }
        }
        return parameters;
    }
    
    protected PossibleInputs getCompletion(final String[] args, final Constructor<?> ctr, final Class<? extends Packet<?>> clazz) {
        final int i = args.length - 4;
        final Class<?>[] types = ctr.getParameterTypes();
        if (i < 0 || i >= types.length) {
            return PossibleInputs.empty();
        }
        final PacketArgument<?> argument = this.getArgument(ctr, types[i], clazz, i);
        if (argument == null) {
            return PossibleInputs.empty();
        }
        return argument.getPossibleInputs(args[args.length - 1]);
    }
    
    protected String getRest(final String[] args, final Constructor<?> ctr, final Class<? extends Packet<?>> clazz) {
        final int start = args.length - 3;
        final Class<?>[] types = ctr.getParameterTypes();
        if (start < 0 || start >= types.length) {
            return "";
        }
        final StringBuilder builder = new StringBuilder(" ");
        for (int i = start; i < types.length; ++i) {
            final PacketArgument<?> argument = this.getArgument(ctr, types[i], clazz, i);
            if (argument == null) {
                return "§c <Couldn't find Argument for " + types[i].getName() + "!>";
            }
            builder.append(argument.getPossibleInputs(null).getRest()).append(" ");
        }
        return builder.toString();
    }
    
    protected PacketArgument<?> getArgument(final Constructor<?> ctr, final Class<?> type, final Class<? extends Packet<?>> clazz, final int i) {
        final List<GenericArgument<?>> gs = this.getGeneric(clazz);
        if (gs != null) {
            for (final GenericArgument<?> g : gs) {
                if (g.getConstructor().equals(ctr) && i == g.getArgIndex()) {
                    return g;
                }
            }
        }
        return this.getArgumentForType(type);
    }
    
    protected List<GenericArgument<?>> getGeneric(final Class<? extends Packet<?>> clazz) {
        return this.command.getGenerics().get(clazz);
    }
    
    protected PacketArgument<?> getArgumentForType(final Class<?> type) {
        return this.command.getArguments().get(type);
    }
}
