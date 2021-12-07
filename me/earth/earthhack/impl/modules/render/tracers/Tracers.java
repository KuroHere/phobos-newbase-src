//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.tracers;

import me.earth.earthhack.impl.util.minecraft.entity.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.tracers.mode.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;

public class Tracers extends EntityTypeModule
{
    protected final Setting<Boolean> items;
    protected final Setting<Boolean> invisibles;
    protected final Setting<Boolean> friends;
    protected final Setting<TracerMode> mode;
    protected final Setting<BodyPart> target;
    protected final Setting<Boolean> lines;
    protected final Setting<Float> lineWidth;
    protected final Setting<Integer> tracers;
    protected final Setting<Float> minRange;
    protected final Setting<Float> maxRange;
    protected List<Entity> sorted;
    
    public Tracers() {
        super("Tracers", Category.Render);
        this.items = this.register(new BooleanSetting("Items", false));
        this.invisibles = this.register(new BooleanSetting("Invisibles", false));
        this.friends = this.register(new BooleanSetting("Friends", true));
        this.mode = this.register(new EnumSetting("Mode", TracerMode.Outline));
        this.target = this.register(new EnumSetting("Target", BodyPart.Body));
        this.lines = this.register(new BooleanSetting("Lines", true));
        this.lineWidth = this.register(new NumberSetting("LineWidth", 1.5f, 0.1f, 5.0f));
        this.tracers = this.register(new NumberSetting("Amount", 100, 1, 250));
        this.minRange = this.register(new NumberSetting("MinRange", 0.0f, 0.0f, 1000.0f));
        this.maxRange = this.register(new NumberSetting("MaxRange", 1000.0f, 0.0f, 1000.0f));
        this.sorted = new ArrayList<Entity>();
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerTick(this));
        this.setData(new TracersData(this));
    }
    
    @Override
    public boolean isValid(final Entity entity) {
        return entity != null && !EntityUtil.isDead(entity) && entity != Tracers.mc.getRenderViewEntity() && (Tracers.mc.getRenderViewEntity() == null || !entity.equals((Object)Tracers.mc.getRenderViewEntity().getRidingEntity())) && entity.getDistanceSqToEntity((Entity)Tracers.mc.player) >= MathUtil.square(this.minRange.getValue()) && entity.getDistanceSqToEntity((Entity)Tracers.mc.player) <= MathUtil.square(this.maxRange.getValue()) && ((this.items.getValue() && entity instanceof EntityItem) || (this.players.getValue() && entity instanceof EntityPlayer && (this.invisibles.getValue() || !entity.isInvisible()) && (this.friends.getValue() || !Managers.FRIENDS.contains(entity.getName()))) || super.isValid(entity));
    }
}
