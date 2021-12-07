// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;

public class RenderEntityEvent extends Event
{
    private final Render<Entity> renderer;
    private final Entity entity;
    
    private RenderEntityEvent(final Render<Entity> renderer, final Entity entity) {
        this.renderer = renderer;
        this.entity = entity;
    }
    
    public Render<Entity> getRenderer() {
        return this.renderer;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public static class Pre extends RenderEntityEvent
    {
        private final double posX;
        private final double posY;
        private final double posZ;
        private final float entityYaw;
        private final float partialTicks;
        
        public Pre(final Render<Entity> renderer, final Entity entity, final double posX, final double posY, final double posZ, final float entityYaw, final float partialTicks) {
            super(renderer, entity, null);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.entityYaw = entityYaw;
            this.partialTicks = partialTicks;
        }
        
        public double getPosX() {
            return this.posX;
        }
        
        public double getPosY() {
            return this.posY;
        }
        
        public double getPosZ() {
            return this.posZ;
        }
        
        public float getEntityYaw() {
            return this.entityYaw;
        }
        
        public float getPartialTicks() {
            return this.partialTicks;
        }
    }
    
    public static class Post extends RenderEntityEvent
    {
        public Post(final Render<Entity> renderer, final Entity entity) {
            super(renderer, entity, null);
        }
    }
}
