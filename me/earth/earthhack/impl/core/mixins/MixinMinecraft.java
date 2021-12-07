//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins;

import me.earth.earthhack.impl.core.ducks.*;
import net.minecraft.client.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.sorter.*;
import me.earth.earthhack.impl.modules.player.multitask.*;
import me.earth.earthhack.impl.modules.player.spectate.*;
import me.earth.earthhack.impl.modules.client.autoconfig.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.client.resources.data.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.lwjgl.input.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.impl.util.thread.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.event.events.client.*;
import me.earth.earthhack.impl.managers.*;
import java.io.*;
import me.earth.earthhack.impl.event.events.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft implements IMinecraft
{
    private static final ModuleCache<Sorter> SORTER;
    private static final ModuleCache<MultiTask> MULTI_TASK;
    private static final ModuleCache<Spectate> SPECTATE;
    private static final ModuleCache<AutoConfig> CONFIG;
    private static boolean isEarthhackRunning;
    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    private int leftClickCounter;
    @Shadow
    private int rightClickDelayTimer;
    @Shadow
    @Final
    private Queue<FutureTask<?>> scheduledTasks;
    @Shadow
    public WorldClient world;
    @Shadow
    public EntityPlayerSP player;
    private int gameLoop;
    @Shadow
    public GuiIngame ingameGUI;
    @Shadow
    public GuiScreen currentScreen;
    
    public MixinMinecraft() {
        this.gameLoop = 0;
    }
    
    @Shadow
    protected abstract void rightClickMouse();
    
    @Shadow
    protected abstract void clickMouse();
    
    @Shadow
    protected abstract void middleClickMouse();
    
    @Accessor("rightClickDelayTimer")
    @Override
    public abstract int getRightClickDelay();
    
    @Accessor("rightClickDelayTimer")
    @Override
    public abstract void setRightClickDelay(final int p0);
    
    @Accessor("metadataSerializer")
    @Override
    public abstract MetadataSerializer getMetadataSerializer();
    
    @Accessor("timer")
    @Override
    public abstract Timer getTimer();
    
    @Override
    public void click(final Click type) {
        switch (type) {
            case RIGHT: {
                this.rightClickMouse();
                break;
            }
            case LEFT: {
                this.clickMouse();
                break;
            }
            case MIDDLE: {
                this.middleClickMouse();
                break;
            }
        }
    }
    
    @Override
    public int getGameLoop() {
        return this.gameLoop;
    }
    
    @Override
    public boolean isEarthhackRunning() {
        return MixinMinecraft.isEarthhackRunning;
    }
    
    @Override
    public void runScheduledTasks() {
        synchronized (this.scheduledTasks) {
            while (!this.scheduledTasks.isEmpty()) {
                Util.runTask((FutureTask)this.scheduledTasks.poll(), MixinMinecraft.LOGGER);
            }
        }
    }
    
    @Inject(method = { "init" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/IReloadableResourceManager;registerReloadListener(Lnet/minecraft/client/resources/IResourceManagerReloadListener;)V", ordinal = 0, shift = At.Shift.AFTER) })
    private void preInitHook(final CallbackInfo ci) {
        Earthhack.preInit();
    }
    
    @Inject(method = { "init" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 2, shift = At.Shift.BEFORE) })
    private void initHook2(final CallbackInfo ci) {
        Earthhack.init();
        Earthhack.postInit();
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At("HEAD") })
    private void runGameLoopHead(final CallbackInfo callbackInfo) {
        ++this.gameLoop;
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", ordinal = 0, shift = At.Shift.AFTER) })
    private void post_ScheduledTasks(final CallbackInfo callbackInfo) {
        Bus.EVENT_BUS.post(new GameLoopEvent());
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", ordinal = 0, shift = At.Shift.BEFORE) })
    public void runTickHook(final CallbackInfo info) {
        Bus.EVENT_BUS.post(new TickEvent());
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;tick()V", shift = At.Shift.AFTER) })
    private void postUpdateWorld(final CallbackInfo info) {
        Bus.EVENT_BUS.post(new TickEvent.PostWorldTick());
    }
    
    @Inject(method = { "runTick" }, at = { @At("RETURN") })
    public void runTickReturnHook(final CallbackInfo info) {
        Bus.EVENT_BUS.post(new TickEvent.Post());
    }
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false) })
    private void runTickKeyboardHook(final CallbackInfo callbackInfo) {
        Bus.EVENT_BUS.post(new KeyboardEvent(Keyboard.getEventKeyState(), Keyboard.getEventKey(), Keyboard.getEventCharacter()));
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;world:Lnet/minecraft/client/multiplayer/WorldClient;", ordinal = 4, shift = At.Shift.BEFORE) })
    public void post_keyboardTickHook(final CallbackInfo info) {
        Bus.EVENT_BUS.post(new KeyboardEvent.Post());
    }
    
    @Inject(method = { "middleClickMouse" }, at = { @At("HEAD") }, cancellable = true)
    private void middleClickMouseHook(final CallbackInfo callbackInfo) {
        final ClickMiddleEvent event = new ClickMiddleEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("HEAD") }, cancellable = true)
    private void rightClickMouseHook(final CallbackInfo callbackInfo) {
        final ClickRightEvent event = new ClickRightEvent(this.rightClickDelayTimer);
        Bus.EVENT_BUS.post(event);
        this.rightClickDelayTimer = event.getDelay();
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;processRightClick(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/EnumActionResult;"))
    private EnumActionResult processRightClickHook(final PlayerControllerMP pCMP, final EntityPlayer player, final World worldIn, final EnumHand hand) {
        try {
            Locks.PLACE_SWITCH_LOCK.lock();
            return pCMP.processRightClick(player, worldIn, hand);
        }
        finally {
            Locks.PLACE_SWITCH_LOCK.unlock();
        }
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;processRightClickBlock(Lnet/minecraft/client/entity/EntityPlayerSP;Lnet/minecraft/client/multiplayer/WorldClient;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/EnumActionResult;"))
    private EnumActionResult processRightClickBlockHook(final PlayerControllerMP pCMP, final EntityPlayerSP player, final WorldClient worldIn, final BlockPos pos, final EnumFacing direction, final Vec3d vec, final EnumHand hand) {
        try {
            Locks.PLACE_SWITCH_LOCK.lock();
            return pCMP.processRightClickBlock(player, worldIn, pos, direction, vec, hand);
        }
        finally {
            Locks.PLACE_SWITCH_LOCK.unlock();
        }
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;interactWithEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/EnumActionResult;"))
    private EnumActionResult interactWithEntityHook(final PlayerControllerMP pCMP, final EntityPlayer player, Entity target, final EnumHand hand) {
        try {
            Locks.PLACE_SWITCH_LOCK.lock();
            return pCMP.interactWithEntity(player, target, hand);
        }
        finally {
            Locks.PLACE_SWITCH_LOCK.unlock();
        }
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;interactWithEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/RayTraceResult;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/EnumActionResult;"))
    private EnumActionResult interactWithEntity2Hook(final PlayerControllerMP pCMP, final EntityPlayer player, Entity target, final RayTraceResult ray, final EnumHand hand) {
        try {
            Locks.PLACE_SWITCH_LOCK.lock();
            return pCMP.interactWithEntity(player, target, ray, hand);
        }
        finally {
            Locks.PLACE_SWITCH_LOCK.unlock();
        }
    }
    
    @Inject(method = { "clickMouse" }, at = { @At("HEAD") }, cancellable = true)
    private void clickMouseHook(final CallbackInfo callbackInfo) {
        final ClickLeftEvent event = new ClickLeftEvent(this.leftClickCounter);
        Bus.EVENT_BUS.post(event);
        this.leftClickCounter = event.getLeftClickCounter();
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "clickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;attackEntity(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V"))
    private void attackEntityHook(final PlayerControllerMP playerControllerMP, final EntityPlayer playerIn, final Entity targetEntity) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> playerControllerMP.attackEntity(playerIn, targetEntity));
    }
    
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") }, cancellable = true)
    private <T extends GuiScreen> void displayGuiScreenHook(final T screen, final CallbackInfo info) {
        if (this.player == null && screen instanceof GuiChat) {
            info.cancel();
            return;
        }
        final GuiScreenEvent<T> event = new GuiScreenEvent<T>(screen);
        Bus.EVENT_BUS.post(event, (screen == null) ? null : screen.getClass());
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "shutdownMinecraftApplet" }, at = { @At("HEAD") })
    private void shutdownMinecraftAppletHook(final CallbackInfo info) {
        Earthhack.getLogger().info("Shutting down 3arthh4ck.");
        Bus.EVENT_BUS.post(new ShutDownEvent());
        try {
            Managers.CONFIG.saveAll();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Managers.THREAD.shutDown();
        MixinMinecraft.isEarthhackRunning = false;
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActiveHook(final EntityPlayerSP playerSP) {
        return !MixinMinecraft.MULTI_TASK.isEnabled() && playerSP.isHandActive();
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0), require = 1)
    private boolean isHittingBlockHook(final PlayerControllerMP playerControllerMP) {
        return !MixinMinecraft.MULTI_TASK.isEnabled() && playerControllerMP.getIsHittingBlock();
    }
    
    @Inject(method = { "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V" }, at = { @At("HEAD") })
    private void loadWorldHook(final WorldClient worldClient, final String loadingMessage, final CallbackInfo info) {
        if (this.world != null) {
            Bus.EVENT_BUS.post(new WorldClientEvent.Unload(this.world));
        }
    }
    
    @Inject(method = { "getRenderViewEntity" }, at = { @At("HEAD") }, cancellable = true)
    private void getRenderViewEntityHook(final CallbackInfoReturnable<Entity> cir) {
        if (MixinMinecraft.SPECTATE.isEnabled()) {
            cir.setReturnValue((Entity)MixinMinecraft.SPECTATE.get().getRender());
        }
    }
    
    @Inject(method = { "launchIntegratedServer" }, at = { @At("HEAD") })
    private void launchIntegratedServerHook(final String folderName, final String worldName, final WorldSettings worldSettingsIn, final CallbackInfo ci) {
        if (MixinMinecraft.CONFIG.isEnabled()) {
            MixinMinecraft.CONFIG.get().onConnect("singleplayer");
        }
    }
    
    @Redirect(method = { "processKeyBinds" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I"))
    private void processKeyBindsHook(final InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> inventoryPlayer.currentItem = MixinMinecraft.SORTER.returnIfPresent(s -> s.getHotbarMapping(value), value));
    }
    
    @Redirect(method = { "processKeyBinds" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;onStoppedUsingItem(Lnet/minecraft/entity/player/EntityPlayer;)V"))
    private void onStoppedUsingItemHook(final PlayerControllerMP playerControllerMP, final EntityPlayer playerIn) {
        Bus.EVENT_BUS.post(new AbortEatingEvent());
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> playerControllerMP.onStoppedUsingItem(playerIn));
    }
    
    @Redirect(method = { "runTickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;changeCurrentItem(I)V"))
    private void changeCurrentItemHook(final InventoryPlayer inventoryPlayer, final int direction) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> inventoryPlayer.changeCurrentItem(direction));
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"))
    private boolean onPlayerDamageBlockHook(final PlayerControllerMP pCMP, final BlockPos posBlock, final EnumFacing directionFacing) {
        try {
            Locks.PLACE_SWITCH_LOCK.lock();
            return pCMP.onPlayerDamageBlock(posBlock, directionFacing);
        }
        finally {
            Locks.PLACE_SWITCH_LOCK.unlock();
        }
    }
    
    static {
        SORTER = Caches.getModule(Sorter.class);
        MULTI_TASK = Caches.getModule(MultiTask.class);
        SPECTATE = Caches.getModule(Spectate.class);
        CONFIG = Caches.getModule(AutoConfig.class);
        MixinMinecraft.isEarthhackRunning = true;
    }
}
