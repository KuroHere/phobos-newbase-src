// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.util;

import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.core.ducks.util.*;

public class ChatComponentUtil
{
    public static HoverEvent setOffset(final HoverEvent event) {
        return ((IHoverEvent)event).setOffset(false);
    }
}
