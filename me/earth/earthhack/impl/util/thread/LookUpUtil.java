//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.thread;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.client.network.*;
import com.mojang.util.*;
import com.mojang.authlib.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.util.*;
import me.earth.earthhack.api.util.*;
import com.google.common.collect.*;

public class LookUpUtil implements Globals
{
    private static final BiMap<String, UUID> CACHE;
    private static final JsonParser PARSER;
    
    public static UUID getUUIDSimple(final String name) {
        final UUID cached = (UUID)LookUpUtil.CACHE.get((Object)name);
        if (cached != null) {
            return cached;
        }
        if (LookUpUtil.mc.getConnection() != null) {
            final List<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(LookUpUtil.mc.getConnection().getPlayerInfoMap());
            final NetworkPlayerInfo profile = infoMap.stream().filter(info -> info.getGameProfile().getName().equalsIgnoreCase(name)).findFirst().orElse(null);
            if (profile != null) {
                final UUID result = profile.getGameProfile().getId();
                LookUpUtil.CACHE.forcePut((Object)name, (Object)result);
                return result;
            }
        }
        return null;
    }
    
    public static UUID getUUID(final String name) {
        final String ids = requestIDs("[\"" + name + "\"]");
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        final JsonElement element = LookUpUtil.PARSER.parse(ids);
        if (element.getAsJsonArray().size() == 0) {
            return null;
        }
        try {
            final String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
            final UUID result = UUIDTypeAdapter.fromString(id);
            LookUpUtil.CACHE.forcePut((Object)name, (Object)result);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getNameSimple(final UUID uuid) {
        final String cached = (String)LookUpUtil.CACHE.inverse().get((Object)uuid);
        if (cached != null) {
            return cached;
        }
        if (LookUpUtil.mc.getConnection() != null) {
            final List<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(LookUpUtil.mc.getConnection().getPlayerInfoMap());
            for (final NetworkPlayerInfo info : infoMap) {
                final GameProfile gameProfile = info.getGameProfile();
                if (gameProfile.getId().equals(uuid)) {
                    final String name = gameProfile.getName();
                    LookUpUtil.CACHE.forcePut((Object)name, (Object)uuid);
                    return name;
                }
            }
        }
        return null;
    }
    
    public static String getName(final UUID uuid) {
        final String url = "https://api.mojang.com/user/profiles/" + uuidToString(uuid) + "/names";
        try {
            final String name = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            final JsonArray array = (JsonArray)LookUpUtil.PARSER.parse(name);
            final String player = array.get(array.size() - 1).toString();
            final JsonObject object = (JsonObject)LookUpUtil.PARSER.parse(player);
            final String result = object.get("name").toString();
            LookUpUtil.CACHE.inverse().put((Object)uuid, (Object)result);
            return result;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String requestIDs(final String data) {
        try {
            final String query = "https://api.mojang.com/profiles/minecraft";
            final URL url = new URL(query);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            final OutputStream os = conn.getOutputStream();
            os.write(data.getBytes(StandardCharsets.UTF_8));
            os.close();
            final InputStream in = new BufferedInputStream(conn.getInputStream());
            final String res = convertStreamToString(in);
            in.close();
            conn.disconnect();
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static Map<Date, String> getNameHistory(final UUID id) {
        final Map<Date, String> result = new TreeMap<Date, String>(Collections.reverseOrder());
        try {
            final JsonArray array = getResources(new URL("https://api.mojang.com/user/profiles/" + uuidToString(id) + "/names")).getAsJsonArray();
            for (final JsonElement element : array) {
                final JsonObject node = element.getAsJsonObject();
                final String name = node.get("name").getAsString();
                final long changedAt = node.has("changedToAt") ? node.get("changedToAt").getAsLong() : 0L;
                result.put(new Date(changedAt), name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private static JsonElement getResources(final URL url) throws Exception {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            final Scanner scanner = new Scanner(connection.getInputStream());
            final StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();
            final String json = builder.toString();
            return LookUpUtil.PARSER.parse(json);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    public static String findNextPlayerName(final String input) {
        if (LookUpUtil.mc.getConnection() != null) {
            final List<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(LookUpUtil.mc.getConnection().getPlayerInfoMap());
            final NetworkPlayerInfo profile = infoMap.stream().filter(info -> TextUtil.startsWith(info.getGameProfile().getName(), input)).findFirst().orElse(null);
            if (profile != null) {
                return profile.getGameProfile().getName();
            }
        }
        for (final String str : LookUpUtil.CACHE.keySet()) {
            if (TextUtil.startsWith(str, input)) {
                return str;
            }
        }
        return null;
    }
    
    public static String uuidToString(final UUID uuid) {
        return uuid.toString().replace("-", "");
    }
    
    public static String convertStreamToString(final InputStream is) {
        final Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "/";
    }
    
    static {
        CACHE = (BiMap)HashBiMap.create();
        PARSER = new JsonParser();
    }
}
