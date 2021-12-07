//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.longjump;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.longjump.mode.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

public class LongJump extends DisablingModule
{
    protected final Setting<JumpMode> mode;
    protected final Setting<Double> boost;
    protected final Setting<Boolean> noKick;
    protected final Setting<Bind> invalidBind;
    protected int stage;
    protected int airTicks;
    protected int groundTicks;
    protected double speed;
    protected double distance;
    
    public LongJump() {
        super("LongJump", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", JumpMode.Normal));
        this.boost = this.register(new NumberSetting("Boost", 4.5, 0.1, 20.0));
        this.noKick = this.register(new BooleanSetting("AntiKick", true));
        this.invalidBind = this.register(new BindSetting("Invalid", Bind.fromKey(50)));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerPosLook(this));
        this.setData(new LongJumpData(this));
    }
    
    @Override
    protected void onEnable() {
        if (LongJump.mc.player != null) {
            this.distance = MovementUtil.getDistance2D();
            this.speed = MovementUtil.getSpeed();
        }
        this.stage = 0;
        this.airTicks = 0;
        this.groundTicks = 0;
    }
    
    @Override
    protected void onDisable() {
        Managers.TIMER.reset();
    }
    
    protected void invalidPacket() {
        this.updatePosition(0.0, 2.147483647E9, 0.0);
    }
    
    protected void updatePosition(final double x, final double y, final double z) {
        LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z, LongJump.mc.player.onGround));
    }
    
    protected double getDistance(final EntityPlayer player, final double distance) {
        final List<AxisAlignedBB> boundingBoxes = player.world.getCollisionBoxes((Entity)player, player.getEntityBoundingBox().offset(0.0, -distance, 0.0));
        if (boundingBoxes.isEmpty()) {
            return 0.0;
        }
        double y = 0.0;
        for (final AxisAlignedBB boundingBox : boundingBoxes) {
            if (boundingBox.maxY > y) {
                y = boundingBox.maxY;
            }
        }
        return player.posY - y;
    }
}
