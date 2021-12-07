//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.holes;

import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.util.math.*;

public class HoleRunnable implements SafeRunnable, Globals
{
    private final IHoleManager manager;
    private final List<BlockPos> safe;
    private final List<BlockPos> unsafe;
    private final List<BlockPos> longOnes;
    private final List<BlockPos> bigHoles;
    private final double holeRange;
    private final int longs;
    private final int big;
    private final int safes;
    private final int unsafes;
    
    public HoleRunnable(final IHoleManager manager, final HoleObserver observer) {
        this(manager, observer.getRange(), observer.getSafeHoles(), observer.getUnsafeHoles(), observer.get2x1Holes(), observer.get2x2Holes());
    }
    
    public HoleRunnable(final IHoleManager manager, final double holeRange, final int safe, final int unsafe, final int longs, final int big) {
        this.manager = manager;
        this.holeRange = holeRange;
        this.safes = safe;
        this.big = big;
        this.unsafes = unsafe;
        this.longs = longs;
        this.safe = new ArrayList<BlockPos>(safe);
        this.unsafe = new ArrayList<BlockPos>(unsafe);
        this.longOnes = new ArrayList<BlockPos>(longs);
        this.bigHoles = new ArrayList<BlockPos>(big);
    }
    
    @Override
    public void runSafely() {
        try {
            final BlockPos middle = PositionUtil.getPosition();
            final int mX = middle.getX();
            final int mY = middle.getY();
            final int mZ = middle.getZ();
            final int maxRadius = Sphere.getRadius(this.holeRange);
            final BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos();
            for (int i = 0; i < maxRadius; ++i) {
                final Vec3i vec3i = Sphere.get(i);
                mPos.setPos(mX + vec3i.getX(), mY + vec3i.getY(), mZ + vec3i.getZ());
                boolean done = true;
                if (this.safe.size() < this.safes || this.unsafe.size() < this.unsafes) {
                    done = false;
                    final boolean[] isHole = HoleUtil.isHole((BlockPos)mPos, true);
                    if (isHole[0]) {
                        if (isHole[1]) {
                            this.safe.add(mPos.toImmutable());
                            continue;
                        }
                        this.unsafe.add(mPos.toImmutable());
                        continue;
                    }
                }
                if (this.longOnes.size() < this.longs) {
                    done = false;
                    if (HoleUtil.is2x1(mPos.toImmutable())) {
                        this.longOnes.add(mPos.toImmutable());
                        continue;
                    }
                }
                if (this.bigHoles.size() < this.big) {
                    done = false;
                    if (HoleUtil.is2x2Partial(mPos.toImmutable())) {
                        this.bigHoles.add(mPos.toImmutable());
                        continue;
                    }
                }
                if (done) {
                    break;
                }
            }
            this.manager.setSafe(this.safe);
            this.manager.setUnsafe(this.unsafe);
            this.manager.setLongHoles(this.longOnes);
            this.manager.setBigHoles(this.bigHoles);
        }
        finally {
            this.manager.setFinished();
        }
    }
}
