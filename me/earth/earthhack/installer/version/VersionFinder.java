// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.version;

import me.earth.earthhack.installer.main.*;
import java.util.*;
import me.earth.earthhack.api.config.*;
import java.io.*;
import com.google.gson.*;

public class VersionFinder
{
    public List<Version> findVersions(final MinecraftFiles files) throws IOException {
        final File[] versionFolders = new File(files.getVersions()).listFiles();
        if (versionFolders == null) {
            throw new IllegalStateException("Version folder was empty!");
        }
        final List<Version> result = new ArrayList<Version>();
        for (final File file : versionFolders) {
            if (file.getName().startsWith("1.12.2")) {
                if (file.isDirectory()) {
                    final File[] contained = file.listFiles();
                    if (contained != null) {
                        for (final File json : contained) {
                            if (json.getName().endsWith("json")) {
                                final Version version = this.readJson(file.getName(), json);
                                result.add(version);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    
    private Version readJson(final String name, final File jsonFile) throws IOException {
        final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(jsonFile.toURI().toURL().openStream())).getAsJsonObject();
        return new Version(name, jsonFile, object);
    }
}
