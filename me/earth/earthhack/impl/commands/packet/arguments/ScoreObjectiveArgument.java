// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.scoreboard.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class ScoreObjectiveArgument extends AbstractArgument<ScoreObjective>
{
    public ScoreObjectiveArgument() {
        super(ScoreObjective.class);
    }
    
    @Override
    public ScoreObjective fromString(final String argument) throws ArgParseException {
        return new DummyScoreObjective();
    }
}
