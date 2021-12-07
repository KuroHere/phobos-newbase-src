//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.crosshair;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.crosshair.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;

final class ListenerRender extends ModuleListener<Crosshair, Render2DEvent>
{
    public ListenerRender(final Crosshair module) {
        super(module, (Class<? super Object>)Render2DEvent.class);
    }
    
    public void invoke(final Render2DEvent event) {
        final int screenMiddleX = event.getResolution().getScaledWidth() / 2;
        final int screenMiddleY = event.getResolution().getScaledHeight() / 2;
        final float width = ((Crosshair)this.module).width.getValue();
        if (((Crosshair)this.module).gapMode.getValue() != GapMode.NONE) {
            Render2DUtil.drawBorderedRect(screenMiddleX - width, screenMiddleY - (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) - ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), screenMiddleX + ((Crosshair)this.module).width.getValue(), screenMiddleY - ((Crosshair)this.module).gapSize.getValue() - ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), 0.5f, ((Crosshair)this.module).color.getValue().getRGB(), ((Crosshair)this.module).outlineColor.getValue().getRGB());
            Render2DUtil.drawBorderedRect(screenMiddleX - width, screenMiddleY + ((Crosshair)this.module).gapSize.getValue() + ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), screenMiddleX + ((Crosshair)this.module).width.getValue(), screenMiddleY + (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) + ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), 0.5f, ((Crosshair)this.module).color.getValue().getRGB(), ((Crosshair)this.module).outlineColor.getValue().getRGB());
            Render2DUtil.drawBorderedRect(screenMiddleX - (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) - ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), screenMiddleY - ((Crosshair)this.module).width.getValue(), screenMiddleX - ((Crosshair)this.module).gapSize.getValue() - ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), screenMiddleY + ((Crosshair)this.module).width.getValue(), 0.5f, ((Crosshair)this.module).color.getValue().getRGB(), ((Crosshair)this.module).outlineColor.getValue().getRGB());
            Render2DUtil.drawBorderedRect(screenMiddleX + ((Crosshair)this.module).gapSize.getValue() + ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), screenMiddleY - ((Crosshair)this.module).width.getValue(), screenMiddleX + (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) + ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), screenMiddleY + ((Crosshair)this.module).width.getValue(), 0.5f, ((Crosshair)this.module).color.getValue().getRGB(), ((Crosshair)this.module).outlineColor.getValue().getRGB());
        }
        if (((Crosshair)this.module).indicator.getValue()) {
            final float f = ListenerRender.mc.player.getCooledAttackStrength(0.0f);
            final float indWidthInc = (screenMiddleX + (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) + ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f) - (screenMiddleX - (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) - ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f))) / 17.0f;
            if (f < 1.0f) {
                final float finWidth = indWidthInc * (f * 17.0f);
                Render2DUtil.drawBorderedRect(screenMiddleX - (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) - ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f), screenMiddleY + (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) + ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f) + 2.0f, screenMiddleX - (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) - ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f) + finWidth, screenMiddleY + (((Crosshair)this.module).gapSize.getValue() + ((Crosshair)this.module).length.getValue()) + ((MovementUtil.isMoving() && ((Crosshair)this.module).gapMode.getValue() == GapMode.DYNAMIC) ? ((Crosshair)this.module).gapSize.getValue() : 0.0f) + 2.0f + ((Crosshair)this.module).width.getValue() * 2.0f, 0.5f, ((Crosshair)this.module).color.getValue().getRGB(), ((Crosshair)this.module).outlineColor.getValue().getRGB());
            }
        }
    }
}
