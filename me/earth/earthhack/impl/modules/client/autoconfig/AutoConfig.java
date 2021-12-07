//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.autoconfig;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.util.misc.*;
import org.apache.commons.io.*;
import me.earth.earthhack.impl.managers.config.helpers.*;
import me.earth.earthhack.impl.managers.*;
import java.nio.file.*;
import java.io.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.managers.client.macro.*;
import net.minecraft.client.multiplayer.*;

public class AutoConfig extends RegisteringModule<String, RemovingString>
{
    private final StopWatch timer;
    private ServerList serverList;
    
    public AutoConfig() {
        super("AutoConfig", Category.Client, "Add Config", "ip> <macro", s -> new RemovingString(s, s), s -> "Applies configs on " + s.getName() + ".");
        this.timer = new StopWatch();
        this.unregister(this.listType);
        this.setData(new SimpleData(this, "Automatically executes a Macro when joining a server."));
    }
    
    @Override
    public void add(final String string) {
        if (this.addSetting(string) != null) {
            ChatUtil.sendMessage("§aAdded AutoConfig §f" + string + "§a" + " successfully.");
        }
        else {
            ChatUtil.sendMessage("§cSomething went wrong while adding AutoConfig §f" + string + "§c" + ". Maybe a config of this name already exists?");
        }
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length >= 2 && args[1].equalsIgnoreCase("secret")) {
            FileUtil.createDirectory(Paths.get("earthhack/modules", new String[0]));
            String name = "3arthqu4ke";
            String configName = "earthhack/modules/" + name + ".json";
            Path path = Paths.get(configName, new String[0]);
            int i = 1;
            while (Files.exists(path, new LinkOption[0])) {
                name = "3arthqu4ke" + i++;
                configName = "earthhack/modules/" + name + ".json";
                path = Paths.get(configName, new String[0]);
            }
            try (final InputStream in = this.getClass().getClassLoader().getResourceAsStream("configs/3arthconfig.json");
                 final OutputStream out = Files.newOutputStream(path, new OpenOption[0])) {
                if (in == null) {
                    throw new IOException("InputStream was null!");
                }
                IOUtils.copy(in, out);
            }
            catch (IOException e) {
                ChatUtil.sendMessage("§cAn error occurred: " + e.getMessage());
                e.printStackTrace();
                return true;
            }
            ChatUtil.sendMessage("§aAdded secret config: §f" + name + "§a" + " to your config folder.");
            final ModuleConfigHelper helper = Managers.CONFIG.getByClass(ModuleConfigHelper.class);
            if (helper != null) {
                try {
                    helper.refresh(path.toString());
                }
                catch (IOException e2) {
                    ChatUtil.sendMessage("§cAn error occurred while refreshing the config!");
                    e2.printStackTrace();
                }
            }
            return true;
        }
        return super.execute(args);
    }
    
    @Override
    public boolean getInput(final String[] args, final PossibleInputs inputs) {
        if (args.length >= 2 && !args[1].isEmpty() && TextUtil.startsWith("secret", args[1].toLowerCase())) {
            inputs.setRest("");
            if (args.length == 2) {
                inputs.setCompletion(TextUtil.substring("secret", args[1].length()));
            }
            return true;
        }
        return super.getInput(args, inputs);
    }
    
    @Override
    public CustomCompleterResult complete(final Completer completer) {
        return super.complete(completer);
    }
    
    @Override
    public void add(final String[] args) {
        if (args.length < 4) {
            ChatUtil.sendMessage("§cPlease specify a Macro!");
            return;
        }
        final RemovingString setting = ((RegisteringModule<I, RemovingString>)this).addSetting(args[2]);
        if (setting == null) {
            ChatUtil.sendMessage("§cAn AutoConfig for §f" + args[2] + "§c" + " already exists!");
            return;
        }
        setting.fromString(CommandUtil.concatenate(args, 3));
    }
    
    @Override
    protected PossibleInputs getInput(final String input, final String[] args) {
        if (args.length == 1 && "del".startsWith(args[0].toLowerCase())) {
            return super.getInput(input, args).setRest(" <ip>");
        }
        if (args.length <= 1 || !args[0].equalsIgnoreCase("add")) {
            return super.getInput(input, args);
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        if (args.length == 2) {
            this.setupServerList();
            final String ip = this.getIpStartingWith(args[1], this.serverList);
            if (ip == null) {
                return inputs;
            }
            return inputs.setCompletion(TextUtil.substring(ip, args[1].length())).setRest(" <macro>");
        }
        else {
            final Macro macro = CommandUtil.getNameableStartingWith(args[2], Managers.MACRO.getRegistered());
            if (macro == null) {
                return inputs.setRest("§c not found!");
            }
            return inputs.setCompletion(TextUtil.substring(macro.getName(), args[2].length()));
        }
    }
    
    public void onConnect(final String ip) {
        RemovingString setting = this.getSetting("all", RemovingString.class);
        if (setting != null) {
            this.execute(setting);
        }
        setting = this.getSetting(ip, RemovingString.class);
        if (setting != null) {
            this.execute(setting);
        }
    }
    
    private void execute(final RemovingString setting) {
        final Macro macro = Managers.MACRO.getObject(setting.getValue());
        if (macro == null) {
            ChatUtil.sendMessage("<AutoConfig>§c Couldn't find macro " + setting.getValue() + "!");
            return;
        }
        ChatUtil.sendMessage("<AutoConfig>§a Applying macro §f" + setting.getValue() + "§a" + ".");
        try {
            macro.execute(Managers.COMMANDS);
        }
        catch (Throwable t) {
            ChatUtil.sendMessage("<AutoConfig>§c An Error occurred while executing macro §f" + macro.getName() + "§c" + ": " + t.getMessage());
            t.printStackTrace();
        }
    }
    
    private String getIpStartingWith(String prefix, final ServerList list) {
        prefix = prefix.toLowerCase();
        for (int i = 0; i < list.countServers(); ++i) {
            final ServerData data = list.getServerData(i);
            if (data.serverIP != null && data.serverIP.toLowerCase().startsWith(prefix)) {
                return data.serverIP;
            }
        }
        return "singleplayer".startsWith(prefix) ? "singleplayer" : ("all".startsWith(prefix) ? "all" : null);
    }
    
    private void setupServerList() {
        if (this.serverList == null || this.timer.passed(60000L)) {
            (this.serverList = new ServerList(AutoConfig.mc)).loadServerList();
            this.timer.reset();
        }
    }
}
