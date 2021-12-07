// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.scoreboard.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class ScorePlayerTeamArgument extends AbstractArgument<ScorePlayerTeam>
{
    public ScorePlayerTeamArgument() {
        super(ScorePlayerTeam.class);
    }
    
    @Override
    public ScorePlayerTeam fromString(final String argument) throws ArgParseException {
        return new DummyScorePlayerTeam();
    }
}
