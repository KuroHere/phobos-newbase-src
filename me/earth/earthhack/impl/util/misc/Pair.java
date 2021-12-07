// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

import java.util.*;

public class Pair<K, V>
{
    private final K key;
    private final V value;
    
    public Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() {
        return this.key;
    }
    
    public V getValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Pair && Objects.equals(this.key, ((Pair)o).key) && Objects.equals(this.value, ((Pair)o).value);
    }
    
    @Override
    public int hashCode() {
        return 31 * Objects.hashCode(this.key) + Objects.hashCode(this.value);
    }
    
    @Override
    public String toString() {
        return this.key + "=" + this.value;
    }
}
