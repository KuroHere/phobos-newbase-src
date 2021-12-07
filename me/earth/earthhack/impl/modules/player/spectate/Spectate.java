//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.util.text.*;
import java.util.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.client.entity.*;

public class Spectate extends DisablingModule implements CustomCommandModule
{
    protected final Setting<Boolean> stopMove;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> playerRotations;
    protected EntityPlayerNoInterp fakePlayer;
    protected EntityPlayerNoInterp render;
    protected MovementInput input;
    protected EntityPlayer player;
    protected boolean spectating;
    
    public Spectate() {
        super("Spectate", Category.Player);
        this.stopMove = this.register(new BooleanSetting("NoMove", true));
        this.rotate = this.register(new BooleanSetting("Rotate", true));
        this.playerRotations = this.register(new BooleanSetting("Spectator-Rotate", false));
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerAttack(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerRemove(this));
        this.listeners.add(new ListenerAnimation(this));
        this.setData(new SimpleData(this, "FreeCam but more Vanilla."));
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.spectating) {
            final EntityPlayer thePlayer = this.player;
            return (thePlayer != null) ? thePlayer.getName() : null;
        }
        return null;
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length == 2 && Spectate.mc.world != null && Spectate.mc.player != null) {
            EntityPlayer player = null;
            for (final EntityPlayer p : Spectate.mc.world.playerEntities) {
                if (p != null && args[1].equalsIgnoreCase(p.getName())) {
                    player = p;
                    break;
                }
            }
            if (player != null) {
                this.specate(player);
                ModuleUtil.sendMessage(this, "§aNow spectating: " + player.getName());
                return true;
            }
            final Setting<?> setting = this.getSetting(args[1]);
            if (setting == null) {
                ChatUtil.sendMessage("§cCould not find setting or player §f" + args[1] + "§c" + " in the Spectate module.");
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean getInput(final String[] args, final PossibleInputs inputs) {
        if (args.length == 1) {
            inputs.setCompletion(TextUtil.substring(this.getName(), args[0].length())).setRest(" <setting/player> <value>");
            return true;
        }
        if (args.length != 2) {
            return false;
        }
        final String next = LookUpUtil.findNextPlayerName(args[1]);
        if (next != null) {
            inputs.setCompletion(TextUtil.substring(next, args[1].length()));
            return true;
        }
        return false;
    }
    
    @Override
    public CustomCompleterResult complete(final Completer completer) {
        if (!completer.isSame() && completer.getArgs().length == 2) {
            final String player = LookUpUtil.findNextPlayerName(completer.getArgs()[1]);
            if (player != null) {
                return CustomCompleterResult.SUPER;
            }
        }
        return CustomCompleterResult.PASS;
    }
    
    @Override
    protected void onEnable() {
        if (Spectate.mc.player == null || Spectate.mc.world == null) {
            this.disable();
            return;
        }
        if (Spectate.mc.player.movementInput instanceof MovementInputFromOptions) {
            Spectate.mc.player.movementInput = new MovementInput();
        }
        this.input = (MovementInput)new MovementInputFromOptions(Spectate.mc.gameSettings);
        (this.render = new EntityPlayerNoInterp((World)Spectate.mc.world)).copyLocationAndAnglesFrom((Entity)Spectate.mc.player);
        this.render.inventory = Spectate.mc.player.inventory;
        this.render.inventoryContainer = Spectate.mc.player.inventoryContainer;
        this.render.inventory.copyInventory(Spectate.mc.player.inventory);
        this.render.setEntityBoundingBox(Spectate.mc.player.getEntityBoundingBox());
        this.render.resetPositionToBB();
        (this.fakePlayer = new EntityPlayerNoInterp((World)Spectate.mc.world)).copyLocationAndAnglesFrom((Entity)Spectate.mc.player);
        this.fakePlayer.inventory.copyInventory(Spectate.mc.player.inventory);
        this.fakePlayer.inventory = Spectate.mc.player.inventory;
        this.fakePlayer.inventoryContainer = Spectate.mc.player.inventoryContainer;
        Spectate.mc.world.addEntityToWorld(-10000, (Entity)this.fakePlayer);
        Spectate.mc.entityRenderer.loadEntityShader((Entity)null);
    }
    
    @Override
    protected void onDisable() {
        final EntityPlayerSP playerSP = Spectate.mc.player;
        if (playerSP != null) {
            final MovementInput input = playerSP.movementInput;
            if (input != null && input.getClass() == MovementInput.class) {
                Spectate.mc.addScheduledTask(() -> playerSP.movementInput = (MovementInput)new MovementInputFromOptions(Spectate.mc.gameSettings));
            }
        }
        final EntityPlayer specPlayer = this.player;
        if (this.spectating) {
            if (specPlayer != null) {
                ((IEntityNoInterp)specPlayer).setNoInterping(true);
            }
            this.spectating = false;
        }
        Spectate.mc.addScheduledTask(() -> this.player = null);
        if (Spectate.mc.world != null) {
            Spectate.mc.addScheduledTask(() -> {
                if (Spectate.mc.world != null) {
                    this.player = null;
                    Spectate.mc.world.removeEntity((Entity)this.fakePlayer);
                }
            });
        }
    }
    
    public boolean shouldTurn() {
        return !this.spectating || this.player == null || this.playerRotations.getValue();
    }
    
    public void specate(final EntityPlayer player) {
        if (this.isEnabled()) {
            this.disable();
        }
        this.spectating = true;
        this.player = player;
        ((IEntityNoInterp)player).setNoInterping(false);
        this.enable();
    }
    
    public EntityPlayer getRender() {
        return (EntityPlayer)(this.spectating ? ((this.player == null) ? Spectate.mc.player : this.player) : this.render);
    }
    
    public EntityPlayer getFake() {
        return (EntityPlayer)this.fakePlayer;
    }
}
