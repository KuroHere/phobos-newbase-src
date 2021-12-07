//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.ncptweaks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.item.*;

final class ListenerWindowClick extends ModuleListener<NCPTweaks, PacketEvent.Send<CPacketClickWindow>>
{
    private final StopWatch timer;
    
    public ListenerWindowClick(final NCPTweaks module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, -1001, CPacketClickWindow.class);
        this.timer = new StopWatch();
    }
    
    public void invoke(final PacketEvent.Send<CPacketClickWindow> event) {
        if (((NCPTweaks)this.module).eating.getValue() && this.isEating()) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> ListenerWindowClick.mc.playerController.onStoppedUsingItem((EntityPlayer)ListenerWindowClick.mc.player));
        }
        if (((NCPTweaks)this.module).moving.getValue()) {
            if (((NCPTweaks)this.module).packet.getValue() && this.timer.passed(((NCPTweaks)this.module).delay.getValue())) {
                final float yaw = ((IEntityPlayerSP)ListenerWindowClick.mc.player).getLastReportedYaw();
                PacketUtil.doRotation((float)(yaw + 4.0E-4), ((IEntityPlayerSP)ListenerWindowClick.mc.player).getLastReportedPitch(), ListenerWindowClick.mc.player.onGround);
                this.timer.reset();
            }
            ListenerWindowClick.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
        if (((NCPTweaks)this.module).resetNCP.getValue()) {
            Managers.NCP.reset();
        }
    }
    
    private boolean isEating() {
        final ItemStack stack = ListenerWindowClick.mc.player.getActiveItemStack();
        if (ListenerWindowClick.mc.player.isHandActive() && !stack.func_190926_b()) {
            final Item item = stack.getItem();
            return item.getItemUseAction(stack) == EnumAction.EAT && item.getMaxItemUseDuration(stack) - ListenerWindowClick.mc.player.getItemInUseCount() >= 5;
        }
        return false;
    }
}
