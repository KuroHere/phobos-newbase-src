// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch.patches;

import me.earth.earthhack.impl.core.transfomer.patch.*;
import org.objectweb.asm.tree.*;
import me.earth.earthhack.impl.core.*;
import java.util.*;

public class EnumFacingPatch extends ArgumentPatch
{
    private int applied;
    
    public EnumFacingPatch() {
        super("fa", "net.minecraft.util.EnumFacing", "vanilla");
        this.applied = 0;
    }
    
    @Override
    protected void applyPatch(final ClassNode node) {
        ++this.applied;
        for (final FieldNode field : node.fields) {
            if (field.signature == null && "o".equals(field.name) && (0x8 & field.access) == 0x8) {
                Core.LOGGER.info("Made EnumFacing.HORIZONTALS public!");
                final FieldNode fieldNode = field;
                fieldNode.access &= 0xFFFFFFFD;
                final FieldNode fieldNode2 = field;
                fieldNode2.access &= 0xFFFFFFFB;
                final FieldNode fieldNode3 = field;
                fieldNode3.access |= 0x1;
            }
        }
        this.setFinished(this.applied >= 2);
    }
}
