//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import net.minecraft.util.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.management.*;
import java.nio.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import net.minecraft.client.shader.*;
import net.minecraft.util.text.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.*;
import org.lwjgl.*;
import net.minecraft.client.renderer.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import net.minecraft.util.text.event.*;
import java.net.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ScreenShotHelper.class })
public abstract class MixinScreenShotHelper
{
    private static final SettingCache<Boolean, BooleanSetting, Management> POOL;
    @Shadow
    private static IntBuffer pixelBuffer;
    @Shadow
    private static int[] pixelValues;
    
    @Redirect(method = { "saveScreenshot(Ljava/io/File;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ScreenShotHelper;saveScreenshot(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;"))
    private static ITextComponent saveScreenshot(final File gameDirectory, @Nullable final String name, final int width, final int height, final Framebuffer buffer) {
        if (MixinScreenShotHelper.POOL.getValue()) {
            try {
                return makeScreenShot(gameDirectory, name, width, height, buffer);
            }
            catch (IOException e) {
                e.printStackTrace();
                return (ITextComponent)new TextComponentTranslation("screenshot.failure", new Object[] { e.getMessage() });
            }
        }
        return ScreenShotHelper.saveScreenshot(gameDirectory, (String)null, width, height, buffer);
    }
    
    private static ITextComponent makeScreenShot(final File gameDirectory, @Nullable final String name, int width, int height, final Framebuffer buffer) throws IOException {
        if (OpenGlHelper.isFramebufferEnabled()) {
            width = buffer.framebufferTextureWidth;
            height = buffer.framebufferTextureHeight;
        }
        final int i = width * height;
        if (MixinScreenShotHelper.pixelBuffer == null || MixinScreenShotHelper.pixelBuffer.capacity() < i) {
            MixinScreenShotHelper.pixelBuffer = BufferUtils.createIntBuffer(i);
            MixinScreenShotHelper.pixelValues = new int[i];
        }
        GlStateManager.glPixelStorei(3333, 1);
        GlStateManager.glPixelStorei(3317, 1);
        MixinScreenShotHelper.pixelBuffer.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(buffer.framebufferTexture);
            GlStateManager.glGetTexImage(3553, 0, 32993, 33639, MixinScreenShotHelper.pixelBuffer);
        }
        else {
            GlStateManager.glReadPixels(0, 0, width, height, 32993, 33639, MixinScreenShotHelper.pixelBuffer);
        }
        MixinScreenShotHelper.pixelBuffer.get(MixinScreenShotHelper.pixelValues);
        final AtomicBoolean finished = new AtomicBoolean();
        final AtomicReference<String> supplier = new AtomicReference<String>("Creating Screenshot...");
        final AtomicReference<File> file = new AtomicReference<File>();
        Managers.THREAD.submit(new ScreenShotRunnable(supplier, file, finished, width, height, MixinScreenShotHelper.pixelValues, gameDirectory, name));
        final ITextComponent component = (ITextComponent)new SuppliedComponent(supplier::get);
        component.getStyle().setClickEvent((ClickEvent)new RunnableClickEvent(() -> {
            final File f = file.get();
            if (f != null) {
                final URI uri = new File(f.getAbsolutePath()).toURI();
                try {
                    FileUtil.openWebLink(uri);
                }
                catch (Throwable t) {
                    final Throwable cause = t.getCause();
                    Earthhack.getLogger().error("Couldn't open link: {}", (Object)((cause == null) ? "<UNKNOWN>" : cause.getMessage()));
                }
            }
            return;
        }));
        component.getStyle().setUnderlined(Boolean.valueOf(true));
        return component;
    }
    
    static {
        POOL = Caches.getSetting(Management.class, BooleanSetting.class, "Pooled-ScreenShots", false);
    }
}
