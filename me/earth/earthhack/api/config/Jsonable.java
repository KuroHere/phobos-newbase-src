// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config;

import com.google.gson.stream.*;
import java.io.*;
import com.google.gson.*;

public interface Jsonable
{
    public static final JsonParser PARSER = new JsonParser();
    public static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    
    void fromJson(final JsonElement p0);
    
    String toJson();
    
    default JsonElement parse(final String string) {
        return parse(string, true);
    }
    
    default JsonElement parse(final String string, final boolean addQuotation) {
        JsonElement element = null;
        try (final JsonReader reader = new JsonReader((Reader)new StringReader(addQuotation ? ("\"" + string + "\"") : string))) {
            reader.setLenient(true);
            element = Jsonable.PARSER.parse(reader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return element;
    }
}
