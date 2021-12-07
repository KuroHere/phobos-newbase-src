//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.entity.ai.attributes.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import java.util.*;

public class AttributeArgument extends AbstractArgument<IAttributeInstance> implements Globals
{
    public AttributeArgument() {
        super(IAttributeInstance.class);
    }
    
    @Override
    public IAttributeInstance fromString(final String argument) throws ArgParseException {
        if (AttributeArgument.mc.world == null) {
            throw new ArgParseException("Minecraft.World was null!");
        }
        final String[] split = argument.split(",");
        if (split.length != 2) {
            throw new ArgParseException("Expected 2 Arguments for IAttributeInstance, but found: " + split.length + "!");
        }
        final Entity entity = this.find(split[0]);
        if (!(entity instanceof EntityLivingBase)) {
            throw new ArgParseException("Couldn't parse Entity from " + split[0] + ", it either doesn't exist or is not an EntityLivingBase!");
        }
        final IAttributeInstance attribute = this.find((EntityLivingBase)entity, split[1]);
        if (attribute == null) {
            throw new ArgParseException("Couldn't parse IAttributeInstance from " + split[1] + "!");
        }
        return attribute;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty() || AttributeArgument.mc.world == null) {
            return inputs.setRest("<IAttributeInstance:Entity,Attribute>");
        }
        final String[] split = argument.split(",");
        if (split.length == 0 || split[0].isEmpty()) {
            return inputs.setRest("<IAttributeInstance:Entity,Attribute>");
        }
        if (split.length > 2) {
            return inputs;
        }
        if ("$closest".startsWith(split[0].toLowerCase()) && split.length == 1) {
            return inputs.setCompletion(TextUtil.substring("$closest", split[0].length()));
        }
        final String s = LookUpUtil.findNextPlayerName(split[0]);
        if (split.length == 1) {
            if (s != null) {
                return inputs.setCompletion(TextUtil.substring(s, split[0].length()));
            }
            return inputs;
        }
        else {
            final Entity entity = this.find(split[0]);
            if (!(entity instanceof EntityLivingBase)) {
                return inputs;
            }
            final IAttributeInstance attribute = this.find((EntityLivingBase)entity, split[1]);
            if (attribute != null) {
                return inputs.setCompletion(TextUtil.substring(attribute.getAttribute().getAttributeUnlocalizedName(), split[1].length()));
            }
            return inputs;
        }
    }
    
    private Entity find(final String name) {
        if ("$closest".startsWith(name.toLowerCase())) {
            return (Entity)EntityUtil.getClosestEnemy();
        }
        Entity entity = null;
        final String s = LookUpUtil.findNextPlayerName(name);
        if (s != null) {
            entity = (Entity)AttributeArgument.mc.world.getPlayerEntityByName(s);
        }
        if (entity == null) {
            try {
                final int id = (int)Long.parseLong(name);
                entity = AttributeArgument.mc.world.getEntityByID(id);
            }
            catch (NumberFormatException e) {
                return null;
            }
        }
        return entity;
    }
    
    private IAttributeInstance find(final EntityLivingBase base, final String name) {
        for (final IAttributeInstance instance : base.getAttributeMap().getAllAttributes()) {
            if (TextUtil.startsWith(instance.getAttribute().getAttributeUnlocalizedName(), name)) {
                return instance;
            }
        }
        return null;
    }
}
