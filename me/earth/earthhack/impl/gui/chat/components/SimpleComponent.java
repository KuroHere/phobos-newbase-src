//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.components;

import me.earth.earthhack.impl.gui.chat.*;
import net.minecraft.util.text.*;
import java.util.*;

public class SimpleComponent extends AbstractTextComponent
{
    private final String text;
    
    public SimpleComponent(final String initial) {
        super(initial);
        this.text = initial;
    }
    
    @Override
    public String getText() {
        return this.text;
    }
    
    @Override
    public String getUnformattedComponentText() {
        return this.text;
    }
    
    @Override
    public TextComponentString createCopy() {
        final SimpleComponent copy = new SimpleComponent(this.text);
        copy.setStyle(this.getStyle().createShallowCopy());
        for (final ITextComponent sibling : this.getSiblings()) {
            copy.appendSibling(sibling.createCopy());
        }
        return copy;
    }
}
