//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;

public class EntityPlayerNoInterp extends EntityOtherPlayerMP implements IEntityNoInterp, Globals
{
    public EntityPlayerNoInterp(final World worldIn) {
        this(worldIn, EntityPlayerNoInterp.mc.player.getGameProfile());
    }
    
    public EntityPlayerNoInterp(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    
    public double getNoInterpX() {
        return this.posX;
    }
    
    public double getNoInterpY() {
        return this.posY;
    }
    
    public double getNoInterpZ() {
        return this.posZ;
    }
    
    public void setNoInterpX(final double x) {
    }
    
    public void setNoInterpY(final double y) {
    }
    
    public void setNoInterpZ(final double z) {
    }
    
    public int getPosIncrements() {
        return 0;
    }
    
    public void setPosIncrements(final int posIncrements) {
    }
    
    public float getNoInterpSwingAmount() {
        return 0.0f;
    }
    
    public float getNoInterpSwing() {
        return 0.0f;
    }
    
    public float getNoInterpPrevSwing() {
        return 0.0f;
    }
    
    public void setNoInterpSwingAmount(final float noInterpSwingAmount) {
    }
    
    public void setNoInterpSwing(final float noInterpSwing) {
    }
    
    public void setNoInterpPrevSwing(final float noInterpPrevSwing) {
    }
    
    public boolean isNoInterping() {
        return false;
    }
    
    public void setNoInterping(final boolean noInterping) {
    }
}
