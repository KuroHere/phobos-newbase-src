// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import me.earth.earthhack.impl.core.ducks.network.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ CPacketUseEntity.class })
public abstract class MixinCPacketUseEntity implements ICPacketUseEntity
{
    private Entity entity;
    
    @Accessor("entityId")
    @Override
    public abstract void setEntityId(final int p0);
    
    @Accessor("action")
    @Override
    public abstract void setAction(final CPacketUseEntity.Action p0);
    
    @Accessor("hitVec")
    @Override
    public abstract void setVec(final Vec3d p0);
    
    @Accessor("hand")
    @Override
    public abstract void setHand(final EnumHand p0);
    
    @Accessor("entityId")
    @Override
    public abstract int getEntityID();
    
    @Accessor("action")
    @Override
    public abstract CPacketUseEntity.Action getAction();
    
    @Accessor("hitVec")
    @Override
    public abstract Vec3d getHitVec();
    
    @Accessor("hand")
    @Override
    public abstract EnumHand getHand();
    
    @Override
    public Entity getAttackedEntity() {
        return this.entity;
    }
    
    @Inject(method = { "<init>(Lnet/minecraft/entity/Entity;)V" }, at = { @At("RETURN") })
    public void initHook(final Entity entity, final CallbackInfo info) {
        this.entity = entity;
    }
}
