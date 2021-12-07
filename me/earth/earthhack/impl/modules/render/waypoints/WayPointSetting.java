//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.waypoints;

import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.render.waypoints.mode.*;
import com.google.gson.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.gui.chat.factory.*;

public class WayPointSetting extends RemovingSetting<BlockPos>
{
    private WayPointType type;
    private boolean corrupted;
    
    public WayPointSetting(final String nameIn, final BlockPos initialValue) {
        super(nameIn, initialValue);
        this.type = WayPointType.None;
    }
    
    @Override
    public void setValue(final BlockPos value) {
        this.value = (T)value;
    }
    
    @Override
    public void setValue(final BlockPos value, final boolean withEvent) {
        this.value = (T)value;
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        final String[] s = element.getAsString().split("[xyz]");
        if (s.length < 4) {
            this.corrupted = true;
            Earthhack.getLogger().warn(this.getName() + " : can't set from Json: " + element.getAsString() + ".");
            return;
        }
        this.type = WayPointType.fromString(s[0]);
        if (this.type == WayPointType.None) {
            Earthhack.getLogger().warn(this.getName() + " corrupted type: " + s[0]);
            this.corrupted = true;
            return;
        }
        try {
            final double x = Double.parseDouble(s[1]);
            final double y = Double.parseDouble(s[2]);
            final double z = Double.parseDouble(s[3]);
            this.setValue(new BlockPos(x, y, z));
        }
        catch (Exception e) {
            this.corrupted = true;
            e.printStackTrace();
        }
    }
    
    @Override
    public String toJson() {
        final BlockPos pos = this.getValue();
        return this.type + "x" + pos.getX() + "y" + pos.getY() + "z" + pos.getZ();
    }
    
    public boolean isCorrupted() {
        return this.corrupted;
    }
    
    public WayPointType getType() {
        return this.type;
    }
    
    public void setType(final WayPointType type) {
        this.type = type;
    }
    
    static {
        ComponentFactory.register(WayPointSetting.class, WayPointComponent::new);
    }
}
