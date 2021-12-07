// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer;

import net.minecraft.launchwrapper.*;
import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.impl.core.transfomer.patch.*;
import me.earth.earthhack.impl.core.transfomer.patch.patches.*;
import me.earth.earthhack.impl.core.*;
import me.earth.earthhack.impl.core.util.*;

@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE)
public class EarthhackTransformer implements IClassTransformer
{
    private boolean changingPriority;
    private int reentrancy;
    
    public EarthhackTransformer() {
        this.changingPriority = true;
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT).addTransformerExclusion(EarthhackTransformer.class.getName());
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.PREINIT).addTransformerExclusion(EarthhackTransformer.class.getName());
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.INIT).addTransformerExclusion(EarthhackTransformer.class.getName());
        MixinEnvironment.getEnvironment(MixinEnvironment.Phase.DEFAULT).addTransformerExclusion(EarthhackTransformer.class.getName());
        final PatchManager patches = EarthhackPatcher.getInstance();
        patches.addPatch(new InventoryPlayerPatch());
        patches.addPatch(new PlayerControllerMPPatch());
        patches.addPatch(new Vec3iPatch());
        patches.addPatch(new BlockPosPatch());
        patches.addPatch(new EnumFacingPatch());
        patches.addPatch(new EntityPatch());
        this.loadReentrantClasses();
        Core.LOGGER.info("Transformer instantiated.");
    }
    
    public byte[] transform(final String name, final String transformed, final byte[] b) {
        ++this.reentrancy;
        if (this.reentrancy > 1) {
            Core.LOGGER.warn("Transformer is reentrant on class: " + name + " : " + transformed + ".");
        }
        if (this.changingPriority) {
            try {
                MixinHelper.getHelper().establishDominance();
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (transformed.equals("net.minecraft.client.entity.EntityPlayerSP")) {
            Core.LOGGER.info("Done changing MixinPriority.");
            this.changingPriority = false;
        }
        final byte[] r = EarthhackPatcher.getInstance().transform(name, transformed, b);
        --this.reentrancy;
        return r;
    }
    
    private void loadReentrantClasses() {
        try {
            Class.forName("me.earth.earthhack.impl.core.util.AsmUtil");
            Class.forName("me.earth.earthhack.impl.util.misc.ReflectionUtil");
            final String pack = "me.earth.earthhack.impl.core.transfomer.patch.";
            Class.forName(pack + "patches.BlockPosPatch$OffsetPatch");
            Class.forName(pack + "patches.BlockPosPatch$Direction");
            Class.forName(pack + "patches.BlockPosPatch$1");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
