//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.search;

import me.earth.earthhack.api.setting.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.util.misc.intintmap.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.api.setting.event.*;

public class Search extends RemovingItemAddingModule
{
    protected final Setting<Boolean> lines;
    protected final Setting<Boolean> fill;
    protected final Setting<Boolean> tracers;
    protected final Setting<Boolean> softReload;
    protected final Setting<Integer> maxBlocks;
    protected final Setting<Double> range;
    protected final Setting<Boolean> countInRange;
    protected final Setting<Boolean> coloredTracers;
    protected final Setting<Boolean> noUnloaded;
    protected final Setting<Boolean> remove;
    protected final Map<BlockPos, SearchResult> toRender;
    protected final IntIntMap colors;
    protected final StopWatch timer;
    protected int found;
    
    public Search() {
        super("Search", Category.Render, s -> "Searches for " + s.getName() + ".");
        this.lines = this.register(new BooleanSetting("Lines", true));
        this.fill = this.register(new BooleanSetting("Fill", false));
        this.tracers = this.register(new BooleanSetting("Tracers", false));
        this.softReload = this.register(new BooleanSetting("SoftReload", false));
        this.maxBlocks = this.register(new NumberSetting("Max-Blocks", 10000, 0, 10000));
        this.range = this.register(new NumberSetting("Range", 256.0, 0.0, 512.0));
        this.countInRange = this.register(new BooleanSetting("Count-Range", false));
        this.coloredTracers = this.register(new BooleanSetting("Colored-Tracers", false));
        this.noUnloaded = this.register(new BooleanSetting("NoUnloaded", false));
        this.remove = this.register(new BooleanSetting("Remove", false));
        this.toRender = new ConcurrentHashMap<BlockPos, SearchResult>();
        this.colors = new IntIntMapImpl(35, 0.75f);
        this.timer = new StopWatch();
        super.listType.setValue(ListType.WhiteList);
        super.listType.addObserver(e -> e.setValue(ListType.WhiteList));
        this.unregister(this.listType);
        this.listeners.add(new ListenerBlockRender(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerUnloadChunk(this));
        this.listeners.add(new ListenerBlockChange(this));
        this.listeners.add(new ListenerMultiBlockChange(this));
        this.colors.put(5, -1517671851);
        this.colors.put(10, -7141377);
        this.colors.put(11, -7141547);
        this.colors.put(14, -1869610923);
        this.colors.put(15, -2140123051);
        this.colors.put(16, 538976341);
        this.colors.put(17, -1517671851);
        this.colors.put(21, 3170389);
        this.colors.put(26, -16777131);
        this.colors.put(41, -1869610923);
        this.colors.put(42, -2140123051);
        this.colors.put(49, 1696715042);
        this.colors.put(52, 8051029);
        this.colors.put(56, 9480789);
        this.colors.put(57, 9480789);
        this.colors.put(73, 1610612821);
        this.colors.put(74, 1610612821);
        this.colors.put(90, 1696715076);
        this.colors.put(98, 9480789);
        this.colors.put(112, 16728862);
        this.colors.put(129, 8396885);
        this.colors.put(162, -1517671851);
        this.colors.put(354, 9480789);
        this.setData(new SearchData(this));
    }
    
    @Override
    protected void onEnable() {
        this.toRender.clear();
        Scheduler.getInstance().schedule(this::reloadRenders);
    }
    
    @Override
    public String getDisplayInfo() {
        return this.found + "";
    }
    
    @Override
    protected SimpleRemovingSetting addSetting(final String string) {
        final SimpleRemovingSetting s = super.addSetting(string);
        if (s != null) {
            this.reloadRenders();
        }
        return s;
    }
    
    @Override
    public Setting<?> unregister(final Setting<?> setting) {
        final Setting<?> s = super.unregister(setting);
        if (s != null) {
            this.toRender.clear();
            this.reloadRenders();
        }
        return s;
    }
    
    public void reloadRenders() {
        if (Search.mc.world != null && Search.mc.renderGlobal != null && Search.mc.player != null) {
            WorldRenderUtil.reload(this.softReload.getValue());
        }
    }
    
    public int getColor(final IBlockState state) {
        final int id = Block.getIdFromBlock(state.getBlock());
        final int color = this.colors.get(id);
        if (color != 0) {
            return color;
        }
        int blue = state.getMaterial().getMaterialMapColor().colorValue;
        final int red = blue >> 16 & 0xFF;
        final int green = blue >> 8 & 0xFF;
        blue &= 0xFF;
        return ColorUtil.toARGB(red, green, blue, 100);
    }
}
