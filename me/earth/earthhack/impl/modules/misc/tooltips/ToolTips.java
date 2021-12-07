//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.modules.misc.tooltips.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import me.earth.earthhack.impl.core.mixins.block.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.util.*;

public class ToolTips extends Module
{
    public static final ResourceLocation SHULKER_GUI_TEXTURE;
    public static final ResourceLocation MAP;
    protected final Setting<Boolean> shulkers;
    protected final Setting<Boolean> maps;
    protected final Setting<Boolean> shulkerSpy;
    protected final Setting<Boolean> own;
    protected final Setting<Bind> peekBind;
    protected final Map<String, TimeStack> spiedPlayers;
    
    public ToolTips() {
        super("ToolTips", Category.Misc);
        this.shulkers = this.register(new BooleanSetting("Shulkers", true));
        this.maps = this.register(new BooleanSetting("Maps", true));
        this.shulkerSpy = this.register(new BooleanSetting("ShulkerSpy", true));
        this.own = this.register(new BooleanSetting("Own", true));
        this.peekBind = this.register(new BindSetting("PeekBind", Bind.fromKey(ToolTips.mc.gameSettings.keyBindSneak.getKeyCode())));
        this.spiedPlayers = new ConcurrentHashMap<String, TimeStack>();
        this.listeners.add(new ListenerToolTip(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerRender2D(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerLogout(this));
        this.listeners.add(new ListenerPostToolTip(this));
        this.setData(new ToolTipsData(this));
    }
    
    public boolean drawShulkerToolTip(final ItemStack stack, final int x, final int y, final String name) {
        if (stack != null && stack.getItem() instanceof ItemShulkerBox) {
            final NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
                final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
                if (blockEntityTag.hasKey("Items", 9)) {
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableLighting();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    ToolTips.mc.getTextureManager().bindTexture(ToolTips.SHULKER_GUI_TEXTURE);
                    Render2DUtil.drawTexturedRect(x, y, 0, 0, 176, 16, 500);
                    Render2DUtil.drawTexturedRect(x, y + 16, 0, 16, 176, 57, 500);
                    Render2DUtil.drawTexturedRect(x, y + 70, 0, 160, 176, 8, 500);
                    GlStateManager.disableDepth();
                    Managers.TEXT.drawStringWithShadow((name == null) ? stack.getDisplayName() : name, (float)(x + 8), (float)(y + 6), -1);
                    GlStateManager.enableDepth();
                    RenderHelper.enableGUIStandardItemLighting();
                    GlStateManager.enableRescaleNormal();
                    GlStateManager.enableColorMaterial();
                    GlStateManager.enableLighting();
                    final NonNullList<ItemStack> nonNullList = (NonNullList<ItemStack>)NonNullList.func_191197_a(27, (Object)ItemStack.field_190927_a);
                    ItemStackHelper.func_191283_b(blockEntityTag, (NonNullList)nonNullList);
                    for (int i = 0; i < nonNullList.size(); ++i) {
                        final int iX = x + i % 9 * 18 + 8;
                        final int iY = y + i / 9 * 18 + 18;
                        final ItemStack itemStack = (ItemStack)nonNullList.get(i);
                        ToolTips.mc.getRenderItem().zLevel = 501.0f;
                        ToolTips.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, iX, iY);
                        ToolTips.mc.getRenderItem().renderItemOverlayIntoGUI(ToolTips.mc.fontRendererObj, itemStack, iX, iY, (String)null);
                        ToolTips.mc.getRenderItem().zLevel = 0.0f;
                    }
                    GlStateManager.disableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void displayInventory(final ItemStack stack, final String name) {
        try {
            final Item item = stack.getItem();
            final TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            final ItemShulkerBox shulker = (ItemShulkerBox)item;
            ((ITileEntity)entityBox).setBlockType(shulker.getBlock());
            entityBox.setWorldObj((World)ToolTips.mc.world);
            ItemStackHelper.func_191283_b(stack.getTagCompound().getCompoundTag("BlockEntityTag"), (NonNullList)((ITileEntityShulkerBox)entityBox).getItems());
            entityBox.readFromNBT(stack.getTagCompound().getCompoundTag("BlockEntityTag"));
            entityBox.func_190575_a((name == null) ? stack.getDisplayName() : name);
            ToolTips.mc.player.displayGUIChest((IInventory)entityBox);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ItemStack getStack(final String name) {
        final TimeStack stack = this.spiedPlayers.get(name.toLowerCase());
        if (stack != null) {
            return stack.getStack();
        }
        return null;
    }
    
    public Set<String> getPlayers() {
        return this.spiedPlayers.keySet();
    }
    
    static {
        SHULKER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
        MAP = new ResourceLocation("textures/map/map_background.png");
    }
}
