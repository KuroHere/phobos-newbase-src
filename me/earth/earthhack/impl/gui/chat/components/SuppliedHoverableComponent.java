//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components;

import me.earth.earthhack.impl.core.ducks.util.*;
import java.util.function.*;
import net.minecraft.util.text.*;
import java.util.*;

public class SuppliedHoverableComponent extends SuppliedComponent implements IHoverable
{
    private final BooleanSupplier canBeHovered;
    
    public SuppliedHoverableComponent(final Supplier<String> supplier, final BooleanSupplier canBeHovered) {
        super(supplier);
        this.canBeHovered = canBeHovered;
    }
    
    @Override
    public boolean canBeHovered() {
        return this.canBeHovered.getAsBoolean();
    }
    
    @Override
    public TextComponentString createCopy() {
        final SuppliedHoverableComponent copy = new SuppliedHoverableComponent(this.supplier, this.canBeHovered);
        copy.setStyle(this.getStyle().createShallowCopy());
        for (final ITextComponent component : this.getSiblings()) {
            copy.appendSibling(component.createCopy());
        }
        return copy;
    }
}
