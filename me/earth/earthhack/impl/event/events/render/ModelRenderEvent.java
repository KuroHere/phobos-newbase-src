// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class ModelRenderEvent extends Event
{
    private final RenderLivingBase<?> renderLiving;
    private final EntityLivingBase entity;
    private final ModelBase model;
    
    private ModelRenderEvent(final RenderLivingBase<?> renderLiving, final EntityLivingBase entity, final ModelBase model) {
        this.renderLiving = renderLiving;
        this.entity = entity;
        this.model = model;
    }
    
    public RenderLivingBase<?> getRenderLiving() {
        return this.renderLiving;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public ModelBase getModel() {
        return this.model;
    }
    
    public static class Pre extends ModelRenderEvent
    {
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float ageInTicks;
        private final float netHeadYaw;
        private final float headPitch;
        private final float scale;
        
        public Pre(final RenderLivingBase<?> renderLiving, final EntityLivingBase entity, final ModelBase model, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
            super(renderLiving, entity, model, null);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
            this.scale = scale;
        }
        
        public float getLimbSwing() {
            return this.limbSwing;
        }
        
        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }
        
        public float getAgeInTicks() {
            return this.ageInTicks;
        }
        
        public float getNetHeadYaw() {
            return this.netHeadYaw;
        }
        
        public float getHeadPitch() {
            return this.headPitch;
        }
        
        public float getScale() {
            return this.scale;
        }
    }
    
    public static class Post extends ModelRenderEvent
    {
        private final float limbSwing;
        private final float limbSwingAmount;
        private final float ageInTicks;
        private final float netHeadYaw;
        private final float headPitch;
        private final float scale;
        
        public Post(final RenderLivingBase<?> renderLiving, final EntityLivingBase entity, final ModelBase model, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
            super(renderLiving, entity, model, null);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
            this.scale = scale;
        }
        
        public float getLimbSwing() {
            return this.limbSwing;
        }
        
        public float getLimbSwingAmount() {
            return this.limbSwingAmount;
        }
        
        public float getAgeInTicks() {
            return this.ageInTicks;
        }
        
        public float getNetHeadYaw() {
            return this.netHeadYaw;
        }
        
        public float getHeadPitch() {
            return this.headPitch;
        }
        
        public float getScale() {
            return this.scale;
        }
    }
}
