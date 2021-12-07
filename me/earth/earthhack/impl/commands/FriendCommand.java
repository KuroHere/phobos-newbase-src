// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.command.*;

public class FriendCommand extends AbstractPlayerManagerCommand
{
    public FriendCommand() {
        super(Managers.FRIENDS, "friend", "Friends", "friended", "a friend", "§b");
        CommandDescriptions.register(this, "Manage your friends.");
    }
}
