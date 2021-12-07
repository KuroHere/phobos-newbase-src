//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.misc.nuker.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.combat.antisurround.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerDamage extends ModuleListener<Speedmine, DamageBlockEvent>
{
    private static final ModuleCache<Nuker> NUKER;
    private static final SettingCache<Boolean, BooleanSetting, Nuker> NUKE;
    private static final ModuleCache<AntiSurround> ANTISURROUND;
    
    public ListenerDamage(final Speedmine module) {
        super(module, (Class<? super Object>)DamageBlockEvent.class);
    }
    
    public void invoke(final DamageBlockEvent event) {
        if (ListenerDamage.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false)) {
            return;
        }
        ((Speedmine)this.module).checkReset();
        if (MineUtil.canBreak(event.getPos()) && !PlayerUtil.isCreative((EntityPlayer)ListenerDamage.mc.player) && (!ListenerDamage.NUKER.isEnabled() || !ListenerDamage.NUKE.getValue())) {
            switch (((Speedmine)this.module).mode.getValue()) {
                case Reset: {
                    this.setPos(event);
                    break;
                }
                case Packet:
                case Civ: {
                    this.setPos(event);
                    ListenerDamage.mc.player.swingArm(EnumHand.MAIN_HAND);
                    final CPacketPlayerDigging start = new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFacing());
                    final CPacketPlayerDigging stop = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFacing());
                    if (((Speedmine)this.module).event.getValue()) {
                        ListenerDamage.mc.player.connection.sendPacket((Packet)start);
                        ListenerDamage.mc.player.connection.sendPacket((Packet)stop);
                    }
                    else {
                        NetworkUtil.sendPacketNoEvent((Packet<?>)start, false);
                        NetworkUtil.sendPacketNoEvent((Packet<?>)stop, false);
                    }
                    if (((Speedmine)this.module).swingStop.getValue()) {
                        Swing.Packet.swing(EnumHand.MAIN_HAND);
                    }
                    event.setCancelled(true);
                    break;
                }
                case Damage: {
                    this.setPos(event);
                    if (((IPlayerControllerMP)ListenerDamage.mc.playerController).getCurBlockDamageMP() >= ((Speedmine)this.module).limit.getValue()) {
                        ((IPlayerControllerMP)ListenerDamage.mc.playerController).setCurBlockDamageMP(1.0f);
                        break;
                    }
                    break;
                }
                case Smart: {
                    boolean aborted = false;
                    if (((Speedmine)this.module).pos != null && !((Speedmine)this.module).pos.equals((Object)event.getPos())) {
                        ((Speedmine)this.module).abortCurrentPos();
                        aborted = true;
                    }
                    if (!aborted && !((Speedmine)this.module).timer.passed(((Speedmine)this.module).delay.getValue())) {
                        break;
                    }
                    if (!aborted && ((Speedmine)this.module).pos != null && ((Speedmine)this.module).pos.equals((Object)event.getPos())) {
                        ((Speedmine)this.module).abortCurrentPos();
                        ((Speedmine)this.module).timer.reset();
                        return;
                    }
                    this.setPos(event);
                    ListenerDamage.mc.player.swingArm(EnumHand.MAIN_HAND);
                    final CPacketPlayerDigging packet = new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFacing());
                    if (((Speedmine)this.module).event.getValue()) {
                        ListenerDamage.mc.player.connection.sendPacket((Packet)packet);
                    }
                    else {
                        NetworkUtil.sendPacketNoEvent((Packet<?>)packet, false);
                    }
                    event.setCancelled(true);
                    ((Speedmine)this.module).timer.reset();
                    break;
                }
                case Instant: {
                    boolean abortedd = false;
                    if (((Speedmine)this.module).pos != null && !((Speedmine)this.module).pos.equals((Object)event.getPos())) {
                        ((Speedmine)this.module).abortCurrentPos();
                        abortedd = true;
                    }
                    if (!abortedd && !((Speedmine)this.module).timer.passed(((Speedmine)this.module).delay.getValue())) {
                        break;
                    }
                    if (!abortedd && ((Speedmine)this.module).pos != null && ((Speedmine)this.module).pos.equals((Object)event.getPos())) {
                        ((Speedmine)this.module).abortCurrentPos();
                        ((Speedmine)this.module).timer.reset();
                        return;
                    }
                    this.setPos(event);
                    ListenerDamage.mc.player.swingArm(EnumHand.MAIN_HAND);
                    final CPacketPlayerDigging packet2 = new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFacing());
                    if (((Speedmine)this.module).event.getValue()) {
                        ListenerDamage.mc.player.connection.sendPacket((Packet)packet2);
                    }
                    else {
                        NetworkUtil.sendPacketNoEvent((Packet<?>)packet2, false);
                    }
                    event.setCancelled(((Speedmine)this.module).shouldAbort = true);
                    ((Speedmine)this.module).timer.reset();
                    break;
                }
            }
        }
    }
    
    private void setPos(final DamageBlockEvent event) {
        ((Speedmine)this.module).reset();
        ((Speedmine)this.module).pos = event.getPos();
        ((Speedmine)this.module).facing = event.getFacing();
        ((Speedmine)this.module).bb = ListenerDamage.mc.world.getBlockState(((Speedmine)this.module).pos).getSelectedBoundingBox((World)ListenerDamage.mc.world, ((Speedmine)this.module).pos).expandXyz(0.0020000000949949026);
    }
    
    static {
        NUKER = Caches.getModule(Nuker.class);
        NUKE = Caches.getSetting(Nuker.class, BooleanSetting.class, "Nuke", false);
        ANTISURROUND = Caches.getModule(AntiSurround.class);
    }
}
