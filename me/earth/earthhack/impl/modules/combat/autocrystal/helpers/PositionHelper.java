//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.api.event.bus.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import java.util.*;
import me.earth.earthhack.impl.event.events.network.*;

public class PositionHelper extends SubscriberImpl implements Globals
{
    private final AutoCrystal module;
    private final Map<Entity, MotionTracker> motionTrackerMap;
    
    public PositionHelper(final AutoCrystal module) {
        this.module = module;
        this.motionTrackerMap = new HashMap<Entity, MotionTracker>();
        this.listeners.add(new EventListener<WorldClientEvent>(WorldClientEvent.class) {
            @Override
            public void invoke(final WorldClientEvent event) {
                PositionHelper.this.motionTrackerMap.clear();
            }
        });
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnPlayer.class, event -> event.addPostEvent(() -> {
            if (PositionHelper.mc.world.getEntityByID(((SPacketSpawnPlayer)event.getPacket()).getEntityID()) instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)PositionHelper.mc.world.getEntityByID(((SPacketSpawnPlayer)event.getPacket()).getEntityID());
                this.motionTrackerMap.put((Entity)player, new MotionTracker((World)PositionHelper.mc.world, player));
            }
        })));
        this.listeners.add(new EventListener<UpdateEntitiesEvent>(UpdateEntitiesEvent.class) {
            @Override
            public void invoke(final UpdateEntitiesEvent event) {
                final Map<Entity, MotionTracker> tempMap = new HashMap<Entity, MotionTracker>(PositionHelper.this.motionTrackerMap);
                for (final Map.Entry<Entity, MotionTracker> entry : tempMap.entrySet()) {
                    if (EntityUtil.isDead((Entity)entry.getValue())) {
                        PositionHelper.this.motionTrackerMap.remove(entry.getValue());
                    }
                    else {
                        entry.getValue().updateFromTrackedEntity();
                    }
                }
            }
        });
    }
    
    public MotionTracker getTrackerFromEntity(final Entity player) {
        return this.motionTrackerMap.get(player);
    }
}
