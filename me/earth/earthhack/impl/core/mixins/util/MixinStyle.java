// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import me.earth.earthhack.impl.core.ducks.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import java.util.function.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Style.class })
public abstract class MixinStyle implements IStyle
{
    private ClickEvent rightClickEvent;
    private ClickEvent middleClickEvent;
    private Supplier<String> leftSupplier;
    private Supplier<String> rightSupplier;
    private Supplier<String> middleSupplier;
    
    @Override
    public void setRightClickEvent(final ClickEvent event) {
        this.rightClickEvent = event;
    }
    
    @Override
    public ClickEvent getRightClickEvent() {
        return this.rightClickEvent;
    }
    
    @Override
    public void setMiddleClickEvent(final ClickEvent event) {
        this.middleClickEvent = event;
    }
    
    @Override
    public ClickEvent getMiddleClickEvent() {
        return this.middleClickEvent;
    }
    
    @Override
    public void setSuppliedInsertion(final Supplier<String> insertion) {
        this.leftSupplier = insertion;
    }
    
    @Override
    public void setRightInsertion(final Supplier<String> rightInsertion) {
        this.rightSupplier = rightInsertion;
    }
    
    @Override
    public void setMiddleInsertion(final Supplier<String> middleInsertion) {
        this.middleSupplier = middleInsertion;
    }
    
    @Override
    public String getRightInsertion() {
        return (this.rightSupplier == null) ? null : this.rightSupplier.get();
    }
    
    @Override
    public String getMiddleInsertion() {
        return (this.middleSupplier == null) ? null : this.middleSupplier.get();
    }
    
    @Inject(method = { "createDeepCopy" }, at = { @At("RETURN") })
    public void createDeepCopyHook(final CallbackInfoReturnable<Style> info) {
        this.copyDucks((IStyle)info.getReturnValue());
    }
    
    @Inject(method = { "createShallowCopy" }, at = { @At("RETURN") })
    public void createShallowCopyHook(final CallbackInfoReturnable<Style> info) {
        this.copyDucks((IStyle)info.getReturnValue());
    }
    
    @Inject(method = { "getInsertion" }, at = { @At("HEAD") }, cancellable = true)
    public void getInsertionHook(final CallbackInfoReturnable<String> info) {
        if (this.leftSupplier != null) {
            info.setReturnValue(this.leftSupplier.get());
        }
    }
    
    private void copyDucks(final IStyle style) {
        style.setMiddleInsertion(this.middleSupplier);
        style.setRightInsertion(this.rightSupplier);
        style.setSuppliedInsertion(this.leftSupplier);
        style.setMiddleClickEvent(this.middleClickEvent);
        style.setRightClickEvent(this.rightClickEvent);
    }
}
