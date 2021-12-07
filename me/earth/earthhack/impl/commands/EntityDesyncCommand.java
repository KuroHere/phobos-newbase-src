//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.event.events.network.*;

public class EntityDesyncCommand extends Command implements Globals
{
    private Entity dismounted;
    
    public EntityDesyncCommand() {
        super(new String[][] { { "entitydesync" }, { "dismount", "remount", "delete" } });
        CommandDescriptions.register(this, "EntityDesync for exploit purposes.");
        Bus.EVENT_BUS.register(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                EntityDesyncCommand.this.dismounted = null;
            }
        });
        Bus.EVENT_BUS.register(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
                if (Globals.mc.player != null && EntityDesyncCommand.this.dismounted != null) {
                    if (Globals.mc.player.isRiding()) {
                        EntityDesyncCommand.this.dismounted = null;
                        return;
                    }
                    EntityDesyncCommand.this.dismounted.setPosition(Globals.mc.player.posX, Globals.mc.player.posY, Globals.mc.player.posZ);
                    Globals.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(EntityDesyncCommand.this.dismounted));
                }
            }
        });
        Bus.EVENT_BUS.register(new SendListener<Object>(CPacketPlayer.Position.class, event -> {
            if (this.dismounted != null) {
                event.setCancelled(true);
            }
            return;
        }));
        Bus.EVENT_BUS.register(new SendListener<Object>(CPacketPlayer.PositionRotation.class, event -> {
            if (this.dismounted != null) {
                event.setCancelled(true);
            }
        }));
    }
    
    @Override
    public void execute(final String[] args) {
        if (EntityDesyncCommand.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be ingame to use this command.");
            return;
        }
        if (args.length == 1) {
            if (this.dismounted == null) {
                ChatUtil.sendMessage("You are currently not desynced from any entity.");
            }
            else {
                ChatUtil.sendMessage("You are currently dismounted.");
            }
            return;
        }
        if (args[1].equalsIgnoreCase("dismount")) {
            final Entity entity = EntityDesyncCommand.mc.player.getRidingEntity();
            if (entity == null) {
                ChatUtil.sendMessage("§cThere's no entity to dismount!");
                return;
            }
            this.dismounted = entity;
            EntityDesyncCommand.mc.player.dismountRidingEntity();
            EntityDesyncCommand.mc.world.removeEntity(entity);
            ChatUtil.sendMessage("§aDismounted successfully.");
        }
        else if (args[1].equalsIgnoreCase("remount")) {
            if (this.dismounted == null) {
                ChatUtil.sendMessage("§cThere's no entity to remount!");
                return;
            }
            this.dismounted.isDead = false;
            EntityDesyncCommand.mc.world.spawnEntityInWorld(this.dismounted);
            EntityDesyncCommand.mc.player.startRiding(this.dismounted, true);
            ChatUtil.sendMessage("§aRemounted successfully.");
        }
        else if (args[1].equalsIgnoreCase("delete")) {
            if (this.dismounted == null) {
                ChatUtil.sendMessage("§cThere's no entity to delete!");
                return;
            }
            this.dismounted = null;
            ChatUtil.sendMessage("§aDeleted dismounted entity.");
        }
        else {
            ChatUtil.sendMessage("§cUnrecognized option, try dis/remount/delete.");
        }
    }
}
