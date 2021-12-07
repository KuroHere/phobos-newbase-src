//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import java.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.thread.*;

public class ServerTimeHelper extends SubscriberImpl implements Globals
{
    private static final ScheduledExecutorService THREAD;
    private final AutoCrystal module;
    private final Setting<ACRotate> rotate;
    private final Setting<SwingTime> placeSwing;
    private final Setting<Boolean> antiFeetPlace;
    private final Setting<Boolean> newVersion;
    private final Setting<Integer> buffer;
    
    public ServerTimeHelper(final AutoCrystal module, final Setting<ACRotate> rotate, final Setting<SwingTime> placeSwing, final Setting<Boolean> antiFeetPlace, final Setting<Boolean> newVersion, final Setting<Integer> buffer) {
        this.module = module;
        this.rotate = rotate;
        this.placeSwing = placeSwing;
        this.antiFeetPlace = antiFeetPlace;
        this.newVersion = newVersion;
        this.buffer = buffer;
    }
    
    public void onUseEntity(final CPacketUseEntity packet, final Entity crystal) {
        final EntityPlayer closest;
        if (packet.getAction() == CPacketUseEntity.Action.ATTACK && this.antiFeetPlace.getValue() && (this.rotate.getValue() == ACRotate.None || this.rotate.getValue() == ACRotate.Break) && crystal instanceof EntityEnderCrystal && (closest = EntityUtil.getClosestEnemy()) != null && BlockUtil.isSemiSafe(closest, true, this.newVersion.getValue()) && BlockUtil.isAtFeet(ServerTimeHelper.mc.world.playerEntities, crystal.getPosition().down(), true, this.newVersion.getValue())) {
            final int intoTick = Managers.TICK.getTickTimeAdjusted();
            final int sleep = Managers.TICK.getServerTickLengthMS() + Managers.TICK.getSpawnTime() + this.buffer.getValue() - intoTick;
            this.place(crystal.getPosition().down(), sleep);
        }
    }
    
    private void place(final BlockPos pos, final int sleep) {
        final SwingTime time = this.placeSwing.getValue();
        ServerTimeHelper.THREAD.schedule(() -> {
            if (InventoryUtil.isHolding(Items.END_CRYSTAL)) {
                final EnumHand hand = InventoryUtil.getHand(Items.END_CRYSTAL);
                final RayTraceResult ray = RotationUtil.rayTraceTo(pos, (IBlockAccess)ServerTimeHelper.mc.world);
                final float[] f = RayTraceUtil.hitVecToPlaceVec(pos, ray.hitVec);
                if (time == SwingTime.Pre) {
                    Swing.Packet.swing(hand);
                    Swing.Client.swing(hand);
                }
                ServerTimeHelper.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, ray.sideHit, hand, f[0], f[1], f[2]));
                if (time == SwingTime.Post) {
                    Swing.Packet.swing(hand);
                    Swing.Client.swing(hand);
                }
            }
        }, sleep, TimeUnit.MILLISECONDS);
    }
    
    static {
        THREAD = ThreadUtil.newDaemonScheduledExecutor("Server-Helper");
    }
}
