//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.modes;

import java.util.function.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;

public enum Attack
{
    Always(() -> true, () -> true), 
    Crystal(() -> InventoryUtil.isHolding(Items.END_CRYSTAL), () -> InventoryUtil.isHolding(Items.END_CRYSTAL)), 
    Calc(() -> true, () -> InventoryUtil.isHolding(Items.END_CRYSTAL));
    
    Supplier<Boolean> shouldCalc;
    Supplier<Boolean> shouldAttack;
    
    private Attack(final Supplier<Boolean> shouldCalc, final Supplier<Boolean> shouldAttack) {
        this.shouldAttack = shouldAttack;
        this.shouldCalc = shouldCalc;
    }
    
    public boolean shouldCalc() {
        return this.shouldCalc.get();
    }
    
    public boolean shouldAttack() {
        return this.shouldAttack.get();
    }
}
