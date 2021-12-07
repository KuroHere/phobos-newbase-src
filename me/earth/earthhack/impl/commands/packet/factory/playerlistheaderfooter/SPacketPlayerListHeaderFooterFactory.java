//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.factory.playerlistheaderfooter;

import me.earth.earthhack.impl.commands.packet.factory.*;
import me.earth.earthhack.impl.commands.packet.arguments.*;
import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.impl.util.misc.*;
import net.minecraft.util.text.*;
import java.lang.reflect.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;

public class SPacketPlayerListHeaderFooterFactory implements PacketFactory
{
    private static final TextComponentArgument TEXT_COMPONENT_ARGUMENT;
    private final PacketCommand command;
    
    public SPacketPlayerListHeaderFooterFactory(final PacketCommand command) {
        this.command = command;
    }
    
    @Override
    public Packet<?> create(final Class<? extends Packet<?>> clazz, final String[] args) throws ArgParseException {
        if (!SPacketPlayerListHeaderFooter.class.isAssignableFrom(clazz)) {
            throw new IllegalStateException("This definitely shouldn't happen! SPacketPlayerListHeaderFooterFactory got: " + clazz.getName());
        }
        if (args.length != 5) {
            throw new ArgParseException("Expected 5 Arguments for SPacketPlayerListHeaderFooter, but found: " + args.length + "!");
        }
        final ITextComponent header = SPacketPlayerListHeaderFooterFactory.TEXT_COMPONENT_ARGUMENT.fromString(args[3]);
        final ITextComponent footer = SPacketPlayerListHeaderFooterFactory.TEXT_COMPONENT_ARGUMENT.fromString(args[4]);
        final SPacketPlayerListHeaderFooter p = new SPacketPlayerListHeaderFooter();
        try {
            final Field headerField = ReflectionUtil.getField(SPacketPlayerListHeaderFooter.class, "header", "header", "a");
            final Field footerField = ReflectionUtil.getField(SPacketPlayerListHeaderFooter.class, "footer", "footer", "b");
            headerField.setAccessible(true);
            headerField.set(p, header);
            footerField.setAccessible(true);
            footerField.set(p, footer);
        }
        catch (NoSuchFieldException | IllegalAccessException ex) {
            final ReflectiveOperationException ex2;
            final ReflectiveOperationException e = ex2;
            throw new ArgParseException("Couldn't set header/footer: " + e.getMessage());
        }
        return (Packet<?>)p;
    }
    
    @Override
    public PossibleInputs getInputs(final Class<? extends Packet<?>> clazz, final String[] args) {
        if (!SPacketPlayerListHeaderFooter.class.isAssignableFrom(clazz)) {
            throw new IllegalStateException("This definitely shouldn't happen! SPacketPlayerListHeaderFooterFactory got: " + clazz.getName());
        }
        PossibleInputs inputs = PossibleInputs.empty();
        switch (args.length) {
            case 2: {
                final String name = this.command.getName(clazz);
                return inputs.setRest(" <index>").setCompletion(TextUtil.substring(name, args[1].length()));
            }
            case 3: {
                final String rest = SPacketPlayerListHeaderFooterFactory.TEXT_COMPONENT_ARGUMENT.getPossibleInputs(null).getRest();
                return inputs.setRest(" " + rest + " " + rest);
            }
            case 4: {
                final String rest2 = SPacketPlayerListHeaderFooterFactory.TEXT_COMPONENT_ARGUMENT.getPossibleInputs(null).getRest();
                inputs = SPacketPlayerListHeaderFooterFactory.TEXT_COMPONENT_ARGUMENT.getPossibleInputs(args[3]);
                return inputs.setRest(inputs.getRest() + " " + rest2);
            }
            case 5: {
                return SPacketPlayerListHeaderFooterFactory.TEXT_COMPONENT_ARGUMENT.getPossibleInputs(args[4]);
            }
            default: {
                return inputs.setRest("§cToo many Arguments (max 2).");
            }
        }
    }
    
    @Override
    public CustomCompleterResult onTabComplete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
    
    static {
        TEXT_COMPONENT_ARGUMENT = new TextComponentArgument();
    }
}
