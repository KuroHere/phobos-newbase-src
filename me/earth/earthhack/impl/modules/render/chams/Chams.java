//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.chams;

import me.earth.earthhack.api.module.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.chams.mode.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import javax.imageio.*;
import me.earth.earthhack.impl.util.render.image.*;
import java.security.*;
import java.awt.image.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.api.setting.event.*;

public class Chams extends Module
{
    public static final ResourceLocation GALAXY_LOCATION;
    protected int texID;
    public final Setting<ChamsMode> mode;
    protected final Setting<Boolean> self;
    protected final Setting<Boolean> players;
    protected final Setting<Boolean> animals;
    protected final Setting<Boolean> monsters;
    protected final Setting<Boolean> texture;
    protected final Setting<Boolean> xqz;
    protected final Setting<Boolean> armor;
    protected final Setting<Float> z;
    protected final Setting<Float> mixFactor;
    protected final Setting<String> image;
    protected final Setting<Color> color;
    protected final Setting<Color> wallsColor;
    protected final Setting<Color> friendColor;
    protected final Setting<Color> friendWallColor;
    protected final Setting<Color> enemyColor;
    protected final Setting<Color> enemyWallsColor;
    protected final Setting<Color> armorColor;
    protected final Setting<Color> armorFriendColor;
    protected final Setting<Color> armorEnemyColor;
    protected boolean force;
    protected boolean hasImageChammed;
    protected boolean renderLayers;
    protected boolean renderModels;
    protected final GlShader fireShader;
    protected final GlShader galaxyShader;
    protected final GlShader waterShader;
    protected final GlShader alphaShader;
    protected final GlShader imageShader;
    protected final long initTime;
    protected boolean gif;
    protected GifImage gifImage;
    protected DynamicTexture dynamicTexture;
    
