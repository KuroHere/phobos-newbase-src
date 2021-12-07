// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.transfomer.patch;

import me.earth.earthhack.impl.core.transfomer.*;
import java.util.*;
import java.util.stream.*;
import me.earth.earthhack.impl.core.*;
import me.earth.earthhack.impl.core.util.*;
import org.objectweb.asm.tree.*;

public class EarthhackPatcher implements PatchManager
{
    private static final EarthhackPatcher INSTANCE;
    private final List<Patch> patches;
    
    private EarthhackPatcher() {
        this.patches = new ArrayList<Patch>();
    }
    
    public static EarthhackPatcher getInstance() {
        return EarthhackPatcher.INSTANCE;
    }
    
    @Override
    public void addPatch(final Patch patch) {
        this.patches.add(patch);
    }
    
    @Override
    public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
        final List<Patch> found = this.patches.stream().filter(p -> p.getName().equals(name) || p.getTransformedName().equals(transformedName)).collect((Collector<? super Object, ?, List<Patch>>)Collectors.toList());
        if (!found.isEmpty()) {
            Core.LOGGER.info("Found " + found.size() + " patch" + ((found.size() == 1) ? "" : "es") + " for: " + name + " : " + transformedName);
            final ClassNode cn = AsmUtil.read(bytes, new int[0]);
            found.forEach(p -> p.apply(cn));
            this.patches.removeIf(Patch::isFinished);
            return AsmUtil.writeNoSuperClass(cn, 1, 2);
        }
        return bytes;
    }
    
    static {
        INSTANCE = new EarthhackPatcher();
    }
}
