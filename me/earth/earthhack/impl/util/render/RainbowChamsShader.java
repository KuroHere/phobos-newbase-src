//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.chams.*;
import me.earth.earthhack.impl.modules.*;
import org.lwjgl.opengl.*;

public class RainbowChamsShader extends FramebufferShader
{
    public static final RainbowChamsShader RAINBOW_CHAMS_SHADER;
    private final ModuleCache<Chams> CHAMS;
    private final long initTime;
    
    private RainbowChamsShader() {
        super("rainbowchams.frag");
        this.CHAMS = Caches.getModule(Chams.class);
        this.initTime = System.currentTimeMillis();
    }
    
    @Override
    public void setupUniforms() {
        this.setupUniform("time");
        this.setupUniform("resolution");
        this.setupUniform("alpha");
    }
    
    @Override
    public void updateUniforms() {
        GL20.glUniform1f(this.getUniform("time"), (System.currentTimeMillis() - this.initTime) / 1000.0f);
        GL20.glUniform2f(this.getUniform("resolution"), RainbowChamsShader.mc.displayWidth * 2 / 20.0f, RainbowChamsShader.mc.displayHeight * 2 / 20.0f);
        GL20.glUniform1f(this.getUniform("alpha"), 1.0f);
    }
    
    static {
        RAINBOW_CHAMS_SHADER = new RainbowChamsShader();
    }
}
