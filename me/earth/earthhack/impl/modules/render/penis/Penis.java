//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.penis;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import java.awt.*;
import org.lwjgl.util.glu.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class Penis extends Module
{
    protected final Setting<Float> selfLength;
    protected final Setting<Float> friendLength;
    protected final Setting<Float> enemyLength;
    protected final Setting<Boolean> uncircumcised;
    public final Setting<Color> selfShaftColor;
    public final Setting<Color> selfTipColor;
    public final Setting<Color> friendShaftColor;
    public final Setting<Color> friendTipColor;
    public final Setting<Color> enemyShaftColor;
    public final Setting<Color> enemyTipColor;
    private final Cylinder shaft;
    private final Sphere ball;
    private final Sphere tip;
    
    public Penis() {
        super("Penis", Category.Render);
        this.selfLength = this.register(new NumberSetting("SelfLength", 0.8f, 0.1f, 2.0f));
        this.friendLength = this.register(new NumberSetting("FriendLength", 0.8f, 0.1f, 2.0f));
        this.enemyLength = this.register(new NumberSetting("EnemyLength", 0.4f, 0.1f, 2.0f));
        this.uncircumcised = this.register(new BooleanSetting("Uncircumcised", false));
        this.selfShaftColor = this.register(new ColorSetting("SelfShaftColor", new Color(95, 67, 63, 255)));
        this.selfTipColor = this.register(new ColorSetting("SelfTipColor", new Color(160, 99, 98, 255)));
        this.friendShaftColor = this.register(new ColorSetting("FriendShaftColor", new Color(95, 67, 63, 255)));
        this.friendTipColor = this.register(new ColorSetting("FriendTipColor", new Color(160, 99, 98, 255)));
        this.enemyShaftColor = this.register(new ColorSetting("EnemyShaftColor", new Color(95, 67, 63, 255)));
        this.enemyTipColor = this.register(new ColorSetting("EnemyTipColor", new Color(160, 99, 98, 255)));
        this.shaft = new Cylinder();
        this.ball = new Sphere();
        this.tip = new Sphere();
        this.listeners.add(new ListenerRender(this));
        this.setData(new PenisData(this));
        this.shaft.setDrawStyle(100012);
        this.ball.setDrawStyle(100012);
        this.tip.setDrawStyle(100012);
    }
    
    protected void onRender3D() {
        for (final EntityPlayer player : Penis.mc.world.playerEntities) {
            final Vec3d interpolateEntity = Interpolation.interpolateEntity((Entity)player);
            this.drawPenis(player, interpolateEntity.xCoord, interpolateEntity.yCoord, interpolateEntity.zCoord);
        }
    }
    
    protected void drawPenis(final EntityPlayer player, final double x, final double y, final double z) {
        final float length = (player == Penis.mc.player) ? this.selfLength.getValue() : (Managers.FRIENDS.contains(player) ? this.friendLength.getValue() : ((float)this.enemyLength.getValue()));
        final Color shaftColor = (player == Penis.mc.player) ? this.selfShaftColor.getValue() : (Managers.FRIENDS.contains(player) ? this.friendShaftColor.getValue() : this.enemyShaftColor.getValue());
        final Color tipColor = (player == Penis.mc.player) ? this.selfTipColor.getValue() : (Managers.FRIENDS.contains(player) ? this.friendTipColor.getValue() : this.enemyTipColor.getValue());
        GL11.glPushMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(false);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-player.rotationYaw, 0.0f, player.height, 0.0f);
        GL11.glTranslated(-x, -y, -z);
        GL11.glTranslated(x, y + player.height / 2.0f - 0.22499999403953552, z);
        GL11.glColor4f(shaftColor.getRed() / 255.0f, shaftColor.getGreen() / 255.0f, shaftColor.getBlue() / 255.0f, 1.0f);
        GL11.glTranslated(0.0, 0.0, 0.07500000298023224);
        this.shaft.draw(0.1f, 0.11f, length, 25, 20);
        GL11.glColor4f(shaftColor.getRed() / 255.0f, shaftColor.getGreen() / 255.0f, shaftColor.getBlue() / 255.0f, 1.0f);
        GL11.glTranslated(0.0, 0.0, 0.02500000298023223);
        GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
        this.ball.draw(0.14f, 10, 20);
        GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
        this.ball.draw(0.14f, 10, 20);
        GL11.glTranslated(-0.07000000074505806, 0.0, length - (this.uncircumcised.getValue() ? 0.15 : 0.0));
        GL11.glColor4f(tipColor.getRed() / 255.0f, tipColor.getGreen() / 255.0f, tipColor.getBlue() / 255.0f, 1.0f);
        this.tip.draw(0.13f, 15, 20);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
