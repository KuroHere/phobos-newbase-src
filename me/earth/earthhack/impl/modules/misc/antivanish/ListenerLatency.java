//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antivanish;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.misc.antivanish.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.lookup.*;
import java.util.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.util.text.*;

final class ListenerLatency extends ModuleListener<AntiVanish, PacketEvent.Receive<SPacketPlayerListItem>>
{
    public ListenerLatency(final AntiVanish module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerListItem.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerListItem> event) {
        final SPacketPlayerListItem packet = event.getPacket();
        if (packet.getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
            for (final SPacketPlayerListItem.AddPlayerData data : packet.getEntries()) {
                final UUID id = data.getProfile().getId();
                if (ListenerLatency.mc.getConnection().getPlayerInfo(id) == null) {
                    if (!((AntiVanish)this.module).timer.passed(1000L)) {
                        return;
                    }
                    String name = data.getProfile().getName();
                    if (name == null && ((AntiVanish)this.module).cache.containsKey(id)) {
                        final VanishedEntry lookUp = ((AntiVanish)this.module).cache.get(id);
                        if (lookUp == null) {
                            this.sendUnknown();
                            return;
                        }
                        if (System.currentTimeMillis() - lookUp.getTime() < 5000L) {
                            return;
                        }
                        name = lookUp.getName();
                        if (name == null) {
                            this.sendUnknown();
                            return;
                        }
                    }
                    if (name == null) {
                        final int lookUpId = ((AntiVanish)this.module).ids.incrementAndGet();
                        final Future<?> future = Managers.LOOK_UP.doLookUp(new LookUp(LookUp.Type.NAME, id) {
                            @Override
                            public void onSuccess() {
                                ((AntiVanish)ListenerLatency.this.module).futures.remove(lookUpId);
                                ((AntiVanish)ListenerLatency.this.module).cache.put(id, new VanishedEntry(this.name));
                                ListenerLatency.this.sendMessage(this.name);
                            }
                            
                            @Override
                            public void onFailure() {
                                ((AntiVanish)ListenerLatency.this.module).futures.remove(lookUpId);
                                ((AntiVanish)ListenerLatency.this.module).cache.put(id, null);
                                ListenerLatency.this.sendUnknown();
                            }
                        });
                        if (future == null) {
                            continue;
                        }
                        ((AntiVanish)this.module).futures.put(lookUpId, future);
                    }
                    else {
                        this.sendMessage(name);
                    }
                }
            }
        }
    }
    
    private void sendUnknown() {
        ((AntiVanish)this.module).timer.reset();
        ListenerLatency.mc.addScheduledTask(() -> ChatUtil.sendMessage("<" + ((AntiVanish)this.module).getDisplayName() + "> " + "§c" + "Someone just vanished."));
    }
    
    private void sendMessage(final String name) {
        ((AntiVanish)this.module).timer.reset();
        ListenerLatency.mc.addScheduledTask(() -> Managers.CHAT.sendDeleteMessage("<" + ((AntiVanish)this.module).getDisplayName() + "> " + "§c" + name + " vanished.", name, 7000));
    }
}
