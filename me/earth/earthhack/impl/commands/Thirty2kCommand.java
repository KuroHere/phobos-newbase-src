//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.api.command.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.thread.*;

public class Thirty2kCommand extends AbstractStackCommand
{
    public Thirty2kCommand() {
        super("32k", "32k");
        CommandDescriptions.register(this, "Gives you a 32k sword.");
    }
    
    @Override
    protected ItemStack getStack(final String[] args) {
        final ItemStack s = new ItemStack(Items.DIAMOND_SWORD);
        s.setStackDisplayName("3²arthbl4de");
        s.func_190920_e(64);
        EnchantmentUtil.addEnchantment(s, 16, 32767);
        EnchantmentUtil.addEnchantment(s, 19, 10);
        EnchantmentUtil.addEnchantment(s, 20, 32767);
        EnchantmentUtil.addEnchantment(s, 21, 10);
        EnchantmentUtil.addEnchantment(s, 22, 3);
        EnchantmentUtil.addEnchantment(s, 34, 32767);
        EnchantmentUtil.addEnchantment(s, 70, 1);
        EnchantmentUtil.addEnchantment(s, 71, 1);
        return s;
    }
}