    public Chams() {
        super("Chams", Category.Render);
        this.texID = -1;
        this.mode = this.register(new EnumSetting("Mode", ChamsMode.Normal));
        this.self = this.register(new BooleanSetting("Self", false));
        this.players = this.register(new BooleanSetting("Players", true));
        this.animals = this.register(new BooleanSetting("Animals", false));
        this.monsters = this.register(new BooleanSetting("Monsters", false));
        this.texture = this.register(new BooleanSetting("Texture", false));
        this.xqz = this.register(new BooleanSetting("XQZ", true));
        this.armor = this.register(new BooleanSetting("Armor", true));
        this.z = new NumberSetting<Float>("Z", -2000.0f, -5000.0f, 5000.0f);
        this.mixFactor = this.register(new NumberSetting("MixFactor", 1.0f, 0.0f, 1.0f));
        this.image = this.register(new StringSetting("Image", "None!"));
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
        this.wallsColor = this.register(new ColorSetting("WallsColor", new Color(255, 255, 255, 255)));
        this.friendColor = this.register(new ColorSetting("FriendColor", new Color(255, 255, 255, 255)));
        this.friendWallColor = this.register(new ColorSetting("FriendWallsColor", new Color(255, 255, 255, 255)));
        this.enemyColor = this.register(new ColorSetting("EnemyColor", new Color(255, 255, 255, 255)));
        this.enemyWallsColor = this.register(new ColorSetting("EnemyWallsColor", new Color(255, 255, 255, 255)));
        this.armorColor = this.register(new ColorSetting("ArmorColor", new Color(255, 255, 255, 255)));
        this.armorFriendColor = this.register(new ColorSetting("ArmorFriendColor", new Color(255, 255, 255, 255)));
        this.armorEnemyColor = this.register(new ColorSetting("ArmorEnemyColor", new Color(255, 255, 255, 255)));
        this.fireShader = GlShader.createShader("chams");
        this.galaxyShader = GlShader.createShader("stars");
        this.waterShader = GlShader.createShader("water");
        this.alphaShader = GlShader.createShader("alpha");
        this.imageShader = GlShader.createShader("image");
        this.initTime = System.currentTimeMillis();
        this.gif = false;
        try {
            this.gifImage = GifConverter.readGifImage(new FileInputStream("D:/DesktopHDD/project/felix.gif"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.listeners.add(new ListenerModelPre(this));
        this.listeners.add(new ListenerModelPost(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerRenderEntity(this));
        this.listeners.add(new ListenerRenderLayers(this));
        final IOException e;
        this.image.addObserver(e -> this.loadImage(e.getValue()));
        this.setData(new ChamsData(this));
        Chams.mc.getTextureManager().loadTexture(Chams.GALAXY_LOCATION, (ITextureObject)new SimpleTexture(Chams.GALAXY_LOCATION));
    }
    
    public void loadImage(final String value) {
        try {
            final File file = new File(value);
            if (value.endsWith(".gif")) {
                final GifImage image = GifConverter.readGifImage(new FileInputStream(file));
                this.gif = true;
                this.gifImage = image;
            }
            else if (value.endsWith(".png")) {
                this.gif = false;
                BufferedImage image2 = ImageIO.read(file);
                image2 = ImageUtil.createFlipped(image2);
                this.dynamicTexture = ImageUtil.cacheBufferedImage(image2, "png");
            }
            else if (value.endsWith(".jpg") || value.endsWith(".jpeg")) {
                this.gif = false;
                BufferedImage image2 = ImageIO.read(file);
                image2 = ImageUtil.createFlipped(image2);
                this.dynamicTexture = ImageUtil.cacheBufferedImage(image2, "jpg");
            }
        }
        catch (IOException ex) {}
        catch (NoSuchAlgorithmException ex2) {}
    }
    
    public boolean isValid(final Entity entity, final ChamsMode modeIn) {
        return this.isEnabled() && modeIn == this.mode.getValue() && this.isValid(entity);
    }
    
    public boolean isValid(final Entity entity) {
        final Entity renderEntity = RenderUtil.getEntity();
        return entity != null && (this.self.getValue() || !entity.equals((Object)renderEntity)) && ((this.players.getValue() && entity instanceof EntityPlayer) || (this.monsters.getValue() && (EntityType.isMonster(entity) || EntityType.isBoss(entity))) || (this.animals.getValue() && (EntityType.isAngry(entity) || EntityType.isAnimal(entity))));
    }
    
    protected Color getVisibleColor(final Entity entity) {
        if (Managers.FRIENDS.contains(entity)) {
            return this.friendColor.getValue();
        }
        if (Managers.ENEMIES.contains(entity)) {
            return this.enemyColor.getValue();
        }
        return this.color.getValue();
    }
    
    protected Color getWallsColor(final Entity entity) {
        if (Managers.FRIENDS.contains(entity)) {
            return this.friendWallColor.getValue();
        }
        if (Managers.ENEMIES.contains(entity)) {
            return this.enemyWallsColor.getValue();
        }
        return this.wallsColor.getValue();
    }
    
    public Color getArmorVisibleColor(final Entity entity) {
        if (Managers.FRIENDS.contains(entity)) {
            return this.armorFriendColor.getValue();
        }
        if (Managers.ENEMIES.contains(entity)) {
            return this.armorEnemyColor.getValue();
        }
        return this.armorColor.getValue();
    }
    
    protected void checkSetupFBO() {
        final Framebuffer fbo = Chams.mc.getFramebuffer();
        if (fbo.depthBuffer > -1) {
            this.setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }
    
    protected void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Chams.mc.displayWidth, Chams.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
    }
    
    public boolean shouldArmorChams() {
        return this.armor.getValue();
    }
    
    public boolean isImageChams() {
        return this.mode.getValue() == ChamsMode.Image;
    }
    
    public float getAlpha() {
        return this.color.getValue().getAlpha() / 255.0f;
    }
    
    static {
        GALAXY_LOCATION = new ResourceLocation("earthhack:textures/client/galaxy.jpg");
    }
}
