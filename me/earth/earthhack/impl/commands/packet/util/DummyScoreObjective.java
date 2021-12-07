//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.util;

import net.minecraft.scoreboard.*;

public class DummyScoreObjective extends ScoreObjective implements Dummy
{
    public DummyScoreObjective() {
        super(new Scoreboard(), "Dummy-Objective", IScoreCriteria.DUMMY);
    }
}
