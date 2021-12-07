// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antipackets;

import me.earth.earthhack.api.module.data.*;

final class AntiPacketData extends DefaultData<AntiPackets>
{
    public AntiPacketData(final AntiPackets antiPackets) {
        super(antiPackets);
    }
    
    @Override
    public int getColor() {
        return -65507;
    }
    
    @Override
    public String getDescription() {
        return "Cancel packets that you receive (SPackets) or send (CPackets).";
    }
}
