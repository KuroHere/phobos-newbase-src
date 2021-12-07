// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class BossInfoArgument extends AbstractArgument<BossInfo>
{
    public BossInfoArgument() {
        super(BossInfo.class);
    }
    
    @Override
    public BossInfo fromString(final String argument) throws ArgParseException {
        return new DummyBossInfo();
    }
}
