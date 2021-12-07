//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat;

import net.minecraft.util.text.*;

public abstract class AbstractTextComponent extends TextComponentString
{
    public static final AbstractTextComponent EMPTY;
    private boolean wrap;
    
    public AbstractTextComponent(final String initial) {
        super(initial);
    }
    
    public AbstractTextComponent setWrap(final boolean wrap) {
        this.wrap = wrap;
        return this;
    }
    
    public boolean isWrapping() {
        return this.wrap;
    }
    
    public abstract String getText();
    
    public abstract String getUnformattedComponentText();
    
    public abstract TextComponentString createCopy();
    
    public boolean equals(final Object o) {
        return this == o || (o instanceof AbstractTextComponent && this.getText().equals(((AbstractTextComponent)o).getText()));
    }
    
    public int hashCode() {
        return super.hashCode();
    }
    
    public String toString() {
        return "CustomComponent{text='" + this.getText() + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
    }
    
    static {
        EMPTY = new AbstractTextComponent("") {
            @Override
            public String getText() {
                return "";
            }
            
            @Override
            public String getUnformattedComponentText() {
                return "";
            }
            
            @Override
            public TextComponentString createCopy() {
                return AbstractTextComponent$1.EMPTY;
            }
        };
    }
}
