//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.util;

import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.text.*;
import java.util.function.*;
import java.util.*;

public class CommandUtil implements Globals
{
    public static String concatenate(final String[] args, final int startIndex) {
        return concatenate(args, startIndex, args.length);
    }
    
    public static String concatenate(final String[] args, final int startIndex, final int end) {
        if (startIndex < 0 || startIndex >= args.length) {
            throw new ArrayIndexOutOfBoundsException(startIndex);
        }
        if (end > args.length) {
            throw new ArrayIndexOutOfBoundsException(end);
        }
        final StringBuilder builder = new StringBuilder(args[startIndex]);
        for (int i = startIndex + 1; i < end; ++i) {
            builder.append(" ").append(args[i]);
        }
        return builder.toString();
    }
    
    public static String completeBoolean(final String bool) {
        if (bool == null) {
            return "";
        }
        if ("true".startsWith(bool.toLowerCase())) {
            return TextUtil.substring("true", bool.length());
        }
        if ("false".startsWith(bool.toLowerCase())) {
            return TextUtil.substring("false", bool.length());
        }
        return null;
    }
    
    public static <T extends Nameable> T getNameableStartingWith(final String prefix, final Register<T> from) {
        return getNameableStartingWith(prefix, from.getRegistered());
    }
    
    public static <T extends Nameable> T getNameableStartingWith(final String prefix, final Collection<T> from) {
        for (final T t : from) {
            if (TextUtil.startsWith(t.getName(), prefix)) {
                return t;
            }
        }
        return null;
    }
    
    public static <T> ITextComponent concatenate(final ITextComponent base, final Iterable<T> iterable, final Function<T, ITextComponent> f) {
        final Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            final T t = it.next();
            base.appendSibling((ITextComponent)f.apply(t));
            if (it.hasNext()) {
                base.appendSibling((ITextComponent)new TextComponentString(", "));
            }
        }
        return base;
    }
    
    public static <T> StringBuilder concatenate(final StringBuilder builder, final Iterable<T> iterable, final BiConsumer<StringBuilder, T> c) {
        final Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            final T t = it.next();
            c.accept(builder, t);
            if (it.hasNext()) {
                builder.append(", ");
            }
        }
        return builder;
    }
    
    public static int levenshtein(final String x, final String y) {
        final int[][] dp = new int[x.length() + 1][y.length() + 1];
        for (int i = 0; i <= x.length(); ++i) {
            for (int j = 0; j <= y.length(); ++j) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }
        return dp[x.length()][y.length()];
    }
    
    public static int costOfSubstitution(final char a, final char b) {
        return (a != b) ? 1 : 0;
    }
    
    public static int min(final int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }
    
    public static String[] toArgs(final String input) {
        boolean escaped = false;
        final List<String> strings = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for (final char ch : input.toCharArray()) {
            if (ch == '\"') {
                if (escaped) {
                    strings.add(builder.toString());
                    builder = new StringBuilder();
                    escaped = false;
                }
                else {
                    escaped = true;
                }
            }
            else if (ch == ' ' && !escaped) {
                if (builder.length() != 0) {
                    strings.add(builder.toString());
                    builder = new StringBuilder();
                }
            }
            else {
                builder.append(ch);
            }
        }
        if (builder.length() != 0) {
            strings.add(builder.toString());
        }
        return strings.toArray(new String[0]);
    }
}
