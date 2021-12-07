//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.sorter;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.client.*;
import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import me.earth.earthhack.impl.managers.config.helpers.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.api.config.*;
import java.nio.file.*;
import com.google.gson.*;
import me.earth.earthhack.impl.managers.config.util.*;
import java.io.*;

public class Sorter extends LoadableModule
{
    public static final String PATH = "earthhack/sorter";
    public static final String JSON_PATH = "earthhack/sorter/Sorter.json";
    protected final Setting<Boolean> virtualHotbar;
    protected final Setting<Boolean> sort;
    protected final Setting<Integer> delay;
    protected final Setting<Integer> globalDelay;
    protected final Setting<Boolean> sortInLoot;
    protected final Setting<Boolean> sortInInv;
    protected final StopWatch timer;
    protected final Map<String, InventoryLayout> layouts;
    protected Map<Integer, Integer> reverse;
    protected Map<Integer, Integer> mapping;
    protected InventoryLayout current;
    protected String currentLayout;
    
    public Sorter() {
        super("Sorter", Category.Player, "Add_Layout", "layout");
        this.virtualHotbar = this.register(new BooleanSetting("VirtualHotbar", false));
        this.sort = this.register(new BooleanSetting("Sort", false));
        this.delay = this.register(new NumberSetting("Delay", 500, 0, 5000));
        this.globalDelay = this.register(new NumberSetting("Global-Delay", 500, 0, 5000));
        this.sortInLoot = this.register(new BooleanSetting("SortInLoot", false));
        this.sortInInv = this.register(new BooleanSetting("SortInInventory", false));
        this.timer = new StopWatch();
        this.layouts = new HashMap<String, InventoryLayout>();
        this.reverse = null;
        this.mapping = null;
        Bus.EVENT_BUS.register(new LambdaListener<Object>(ShutDownEvent.class, e -> this.saveConfig()));
        this.listeners.add(new ListenerMotion(this));
    }
    
    public boolean scroll(int direction) {
        if (!this.isEnabled() || !this.virtualHotbar.getValue()) {
            return false;
        }
        final Map<Integer, Integer> mapping = this.mapping;
        final Map<Integer, Integer> reverse = this.reverse;
        if (mapping == null || reverse == null) {
            return false;
        }
        if (direction > 0) {
            direction = 1;
        }
        if (direction < 0) {
            direction = -1;
        }
        final int c = Sorter.mc.player.inventory.currentItem;
        int curr = reverse.getOrDefault(c, c);
        curr -= direction;
        if (curr < 0) {
            curr = 8;
        }
        else if (curr > 8) {
            curr = 0;
        }
        Sorter.mc.player.inventory.currentItem = mapping.getOrDefault(curr, curr);
        return true;
    }
    
    public void updateMapping() {
        if (!this.isEnabled() || !this.virtualHotbar.getValue()) {
            return;
        }
        final InventoryLayout current = this.current;
        if (current == null) {
            return;
        }
        final Map<Integer, Integer> mapping = new HashMap<Integer, Integer>(14, 0.75f);
        final Map<Integer, Integer> reverse = new HashMap<Integer, Integer>(14, 0.75f);
        final List<Integer> empty = new ArrayList<Integer>();
        for (int i = 0; i < 9; ++i) {
            final Item item = current.getItem(InventoryUtil.hotbarToInventory(i));
            if (item == Items.field_190931_a) {
                empty.add(i);
            }
            else {
                boolean notFound = true;
                for (int j = 0; j < 9; ++j) {
                    if (!mapping.containsValue(j) && item == Sorter.mc.player.inventory.getStackInSlot(j).getItem()) {
                        mapping.put(i, j);
                        reverse.put(j, i);
                        notFound = false;
                        break;
                    }
                }
                if (notFound) {
                    empty.add(i);
                }
            }
        }
        for (final int k : empty) {
            for (int l = 0; l < 9; ++l) {
                if (!mapping.containsValue(l)) {
                    mapping.put(k, l);
                    reverse.put(l, k);
                    break;
                }
            }
        }
        this.reverse = reverse;
        this.mapping = mapping;
    }
    
