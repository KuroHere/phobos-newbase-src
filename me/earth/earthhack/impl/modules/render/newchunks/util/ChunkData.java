// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.newchunks.util;

public class ChunkData
{
    private final int x;
    private final int z;
    
    public ChunkData(final int x, final int z) {
        this.x = x;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getZ() {
        return this.z;
    }
    
    @Override
    public int hashCode() {
        int hash = 23;
        hash = hash * 31 + this.x;
        hash = hash * 31 + this.z;
        return hash;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ChunkData) {
            return Double.compare(((ChunkData)o).x, this.x) == 0 && Double.compare(((ChunkData)o).z, this.z) == 0;
        }
        return super.equals(o);
    }
}
