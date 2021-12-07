// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.offhand;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.util.bind.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;

final class ListenerKeyboard extends ModuleListener<Offhand, KeyboardEvent>
{
    public ListenerKeyboard(final Offhand module) {
        super(module, (Class<? super Object>)KeyboardEvent.class);
    }
    
    public void invoke(final KeyboardEvent event) {
        if (event.getEventState()) {
            if (event.getKey() == ((Offhand)this.module).gappleBind.getValue().getKey()) {
                if (((Offhand)this.module).cToTotem.getValue() && (!((Offhand)this.module).crystalsIfNoTotem.getValue() || InventoryUtil.getCount(Items.field_190929_cY) != 0 || !((Offhand)this.module).setSlotTimer.passed(250L)) && OffhandMode.CRYSTAL.equals(((Offhand)this.module).getMode())) {
                    ((Offhand)this.module).setMode(OffhandMode.TOTEM);
                }
                else {
                    ((Offhand)this.module).setMode((((Offhand)this.module).getMode() == OffhandMode.GAPPLE) ? OffhandMode.TOTEM : OffhandMode.GAPPLE);
                }
            }
            else if (event.getKey() == ((Offhand)this.module).crystalBind.getValue().getKey()) {
                ((Offhand)this.module).setMode(OffhandMode.CRYSTAL.equals(((Offhand)this.module).getMode()) ? OffhandMode.TOTEM : OffhandMode.CRYSTAL);
            }
        }
    }
}
