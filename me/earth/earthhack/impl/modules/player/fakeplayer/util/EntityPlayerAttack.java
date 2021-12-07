//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fakeplayer.util;

import net.minecraft.client.entity.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import java.util.function.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;

public class EntityPlayerAttack extends EntityOtherPlayerMP implements Globals, IEntityOtherPlayerMP, IEntityRemoteAttack
{
    private BooleanSupplier remoteSupplier;
    
    public EntityPlayerAttack(final World worldIn) {
        this(worldIn, EntityPlayerAttack.mc.player.getGameProfile());
    }
    
    public EntityPlayerAttack(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
        this.remoteSupplier = (() -> true);
    }
    
    protected void setBeenAttacked() {
    }
    
    public boolean shouldRemoteAttack() {
        return this.remoteSupplier.getAsBoolean();
    }
    
    public boolean returnFromSuperAttack(final DamageSource source, final float amount) {
        super.returnFromSuperAttack(source, amount);
        return true;
    }
    
    public boolean shouldAttackSuper() {
        return true;
    }
    
    public void setRemoteSupplier(final BooleanSupplier remoteSupplier) {
        this.remoteSupplier = remoteSupplier;
    }
}
