//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import net.minecraft.entity.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraft.entity.item.*;

public enum EntityType
{
    Animal(EntityType::isAnimal), 
    Player(EntityType::isPlayer), 
    Boss(EntityType::isBoss), 
    Monster(EntityType::isMonster), 
    Vehicle(EntityType::isVehicle), 
    Other(EntityType::isOther);
    
    private static final Map<Class<? extends Entity>, String> entityNames;
    final Predicate<Entity> predicate;
    
    private EntityType(final Predicate<Entity> predicate) {
        this.predicate = predicate;
    }
    
    public boolean is(final Entity entity) {
        return this.predicate.test(entity);
    }
    
    public static boolean isPlayer(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public static boolean isAnimal(final Entity entity) {
        return entity instanceof EntityPig || entity instanceof EntityParrot || entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityBat || entity instanceof EntityVillager || entity instanceof EntityOcelot || entity instanceof EntityHorse || entity instanceof EntityLlama || entity instanceof EntityMule || entity instanceof EntityDonkey || entity instanceof EntitySkeletonHorse || entity instanceof EntityZombieHorse || entity instanceof EntitySnowman || entity instanceof EntityWolf || (entity instanceof EntityRabbit && isFriendlyRabbit(entity));
    }
    
    public static boolean isMonster(final Entity entity) {
        return entity instanceof EntityCreeper || entity instanceof EntityIllusionIllager || entity instanceof EntitySkeleton || entity instanceof EntityZombie || entity instanceof EntityBlaze || entity instanceof EntitySpider || entity instanceof EntityWitch || entity instanceof EntitySlime || entity instanceof EntitySilverfish || entity instanceof EntityGuardian || entity instanceof EntityEndermite || entity instanceof EntityGhast || entity instanceof EntityEvoker || entity instanceof EntityShulker || entity instanceof EntityWitherSkeleton || entity instanceof EntityStray || entity instanceof EntityVex || entity instanceof EntityVindicator || entity instanceof EntityPolarBear || entity instanceof EntityWolf || entity instanceof EntityEnderman || entity instanceof EntityRabbit || entity instanceof EntityIronGolem;
    }
    
    public static boolean isBoss(final Entity entity) {
        return entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityGiantZombie;
    }
    
    public static boolean isOther(final Entity entity) {
        return entity instanceof EntityEnderCrystal || entity instanceof EntityEvokerFangs || entity instanceof EntityShulkerBullet || entity instanceof EntityFallingBlock || entity instanceof EntityFireball || entity instanceof EntityEnderEye || entity instanceof EntityEnderPearl;
    }
    
    public static boolean isVehicle(final Entity entity) {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }
    
    public static boolean isAngry(final Entity entity) {
        return (entity instanceof EntityWolf && isAngryWolf(entity)) || (entity instanceof EntityPolarBear && isAngryPolarBear(entity)) || (entity instanceof EntityIronGolem && isAngryGolem(entity)) || (entity instanceof EntityEnderman && isAngryEnderMan(entity)) || (entity instanceof EntityPigZombie && isAngryPigMan(entity));
    }
    
    public static boolean isAngryEnderMan(final Entity entity) {
        return entity instanceof EntityEnderman && !((EntityEnderman)entity).isScreaming();
    }
    
    public static boolean isAngryPigMan(final Entity entity) {
        return entity instanceof EntityPigZombie && entity.rotationPitch == 0.0f && ((EntityPigZombie)entity).getRevengeTimer() <= 0;
    }
    
    public static boolean isAngryGolem(final Entity entity) {
        return entity instanceof EntityIronGolem && entity.rotationPitch == 0.0f;
    }
    
    public static boolean isAngryWolf(final Entity entity) {
        return entity instanceof EntityWolf && !((EntityWolf)entity).isAngry();
    }
    
    public static boolean isAngryPolarBear(final Entity entity) {
        return entity instanceof EntityPolarBear && entity.rotationPitch == 0.0f && ((EntityPolarBear)entity).getRevengeTimer() <= 0;
    }
    
    public static boolean isFriendlyRabbit(final Entity entity) {
        return entity instanceof EntityRabbit && ((EntityRabbit)entity).getRabbitType() != 99;
    }
    
    public static String getName(final Entity entity) {
        final String name = EntityType.entityNames.get(entity.getClass());
        if (name != null) {
            return name;
        }
        return entity.getName();
    }
    
    static {
        (entityNames = new HashMap<Class<? extends Entity>, String>()).put(EntityItemFrame.class, "Item Frame");
        EntityType.entityNames.put((Class<? extends Entity>)EntityEnderCrystal.class, "End Crystal");
        EntityType.entityNames.put((Class<? extends Entity>)EntityMinecartEmpty.class, "Minecart");
        EntityType.entityNames.put((Class<? extends Entity>)EntityMinecart.class, "Minecart");
        EntityType.entityNames.put((Class<? extends Entity>)EntityMinecartFurnace.class, "Minecart with Furnace");
        EntityType.entityNames.put((Class<? extends Entity>)EntityMinecartTNT.class, "Minecart with TNT");
    }
}
