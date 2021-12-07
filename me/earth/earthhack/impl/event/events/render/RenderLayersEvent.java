// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.renderer.entity.layers.*;
import me.earth.earthhack.api.event.events.*;

public class RenderLayersEvent extends StageEvent
{
    private final Render<EntityLivingBase> render;
    private final EntityLivingBase entity;
    private final List<LayerRenderer<?>> layers;
    
    public RenderLayersEvent(final Render<EntityLivingBase> render, final EntityLivingBase entity, final List<LayerRenderer<?>> layers, final Stage stage) {
        super(stage);
        this.render = render;
        this.entity = entity;
        this.layers = layers;
    }
    
    public Render<EntityLivingBase> getRender() {
        return this.render;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public List<LayerRenderer<?>> getLayers() {
        return this.layers;
    }
}
