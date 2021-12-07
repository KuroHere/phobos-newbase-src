//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowkill;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import com.mojang.text2speech.*;
import me.earth.earthhack.api.event.events.*;
import com.mojang.realmsclient.gui.*;
import me.earth.earthhack.impl.util.text.*;

final class ListenerEntityChunk extends ModuleListener<BowKiller, EntityChunkEvent>
{
    private final Narrator narrator;
    
    public ListenerEntityChunk(final BowKiller module) {
        super(module, (Class<? super Object>)EntityChunkEvent.class);
        this.narrator = Narrator.getNarrator();
    }
    
    public void invoke(final EntityChunkEvent event) {
        if (!((BowKiller)this.module).oppSpotted.getValue()) {
            return;
        }
        if (event.getStage() == Stage.PRE && event.getEntity() != null && ((BowKiller)this.module).isValid(event.getEntity())) {
            ChatUtil.sendMessage(ChatFormatting.RED + "Opp detected I repeat opp detected!");
            if (!((BowKiller)this.module).hasEntity(event.getEntity().getUniqueID().toString())) {
                this.narrator.clear();
                this.narrator.say("Ah pu  Detected!");
                ((BowKiller)this.module).entityDataArrayList.add(new BowKiller.EntityData(event.getEntity().getUniqueID().toString(), System.currentTimeMillis()));
            }
        }
    }
}
