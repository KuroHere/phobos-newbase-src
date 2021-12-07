// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.util;

import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.util.text.*;

public class ChatLogger implements ILogger
{
    @Override
    public void log(final String message) {
        ChatUtil.sendMessageScheduled(message);
    }
}
