//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.offhand.modes;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class OffhandMode
{
    public static final OffhandMode TOTEM;
    public static final OffhandMode GAPPLE;
    public static final OffhandMode CRYSTAL;
    public static final OffhandMode OBSIDIAN;
    private final String name;
    private final Item item;
    
    public OffhandMode(final Block block, final String name) {
        this(Item.getItemFromBlock(block), name);
    }
    
    public OffhandMode(final Item item, final String name) {
        this.item = item;
        this.name = name;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public int hashCode() {
        return (this.item == null) ? 0 : this.item.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof OffhandMode && ((OffhandMode)o).item == this.item;
    }
    
    static {
        TOTEM = new OffhandMode(Items.field_190929_cY, "Totem");
        GAPPLE = new OffhandMode(Items.GOLDEN_APPLE, "Gapple");
        CRYSTAL = new OffhandMode(Items.END_CRYSTAL, "Crystal");
        OBSIDIAN = new OffhandMode(Blocks.OBSIDIAN, "Obsidian");
    }
}
