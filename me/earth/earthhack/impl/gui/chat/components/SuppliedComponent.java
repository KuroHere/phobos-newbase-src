//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components;

import me.earth.earthhack.impl.gui.chat.*;
import java.util.function.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import me.earth.earthhack.impl.core.util.*;
import net.minecraft.util.text.*;
import java.util.*;

public class SuppliedComponent extends AbstractTextComponent
{
    protected final Supplier<String> supplier;
    
    public SuppliedComponent(final Supplier<String> supplier) {
        super(supplier.get());
        this.supplier = supplier;
        ((ITextComponentBase)this).setFormattingHook(new SimpleTextFormatHook((TextComponentBase)this));
        ((ITextComponentBase)this).setUnFormattedHook(new SimpleTextFormatHook((TextComponentBase)this));
    }
    
    @Override
    public String getText() {
        return this.supplier.get();
    }
    
    @Override
    public String getUnformattedComponentText() {
        return this.supplier.get();
    }
    
    @Override
    public TextComponentString createCopy() {
        final SuppliedComponent copy = new SuppliedComponent(this.supplier);
        copy.setStyle(this.getStyle().createShallowCopy());
        for (final ITextComponent component : this.getSiblings()) {
            copy.appendSibling(component.createCopy());
        }
        return copy;
    }
}
