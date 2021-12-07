// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import net.minecraft.util.math.*;
import me.earth.earthhack.impl.commands.packet.exception.*;

public class Vec3dArgument extends AbstractPositionArgument<Vec3d>
{
    public Vec3dArgument() {
        super("Vec3d", Vec3d.class);
    }
    
    @Override
    public Vec3d fromString(final String argument) throws ArgParseException {
        if (argument.equalsIgnoreCase("ORIGIN")) {
            return new Vec3d(0.0, 0.0, 0.0);
        }
        final String[] split = argument.split(" ");
        if (split.length != 3) {
            throw new ArgParseException("Vec3d takes 3 arguments!");
        }
        try {
            final double x = Double.parseDouble(split[0]);
            final double y = Double.parseDouble(split[1]);
            final double z = Double.parseDouble(split[2]);
            return new Vec3d(x, y, z);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse " + argument + " to Vec3d!");
        }
    }
}
