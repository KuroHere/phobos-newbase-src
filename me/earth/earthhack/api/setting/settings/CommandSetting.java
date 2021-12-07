// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.settings;

import java.util.function.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;

public class CommandSetting extends StringSetting
{
    private final Consumer<String> inputReader;
    
    public CommandSetting(final String nameIn, final Consumer<String> inputReader) {
        super(nameIn, "<...>");
        this.inputReader = inputReader;
    }
    
    public void onInput(final String input) {
        this.inputReader.accept(input);
    }
    
    @Override
    public void fromJson(final JsonElement element) {
    }
    
    @Override
    public SettingResult fromString(final String string) {
        this.onInput(string);
        return SettingResult.SUCCESSFUL;
    }
    
    @Override
    public void setValue(final String value, final boolean withEvent) {
        this.onInput(value);
    }
}
