// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.command.*;

public class EnemyCommand extends AbstractPlayerManagerCommand
{
    public EnemyCommand() {
        super(Managers.ENEMIES, "enemy", "Enemies", "enemied", "an enemy", "§c");
        CommandDescriptions.register(this, "Manage your enemies.");
    }
}
