// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.util;

public class EnumHelper
{
    public static Enum<?> next(final Enum<?> entry) {
        final Enum<?>[] array = (Enum<?>[])entry.getDeclaringClass().getEnumConstants();
        return (array.length - 1 == entry.ordinal()) ? array[0] : array[entry.ordinal() + 1];
    }
    
    public static Enum<?> previous(final Enum<?> entry) {
        final Enum<?>[] array = (Enum<?>[])entry.getDeclaringClass().getEnumConstants();
        return (entry.ordinal() - 1 < 0) ? array[array.length - 1] : array[entry.ordinal() - 1];
    }
    
    public static Enum<?> fromString(final Enum<?> initial, final String name) {
        final Enum<?> e = fromString(initial.getDeclaringClass(), name);
        if (e != null) {
            return e;
        }
        return initial;
    }
    
    public static <T extends Enum<?>> T fromString(final Class<T> type, final String name) {
        for (final T constant : type.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(name)) {
                return constant;
            }
        }
        return null;
    }
    
    public static Enum<?> getEnumStartingWith(String prefix, final Class<? extends Enum<?>> type) {
        if (prefix == null) {
            return null;
        }
        prefix = prefix.toLowerCase();
        final Enum[] array2;
        final Enum<?>[] array = array2 = (Enum[])type.getEnumConstants();
        for (final Enum<?> entry : array2) {
            if (entry.name().toLowerCase().startsWith(prefix)) {
                return entry;
            }
        }
        return null;
    }
}
