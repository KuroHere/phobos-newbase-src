//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.guis;

import java.util.concurrent.atomic.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.cache.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import java.net.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.util.text.*;
import org.apache.logging.log4j.*;
import me.earth.earthhack.impl.modules.*;

public class GuiConnectingPingBypass extends GuiScreen
{
    private static final AtomicInteger CONNECTION_ID;
    private static final Logger LOGGER;
    private static final SettingCache<String, StringSetting, PingBypass> IP;
    private static final ModuleCache<PingBypass> PINGBYPASS;
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;
    
    public GuiConnectingPingBypass(final GuiScreen parent, final Minecraft mcIn, final ServerData serverDataIn) {
        this.mc = mcIn;
        this.previousGuiScreen = parent;
        mcIn.loadWorld((WorldClient)null);
        mcIn.setServerData(serverDataIn);
        final ServerAddress serveraddress = ServerAddress.fromString(serverDataIn.serverIP);
        this.connect(GuiConnectingPingBypass.IP.getValue(), GuiConnectingPingBypass.PINGBYPASS.returnIfPresent(PingBypass::getPort, 25565), serveraddress.getIP(), serveraddress.getPort());
    }
    
    private void connect(final String proxyIP, final int proxyPort, final String actualIP, final int actualPort) {
        GuiConnectingPingBypass.LOGGER.info("Connecting to PingBypass: {}, {}", (Object)proxyIP, (Object)proxyPort);
        new Thread("Server Connector #" + GuiConnectingPingBypass.CONNECTION_ID.incrementAndGet()) {
            @Override
            public void run() {
                InetAddress inetaddress = null;
                try {
                    if (GuiConnectingPingBypass.this.cancel) {
                        return;
                    }
                    inetaddress = InetAddress.getByName(proxyIP);
                    GuiConnectingPingBypass.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, proxyPort, GuiConnectingPingBypass.this.mc.gameSettings.isUsingNativeTransport());
                    GuiConnectingPingBypass.this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginClient(GuiConnectingPingBypass.this.networkManager, GuiConnectingPingBypass.this.mc, GuiConnectingPingBypass.this.previousGuiScreen));
                    GuiConnectingPingBypass.this.networkManager.sendPacket((Packet)new C00Handshake(actualIP, actualPort, EnumConnectionState.LOGIN, true));
                    GuiConnectingPingBypass.this.networkManager.sendPacket((Packet)new CPacketLoginStart(GuiConnectingPingBypass.this.mc.getSession().getProfile()));
                }
                catch (UnknownHostException e) {
                    if (GuiConnectingPingBypass.this.cancel) {
                        return;
                    }
                    GuiConnectingPingBypass.LOGGER.error("Couldn't connect to PingBypass", (Throwable)e);
                    GuiConnectingPingBypass.this.mc.addScheduledTask(() -> {
                        final Minecraft mc = GuiConnectingPingBypass.this.mc;
                        new(net.minecraft.client.gui.GuiDisconnected.class)();
                        GuiConnectingPingBypass.this.previousGuiScreen;
                        new TextComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" });
                        final TextComponentTranslation textComponentTranslation;
                        final GuiScreen guiScreen2;
                        final String s3;
                        new GuiDisconnected(guiScreen2, s3, (ITextComponent)textComponentTranslation);
                        final GuiScreen guiScreen;
                        mc.displayGuiScreen(guiScreen);
                    });
                }
                catch (Exception exception) {
                    if (GuiConnectingPingBypass.this.cancel) {
                        return;
                    }
                    GuiConnectingPingBypass.LOGGER.error("Couldn't connect to PingBypass", (Throwable)exception);
                    String s = exception.toString();
                    if (inetaddress != null) {
                        final String s2 = inetaddress + ":" + proxyPort;
                        s = s.replace(s2, "");
                    }
                    final String finalS = s;
                    GuiConnectingPingBypass.this.mc.addScheduledTask(() -> {
                        final Minecraft mc2 = GuiConnectingPingBypass.this.mc;
                        new(net.minecraft.client.gui.GuiDisconnected.class)();
                        GuiConnectingPingBypass.this.previousGuiScreen;
                        new TextComponentTranslation("disconnect.genericReason", new Object[] { finalS });
                        final TextComponentTranslation textComponentTranslation2;
                        final GuiScreen guiScreen4;
                        final String s4;
                        new GuiDisconnected(guiScreen4, s4, (ITextComponent)textComponentTranslation2);
                        final GuiScreen guiScreen3;
                        mc2.displayGuiScreen(guiScreen3);
                    });
                }
            }
        }.start();
    }
    
    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            }
            else {
                this.networkManager.checkDisconnected();
            }
        }
    }
    
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            this.cancel = true;
            if (this.networkManager != null) {
                this.networkManager.closeChannel((ITextComponent)new TextComponentString("Aborted"));
            }
            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        if (this.networkManager == null) {
            this.drawCenteredString(this.fontRendererObj, "Authentication" + IdleUtil.getDots(), this.width / 2 + IdleUtil.getDots().length(), this.height / 2 - 50, 16777215);
        }
        else {
            this.drawCenteredString(this.fontRendererObj, "Loading PingBypass" + IdleUtil.getDots(), this.width / 2 + IdleUtil.getDots().length(), this.height / 2 - 50, 16777215);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        CONNECTION_ID = new AtomicInteger(0);
        LOGGER = LogManager.getLogger();
        IP = Caches.getSetting(PingBypass.class, StringSetting.class, "IP", "Proxy-IP");
        PINGBYPASS = Caches.getModule(PingBypass.class);
    }
}
