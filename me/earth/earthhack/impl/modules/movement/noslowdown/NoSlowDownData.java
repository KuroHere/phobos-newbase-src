// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class NoSlowDownData extends DefaultData<NoSlowDown>
{
    public NoSlowDownData(final NoSlowDown module) {
        super(module);
        this.register(module.guiMove, "Allows you to move while you have a Gui open. (Your Inventory, a chest etc.)");
        this.register(module.items, "Using items (like eating food) won't slow you down.");
        this.register(module.legit, "Needed on some servers to not get lagged back by the AntiCheat.");
        this.register(module.sprint, "Development setting, should be on.");
        this.register(module.input, "Development setting, should be on.");
        this.register(module.websY, "Multiplier for moving vertically through webs.");
        this.register(module.websXZ, "Multiplier for moving horizontally through webs.");
        this.register(module.sneak, "Only apply the WebsVertical modifier when your sneak button is pressed.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Allows you to move in Guis and makes you move normally while eating or in webs.";
    }
}
