//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.fastswim;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.block.material.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerMove extends ModuleListener<FastSwim, MoveEvent>
{
    public ListenerMove(final FastSwim module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (((FastSwim)this.module).strafe.getValue()) {
            if (!((FastSwim)this.module).accelerate.getValue() && Managers.NCP.passed(250)) {
                if (!ListenerMove.mc.player.onGround) {
                    if (ListenerMove.mc.player.isInsideOfMaterial(Material.LAVA)) {
                        MovementUtil.strafe(event, ((FastSwim)this.module).hLava.getValue());
                        if (!((FastSwim)this.module).fall.getValue()) {
                            if (ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown()) {
                                event.setY(-((FastSwim)this.module).downLava.getValue());
                            }
                            else if (ListenerMove.mc.gameSettings.keyBindJump.isKeyDown()) {
                                event.setY(((FastSwim)this.module).hLava.getValue());
                            }
                            else {
                                event.setY(0.0);
                            }
                        }
                    }
                    else if (ListenerMove.mc.player.isInsideOfMaterial(Material.WATER)) {
                        MovementUtil.strafe(event, ((FastSwim)this.module).hLava.getValue());
                        if (!((FastSwim)this.module).fall.getValue()) {
                            if (ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown()) {
                                event.setY(-((FastSwim)this.module).downLava.getValue());
                            }
                            else if (ListenerMove.mc.gameSettings.keyBindJump.isKeyDown()) {
                                event.setY(((FastSwim)this.module).hLava.getValue());
                            }
                            else {
                                event.setY(0.0);
                            }
                        }
                    }
                }
            }
            else if (((FastSwim)this.module).accelerate.getValue()) {
                if (!ListenerMove.mc.player.onGround) {
                    if (Managers.NCP.passed(250)) {
                        if (ListenerMove.mc.player.isInsideOfMaterial(Material.LAVA)) {
                            final FastSwim fastSwim = (FastSwim)this.module;
                            fastSwim.waterSpeed *= ((FastSwim)this.module).accelerateFactor.getValue();
                        }
                        else if (ListenerMove.mc.player.isInsideOfMaterial(Material.WATER)) {
                            final FastSwim fastSwim2 = (FastSwim)this.module;
                            fastSwim2.lavaSpeed *= ((FastSwim)this.module).accelerateFactor.getValue();
                        }
                    }
                    if (ListenerMove.mc.player.isInsideOfMaterial(Material.LAVA)) {
                        MovementUtil.strafe(event, ((FastSwim)this.module).lavaSpeed);
                        if (!((FastSwim)this.module).fall.getValue()) {
                            if (ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown()) {
                                event.setY(-((FastSwim)this.module).downLava.getValue());
                            }
                            else if (ListenerMove.mc.gameSettings.keyBindJump.isKeyDown()) {
                                event.setY(((FastSwim)this.module).hLava.getValue());
                            }
                            else {
                                event.setY(0.0);
                            }
                        }
                    }
                    else if (ListenerMove.mc.player.isInsideOfMaterial(Material.WATER)) {
                        MovementUtil.strafe(event, ((FastSwim)this.module).waterSpeed);
                        if (!((FastSwim)this.module).fall.getValue()) {
                            if (ListenerMove.mc.gameSettings.keyBindSneak.isKeyDown()) {
                                event.setY(-((FastSwim)this.module).downLava.getValue());
                            }
                            else if (ListenerMove.mc.gameSettings.keyBindJump.isKeyDown()) {
                                event.setY(((FastSwim)this.module).hLava.getValue());
                            }
                            else {
                                event.setY(0.0);
                            }
                        }
                    }
                }
                else {
                    ((FastSwim)this.module).waterSpeed = ((FastSwim)this.module).hWater.getValue();
                    ((FastSwim)this.module).lavaSpeed = ((FastSwim)this.module).hLava.getValue();
                }
            }
        }
        else if (Managers.NCP.passed(250) && !ListenerMove.mc.player.onGround) {
            if (ListenerMove.mc.player.isInsideOfMaterial(Material.LAVA)) {
                event.setX(event.getX() * ((FastSwim)this.module).hLava.getValue());
                event.setY(event.getY() * ((FastSwim)this.module).vLava.getValue());
                event.setZ(event.getZ() * ((FastSwim)this.module).hLava.getValue());
            }
            else if (ListenerMove.mc.player.isInsideOfMaterial(Material.WATER)) {
                event.setX(event.getX() * ((FastSwim)this.module).hWater.getValue());
                event.setY(event.getY() * ((FastSwim)this.module).vWater.getValue());
                event.setZ(event.getZ() * ((FastSwim)this.module).hWater.getValue());
            }
        }
    }
}
