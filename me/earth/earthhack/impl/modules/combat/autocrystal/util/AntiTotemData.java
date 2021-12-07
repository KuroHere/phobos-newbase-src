//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

import java.util.*;
import net.minecraft.entity.player.*;

public class AntiTotemData extends PositionData
{
    private final Set<PositionData> corresponding;
    
    public AntiTotemData(final PositionData data) {
        super(data.getPos(), data.getMaxLength(), data.getAntiTotems());
        this.corresponding = new TreeSet<PositionData>();
    }
    
    public void addCorrespondingData(final PositionData data) {
        this.corresponding.add(data);
    }
    
    public Set<PositionData> getCorresponding() {
        return this.corresponding;
    }
    
    @Override
    public int compareTo(final PositionData o) {
        if (Math.abs(o.getSelfDamage() - this.getSelfDamage()) >= 1.0f || !(o instanceof AntiTotemData)) {
            return super.compareTo(o);
        }
        final EntityPlayer player = this.getFirstTarget();
        final EntityPlayer other = ((AntiTotemData)o).getFirstTarget();
        if (other == null) {
            if (player == null) {
                return super.compareTo(o);
            }
            return -1;
        }
        else {
            if (player == null) {
                return 1;
            }
            return Double.compare(player.getDistanceSq(this.getPos()), other.getDistanceSq(o.getPos()));
        }
    }
    
    public EntityPlayer getFirstTarget() {
        return this.getAntiTotems().stream().findFirst().orElse(null);
    }
}
