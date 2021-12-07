// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import net.minecraft.scoreboard.*;

public class DummyScorePlayerTeam extends ScorePlayerTeam implements Dummy
{
    public DummyScorePlayerTeam() {
        super(new Scoreboard(), "Dummy-ScorePlayerTeam");
    }
}
