// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.entity;

public interface IEntityRemoteAttack
{
    default boolean shouldRemoteAttack() {
        return false;
    }
}
