//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.core.ducks.entity.*;

public class ArmUtil implements Globals
{
    public static void swingPacket(final EnumHand hand) {
        Objects.requireNonNull(ArmUtil.mc.getConnection()).sendPacket((Packet)new CPacketAnimation(hand));
    }
    
    public static void swingArmNoPacket(final EnumHand hand) {
        if (!ArmUtil.mc.player.isSwingInProgress || ArmUtil.mc.player.swingProgressInt >= ((IEntityLivingBase)ArmUtil.mc.player).armSwingAnimationEnd() / 2 || ArmUtil.mc.player.swingProgressInt < 0) {
            ArmUtil.mc.player.swingProgressInt = -1;
            ArmUtil.mc.player.isSwingInProgress = true;
            ArmUtil.mc.player.swingingHand = hand;
        }
    }
    
    public static void swingArm(final EnumHand hand) {
        ArmUtil.mc.player.swingArm(hand);
    }
}
