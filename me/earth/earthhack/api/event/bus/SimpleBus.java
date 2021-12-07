// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus;

import me.earth.earthhack.api.event.bus.api.*;
import java.util.*;
import java.util.concurrent.*;

public class SimpleBus implements EventBus
{
    private final Map<Class<?>, List<Listener>> listeners;
    private final Set<Subscriber> subscribers;
    private final Set<Listener> subbedlisteners;
    
    public SimpleBus() {
        this.listeners = new ConcurrentHashMap<Class<?>, List<Listener>>();
        this.subscribers = Collections.newSetFromMap(new ConcurrentHashMap<Subscriber, Boolean>());
        this.subbedlisteners = (Set<Listener>)Collections.newSetFromMap(new ConcurrentHashMap<Listener, Boolean>());
    }
    
    @Override
    public void post(final Object object) {
        final List<Listener> listening = this.listeners.get(object.getClass());
        if (listening != null) {
            for (final Listener listener : listening) {
                listener.invoke(object);
            }
        }
    }
    
    @Override
    public void post(final Object object, final Class<?> type) {
        final List<Listener> listening = this.listeners.get(object.getClass());
        if (listening != null) {
            for (final Listener listener : listening) {
                if (listener.getType() == null || listener.getType() == type) {
                    listener.invoke(object);
                }
            }
        }
    }
    
    @Override
    public boolean postCancellable(final ICancellable object) {
        final List<Listener> listening = this.listeners.get(object.getClass());
        if (listening != null) {
            for (final Listener listener : listening) {
                listener.invoke(object);
                if (object.isCancelled()) {
                    return true;
                }
            }
        }
        return object.isCancelled();
    }
    
    @Override
    public boolean postCancellable(final ICancellable object, final Class<?> type) {
        final List<Listener> listening = this.listeners.get(object.getClass());
        if (listening != null) {
            for (final Listener listener : listening) {
                if (listener.getType() == null || listener.getType() == type) {
                    listener.invoke(object);
                    if (object.isCancelled()) {
                        return true;
                    }
                    continue;
                }
            }
        }
        return object.isCancelled();
    }
    
    @Override
    public void postReversed(final Object object, final Class<?> type) {
        final List<Listener> list = this.listeners.get(object.getClass());
        if (list != null) {
            final ListIterator<Listener> li = (ListIterator<Listener>)list.listIterator(list.size());
            while (li.hasPrevious()) {
                final Listener l = li.previous();
                if (l != null && (l.getType() == null || l.getType() == type)) {
                    l.invoke(object);
                }
            }
        }
    }
    
    @Override
    public void subscribe(final Object object) {
        if (object instanceof Subscriber) {
            final Subscriber subscriber = (Subscriber)object;
            for (final Listener<?> listener : subscriber.getListeners()) {
                this.register(listener);
            }
            this.subscribers.add(subscriber);
        }
    }
    
    @Override
    public void unsubscribe(final Object object) {
        if (object instanceof Subscriber) {
            final Subscriber subscriber = (Subscriber)object;
            for (final Listener<?> listener : subscriber.getListeners()) {
                this.unregister(listener);
            }
            this.subscribers.remove(subscriber);
        }
    }
    
    @Override
    public void register(final Listener<?> listener) {
        if (this.subbedlisteners.add(listener)) {
            this.addAtPriority(listener, this.listeners.computeIfAbsent(listener.getTarget(), v -> new CopyOnWriteArrayList()));
        }
    }
    
    @Override
    public void unregister(final Listener<?> listener) {
        if (this.subbedlisteners.remove(listener)) {
            final List<Listener> list = this.listeners.get(listener.getTarget());
            if (list != null) {
                list.remove(listener);
            }
        }
    }
    
    @Override
    public boolean isSubscribed(final Object object) {
        if (object instanceof Subscriber) {
            return this.subscribers.contains(object);
        }
        return object instanceof Listener && this.subbedlisteners.contains(object);
    }
    
    @Override
    public boolean hasSubscribers(final Class<?> clazz) {
        final List<Listener> listening = this.listeners.get(clazz);
        return listening != null && !listening.isEmpty();
    }
    
    @Override
    public boolean hasSubscribers(final Class<?> clazz, final Class<?> type) {
        final List<Listener> listening = this.listeners.get(clazz);
        return listening != null && listening.stream().anyMatch(listener -> listener.getType() == null || listener.getType() == type);
    }
    
    private void addAtPriority(final Listener<?> listener, final List<Listener> list) {
        int index;
        for (index = 0; index < list.size() && listener.getPriority() < list.get(index).getPriority(); ++index) {}
        list.add(index, listener);
    }
}
