//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.trajectories;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class Trajectories extends Module
{
    public final Setting<Color> color;
    public final Setting<Boolean> landed;
    
    public Trajectories() {
        super("Trajectories", Category.Render);
        this.color = this.register(new ColorSetting("Color", new Color(0, 255, 0)));
        this.landed = this.register(new BooleanSetting("Landed", true));
        this.listeners.add(new ListenerRender(this));
    }
    
    protected boolean isThrowable(final Item item) {
        return item instanceof ItemEnderPearl || item instanceof ItemExpBottle || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion;
    }
    
    protected float getDistance(final Item item) {
        return (item instanceof ItemBow) ? 1.0f : 0.4f;
    }
    
    protected float getThrowVelocity(final Item item) {
        if (item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion) {
            return 0.5f;
        }
        if (item instanceof ItemExpBottle) {
            return 0.59f;
        }
        return 1.5f;
    }
    
    protected int getThrowPitch(final Item item) {
        if (item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemExpBottle) {
            return 20;
        }
        return 0;
    }
    
    protected float getGravity(final Item item) {
        if (item instanceof ItemBow || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemExpBottle) {
            return 0.05f;
        }
        return 0.03f;
    }
    
    protected List<Entity> getEntitiesWithinAABB(final AxisAlignedBB bb) {
        final ArrayList<Entity> list = new ArrayList<Entity>();
        final int chunkMinX = MathHelper.floor((bb.minX - 2.0) / 16.0);
        final int chunkMaxX = MathHelper.floor((bb.maxX + 2.0) / 16.0);
        final int chunkMinZ = MathHelper.floor((bb.minZ - 2.0) / 16.0);
        final int chunkMaxZ = MathHelper.floor((bb.maxZ + 2.0) / 16.0);
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (Trajectories.mc.world.getChunkProvider().getLoadedChunk(x, z) != null) {
                    Trajectories.mc.world.getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity((Entity)Trajectories.mc.player, bb, (List)list, EntitySelectors.NOT_SPECTATING);
                }
            }
        }
        return list;
    }
}
