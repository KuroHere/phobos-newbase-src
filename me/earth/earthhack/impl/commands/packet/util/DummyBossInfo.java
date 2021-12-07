// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.text.*;

public class DummyBossInfo extends BossInfo implements Dummy
{
    public DummyBossInfo() {
        super(UUID.randomUUID(), (ITextComponent)new TextComponentString("Dummy-Boss"), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_20);
    }
}
