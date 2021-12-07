//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class MovementInputFromRemotePlayer extends MovementInput
{
    private final EntityPlayer player;
    
    public MovementInputFromRemotePlayer(final EntityPlayer player) {
        this.player = player;
    }
    
    public void updatePlayerMoveState() {
        final MovementInput input = MovementUtil.inverse((Entity)this.player, 0.2783);
        this.field_192832_b = input.field_192832_b;
        this.moveStrafe = input.moveStrafe;
        if (this.field_192832_b == 0.0f && this.moveStrafe == 0.0f) {
            this.forwardKeyDown = false;
            this.backKeyDown = false;
            this.leftKeyDown = false;
            this.rightKeyDown = false;
        }
        if (this.field_192832_b < 0.0f) {
            this.backKeyDown = true;
        }
        else if (this.field_192832_b > 0.0f) {
            this.forwardKeyDown = true;
        }
        if (this.moveStrafe < 0.0f) {
            this.rightKeyDown = true;
        }
        else if (this.moveStrafe > 0.0f) {
            this.leftKeyDown = true;
        }
        this.jump = false;
        this.sneak = input.sneak;
        if (this.sneak) {
            this.field_192832_b *= (float)0.3;
            this.moveStrafe *= (float)0.3;
        }
    }
}
