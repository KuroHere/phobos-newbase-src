//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.managers.thread.*;
import javax.script.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.tweaker.launch.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.math.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import java.util.*;

public class JavaScriptCommand extends Command implements Globals, GlobalExecutor, CommandScheduler
{
    private static final String[] EMPTY;
    private final Map<String, String[]> arguments;
    private final ScriptEngine engine;
    private final boolean replaceRn;
    private final boolean invalid;
    private final boolean jsNull;
    
    public JavaScriptCommand() {
        super(new String[][] { { "javascript" }, { "code" } });
        this.arguments = new HashMap<String, String[]>();
        CommandDescriptions.register(this, "Allows you to execute JavaScript.");
        Argument<Boolean> arg = DevArguments.getInstance().getArgument("jsrn");
        this.replaceRn = (arg == null || arg.getValue());
        arg = DevArguments.getInstance().getArgument("jsnull");
        this.jsNull = (arg == null || arg.getValue());
        boolean invalid = false;
        final ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine theEngine = factory.getEngineByName("JavaScript");
        if (theEngine == null) {
            Earthhack.getLogger().warn("JavaScript was null, using nashorn!");
            theEngine = factory.getEngineByName("nashorn");
            if (theEngine == null) {
                invalid = true;
            }
        }
        if (this.invalid = invalid) {
            this.engine = null;
            return;
        }
        this.engine = theEngine;
        final EarthhackJsBridge bridge = new EarthhackJsBridge();
        this.engine.put("Earthhack", bridge);
        this.setupArguments();
    }
    
    @Override
    public boolean fits(final String[] args) {
        final String low = args[0].toLowerCase();
        return "javascript".startsWith(low) || low.startsWith("javascript");
    }
    
