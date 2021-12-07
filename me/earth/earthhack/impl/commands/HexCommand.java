// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import java.awt.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;

public class HexCommand extends Command
{
    public HexCommand() {
        super(new String[][] { { "hex" }, { "red", "num" }, { "green", "radix" }, { "blue" }, { "alpha" } });
        CommandDescriptions.register(this, "ColorSettings are hard to use if you don't know how hex numbers work. This command helps you with converting numbers to different radixes.");
    }
    
    @Override
    public void execute(final String[] args) {
        switch (args.length) {
            case 1: {
                ChatUtil.sendMessage("This command converts a Number (1 arg) or a Color (3-4 args) to a 32-Bit-Hex-String. If you choose 2 args you can convert a Number with the given radix. (hex-num, 16 for example).");
                break;
            }
            case 2: {
                try {
                    final int n = (int)Long.parseLong(args[1]);
                    final String r = Integer.toHexString(n).toUpperCase();
                    ChatUtil.sendMessage("Hex value of §b" + args[1] + "§f" + " is " + "§c" + r + ".");
                }
                catch (Exception e) {
                    ChatUtil.sendMessage("<Hex> §c" + args[1] + " was not parsable.");
                }
                break;
            }
            case 3: {
                int radix = 16;
                try {
                    radix = Integer.parseInt(args[2]);
                }
                catch (Exception e2) {
                    ChatUtil.sendMessage("<Hex> §c" + args[2] + " was not parsable, continuing with radix 16.");
                }
                try {
                    final int r2 = (int)Long.parseLong(args[1], radix);
                    ChatUtil.sendMessage("Hex value of §b" + args[1] + "§f" + " in radix " + "§b" + radix + "§f" + " is " + "§c" + r2 + ".");
                }
                catch (Exception e2) {
                    ChatUtil.sendMessage("<Hex> §c" + args[1] + " was not parsable.");
                }
                break;
            }
            default: {
                int r2;
                int g;
                int b;
                try {
                    r2 = Integer.parseInt(args[1]);
                    g = Integer.parseInt(args[2]);
                    b = Integer.parseInt(args[3]);
                }
                catch (Exception e3) {
                    ChatUtil.sendMessage("<Hex> §cAn Argument was not parsable.");
                    break;
                }
                int a = 255;
                if (args.length > 5) {
                    try {
                        a = Integer.parseInt(args[4]);
                    }
                    catch (Exception e4) {
                        ChatUtil.sendMessage("<Hex> §cAlpha was not parsable, continuing with 255.");
                    }
                }
                final Color color = new Color(r2, g, b, a);
                ChatUtil.sendMessage("Hex value of §z" + TextUtil.get32BitString(color.getRGB()) + "[" + r2 + ", " + g + ", " + b + ", " + a + "] " + "§f" + "is " + "§b" + TextUtil.get32BitString(color.getRGB()));
                break;
            }
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length > 1) {
            return super.getPossibleInputs(args).setCompletion("");
        }
        return super.getPossibleInputs(args);
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        if (completer.getArgs().length > 1) {
            return completer.setMcComplete(true);
        }
        return super.onTabComplete(completer);
    }
}
