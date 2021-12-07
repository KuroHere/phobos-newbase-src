//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.entity.*;

public abstract class AbstractEntityPlayerArgument<T extends EntityPlayer> extends AbstractEntityArgument<T>
{
    public AbstractEntityPlayerArgument(final Class<T> type) {
        super(type);
    }
    
    protected abstract T create();
    
    @Override
    public T fromString(final String arg) throws ArgParseException {
        if (AbstractEntityPlayerArgument.mc.world == null) {
            throw new ArgParseException("Minecraft.world was null!");
        }
        T entity = AbstractEntityArgument.getEntity(arg, this.directType);
        if (entity == null) {
            int id = -1337;
            try {
                id = (int)Long.parseLong(arg);
            }
            catch (Exception ex) {}
            entity = this.create();
            entity.setEntityId(id);
        }
        return entity;
    }
}
