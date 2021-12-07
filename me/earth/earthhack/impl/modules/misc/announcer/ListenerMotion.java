//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

final class ListenerMotion extends ModuleListener<Announcer, MotionUpdateEvent>
{
    public ListenerMotion(final Announcer module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            if (((Announcer)this.module).refresh.getValue()) {
                ((Announcer)this.module).reset();
                ((Announcer)this.module).loadFiles();
                ChatUtil.sendMessage("§a<§f" + ((Announcer)this.module).getDisplayName() + "§a" + "> Files loaded.");
                ((Announcer)this.module).refresh.setValue(false);
            }
            if (((Announcer)this.module).distance.getValue()) {
                final Announcer announcer = (Announcer)this.module;
                announcer.travelled += MovementUtil.getDistance2D();
            }
            if (((Announcer)this.module).autoEZ.getValue()) {
                final EntityPlayer autoCrystal = Managers.TARGET.getAutoCrystal();
                final Entity killAura = Managers.TARGET.getKillAura();
                if (autoCrystal != null) {
                    ((Announcer)this.module).targets.add(Managers.TARGET.getAutoCrystal());
                }
                if (killAura instanceof EntityPlayer) {
                    ((Announcer)this.module).targets.add((EntityPlayer)killAura);
                }
            }
            if (((Announcer)this.module).timer.passed(((Announcer)this.module).delay.getValue() * 1000.0)) {
                final String next = ((Announcer)this.module).getNextMessage();
                if (next != null) {
                    ListenerMotion.mc.player.sendChatMessage(next);
                    ((Announcer)this.module).timer.reset();
                }
            }
        }
    }
}
