//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import org.lwjgl.opengl.*;

public class RainbowGlowShader extends FramebufferShader
{
    public static final RainbowGlowShader RAINBOW_GLOW_SHADER;
    private final long initTime;
    
    public RainbowGlowShader() {
        super("rainbow.frag");
        this.initTime = System.currentTimeMillis();
    }
    
    @Override
    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
        this.setupUniform("time");
        this.setupUniform("resolution");
    }
    
    @Override
    public void updateUniforms() {
        GL20.glUniform1i(this.getUniform("texture"), 0);
        GL20.glUniform2f(this.getUniform("texelSize"), 1.0f / RainbowGlowShader.mc.displayWidth * (this.radius * this.quality), 1.0f / RainbowGlowShader.mc.displayHeight * (this.radius * this.quality));
        GL20.glUniform1f(this.getUniform("divider"), 140.0f);
        GL20.glUniform1f(this.getUniform("radius"), this.radius);
        GL20.glUniform1f(this.getUniform("maxSample"), 10.0f);
        GL20.glUniform1f(this.getUniform("time"), (System.currentTimeMillis() - this.initTime) / 1000.0f);
        GL20.glUniform2f(this.getUniform("resolution"), RainbowGlowShader.mc.displayWidth * 2 / 20.0f, RainbowGlowShader.mc.displayHeight * 2 / 20.0f);
    }
    
    static {
        RAINBOW_GLOW_SHADER = new RainbowGlowShader();
    }
}
