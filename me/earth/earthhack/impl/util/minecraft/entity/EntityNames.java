//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.entity;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;

public class EntityNames
{
    private static final List<Map.Entry<Class<? extends Entity>, String>> entityNames;
    
    public static void register(final Class<? extends Entity> type, final String name) {
        EntityNames.entityNames.add(0, new AbstractMap.SimpleEntry<Class<? extends Entity>, String>(type, name));
    }
    
    public static String getName(final Entity entity) {
        for (final Map.Entry<Class<? extends Entity>, String> entry : EntityNames.entityNames) {
            if (entry.getKey().isInstance(entity)) {
                return entry.getValue();
            }
        }
        return entity.getName();
    }
    
    static {
        entityNames = new LinkedList<Map.Entry<Class<? extends Entity>, String>>();
        register((Class<? extends Entity>)EntityItemFrame.class, "Item Frame");
        register((Class<? extends Entity>)EntityEnderCrystal.class, "End Crystal");
        register((Class<? extends Entity>)EntityMinecartEmpty.class, "Minecart");
        register((Class<? extends Entity>)EntityMinecart.class, "Minecart");
        register((Class<? extends Entity>)EntityMinecartFurnace.class, "Minecart with Furnace");
        register((Class<? extends Entity>)EntityMinecartTNT.class, "Minecart with TNT");
    }
}
