//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.network.handshake.client.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ C00Handshake.class })
public abstract class MixinC00Handshake implements IC00Handshake
{
    @Shadow
    private String ip;
    private boolean cancel;
    
    @Accessor("ip")
    @Override
    public abstract void setIP(final String p0);
    
    @Accessor("port")
    @Override
    public abstract void setPort(final int p0);
    
    @Override
    public void cancelFML(final boolean cancel) {
        this.cancel = cancel;
    }
    
    @Redirect(method = { "writePacketData" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketBuffer;writeString(Ljava/lang/String;)Lnet/minecraft/network/PacketBuffer;"))
    public PacketBuffer writePacketDataHook(final PacketBuffer buffer, final String string) {
        if (this.cancel) {
            buffer.writeString(this.ip);
        }
        else {
            buffer.writeString(string);
        }
        return buffer;
    }
}
