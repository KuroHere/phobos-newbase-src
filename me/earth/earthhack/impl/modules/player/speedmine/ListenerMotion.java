//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.misc.nuker.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<Speedmine, MotionUpdateEvent>
{
    private static final ModuleCache<Nuker> NUKER;
    private static final SettingCache<Boolean, BooleanSetting, Nuker> NUKE;
    
    public ListenerMotion(final Speedmine module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, 999);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (!((Speedmine)this.module).rotate.getValue()) {
            return;
        }
        final Packet<?> packet = ((Speedmine)this.module).limitRotationPacket;
        if (event.getStage() == Stage.PRE && ((Speedmine)this.module).pos != null && !PlayerUtil.isCreative((EntityPlayer)ListenerMotion.mc.player) && (!ListenerMotion.NUKER.isEnabled() || !ListenerMotion.NUKE.getValue()) && (!InventoryUtil.isHolding(Items.EXPERIENCE_BOTTLE) || ListenerMotion.mc.gameSettings.keyBindUseItem.isKeyDown()) && (!((Speedmine)this.module).limitRotations.getValue() || packet != null)) {
            ((Speedmine)this.module).rotations = RotationUtil.getRotations(((Speedmine)this.module).pos, ((Speedmine)this.module).facing);
            event.setYaw(((Speedmine)this.module).rotations[0]);
            event.setPitch(((Speedmine)this.module).rotations[1]);
        }
        else if (event.getStage() == Stage.POST && ((Speedmine)this.module).limitRotations.getValue() && packet != null) {
            final boolean toAir = ((Speedmine)this.module).toAir.getValue();
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                final int last = ListenerMotion.mc.player.inventory.currentItem;
                InventoryUtil.switchTo(((Speedmine)this.module).limitRotationSlot);
                if (((Speedmine)this.module).event.getValue()) {
                    ListenerMotion.mc.player.connection.sendPacket(packet);
                }
                else {
                    NetworkUtil.sendPacketNoEvent((Packet<?>)packet, false);
                }
                InventoryUtil.switchTo(last);
                return;
            });
            ((Speedmine)this.module).onSendPacket();
            ((Speedmine)this.module).limitRotationPacket = null;
            ((Speedmine)this.module).limitRotationSlot = -1;
            ((Speedmine)this.module).postSend(toAir);
        }
    }
    
    static {
        NUKER = Caches.getModule(Nuker.class);
        NUKE = Caches.getSetting(Nuker.class, BooleanSetting.class, "Nuke", false);
    }
}
