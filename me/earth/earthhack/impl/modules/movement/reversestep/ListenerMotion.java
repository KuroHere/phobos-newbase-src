//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.reversestep;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.movement.packetfly.*;
import me.earth.earthhack.impl.modules.movement.blocklag.*;
import me.earth.earthhack.impl.modules.movement.longjump.*;
import me.earth.earthhack.impl.modules.movement.holetp.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.movement.speed.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.math.position.*;
import java.util.*;
import net.minecraft.entity.item.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.*;

final class ListenerMotion extends ModuleListener<ReverseStep, MotionUpdateEvent>
{
    private static final ModuleCache<PacketFly> PACKET_FLY;
    private static final ModuleCache<BlockLag> BLOCK_LAG;
    private static final ModuleCache<Speed> SPEED;
    private static final ModuleCache<LongJump> LONGJUMP;
    private static final ModuleCache<HoleTP> HOLETP;
    private static final SettingCache<SpeedMode, EnumSetting<SpeedMode>, Speed> SPEED_MODE;
    private boolean Reset;
    
    public ListenerMotion(final ReverseStep module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
        this.Reset = false;
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.POST) {
            if (PositionUtil.inLiquid(true) || PositionUtil.inLiquid(false) || ListenerMotion.PACKET_FLY.isEnabled() || ListenerMotion.BLOCK_LAG.isEnabled() || ListenerMotion.LONGJUMP.isEnabled() || (ListenerMotion.HOLETP.get().isInHole() && ListenerMotion.HOLETP.isEnabled()) || (ListenerMotion.SPEED.isEnabled() && ListenerMotion.SPEED_MODE.getValue() != SpeedMode.Instant)) {
                this.Reset = true;
                return;
            }
            final List<EntityEnderPearl> pearls = (List<EntityEnderPearl>)ListenerMotion.mc.world.loadedEntityList.stream().filter(EntityEnderPearl.class::isInstance).map(EntityEnderPearl.class::cast).collect(Collectors.toList());
            if (!pearls.isEmpty()) {
                ((ReverseStep)this.module).waitForOnGround = true;
            }
            if (!ListenerMotion.mc.player.onGround) {
                if (ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown()) {
                    ((ReverseStep)this.module).jumped = true;
                }
            }
            else {
                ((ReverseStep)this.module).jumped = false;
                this.Reset = false;
                ((ReverseStep)this.module).waitForOnGround = false;
            }
            if (!((ReverseStep)this.module).jumped && ListenerMotion.mc.player.fallDistance < 0.5 && ListenerMotion.mc.player.posY - ((ReverseStep)this.module).getNearestBlockBelow() > 0.625 && ListenerMotion.mc.player.posY - ((ReverseStep)this.module).getNearestBlockBelow() <= 3.125 && !this.Reset && !((ReverseStep)this.module).waitForOnGround) {
                if (!ListenerMotion.mc.player.onGround) {
                    final ReverseStep reverseStep = (ReverseStep)this.module;
                    ++reverseStep.packets;
                }
                if (!ListenerMotion.mc.player.onGround && ListenerMotion.mc.player.motionY < 0.0 && !ListenerMotion.mc.player.isOnLadder() && !ListenerMotion.mc.player.isEntityInsideOpaqueBlock() && !ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown() && ((ReverseStep)this.module).packets > 0) {
                    final EntityPlayerSP player = ListenerMotion.mc.player;
                    --player.motionY;
                    ((ReverseStep)this.module).packets = 0;
                }
            }
        }
    }
    
    static {
        PACKET_FLY = Caches.getModule(PacketFly.class);
        BLOCK_LAG = Caches.getModule(BlockLag.class);
        SPEED = Caches.getModule(Speed.class);
        LONGJUMP = Caches.getModule(LongJump.class);
        HOLETP = Caches.getModule(HoleTP.class);
        SPEED_MODE = Caches.getSetting(Speed.class, Setting.class, "Mode", SpeedMode.Instant);
    }
}
