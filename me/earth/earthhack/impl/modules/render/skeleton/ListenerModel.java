//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.skeleton;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.model.*;

final class ListenerModel extends ModuleListener<Skeleton, ModelRenderEvent.Post>
{
    public ListenerModel(final Skeleton module) {
        super(module, (Class<? super Object>)ModelRenderEvent.Post.class);
    }
    
    public void invoke(final ModelRenderEvent.Post event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntity();
            if (event.getModel() instanceof ModelBiped) {
                final ModelBiped model = (ModelBiped)event.getModel();
                final float[][] rotations = new float[5][3];
                rotations[0] = this.getRotations(model.bipedHead);
                rotations[1] = this.getRotations(model.bipedRightArm);
                rotations[2] = this.getRotations(model.bipedLeftArm);
                rotations[3] = this.getRotations(model.bipedRightLeg);
                rotations[4] = this.getRotations(model.bipedLeftLeg);
                ((Skeleton)this.module).rotations.put(player, rotations);
            }
        }
    }
    
    private float[] getRotations(final ModelRenderer model) {
        return new float[] { model.rotateAngleX, model.rotateAngleY, model.rotateAngleZ };
    }
}
