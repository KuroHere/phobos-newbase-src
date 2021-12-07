//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tracker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.util.math.*;
import java.util.*;

final class ListenerTick extends ModuleListener<Tracker, TickEvent>
{
    public ListenerTick(final Tracker module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe()) {
            if (((Tracker)this.module).isEnabled()) {
                boolean found = false;
                for (final EntityPlayer player : ListenerTick.mc.world.playerEntities) {
                    if (player != null && !player.equals((Object)ListenerTick.mc.player) && !player.equals((Object)RotationUtil.getRotationPlayer())) {
                        if (PlayerUtil.isFakePlayer((Entity)player)) {
                            continue;
                        }
                        if (found && ((Tracker)this.module).only1v1.getValue()) {
                            ModuleUtil.disableRed((Module)this.module, "Disabled, you are not in a 1v1!");
                            return;
                        }
                        if (((Tracker)this.module).trackedPlayer == null) {
                            ModuleUtil.sendMessage((Module)this.module, "§dNow tracking §5" + player.getName() + "§d" + "!");
                        }
                        ((Tracker)this.module).trackedPlayer = player;
                        found = true;
                    }
                }
                if (((Tracker)this.module).trackedPlayer == null) {
                    return;
                }
                final int exp = ((Tracker)this.module).exp.get() / 64;
                if (((Tracker)this.module).expStacks != exp) {
                    ((Tracker)this.module).expStacks = exp;
                    ModuleUtil.sendMessage((Module)this.module, "§5" + ((Tracker)this.module).trackedPlayer.getName() + "§d" + " used " + "§f" + exp + "§d" + ((exp == 1) ? " stack" : " stacks") + " of Exp!", "Exp");
                }
                final int crystals = ((Tracker)this.module).crystals.get() / 64;
                if (((Tracker)this.module).crystalStacks != crystals) {
                    ((Tracker)this.module).crystalStacks = crystals;
                    ModuleUtil.sendMessage((Module)this.module, "§5" + ((Tracker)this.module).trackedPlayer.getName() + "§d" + " used " + "§f" + crystals + "§d" + ((crystals == 1) ? " stack" : " stacks") + " of Crystals!", "Crystals");
                }
            }
            else if (((Tracker)this.module).awaiting) {
                if (((Tracker)this.module).timer.passed(5000L)) {
                    ((Tracker)this.module).enable();
                    ((Tracker)this.module).awaiting = false;
                    return;
                }
                final double time = MathUtil.round((5000L - ((Tracker)this.module).timer.getTime()) / 1000.0, 1);
                ModuleUtil.sendMessage((Module)this.module, "§dDuel accepted. Tracker will enable in §f" + time + "§d" + " seconds!");
            }
        }
    }
}
