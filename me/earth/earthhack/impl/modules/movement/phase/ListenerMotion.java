//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.movement.phase.mode.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.material.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

final class ListenerMotion extends ModuleListener<Phase, MotionUpdateEvent>
{
    private static final double[] off;
    private final StopWatch timer;
    
    public ListenerMotion(final Phase module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
        this.timer = new StopWatch();
    }
    
    public void invoke(final MotionUpdateEvent event) {
        final double xSpeed = ListenerMotion.mc.player.getHorizontalFacing().getDirectionVec().getX() * 0.1;
        final double zSpeed = ListenerMotion.mc.player.getHorizontalFacing().getDirectionVec().getZ() * 0.1;
        switch (((Phase)this.module).mode.getValue()) {
            case Constantiam: {
                if (event.getStage() == Stage.PRE && ListenerMotion.mc.player.isCollidedHorizontally && !((Phase)this.module).isPhasing()) {
                    event.setY(event.getY() - 0.032);
                }
                if (event.getStage() == Stage.PRE && ((Phase)this.module).isPhasing() && ListenerMotion.mc.world.getBlockState(PositionUtil.getPosition().up()).getBlock() == Blocks.AIR) {
                    event.setY(event.getY() - 0.032);
                }
            }
            case Normal: {
                if (event.getStage() != Stage.PRE || !ListenerMotion.mc.player.isSneaking() || !((Phase)this.module).isPhasing() || (((Phase)this.module).requireForward.getValue() && !ListenerMotion.mc.gameSettings.keyBindForward.isKeyDown())) {
                    break;
                }
                if (this.checkAutoClick()) {
                    return;
                }
                final float yaw = ListenerMotion.mc.player.rotationYaw;
                ListenerMotion.mc.player.setEntityBoundingBox(ListenerMotion.mc.player.getEntityBoundingBox().offset(((Phase)this.module).distance.getValue() * Math.cos(Math.toRadians(yaw + 90.0f)), 0.0, ((Phase)this.module).distance.getValue() * Math.sin(Math.toRadians(yaw + 90.0f))));
                break;
            }
            case Sand: {
                ListenerMotion.mc.player.motionY = 0.0;
                if (ListenerMotion.mc.inGameHasFocus) {
                    if (ListenerMotion.mc.player.movementInput.jump) {
                        final EntityPlayerSP player = ListenerMotion.mc.player;
                        player.motionY += 0.3;
                    }
                    if (ListenerMotion.mc.player.movementInput.sneak) {
                        final EntityPlayerSP player2 = ListenerMotion.mc.player;
                        player2.motionY -= 0.3;
                    }
                }
                ListenerMotion.mc.player.noClip = true;
                break;
            }
            case Packet: {
                if (ListenerMotion.mc.player.isCollidedHorizontally && ((Phase)this.module).timer.passed(200L)) {
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY + 0.05, ListenerMotion.mc.player.posZ, true));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX + xSpeed * ((Phase)this.module).speed.getValue(), ListenerMotion.mc.player.posY, ListenerMotion.mc.player.posZ + zSpeed * ((Phase)this.module).speed.getValue(), true));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY, ListenerMotion.mc.player.posZ, true));
                    ((Phase)this.module).timer.reset();
                    break;
                }
                break;
            }
            case Skip: {
                if (event.getStage() != Stage.PRE || !ListenerMotion.mc.player.isCollidedHorizontally) {
                    break;
                }
                if (!this.timer.passed(((Phase)this.module).skipTime.getValue())) {
                    if (((Phase)this.module).cancel.getValue()) {
                        event.setCancelled(true);
                    }
                    return;
                }
                float direction = ListenerMotion.mc.player.rotationYaw;
                if (ListenerMotion.mc.player.field_191988_bg < 0.0f) {
                    direction += 180.0f;
                }
                if (ListenerMotion.mc.player.moveStrafing > 0.0f) {
                    direction -= 90.0f * ((ListenerMotion.mc.player.field_191988_bg < 0.0f) ? -0.5f : ((ListenerMotion.mc.player.field_191988_bg > 0.0f) ? 0.5f : 1.0f));
                }
                if (ListenerMotion.mc.player.moveStrafing < 0.0f) {
                    direction += 90.0f * ((ListenerMotion.mc.player.field_191988_bg < 0.0f) ? -0.5f : ((ListenerMotion.mc.player.field_191988_bg > 0.0f) ? 0.5f : 1.0f));
                }
                final double x = Math.cos(Math.toRadians(direction + 90.0f)) * 0.2;
                final double z = Math.sin(Math.toRadians(direction + 90.0f)) * 0.2;
                if (((Phase)this.module).limit.getValue()) {
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY, ListenerMotion.mc.player.posZ, ListenerMotion.mc.player.onGround));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX + x * 0.0010000000474974513, ListenerMotion.mc.player.posY + 0.10000000149011612, ListenerMotion.mc.player.posZ + z * 0.0010000000474974513, ListenerMotion.mc.player.onGround));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX + x * 0.029999999329447746, 0.0, ListenerMotion.mc.player.posZ + z * 0.029999999329447746, ListenerMotion.mc.player.onGround));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX + x * 0.05999999865889549, ListenerMotion.mc.player.posY, ListenerMotion.mc.player.posZ + z * 0.05999999865889549, ListenerMotion.mc.player.onGround));
                    event.setCancelled(true);
                    this.timer.reset();
                    return;
                }
                for (int index = 0; index < ListenerMotion.off.length; ++index) {
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY + ListenerMotion.off[index], ListenerMotion.mc.player.posZ, ListenerMotion.mc.player.onGround));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX + x * index, ListenerMotion.mc.player.posY, ListenerMotion.mc.player.posZ + z * index, ListenerMotion.mc.player.onGround));
                }
                event.setCancelled(true);
                this.timer.reset();
                break;
            }
        }
    }
    
    private boolean checkAutoClick() {
        if (!((Phase)this.module).autoClick.getValue()) {
            return false;
        }
        if (!((Phase)this.module).clickTimer.passed(((Phase)this.module).clickDelay.getValue())) {
            return ((Phase)this.module).requireClick.getValue();
        }
        RayTraceResult result = ListenerMotion.mc.objectMouseOver;
        if (((Phase)this.module).smartClick.getValue()) {
            final EnumFacing facing = ListenerMotion.mc.player.getHorizontalFacing();
            BlockPos pos = PositionUtil.getPosition().offset(facing);
            if (!ListenerMotion.mc.player.getEntityBoundingBox().intersectsWith(new AxisAlignedBB(pos))) {
                pos = PositionUtil.getPosition();
            }
            if ((ListenerMotion.mc.objectMouseOver != null && pos.equals((Object)ListenerMotion.mc.objectMouseOver.getBlockPos())) || pos.up().equals((Object)ListenerMotion.mc.objectMouseOver.getBlockPos())) {
                result = ListenerMotion.mc.objectMouseOver;
            }
            else {
                BlockPos target = pos.up();
                if (ListenerMotion.mc.world.getBlockState(target).getMaterial() == Material.AIR) {
                    target = pos;
                }
                result = new RayTraceResult(new Vec3d(0.0, 0.5, 0.0), facing.getOpposite(), target);
            }
        }
        if (result != null && result.getBlockPos() != null) {
            final float[] r = RayTraceUtil.hitVecToPlaceVec(result.getBlockPos(), result.hitVec);
            final EnumHand hand = (ListenerMotion.mc.player.getHeldItemOffhand().getItem() instanceof ItemFood || ListenerMotion.mc.player.getHeldItemOffhand().getItem() == Items.field_190929_cY) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            final Packet<?> packet = (Packet<?>)new CPacketPlayerTryUseItemOnBlock(result.getBlockPos(), result.sideHit, hand, r[0], r[1], r[2]);
            NetworkUtil.sendPacketNoEvent(packet);
            ((Phase)this.module).pos = result.getBlockPos();
            ((Phase)this.module).clickTimer.reset();
            return false;
        }
        return ((Phase)this.module).requireClick.getValue();
    }
    
    static {
        off = new double[] { -0.02500000037252903, -0.028571428997176036, -0.033333333830038704, -0.04000000059604645, -0.05000000074505806, -0.06666666766007741, -0.10000000149011612, -0.20000000298023224, -0.04000000059604645, -0.033333333830038704, -0.028571428997176036, -0.02500000037252903 };
    }
}
