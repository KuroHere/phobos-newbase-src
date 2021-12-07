// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.util.bind;

import org.lwjgl.input.*;

public class Bind
{
    private final int key;
    
    private Bind(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
    
    @Override
    public int hashCode() {
        return this.key;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Bind && ((Bind)o).key == this.key;
    }
    
    @Override
    public String toString() {
        return (this.key < 0) ? "NONE" : Keyboard.getKeyName(this.key);
    }
    
    public static Bind none() {
        return new Bind(-1);
    }
    
    public static Bind fromKey(final int key) {
        return new Bind(key);
    }
    
    public static Bind fromString(String string) {
        string = string.toUpperCase();
        if (string.equals("NONE")) {
            return none();
        }
        return new Bind(Keyboard.getKeyIndex(string));
    }
}
