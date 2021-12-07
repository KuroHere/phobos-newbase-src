//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.killaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import java.util.*;

final class ListenerGameLoop extends ModuleListener<KillAura, GameLoopEvent>
{
    public ListenerGameLoop(final KillAura module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        final Entity from = (Entity)RotationUtil.getRotationPlayer();
        if (ListenerGameLoop.mc.world == null || from == null) {
            return;
        }
        final boolean k = DamageUtil.isSharper(ListenerGameLoop.mc.player.getHeldItemMainhand(), 1000);
        final boolean multi = ((KillAura)this.module).multi32k.getValue() && k;
        if ((((KillAura)this.module).target != null || multi) && ((KillAura)this.module).shouldAttack() && (!((KillAura)this.module).rotate.getValue() || RotationUtil.isLegit(((KillAura)this.module).target, new Entity[0])) && (!((KillAura)this.module).delay.getValue() || (((KillAura)this.module).t2k.getValue() && k)) && ((KillAura)this.module).cps.getValue() > 20.0f && ((KillAura)this.module).timer.passed((long)(1000.0 / ((KillAura)this.module).cps.getValue()))) {
            if (multi) {
                int packets = 0;
                for (final EntityPlayer player : ListenerGameLoop.mc.world.playerEntities) {
                    if (((KillAura)this.module).isValid((Entity)player) && ((KillAura)this.module).isInRange(from, (Entity)player)) {
                        PacketUtil.attack((Entity)player);
                        if (++packets >= ((KillAura)this.module).packets.getValue()) {
                            break;
                        }
                        continue;
                    }
                }
            }
            else {
                for (int i = 0; i < ((KillAura)this.module).packets.getValue(); ++i) {
                    PacketUtil.attack(((KillAura)this.module).target);
                }
                if (((KillAura)this.module).swing.getValue() == Swing.Client || ((KillAura)this.module).swing.getValue() == Swing.Full) {
                    Swing.Client.swing(EnumHand.MAIN_HAND);
                }
            }
            ((KillAura)this.module).timer.reset((long)(1000.0 / ((KillAura)this.module).cps.getValue()));
        }
    }
}
