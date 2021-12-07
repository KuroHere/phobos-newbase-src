//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.esp;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.esp.mode.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.tileentity.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class ESP extends Module
{
    public static boolean isRendering;
    public final Setting<EspMode> mode;
    protected final Setting<Boolean> players;
    protected final Setting<Boolean> monsters;
    protected final Setting<Boolean> animals;
    protected final Setting<Boolean> vehicles;
    protected final Setting<Boolean> misc;
    protected final Setting<Boolean> items;
    protected final Setting<Boolean> storage;
    protected final Setting<Float> storageRange;
    protected final Setting<Float> lineWidth;
    protected final Setting<Boolean> hurt;
    public final Setting<Color> color;
    public final Setting<Color> invisibleColor;
    public final Setting<Color> friendColor;
    public final Setting<Color> targetColor;
    protected final Setting<Float> scale;
    
    public ESP() {
        super("ESP", Category.Render);
        this.mode = this.register(new EnumSetting("Mode", EspMode.Outline));
        this.players = this.register(new BooleanSetting("Players", true));
        this.monsters = this.register(new BooleanSetting("Monsters", false));
        this.animals = this.register(new BooleanSetting("Animals", false));
        this.vehicles = this.register(new BooleanSetting("Vehicles", false));
        this.misc = this.register(new BooleanSetting("Other", false));
        this.items = this.register(new BooleanSetting("Items", false));
        this.storage = this.register(new BooleanSetting("Storage", false));
        this.storageRange = this.register(new NumberSetting("Storage-Range", 1000.0f, 0.0f, 1000.0f));
        this.lineWidth = this.register(new NumberSetting("LineWidth", 3.0f, 0.1f, 10.0f));
        this.hurt = this.register(new BooleanSetting("Hurt", false));
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
        this.invisibleColor = this.register(new ColorSetting("InvisibleColor", new Color(180, 180, 255, 255)));
        this.friendColor = this.register(new ColorSetting("FriendColor", new Color(50, 255, 50, 255)));
        this.targetColor = this.register(new ColorSetting("TargetColor", new Color(255, 0, 0, 255)));
        this.scale = this.register(new NumberSetting("Scale", 0.003f, 0.001f, 0.01f));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerModel(this));
        this.setData(new ESPData(this));
    }
    
    @Override
    protected void onDisable() {
        ESP.isRendering = false;
    }
    
    protected boolean isValid(final Entity entity) {
        final Entity renderEntity = RenderUtil.getEntity();
        return entity != null && !entity.isDead && !entity.equals((Object)renderEntity) && ((EntityType.isAnimal(entity) && this.animals.getValue()) || (EntityType.isMonster(entity) && this.monsters.getValue()) || (entity instanceof EntityEnderCrystal && this.misc.getValue()) || (entity instanceof EntityPlayer && this.players.getValue()) || (EntityType.isVehicle(entity) && this.vehicles.getValue()));
    }
    
    protected void drawTileEntities() {
        final Frustum frustum = new Frustum();
        final Entity renderEntity = (Entity)((ESP.mc.getRenderViewEntity() == null) ? ESP.mc.player : ESP.mc.getRenderViewEntity());
        try {
            final double x = renderEntity.posX;
            final double y = renderEntity.posY;
            final double z = renderEntity.posZ;
            frustum.setPosition(x, y, z);
            for (final TileEntity tileEntity : ESP.mc.world.loadedTileEntityList) {
                if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest) {
                    if (ESP.mc.player.getDistance((double)tileEntity.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ()) > this.storageRange.getValue()) {
                        continue;
                    }
                    final double posX = tileEntity.getPos().getX() - Interpolation.getRenderPosX();
                    final double posY = tileEntity.getPos().getY() - Interpolation.getRenderPosY();
                    final double posZ = tileEntity.getPos().getZ() - Interpolation.getRenderPosZ();
                    AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
                    if (tileEntity instanceof TileEntityChest) {
                        TileEntityChest adjacent = null;
                        if (((TileEntityChest)tileEntity).adjacentChestXNeg != null) {
                            adjacent = ((TileEntityChest)tileEntity).adjacentChestXNeg;
                        }
                        if (((TileEntityChest)tileEntity).adjacentChestXPos != null) {
                            adjacent = ((TileEntityChest)tileEntity).adjacentChestXPos;
                        }
                        if (((TileEntityChest)tileEntity).adjacentChestZNeg != null) {
                            adjacent = ((TileEntityChest)tileEntity).adjacentChestZNeg;
                        }
                        if (((TileEntityChest)tileEntity).adjacentChestZPos != null) {
                            adjacent = ((TileEntityChest)tileEntity).adjacentChestZPos;
                        }
                        if (adjacent != null) {
                            bb = bb.union(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(adjacent.getPos().getX() - Interpolation.getRenderPosX(), adjacent.getPos().getY() - Interpolation.getRenderPosY(), adjacent.getPos().getZ() - Interpolation.getRenderPosZ()));
                        }
                    }
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3553);
                    GL11.glEnable(2848);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    this.colorTileEntityInside(tileEntity);
                    RenderUtil.drawBox(bb);
                    this.colorTileEntity(tileEntity);
                    RenderUtil.drawOutline(bb, this.lineWidth.getValue());
                    GL11.glDisable(2848);
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    GL11.glDisable(3042);
                    RenderUtil.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPopMatrix();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void colorTileEntityInside(final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            if (((TileEntityChest)tileEntity).getChestType() == BlockChest.Type.TRAP) {
                RenderUtil.color(new Color(250, 54, 0, 60));
            }
            else {
                RenderUtil.color(new Color(234, 183, 88, 60));
            }
        }
        else if (tileEntity instanceof TileEntityEnderChest) {
            RenderUtil.color(new Color(174, 0, 255, 60));
        }
    }
    
    protected void colorTileEntity(final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            if (((TileEntityChest)tileEntity).getChestType() == BlockChest.Type.TRAP) {
                RenderUtil.color(new Color(250, 54, 0, 255));
            }
            else {
                RenderUtil.color(new Color(234, 183, 88, 255));
            }
        }
        else if (tileEntity instanceof TileEntityEnderChest) {
            RenderUtil.color(new Color(174, 0, 255, 255));
        }
    }
    
    protected Color getEntityColor(final Entity entity) {
        final Entity target = Managers.TARGET.getKillAura();
        final Entity target2 = Managers.TARGET.getCrystal();
        final EntityPlayer target3 = Managers.TARGET.getAutoCrystal();
        if (entity.equals((Object)target) || entity.equals((Object)target2) || entity.equals((Object)target3)) {
            return this.targetColor.getValue();
        }
        if (entity instanceof EntityItem) {
            return new Color(255, 255, 255, 255);
        }
        if (EntityType.isVehicle(entity) && this.vehicles.getValue()) {
            return new Color(200, 100, 0, 255);
        }
        if (EntityType.isAnimal(entity) && this.animals.getValue()) {
            return new Color(0, 200, 0, 255);
        }
        if (EntityType.isMonster(entity) || (EntityType.isAngry(entity) && this.monsters.getValue())) {
            return new Color(200, 60, 60, 255);
        }
        if (entity instanceof EntityEnderCrystal && this.misc.getValue()) {
            return new Color(200, 100, 200, 255);
        }
        if (!(entity instanceof EntityPlayer) || !this.players.getValue()) {
            return this.color.getValue();
        }
        final EntityPlayer player = (EntityPlayer)entity;
        if (player.isInvisible()) {
            return this.invisibleColor.getValue();
        }
        if (Managers.FRIENDS.contains(player)) {
            return this.friendColor.getValue();
        }
        return this.color.getValue();
    }
    
    protected void checkSetupFBO() {
        final Framebuffer fbo = ESP.mc.getFramebuffer();
        if (fbo.depthBuffer > -1) {
            this.setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }
    
    protected void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, ESP.mc.displayWidth, ESP.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
    }
    
    public void renderOne(final float lineWidth) {
        this.checkSetupFBO();
        GL11.glPushMatrix();
        GL11.glEnable(32823);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public void renderFour(final Color color) {
        RenderUtil.color(color);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(32823);
        GL11.glPopMatrix();
    }
    
    public boolean shouldHurt() {
        return this.isEnabled() && ESP.isRendering && this.hurt.getValue();
    }
}
