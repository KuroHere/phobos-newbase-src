// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.spammer;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.module.data.*;
import com.google.common.collect.*;
import me.earth.earthhack.impl.util.misc.*;
import java.util.*;
import me.earth.earthhack.api.setting.event.*;

public class Spammer extends Module
{
    private static final String FILE = "earthhack/util/Spammer.txt";
    private static final String DEFAULT = "Good Fight!";
    private static final Random RND;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> random;
    protected final Setting<Boolean> antiKick;
    protected final Setting<Boolean> greenText;
    protected final Setting<Boolean> refresh;
    protected final Setting<Boolean> autoOff;
    protected final List<String> messages;
    protected final StopWatch timer;
    protected int currentIndex;
    
    public Spammer() {
        super("Spammer", Category.Misc);
        this.delay = this.register(new NumberSetting("Delay", 5, 1, 60));
        this.random = this.register(new BooleanSetting("Random", false));
        this.antiKick = this.register(new BooleanSetting("AntiKick", false));
        this.greenText = this.register(new BooleanSetting("GreenText", false));
        this.refresh = this.register(new BooleanSetting("Refresh", false));
        this.autoOff = this.register(new BooleanSetting("AutoOff", false));
        this.messages = new ArrayList<String>();
        this.timer = new StopWatch();
        this.currentIndex = 0;
        this.listeners.add(new ListenerUpdate(this));
        this.refresh.addObserver(event -> {
            ChatUtil.sendMessage("<" + this.getDisplayName() + "> Reloading File...");
            this.loadFile();
            this.currentIndex = 0;
            event.setCancelled(true);
            return;
        });
        this.setData(new SpammerData(this));
    }
    
    @Override
    protected void onLoad() {
        this.loadFile();
    }
    
    @Override
    protected void onEnable() {
        this.currentIndex = 0;
    }
    
    private void loadFile() {
        this.messages.clear();
        for (final String string : FileUtil.readFile("earthhack/util/Spammer.txt", true, Lists.newArrayList((Object[])new String[] { "Good Fight!" }))) {
            if (!string.replace("\\s", "").isEmpty()) {
                this.messages.add(string);
            }
        }
    }
    
    protected String getSuffixedMessage() {
        return this.getMessage() + this.getSuffix();
    }
    
    protected String getSuffix() {
        if (this.antiKick.getValue()) {
            return ChatUtil.generateRandomHexSuffix(2);
        }
        return "";
    }
    
    private String getMessage() {
        if (this.messages.isEmpty()) {
            return "Good Fight!";
        }
        if (this.random.getValue()) {
            final String result = this.messages.get(Spammer.RND.nextInt(this.messages.size()));
            if (result != null) {
                return result;
            }
        }
        final String result = this.messages.get(this.currentIndex);
        ++this.currentIndex;
        if (this.currentIndex >= this.messages.size()) {
            this.currentIndex = 0;
        }
        if (result != null) {
            return result;
        }
        return "Good Fight!";
    }
    
    static {
        RND = new Random();
    }
}
