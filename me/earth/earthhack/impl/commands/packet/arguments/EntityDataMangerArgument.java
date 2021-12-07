//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.network.datasync.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.entity.*;

public class EntityDataMangerArgument extends AbstractArgument<EntityDataManager> implements Globals
{
    public EntityDataMangerArgument() {
        super(EntityDataManager.class);
    }
    
    @Override
    public EntityDataManager fromString(final String arg) throws ArgParseException {
        if (EntityDataMangerArgument.mc.world == null) {
            throw new ArgParseException("Minecraft.World was null!");
        }
        final Entity entity = AbstractEntityArgument.getEntity(arg, Entity.class);
        if (entity == null) {
            throw new ArgParseException("Could not parse " + arg + " to Entity!");
        }
        return entity.getDataManager();
    }
}