    @Override
    public void execute(final String[] args) {
        if (this.invalid) {
            ChatUtil.sendMessage("§cYour Java version doesn't support nashorn or JavaScript!");
            return;
        }
        long timeout = 5000L;
        boolean noTimeout = false;
        final String s = args[0].toLowerCase().replace("javascript", "");
        if (s.equals("notimeout")) {
            noTimeout = true;
        }
        else if (!s.isEmpty()) {
            try {
                timeout = Long.parseLong(s);
            }
            catch (NumberFormatException e) {
                ChatUtil.sendMessage("§cCouldn't parse timeout: §f" + s + "§c" + ".");
                return;
            }
            if (timeout < 0L) {
                ChatUtil.sendMessage("§cTimeout can't be negative!");
                return;
            }
        }
        if (args.length <= 1) {
            ChatUtil.sendMessage("§cThis command allows you to execute JavaScript Code.§b Tip:§f Use the functions offered by \"Math\", or \"Earthhack\".");
            return;
        }
        final String code = CommandUtil.concatenate(args, 1);
        final Future<?> future = JavaScriptCommand.FIXED_EXECUTOR.submit(new SafeRunnable() {
            @Override
            public void runSafely() throws Throwable {
                final Object o = JavaScriptCommand.this.engine.eval(code);
                if (o != null || JavaScriptCommand.this.jsNull) {
                    Globals.mc.addScheduledTask(() -> ChatUtil.sendMessage(o + ""));
                }
            }
            
            @Override
            public void handle(final Throwable t) {
                String message;
                if (JavaScriptCommand.this.replaceRn) {
                    message = t.getMessage().replace("\r\n", "\n");
                }
                else {
                    message = t.getMessage();
                }
                Globals.mc.addScheduledTask(() -> ChatUtil.sendMessage("<JavaScript> §cError: " + message));
            }
        });
        if (noTimeout) {
            return;
        }
        final long finalTimeout = timeout;
        JavaScriptCommand.SCHEDULER.schedule(() -> {
            if (future.cancel(true)) {
                final double t = MathUtil.round(finalTimeout / 1000.0, 2);
                JavaScriptCommand.mc.addScheduledTask(() -> ChatUtil.sendMessage("<JavaScript> §c" + t + " seconds passed, your js timed out!"));
            }
        }, timeout, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        if (completer.isSame() && completer.getArgs().length > 1) {
            final String[] args = completer.getArgs();
            final String last = args[args.length - 1];
            if (last.isEmpty()) {
                if (args.length == 2) {
                    return completer;
                }
                return super.onTabComplete(completer);
            }
            else {
                for (final Map.Entry<String, String[]> entry : this.arguments.entrySet()) {
                    if (last.startsWith(entry.getKey())) {
                        final String[] l = entry.getValue();
                        if (l.length == 0) {
                            continue;
                        }
                        if (entry.getKey().length() == last.length()) {
                            completer.setResult(completer.getInitial() + l[0]);
                            return completer;
                        }
                        if (l.length == 1) {
                            return completer;
                        }
                        final String r = Commands.getPrefix() + CommandUtil.concatenate(args, 0, args.length - 1);
                        if (last.equals(l[l.length - 1])) {
                            final String s = entry.getKey() + l[0];
                            return completer.setResult(r + " " + s);
                        }
                        boolean found = false;
                        for (final String s2 : entry.getValue()) {
                            final String o = entry.getKey() + s2;
                            if (found) {
                                return completer.setResult(r + " " + o);
                            }
                            if (o.equals(last)) {
                                found = true;
                            }
                        }
                    }
                }
            }
        }
        return super.onTabComplete(completer);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length == 1 && args[0].length() > 10 && "javascriptnotimeout".startsWith(args[0].toLowerCase())) {
            return new PossibleInputs(TextUtil.substring("JavaScriptNoTimeout", args[0].length()), " <code>");
        }
        if (args.length < 2) {
            return super.getPossibleInputs(args);
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        final String last = args[args.length - 1];
        if (!last.isEmpty()) {
            for (final Map.Entry<String, String[]> entry : this.arguments.entrySet()) {
                if (entry.getKey().startsWith(last) || last.startsWith(entry.getKey())) {
                    if (last.length() < entry.getKey().length()) {
                        return inputs.setCompletion(TextUtil.substring(entry.getKey(), last.length()));
                    }
                    for (final String s : entry.getValue()) {
                        final String o = entry.getKey() + s;
                        if (o.startsWith(last)) {
                            return inputs.setCompletion(TextUtil.substring(o, last.length()));
                        }
                    }
                }
            }
        }
        boolean string = false;
        final Deque<Character> lastOpened = new LinkedList<Character>();
        for (int i = 1; i < args.length; ++i) {
            final String s2 = args[i];
            for (int j = 0; j < s2.length(); ++j) {
                final char c = s2.charAt(j);
                if (!string || c == '\'') {
                    switch (c) {
                        case '\'': {
                            if (!lastOpened.isEmpty() && lastOpened.getLast() == '\'') {
                                lastOpened.pollLast();
                                string = false;
                                break;
                            }
                            lastOpened.add('\'');
                            string = true;
                            break;
                        }
                        case '{': {
                            lastOpened.add('}');
                            break;
                        }
                        case '}': {
                            final Character l = lastOpened.pollLast();
                            if (l == null || l == ')') {
                                return inputs.setRest("Did you forget a \")\" somewhere?");
                            }
                            break;
                        }
                        case '(': {
                            lastOpened.add(')');
                            break;
                        }
                        case ')': {
                            final Character l = lastOpened.pollLast();
                            if (l == null || l == '}') {
                                return inputs.setRest("Did you forget a \"}\" somewhere?");
                            }
                            break;
                        }
                    }
                }
            }
        }
        final Character opened = lastOpened.pollLast();
        if (opened != null) {
            return inputs.setCompletion((opened == '\'') ? "'" : (last.isEmpty() ? ("  " + opened) : (" " + opened)));
        }
        return inputs;
    }
    
    private void setupArguments() {
        this.arguments.put("function", JavaScriptCommand.EMPTY);
        this.arguments.put("return", JavaScriptCommand.EMPTY);
        this.arguments.put("Infinity", JavaScriptCommand.EMPTY);
        this.arguments.put("NaN", JavaScriptCommand.EMPTY);
        this.arguments.put("null", JavaScriptCommand.EMPTY);
        this.arguments.put("isNaN(", JavaScriptCommand.EMPTY);
        this.arguments.put("isFinite(", JavaScriptCommand.EMPTY);
        this.arguments.put("eval(", JavaScriptCommand.EMPTY);
        this.arguments.put("Earthhack.", new String[] { "command(", "isEnabled(" });
        final List<String> mathArgs = new ArrayList<String>(42);
        mathArgs.add("abs(");
        mathArgs.add("acos(");
        mathArgs.add("acosh(");
        mathArgs.add("asin(");
        mathArgs.add("asinh(");
        mathArgs.add("atan(");
        mathArgs.add("atanh(");
        mathArgs.add("cbrt(");
        mathArgs.add("ceil(");
        mathArgs.add("clz32(");
        mathArgs.add("cos(");
        mathArgs.add("cosh(");
        mathArgs.add("exp(");
        mathArgs.add("expm1(");
        mathArgs.add("floor(");
        mathArgs.add("fround(");
        mathArgs.add("hypot(");
        mathArgs.add("imul(");
        mathArgs.add("log(");
        mathArgs.add("log1p(");
        mathArgs.add("log10(");
        mathArgs.add("log2(");
        mathArgs.add("max(");
        mathArgs.add("min(");
        mathArgs.add("pow(");
        mathArgs.add("random(");
        mathArgs.add("round(");
        mathArgs.add("sign(");
        mathArgs.add("sin(");
        mathArgs.add("sinh(");
        mathArgs.add("sqrt(");
        mathArgs.add("tan(");
        mathArgs.add("tanh(");
        mathArgs.add("trunc(");
        mathArgs.add("E");
        mathArgs.add("LN2");
        mathArgs.add("LN10");
        mathArgs.add("LOG2E");
        mathArgs.add("LOG10E");
        mathArgs.add("PI");
        mathArgs.add("SQRT1_2");
        mathArgs.add("SQRT2");
        this.arguments.put("Math.", mathArgs.toArray(new String[0]));
    }
    
    static {
        EMPTY = new String[0];
    }
}
