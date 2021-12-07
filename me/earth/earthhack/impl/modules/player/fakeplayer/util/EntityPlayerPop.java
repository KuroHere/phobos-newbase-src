//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fakeplayer.util;

import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class EntityPlayerPop extends EntityPlayerAttack
{
    public EntityPlayerPop(final World worldIn) {
        super(worldIn);
    }
    
    public EntityPlayerPop(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    
    public ItemStack getItemStackFromSlot(final EntityEquipmentSlot slotIn) {
        if (slotIn == EntityEquipmentSlot.OFFHAND) {
            final ItemStack stack = new ItemStack(Items.field_190929_cY);
            stack.func_190920_e(1);
            return stack;
        }
        return super.getItemStackFromSlot(slotIn);
    }
    
    public void setHealth(final float health) {
        if (health <= 0.0f) {
            this.pop();
            return;
        }
        super.setHealth(health);
    }
    
    public void setDead() {
    }
    
    public void pop() {
        NetworkUtil.receive((Packet<INetHandlerPlayClient>)new SPacketEntityStatus((Entity)this, (byte)35));
        super.setHealth(1.0f);
        this.setAbsorptionAmount(8.0f);
        this.clearActivePotions();
        this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
        this.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
    }
}
