// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ WorldClient.class })
public abstract class MixinWorldClient
{
    private static final ModuleCache<NoRender> NO_RENDER;
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void constructorHook(final CallbackInfo callbackInfo) {
        Bus.EVENT_BUS.post(new WorldClientEvent.Load(WorldClient.class.cast(this)));
    }
    
    @ModifyVariable(method = { "showBarrierParticles(IIIILjava/util/Random;ZLnet/minecraft/util/math/BlockPos$MutableBlockPos;)V" }, at = @At("HEAD"))
    private boolean showBarrierParticlesHook(final boolean holdingBarrier) {
        return MixinWorldClient.NO_RENDER.returnIfPresent(NoRender::showBarriers, false) || holdingBarrier;
    }
    
    @Inject(method = { "onEntityAdded" }, at = { @At("HEAD") })
    private void onEntityAdded(final Entity entity, final CallbackInfo info) {
        Bus.EVENT_BUS.post(new EntityChunkEvent(Stage.PRE, entity));
    }
    
    @Inject(method = { "onEntityRemoved" }, at = { @At("HEAD") })
    private void onEntityRemoved(final Entity entity, final CallbackInfo info) {
        Bus.EVENT_BUS.post(new EntityChunkEvent(Stage.POST, entity));
    }
    
    static {
        NO_RENDER = Caches.getModule(NoRender.class);
    }
}
