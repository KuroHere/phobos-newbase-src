//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch.patches;

import me.earth.earthhack.impl.core.transfomer.patch.*;
import org.objectweb.asm.tree.*;
import me.earth.earthhack.impl.core.*;
import java.util.*;

public class PlayerControllerMPPatch extends ArgumentPatch
{
    private int applied;
    
    public PlayerControllerMPPatch() {
        super("bsa", "net.minecraft.client.multiplayer.PlayerControllerMP", "inventorymp");
        this.applied = 0;
    }
    
    @Override
    protected void applyPatch(final ClassNode node) {
        ++this.applied;
        for (final FieldNode field : node.fields) {
            if (field.signature == null && ("currentPlayerItem".equals(field.name) || "currentPlayerItem".equals(field.name) || "j".equals(field.name))) {
                Core.LOGGER.info("Made PlayerControllerMP.currentPlayerItem volatile!");
                final FieldNode fieldNode = field;
                fieldNode.access |= 0x40;
            }
        }
        this.setFinished(this.applied >= 2);
    }
}
