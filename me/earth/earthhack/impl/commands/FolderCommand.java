// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.commands.util.*;
import java.awt.*;
import java.nio.file.*;
import me.earth.earthhack.impl.util.text.*;
import java.io.*;

public class FolderCommand extends Command
{
    public FolderCommand() {
        super(new String[][] { { "folder" } });
        CommandDescriptions.register(this, "Opens the 3arthh4ck folder.");
    }
    
    @Override
    public void execute(final String[] args) {
        try {
            Desktop.getDesktop().open(Paths.get("earthhack", new String[0]).toFile());
        }
        catch (IOException e) {
            ChatUtil.sendMessage("§cAn error occurred.");
            e.printStackTrace();
        }
    }
}
