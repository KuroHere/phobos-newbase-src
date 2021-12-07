// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable;

import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import java.util.*;

public class AddableModule extends AddableCommandModule
{
    public final Set<String> strings;
    public final String descriptor;
    public final Setting<ListType> listType;
    public final Setting<String> commandSetting;
    
    public AddableModule(final String name, final Category category, final String command, final String descriptor) {
        super(name, category);
        this.strings = new HashSet<String>();
        this.listType = this.register(new EnumSetting("List-Type", ListType.WhiteList));
        this.descriptor = descriptor;
        this.commandSetting = this.register(new CommandSetting(command, this::onSettingInput));
    }
    
    @Override
    public <T, S extends Setting<T>> S register(final S setting) {
        if (setting.getName().equalsIgnoreCase("add") || setting.getName().equalsIgnoreCase("del")) {
            Earthhack.getLogger().error(this.getName() + " Can't register the setting: " + setting.getName() + " in AddableModules these names (add/del) are reserved for the module command!");
            return setting;
        }
        return super.register(setting);
    }
    
    @Override
    public void add(final String string) {
        this.strings.add(this.formatString(string));
    }
    
    @Override
    public void del(final String string) {
        this.strings.remove(this.formatString(string));
    }
    
    @Override
    public PossibleInputs getSettingInput(final String input, final String[] args) {
        if (input == null || input.isEmpty()) {
            return new PossibleInputs("<add/del> <" + this.descriptor + ">", "");
        }
        return this.getInput(input, args);
    }
    
    public boolean isValid(final String string) {
        if (string == null) {
            return false;
        }
        if (this.listType.getValue() == ListType.WhiteList) {
            return this.strings.contains(this.formatString(string));
        }
        return !this.strings.contains(this.formatString(string));
    }
    
    public Collection<String> getList() {
        return this.strings;
    }
    
    public String getInput(final String input, final boolean add) {
        if (!add) {
            for (final String s : this.strings) {
                if (TextUtil.startsWith(s, input)) {
                    return TextUtil.substring(s, input.length());
                }
            }
        }
        return "";
    }
    
    protected void onSettingInput(final String input) {
        this.add(this.formatString(input));
    }
    
    protected PossibleInputs getInput(final String input, final String[] args) {
        final PossibleInputs inputs = PossibleInputs.empty().setRest(" <" + this.descriptor + ">");
        if (args.length == 1) {
            if ("add".startsWith(args[0].toLowerCase())) {
                return inputs.setCompletion(TextUtil.substring("add", args[0].length()));
            }
            if ("del".startsWith(args[0].toLowerCase())) {
                return inputs.setCompletion(TextUtil.substring("del", args[0].length()));
            }
            return inputs;
        }
        else {
            if (args.length <= 1) {
                return inputs.setRest("");
            }
            inputs.setRest("");
            if (args[0].equalsIgnoreCase("add")) {
                return inputs.setCompletion(this.getInput(input.substring(4), true));
            }
            if (args[0].equalsIgnoreCase("del")) {
                return inputs.setCompletion(this.getInput(input.substring(4), false));
            }
            return inputs;
        }
    }
    
    protected String formatString(final String string) {
        return string.toLowerCase();
    }
}
