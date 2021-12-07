// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.version;

import java.io.*;
import com.google.gson.*;

public class Version
{
    private final String name;
    private final File file;
    private final JsonObject json;
    
    public Version(final String name, final File file, final JsonObject json) {
        this.name = name;
        this.file = file;
        this.json = json;
    }
    
    public String getName() {
        return this.name;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public JsonObject getJson() {
        return this.json;
    }
}
