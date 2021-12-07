//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.nametags;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.media.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;
import net.minecraft.enchantment.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.network.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.modules.*;

public class Nametag implements Globals
{
    private static final ModuleCache<Media> MEDIA;
    private final Nametags module;
    public final EntityPlayer player;
    public final StackRenderer mainHand;
    public final List<StackRenderer> stacks;
    public final String nameString;
    public final int nameColor;
    public final int nameWidth;
    public int maxEnchHeight;
    public boolean renderDura;
    public static boolean isRendering;
    
    public Nametag(final Nametags module, final EntityPlayer player) {
        this.module = module;
        this.player = player;
        this.stacks = new ArrayList<StackRenderer>(6);
        final ItemStack mainStack = player.getHeldItemMainhand();
        if (mainStack.func_190926_b()) {
            this.mainHand = null;
        }
        else {
            final boolean damageable = mainStack.isItemStackDamageable() && module.durability.getValue();
            if (damageable) {
                this.renderDura = true;
            }
            this.calcEnchHeight(this.mainHand = new StackRenderer(mainStack, damageable));
        }
        for (int i = 3; i > -1; --i) {
            this.addStack((ItemStack)player.inventory.armorInventory.get(i));
        }
        this.addStack(player.getHeldItemOffhand());
        this.nameColor = this.getColor(player);
        this.nameString = this.getName(player);
        this.nameWidth = Managers.TEXT.getStringWidth(this.nameString);
        for (final StackRenderer sr : this.stacks) {
            this.calcEnchHeight(sr);
        }
    }
    
    private void calcEnchHeight(final StackRenderer sr) {
        final int enchHeight = EnchantmentHelper.getEnchantments(sr.getStack()).size();
        if (this.module.armor.getValue() && enchHeight > this.maxEnchHeight) {
            this.maxEnchHeight = enchHeight;
        }
    }
    
    private void addStack(final ItemStack stack) {
        if (!stack.func_190926_b()) {
            final boolean damageable = stack.isItemStackDamageable() && this.module.durability.getValue();
            if (damageable) {
                this.renderDura = true;
            }
            this.stacks.add(new StackRenderer(stack, damageable));
        }
    }
    
    private String getName(final EntityPlayer player) {
        final String name = player.getDisplayName().getFormattedText().trim();
        String s;
        if (this.module.media.getValue()) {
            s = Nametag.MEDIA.returnIfPresent(m -> m.convert(name), name);
        }
        else {
            s = name;
        }
        final StringBuilder builder = new StringBuilder(s);
        boolean offset = builder.toString().replaceAll("\u00c2§.", "").length() > 0;
        if (this.module.id.getValue()) {
            builder.append(offset ? " " : "").append("ID: ").append(player.getEntityId());
            offset = true;
        }
        if (this.module.gameMode.getValue()) {
            builder.append(offset ? " " : "").append("[").append(player.isCreative() ? "C" : (player.isSpectator() ? "I" : "S")).append("]");
            offset = true;
        }
        final NetHandlerPlayClient connection = Nametag.mc.getConnection();
        if (connection != null) {
            final NetworkPlayerInfo playerInfo = connection.getPlayerInfo(player.getUniqueID());
            if (this.module.ping.getValue() && playerInfo != null) {
                builder.append(offset ? " " : "").append(playerInfo.getResponseTime()).append("ms");
                offset = true;
            }
        }
        if (this.module.health.getValue()) {
            final double health = Math.ceil(EntityUtil.getHealth((EntityLivingBase)player));
            String healthColor;
            if (health > 18.0) {
                healthColor = "§a";
            }
            else if (health > 16.0) {
                healthColor = "§2";
            }
            else if (health > 12.0) {
                healthColor = "§e";
            }
            else if (health > 8.0) {
                healthColor = "§6";
            }
            else if (health > 5.0) {
                healthColor = "§c";
            }
            else {
                healthColor = "§4";
            }
            builder.append(offset ? " " : "").append(healthColor).append((health > 0.0) ? Integer.valueOf((int)health) : "0");
        }
        if (this.module.pops.getValue()) {
            final int pops = Managers.COMBAT.getPops((Entity)player);
            if (pops != 0) {
                builder.append("§f").append(" -").append(pops);
            }
        }
        return builder.toString();
    }
    
    private int getColor(final EntityPlayer player) {
        if (Managers.FRIENDS.contains(player)) {
            return -10027009;
        }
        if (this.module.burrow.getValue()) {
            final BlockPos pos = PositionUtil.getPosition((Entity)player);
            final IBlockState state = Nametag.mc.world.getBlockState(pos);
            if (!state.getMaterial().isReplaceable() && state.getBoundingBox((IBlockAccess)Nametag.mc.world, pos).offset(pos).maxY > player.posY) {
                return -10026905;
            }
        }
        if (Managers.ENEMIES.contains(player)) {
            return -65536;
        }
        if (player.isInvisible()) {
            return -56064;
        }
        if (Nametag.mc.getConnection() != null && Nametag.mc.getConnection().getPlayerInfo(player.getUniqueID()) == null) {
            return -1113785;
        }
        if (player.isSneaking() && this.module.sneak.getValue()) {
            return -26368;
        }
        return -1;
    }
    
    static {
        MEDIA = Caches.getModule(Media.class);
    }
}
