//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.abstracts;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.managers.client.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.thread.lookup.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.command.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.gui.chat.util.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import net.minecraft.util.text.event.*;
import java.util.*;
import net.minecraft.client.*;

public abstract class AbstractPlayerManagerCommand extends Command implements Globals
{
    private final PlayerManager manager;
    private final String listingName;
    private final String added;
    private final String verb;
    private final String color;
    
    public AbstractPlayerManagerCommand(final PlayerManager manager, final String name, final String listingName, final String verb, final String added, final String color) {
        super(new String[][] { { name }, { "add", "del", "list" }, { "name" } });
        this.added = added;
        this.manager = manager;
        this.listingName = listingName;
        this.verb = verb;
        this.color = color;
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1 || (args.length == 2 && args[1].equalsIgnoreCase("list"))) {
            Managers.CHAT.sendDeleteComponent(this.getComponent(), this.verb, 5000);
        }
        else if (args.length == 2) {
            final boolean isAdded = this.manager.contains(args[1]);
            ChatUtil.sendMessage(args[1] + (isAdded ? "§a" : "§c") + " is " + (isAdded ? "" : "not ") + this.verb + ".");
        }
        else {
            final String name = args[2];
            if (args[1].equalsIgnoreCase("add")) {
                Managers.LOOK_UP.doLookUp(new LookUp(LookUp.Type.UUID, name) {
                    @Override
                    public void onSuccess() {
                        AbstractPlayerManagerCommand.this.manager.add(this.name, this.uuid);
                        Managers.CHAT.sendDeleteMessageScheduled(AbstractPlayerManagerCommand.this.color + this.name + "§a" + " was added as " + AbstractPlayerManagerCommand.this.added + ".", this.name, 5000);
                    }
                    
                    @Override
                    public void onFailure() {
                        ChatUtil.sendMessageScheduled("§cFailed to find " + this.name);
                    }
                });
            }
            else if (args[1].equalsIgnoreCase("del")) {
                this.manager.remove(name);
                Managers.CHAT.sendDeleteMessage("§c" + name + " un" + this.verb + ".", name, 5000);
            }
            else {
                ChatUtil.sendMessage("§cPlease specify <add/del>.");
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length == 1) {
            return inputs;
        }
        if (args.length == 2) {
            final String filler = this.fillArgs(args[1]);
            if (filler != null) {
                return inputs.setCompletion(TextUtil.substring(filler, args[1].length())).setRest(filler.equalsIgnoreCase("list") ? "" : inputs.getRest());
            }
            final String next = LookUpUtil.findNextPlayerName(args[1]);
            return inputs.setCompletion(TextUtil.substring((next == null) ? "" : next, args[1].length()));
        }
        else {
            if (args.length == 3) {
                final String next2 = LookUpUtil.findNextPlayerName(args[2]);
                return inputs.setCompletion(TextUtil.substring((next2 == null) ? "" : next2, args[2].length()));
            }
            return inputs.setCompletion("").setRest("§cinvalid.");
        }
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        if (completer.isSame()) {
            if (completer.getArgs().length == 2) {
                for (int i = 0; i < this.getUsage()[0].length; ++i) {
                    final String str = this.getUsage()[0][i];
                    if (str.equalsIgnoreCase(completer.getArgs()[1])) {
                        final String result = (i == this.getUsage()[0].length - 1) ? this.getUsage()[0][0] : this.getUsage()[0][i + 1];
                        final String newInitial = TextUtil.substring(completer.getInitial().trim(), 0, completer.getInitial().length() - completer.getArgs()[completer.getArgs().length - 1].length());
                        completer.setResult(newInitial + result);
                    }
                }
            }
            else {
                completer.setMcComplete(true);
            }
            completer.setLastCompleted(completer.getResult());
            return completer;
        }
        final String[] args = completer.getArgs();
        if (args.length == 3 && (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("del"))) {
            final String next = LookUpUtil.findNextPlayerName(args[2]);
            if (next != null) {
                final String result = TextUtil.substring(completer.getInitial().trim(), 0, completer.getInitial().trim().length() - args[2].length());
                completer.setResult(result + next);
                return completer;
            }
        }
        return super.onTabComplete(completer);
    }
    
    private String fillArgs(final String input) {
        for (final String str : this.getUsage()[1]) {
            if (str.startsWith(input.toLowerCase())) {
                return str;
            }
        }
        return null;
    }
    
    private ITextComponent getComponent() {
        final ITextComponent component = (ITextComponent)new TextComponentString(this.listingName + ": ");
        final Iterator<String> players = this.manager.getPlayers().iterator();
        while (players.hasNext()) {
            final String name = players.next();
            component.appendSibling(new TextComponentString(this.color + name + "§f" + (players.hasNext() ? ", " : "")).setStyle(new Style().setHoverEvent(ChatComponentUtil.setOffset(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("UUID: " + this.manager.getPlayersWithUUID().get(name))))).setClickEvent((ClickEvent)new RunnableClickEvent(() -> {
                final GuiScreen before = AbstractPlayerManagerCommand.mc.currentScreen;
                final Minecraft mc = AbstractPlayerManagerCommand.mc;
                new YesNoNonPausing((result, id) -> {
                    AbstractPlayerManagerCommand.mc.displayGuiScreen(before);
                    if (!result) {
                        return;
                    }
                    this.manager.remove(name);
                    Managers.CHAT.sendDeleteComponent(this.getComponent(), this.verb, 5000);
                    ChatUtil.sendMessage("§c" + name + " un" + this.verb + ".");
                }, "", this.color + name + "§f" + " will be un" + this.verb + ". Continue?", 1337);
                final YesNoNonPausing yesNoNonPausing;
                mc.displayGuiScreen((GuiScreen)yesNoNonPausing);
                return;
            }))));
        }
        return component;
    }
}
