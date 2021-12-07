//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import me.earth.earthhack.api.util.interfaces.*;

public class WorldRenderUtil implements Globals
{
    public static void reload(final boolean soft) {
        if (soft) {
            final int x = (int)WorldRenderUtil.mc.player.posX;
            final int y = (int)WorldRenderUtil.mc.player.posY;
            final int z = (int)WorldRenderUtil.mc.player.posZ;
            final int d = WorldRenderUtil.mc.gameSettings.renderDistanceChunks * 16;
            WorldRenderUtil.mc.renderGlobal.markBlockRangeForRenderUpdate(x - d, y - d, z - d, x + d, y + d, z + d);
            return;
        }
        WorldRenderUtil.mc.renderGlobal.loadRenderers();
    }
}
