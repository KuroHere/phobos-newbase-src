// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.settingspoof;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;

final class ListenerSettings extends ModuleListener<SettingSpoof, PacketEvent.Send<CPacketClientSettings>>
{
    public ListenerSettings(final SettingSpoof module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketClientSettings.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketClientSettings> event) {
        final ICPacketClientSettings p = event.getPacket();
        p.setLang(((SettingSpoof)this.module).getLanguage(p.getLang()));
        p.setView(((SettingSpoof)this.module).getRenderDistance(p.getView()));
        p.setChatVisibility(((SettingSpoof)this.module).getVisibility(p.getChatVisibility()));
        p.setEnableColors(((SettingSpoof)this.module).getChatColors(p.getEnableColors()));
        p.setModelPartFlags(((SettingSpoof)this.module).getModelParts(p.getModelPartFlags()));
        p.setMainHand(((SettingSpoof)this.module).getHandSide(p.getMainHand()));
    }
}
