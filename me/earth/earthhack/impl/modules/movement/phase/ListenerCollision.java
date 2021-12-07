//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.phase.mode.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;

final class ListenerCollision extends ModuleListener<Phase, CollisionEvent>
{
    public ListenerCollision(final Phase module) {
        super(module, (Class<? super Object>)CollisionEvent.class);
    }
    
    public void invoke(final CollisionEvent event) {
        if (ListenerCollision.mc.player == null || ListenerCollision.mc.player.movementInput == null || !ListenerCollision.mc.player.equals((Object)event.getEntity())) {
            return;
        }
        switch (((Phase)this.module).mode.getValue()) {
            case Constantiam: {
                if (event.getBB() != null && event.getBB().maxY > ListenerCollision.mc.player.getEntityBoundingBox().minY && ListenerCollision.mc.world.getBlockState(PositionUtil.getPosition().up()).getBlock() != Blocks.AIR) {
                    event.setBB(null);
                    break;
                }
                break;
            }
            case ConstantiamNew: {
                if (((Phase)this.module).isPhasing()) {
                    event.setBB(null);
                    break;
                }
                break;
            }
            case Normal: {
                if ((((Phase)this.module).onlyBlock.getValue() && !((Phase)this.module).isPhasing()) || (((Phase)this.module).autoClick.getValue() && ((Phase)this.module).requireClick.getValue() && ((Phase)this.module).clickBB.getValue() && !((Phase)this.module).clickTimer.passed(((Phase)this.module).clickDelay.getValue())) || (((Phase)this.module).forwardBB.getValue() && !ListenerCollision.mc.gameSettings.keyBindForward.isKeyDown())) {
                    return;
                }
                if (event.getBB() != null && event.getBB().maxY > ListenerCollision.mc.player.getEntityBoundingBox().minY && ListenerCollision.mc.player.isSneaking()) {
                    event.setBB(null);
                    break;
                }
                break;
            }
            case Sand: {
                event.setBB(null);
                ListenerCollision.mc.player.noClip = true;
                break;
            }
            case Climb: {
                if (ListenerCollision.mc.player.isCollidedHorizontally) {
                    event.setBB(null);
                }
                if (ListenerCollision.mc.player.movementInput.sneak || (ListenerCollision.mc.player.movementInput.jump && event.getPos().getY() > ListenerCollision.mc.player.posY)) {
                    event.setCancelled(true);
                    break;
                }
                break;
            }
        }
    }
}
