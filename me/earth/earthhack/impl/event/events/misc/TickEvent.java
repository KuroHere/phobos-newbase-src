//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import me.earth.earthhack.api.util.interfaces.*;

public class TickEvent implements Globals
{
    public boolean isSafe() {
        return TickEvent.mc.player != null && TickEvent.mc.world != null;
    }
    
    public static final class PostWorldTick extends TickEvent
    {
    }
    
    public static final class Post extends TickEvent
    {
    }
}
