// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc.collections;

import java.util.*;

public class ConvenientStack<E> extends Stack<E>
{
    public ConvenientStack() {
    }
    
    public ConvenientStack(final Collection<E> collection) {
        this.addAll((Collection<? extends E>)collection);
    }
    
    @Override
    public synchronized E pop() {
        if (this.empty()) {
            return null;
        }
        return super.pop();
    }
    
    @Override
    public synchronized E peek() {
        if (this.empty()) {
            return null;
        }
        return super.peek();
    }
}
