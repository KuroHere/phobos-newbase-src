// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main.command;

public interface ICommandLineHandler
{
    void handle(final String p0) throws CommandException;
    
    void add(final String p0, final ICommandHandler p1);
}
