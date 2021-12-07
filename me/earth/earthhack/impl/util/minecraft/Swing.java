// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import net.minecraft.util.*;

public enum Swing
{
    None {
        @Override
        public void swing(final EnumHand hand) {
        }
    }, 
    Packet {
        @Override
        public void swing(final EnumHand hand) {
            ArmUtil.swingPacket(hand);
        }
    }, 
    Full {
        @Override
        public void swing(final EnumHand hand) {
            ArmUtil.swingArm(hand);
        }
    }, 
    Client {
        @Override
        public void swing(final EnumHand hand) {
            ArmUtil.swingArmNoPacket(hand);
        }
    };
    
    public static final String DESCRIPTION = "-None won't swing at all.\n-Packet will swing on the server.\n-Full will swing both on client and server.\n-Client will only swing client-sided.";
    
    public abstract void swing(final EnumHand p0);
}
