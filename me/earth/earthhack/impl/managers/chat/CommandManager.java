//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.chat;

import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.register.exception.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.command.*;
import java.util.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.*;
import me.earth.earthhack.impl.commands.hidden.*;

public class CommandManager extends SubscriberImpl implements Globals, Register<Command>
{
    private static final Command MODULE_COMMAND;
    private static final Command FAIL_COMMAND;
    private final Set<Command> commands;
    private final Set<Command> hidden;
    private String concatenated;
    private String lastMessage;
    
    public CommandManager() {
        this.commands = new LinkedHashSet<Command>();
        this.hidden = new LinkedHashSet<Command>();
        this.listeners.add(new EventListener<PacketEvent.Send<CPacketChatMessage>>(PacketEvent.Send.class, CPacketChatMessage.class) {
            @Override
            public void invoke(final PacketEvent.Send<CPacketChatMessage> event) {
                if (event.getPacket().getMessage().startsWith(Commands.getPrefix())) {
                    CommandManager.this.applyCommand(event.getPacket().getMessage());
                    if (!event.getPacket().getMessage().toLowerCase().startsWith(Commands.getPrefix() + "last ") && !event.getPacket().getMessage().equalsIgnoreCase(Commands.getPrefix() + "last")) {
                        CommandManager.this.lastMessage = event.getPacket().getMessage();
                    }
                    event.setCancelled(true);
                }
            }
        });
    }
    
    public void init() {
        Earthhack.getLogger().info("Initializing Commands.");
        this.commands.add(new ConfigCommand());
        this.commands.add(new FontCommand());
        this.commands.add(new FriendCommand());
        this.commands.add(new EnemyCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new HexCommand());
        this.commands.add(new HistoryCommand());
        this.commands.add(new MacroCommand());
        this.commands.add(new LastCommand());
        this.commands.add(new ModuleListCommand());
        this.commands.add(new PeekCommand());
        this.commands.add(new PrefixCommand());
        this.commands.add(new ToggleCommand());
        this.commands.add(new TimesCommand());
        this.commands.add(new PluginCommand());
        this.commands.add(new SayCommand());
        this.commands.add(new GameModeCommand());
        this.commands.add(new JavaScriptCommand());
        this.commands.add(new KitCommand());
        this.commands.add(new Thirty2kCommand());
        this.commands.add(new BindCommand());
        this.commands.add(new ResetCommand());
        this.commands.add(new PrintCommand());
        this.commands.add(new QuitCommand());
        this.commands.add(new ConnectCommand());
        this.commands.add(new DisconnectCommand());
        this.commands.add(new VClipCommand());
        this.commands.add(new HClipCommand());
        this.commands.add(new GiveCommand());
        this.commands.add(new EnchantCommand());
        this.commands.add(new ShrugCommand());
        this.commands.add(new EntityDesyncCommand());
        this.commands.add(new SoundCommand());
        this.commands.add(new FolderCommand());
        this.commands.add(new PacketCommandImpl());
        this.commands.add(new PresetCommand());
        this.hidden.add(new HListSettingCommand());
        this.hidden.add(new HModulesCommand());
        this.hidden.add(new HSettingCommand());
        this.setupAndConcatenate();
    }
    
    @Override
    public void register(final Command command) throws AlreadyRegisteredException {
        if (command.isHidden()) {
            this.hidden.add(command);
        }
        else {
            this.commands.add(command);
        }
        if (command instanceof Registrable) {
            ((Registrable)command).onRegister();
        }
        this.setupAndConcatenate();
    }
    
    @Override
    public void unregister(final Command command) throws CantUnregisterException {
        if (command instanceof Registrable) {
            ((Registrable)command).onUnRegister();
        }
        this.hidden.remove(command);
        this.commands.remove(command);
        this.setupAndConcatenate();
    }
    
    @Override
    public Command getObject(final String name) {
        Command command = CommandUtil.getNameableStartingWith(name, this.commands);
        if (command == null || !command.getName().equalsIgnoreCase(name)) {
            command = CommandUtil.getNameableStartingWith(name, this.hidden);
            if (command != null && !command.getName().equalsIgnoreCase(name)) {
                return null;
            }
        }
        return command;
    }
    
    @Override
    public <C extends Command> C getByClass(final Class<C> clazz) {
        C command = CollectionUtil.getByClass(clazz, this.commands);
        if (command == null) {
            command = CollectionUtil.getByClass(clazz, this.hidden);
        }
        return command;
    }
    
    @Override
    public Collection<Command> getRegistered() {
        return this.commands;
    }
    
    public String getLastCommand() {
        return this.lastMessage;
    }
    
    private void setupAndConcatenate() {
        this.commands.remove(CommandManager.MODULE_COMMAND);
        this.commands.add(CommandManager.MODULE_COMMAND);
        this.concatenated = this.concatenateCommands();
    }
    
    public void renderCommandGui(final String message, final int x, final int y) {
        if (message != null && message.startsWith(Commands.getPrefix())) {
            final String[] array = this.createArray(message);
            final String possible = this.getCommandForMessage(array).getPossibleInputs(array).getFullText();
            final int width = x + CommandManager.mc.fontRendererObj.getStringWidth(message.trim());
            CommandManager.mc.fontRendererObj.drawString(possible, (float)width, (float)y, -1, true);
        }
    }
    
    public boolean onTabComplete(final GuiTextField inputField) {
        if (inputField.getText().startsWith(Commands.getPrefix())) {
            final String[] array = this.createArray(inputField.getText());
            final Completer completer = this.getCommandForMessage(array).onTabComplete(new Completer(inputField.getText(), array));
            inputField.setText(completer.getResult());
            return completer.shouldMcComplete();
        }
        return true;
    }
    
    public void applyCommand(final String message) {
        if (message != null && message.length() > 1) {
            final String[] array = this.createArray(message);
            Command command = this.getCommandForMessage(array);
            if (command.equals(CommandManager.FAIL_COMMAND)) {
                command = this.getHiddenCommand(array);
            }
            command.execute(array);
        }
    }
    
    public String getConcatenatedCommands() {
        return this.concatenated;
    }
    
    public Command getCommandForMessage(final String[] array) {
        if (array == null || array.length == 0) {
            return CommandManager.FAIL_COMMAND;
        }
        for (final Command command : this.commands) {
            if (command.fits(array)) {
                return command;
            }
        }
        return CommandManager.FAIL_COMMAND;
    }
    
    public String[] createArray(final String message) {
        final String noPrefix = message.substring(Commands.getPrefix().length());
        return CommandUtil.toArgs(noPrefix);
    }
    
    private Command getHiddenCommand(final String[] array) {
        for (final Command command : this.hidden) {
            if (command.fits(array)) {
                return command;
            }
        }
        return CommandManager.FAIL_COMMAND;
    }
    
    private String concatenateCommands() {
        final StringBuilder builder = new StringBuilder();
        final Iterator<Command> itr = this.commands.iterator();
        while (itr.hasNext()) {
            builder.append(itr.next().getName().toLowerCase());
            if (itr.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
    
    static {
        MODULE_COMMAND = new ModuleCommand();
        FAIL_COMMAND = new FailCommand();
    }
}
