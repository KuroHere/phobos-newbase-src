//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.client.entity.*;
import com.mojang.authlib.*;
import java.util.function.*;
import net.minecraft.world.*;
import java.util.concurrent.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.util.math.*;
import java.util.*;

public class PlayerUtil implements Globals
{
    private static final Map<Integer, EntityOtherPlayerMP> FAKE_PLAYERS;
    
    public static EntityOtherPlayerMP createFakePlayerAndAddToWorld(final GameProfile profile) {
        return createFakePlayerAndAddToWorld(profile, EntityOtherPlayerMP::new);
    }
    
    public static EntityOtherPlayerMP createFakePlayerAndAddToWorld(final GameProfile profile, final BiFunction<World, GameProfile, EntityOtherPlayerMP> create) {
        final EntityOtherPlayerMP fakePlayer = createFakePlayer(profile, create);
        int randomID;
        for (randomID = -1000; PlayerUtil.FAKE_PLAYERS.containsKey(randomID) || PlayerUtil.mc.world.getEntityByID(randomID) != null; randomID = ThreadLocalRandom.current().nextInt(-100000, -100)) {}
        PlayerUtil.FAKE_PLAYERS.put(randomID, fakePlayer);
        PlayerUtil.mc.world.addEntityToWorld(randomID, (Entity)fakePlayer);
        return fakePlayer;
    }
    
    public static EntityOtherPlayerMP createFakePlayer(final GameProfile profile, final BiFunction<World, GameProfile, EntityOtherPlayerMP> create) {
        final EntityOtherPlayerMP fakePlayer = create.apply((World)PlayerUtil.mc.world, profile);
        fakePlayer.inventory = PlayerUtil.mc.player.inventory;
        fakePlayer.inventoryContainer = PlayerUtil.mc.player.inventoryContainer;
        fakePlayer.setPositionAndRotation(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.getEntityBoundingBox().minY, PlayerUtil.mc.player.posZ, PlayerUtil.mc.player.rotationYaw, PlayerUtil.mc.player.rotationPitch);
        fakePlayer.rotationYawHead = PlayerUtil.mc.player.rotationYawHead;
        fakePlayer.onGround = PlayerUtil.mc.player.onGround;
        fakePlayer.setSneaking(PlayerUtil.mc.player.isSneaking());
        fakePlayer.setHealth(PlayerUtil.mc.player.getHealth());
        fakePlayer.setAbsorptionAmount(PlayerUtil.mc.player.getAbsorptionAmount());
        for (final PotionEffect effect : PlayerUtil.mc.player.getActivePotionEffects()) {
            fakePlayer.addPotionEffect(effect);
        }
        return fakePlayer;
    }
    
    public static void removeFakePlayer(final EntityOtherPlayerMP fakePlayer) {
        if (fakePlayer != null && PlayerUtil.FAKE_PLAYERS.containsKey(fakePlayer.getEntityId())) {
            PlayerUtil.FAKE_PLAYERS.remove(fakePlayer.getEntityId());
            PlayerUtil.mc.addScheduledTask(() -> {
                if (PlayerUtil.mc.world != null) {
                    PlayerUtil.mc.world.removeEntity((Entity)fakePlayer);
                }
            });
        }
    }
    
    public static boolean isFakePlayer(final Entity entity) {
        return entity != null && PlayerUtil.FAKE_PLAYERS.containsKey(entity.getEntityId());
    }
    
    public static boolean isOtherFakePlayer(final Entity entity) {
        return entity != null && entity.getEntityId() < 0;
    }
    
    public static boolean isCreative(final EntityPlayer player) {
        return player != null && (player.isCreative() || player.capabilities.isCreativeMode);
    }
    
    public static BlockPos getBestPlace(final BlockPos pos, final EntityPlayer player) {
        final EnumFacing facing = getSide(player, pos);
        if (facing == EnumFacing.UP) {
            final Block block = PlayerUtil.mc.world.getBlockState(pos).getBlock();
            final Block block2 = PlayerUtil.mc.world.getBlockState(pos.offset(EnumFacing.UP)).getBlock();
            if (block2 instanceof BlockAir && (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK)) {
                return pos;
            }
        }
        else {
            final BlockPos blockPos = pos.offset(facing);
            final Block block3 = PlayerUtil.mc.world.getBlockState(blockPos).getBlock();
            final BlockPos blockPos2 = blockPos.down();
            final Block block4 = PlayerUtil.mc.world.getBlockState(blockPos2).getBlock();
            if (block3 instanceof BlockAir && (block4 == Blocks.OBSIDIAN || block4 == Blocks.BEDROCK)) {
                return blockPos2;
            }
        }
        return null;
    }
    
    public static EnumFacing getSide(final EntityPlayer player, final BlockPos blockPos) {
        final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (playerPos.offset(facing).equals((Object)blockPos)) {
                return facing;
            }
        }
        if (playerPos.offset(EnumFacing.UP).offset(EnumFacing.UP).equals((Object)blockPos)) {
            return EnumFacing.UP;
        }
        return EnumFacing.DOWN;
    }
    
    public static boolean isInHole(final EntityPlayer player) {
        final BlockPos position = PositionUtil.getPosition((Entity)player);
        int count = 0;
        for (final EnumFacing face : EnumFacing.values()) {
            if (face != EnumFacing.UP) {
                if (face != EnumFacing.DOWN) {
                    if (!BlockUtil.isReplaceable(position.offset(face))) {
                        ++count;
                    }
                }
            }
        }
        return count >= 3;
    }
    
    public static EnumFacing getOppositePlayerFaceBetter(final EntityPlayer player, final BlockPos pos) {
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            final BlockPos off = pos.offset(face);
            final BlockPos off2 = pos.offset(face).offset(face);
            final BlockPos playerOff = PositionUtil.getPosition((Entity)player);
            if (new Vec3d((Vec3i)off).equals((Object)new Vec3d((Vec3i)playerOff)) || new Vec3d((Vec3i)off2).equals((Object)new Vec3d((Vec3i)off2))) {
                return face.getOpposite();
            }
        }
        return null;
    }
    
    static {
        FAKE_PLAYERS = new HashMap<Integer, EntityOtherPlayerMP>();
    }
}
