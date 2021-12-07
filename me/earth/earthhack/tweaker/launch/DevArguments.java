// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.tweaker.launch;

import java.util.concurrent.*;
import me.earth.earthhack.api.config.*;
import java.nio.file.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class DevArguments implements ArgumentManager
{
    private static final Logger LOGGER;
    private static final DevArguments INSTANCE;
    private static final String PATH = "earthhack/dev.json";
    private final Map<String, Argument<?>> arguments;
    
    private DevArguments() {
        this.arguments = new ConcurrentHashMap<String, Argument<?>>();
    }
    
    public static DevArguments getInstance() {
        return DevArguments.INSTANCE;
    }
    
    @Override
    public void loadArguments() {
        final Path path = Paths.get("earthhack/dev.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        try (final InputStream stream = Files.newInputStream(path, new OpenOption[0])) {
            final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
            for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                final Argument<?> argument = this.getArgument(entry.getKey());
                if (argument == null) {
                    DevArguments.LOGGER.warn("Unknown DevArgument: " + entry.getKey() + "!");
                }
                else {
                    argument.fromJson(entry.getValue());
                    DevArguments.LOGGER.info("Dev-Argument: " + entry.getKey() + " : " + argument.getValue());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void addArgument(final String name, final Argument<?> argument) {
        if (this.arguments.containsKey(name)) {
            throw new IllegalStateException("Argument with name: " + name + " already exists!");
        }
        this.arguments.put(name, argument);
    }
    
    @Override
    public <T> Argument<T> getArgument(final String name) {
        return (Argument)this.arguments.get(name);
    }
    
    static {
        LOGGER = LogManager.getLogger("3arthh4ck-Core");
        INSTANCE = new DevArguments();
    }
}
