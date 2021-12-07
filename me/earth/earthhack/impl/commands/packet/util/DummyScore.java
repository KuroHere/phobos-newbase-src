// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import net.minecraft.scoreboard.*;

public class DummyScore extends Score implements Dummy
{
    public DummyScore() {
        super(new Scoreboard(), (ScoreObjective)new DummyScoreObjective(), "Dummy");
    }
}
