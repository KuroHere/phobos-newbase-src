// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.observable;

import java.util.*;

public class Observable<T>
{
    private final List<Observer<? super T>> observers;
    
    public Observable() {
        this.observers = new LinkedList<Observer<? super T>>();
    }
    
    public T onChange(final T value) {
        for (final Observer<? super T> observer : this.observers) {
            observer.onChange(value);
        }
        return value;
    }
    
    public void addObserver(final Observer<? super T> observer) {
        if (observer != null && !this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }
    
    public void removeObserver(final Observer<? super T> observer) {
        this.observers.remove(observer);
    }
}