    public int getReverseMapping(final int slot) {
        return this.getMapping(slot, this.reverse);
    }
    
    public int getHotbarMapping(final int slot) {
        return this.getMapping(slot, this.mapping);
    }
    
    private int getMapping(final int slot, final Map<Integer, Integer> mapping) {
        if (!this.isEnabled() || !this.virtualHotbar.getValue() || mapping == null) {
            return slot;
        }
        return mapping.getOrDefault(slot, slot);
    }
    
    @Override
    protected void onLoad() {
        this.loadConfig();
        this.currentLayout = CurrentConfig.getInstance().get("sorter");
        if (this.currentLayout == null) {
            this.currentLayout = this.layouts.keySet().stream().findFirst().orElse(null);
        }
        if (this.currentLayout != null) {
            this.current = this.layouts.get(this.currentLayout);
        }
    }
    
    @Override
    public void add(final String string) {
        final InventoryLayout layout = InventoryLayout.createFromMcPlayer();
        this.layouts.put(string.toLowerCase(), layout);
        this.load(string.toLowerCase());
    }
    
    @Override
    public void del(final String string) {
        this.layouts.remove(string.toLowerCase());
        if (string.equalsIgnoreCase(this.currentLayout)) {
            this.load(this.currentLayout = this.layouts.keySet().stream().findFirst().orElse(null));
        }
    }
    
    @Override
    protected void load(final String string, final boolean noArgGiven) {
        if (noArgGiven) {
            ChatUtil.sendMessage("§cPlease specify a Layout to load!");
            return;
        }
        if (this.load(string)) {
            ChatUtil.sendMessage("§cCouldn't find layout §f" + string + "§c" + "!");
        }
        else {
            ChatUtil.sendMessage("§aLayout §f" + string + "§a" + " loaded successfully.");
        }
    }
    
    @Override
    protected String getLoadableStartingWith(String string) {
        string = string.toLowerCase();
        for (final String s : this.layouts.keySet()) {
            if (s.toLowerCase().startsWith(string.toLowerCase())) {
                return s;
            }
        }
        return null;
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length > 1 && args[1].equalsIgnoreCase("add") && Sorter.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be in game to add a new Inventory Layout.");
            return true;
        }
        return super.execute(args);
    }
    
    @Override
    public String getInput(final String input, final boolean add) {
        final String s = this.getLoadableStartingWith(input);
        if (s == null) {
            return "";
        }
        return TextUtil.substring(s, input.length());
    }
    
    public boolean load(final String layout) {
        final InventoryLayout l = this.layouts.get(layout);
        if (l == null) {
            return true;
        }
        this.currentLayout = layout;
        this.current = l;
        return false;
    }
    
    public void loadConfig() {
        this.layouts.clear();
        FileUtil.createDirectory(Paths.get("earthhack/sorter", new String[0]));
        final Path path = Paths.get("earthhack/sorter/Sorter.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        try (final InputStream stream = Files.newInputStream(path, new OpenOption[0])) {
            final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
            for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                final InventoryLayout layout = new InventoryLayout();
                layout.fromJson(entry.getValue());
                this.layouts.put(entry.getKey().toLowerCase(), layout);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveConfig() {
        if (this.currentLayout != null) {
            CurrentConfig.getInstance().set("sorter", this.currentLayout);
        }
        FileUtil.createDirectory(Paths.get("earthhack/sorter", new String[0]));
        final JsonObject object = new JsonObject();
        for (final Map.Entry<String, InventoryLayout> entry : this.layouts.entrySet()) {
            object.add(entry.getKey().toLowerCase(), Jsonable.parse(entry.getValue().toJson(), false));
        }
        try {
            JsonPathWriter.write(Paths.get("earthhack/sorter/Sorter.json", new String[0]), object);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
