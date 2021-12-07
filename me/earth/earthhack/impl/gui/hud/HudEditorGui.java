//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.hud;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.misc.*;
import java.util.*;
import java.io.*;
import me.earth.earthhack.impl.*;

public class HudEditorGui extends GuiScreen
{
    private static HudEditorGui INSTANCE;
    private static final Minecraft mc;
    private HudPanel panel;
    private Set<SnapPoint> snapPoints;
    private Map<HudElement, SnapPoint> moduleSnapPoints;
    
    public HudEditorGui() {
        HudEditorGui.INSTANCE = this;
        this.panel = new HudPanel();
        this.snapPoints = new HashSet<SnapPoint>();
        this.moduleSnapPoints = new HashMap<HudElement, SnapPoint>();
        for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (element.isEnabled()) {
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.TOP));
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.BOTTOM));
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.LEFT));
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.RIGHT));
            }
        }
        this.snapPoints.add(new SnapPoint(2.0f, -2.0f, (float)(HudEditorGui.mc.displayHeight + 4), Orientation.LEFT));
        this.snapPoints.add(new SnapPoint((float)(this.width - 2), -2.0f, (float)(HudEditorGui.mc.displayHeight + 4), Orientation.RIGHT));
        this.snapPoints.add(new SnapPoint(-2.0f, 2.0f, (float)(HudEditorGui.mc.displayWidth + 4), Orientation.TOP));
        this.snapPoints.add(new SnapPoint(-2.0f, (float)(this.height - 2), (float)(HudEditorGui.mc.displayWidth + 4), Orientation.BOTTOM));
    }
    
    public static HudEditorGui getInstance() {
        return HudEditorGui.INSTANCE;
    }
    
    public void onToggle() {
        this.snapPoints.clear();
        this.moduleSnapPoints.clear();
        for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (element.isEnabled()) {
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.TOP));
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.BOTTOM));
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.LEFT));
                this.moduleSnapPoints.put(element, new ElementSnapPoint(element, Orientation.RIGHT));
            }
        }
        this.snapPoints.add(new SnapPoint(2.0f, -2.0f, (float)(this.height + 4), Orientation.LEFT));
        this.snapPoints.add(new SnapPoint((float)(this.width - 2), -2.0f, (float)(this.height + 4), Orientation.RIGHT));
        this.snapPoints.add(new SnapPoint(-2.0f, 2.0f, (float)(this.width + 4), Orientation.TOP));
        this.snapPoints.add(new SnapPoint(-2.0f, (float)(this.height - 2), (float)(this.width + 4), Orientation.BOTTOM));
    }
    
    public HudPanel getPanel() {
        return this.panel;
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final List<HudElement> clicked = new ArrayList<HudElement>();
        final Iterator<HudElement> iterator = Managers.ELEMENTS.getRegistered().iterator();
        HudElement element = null;
        while (iterator.hasNext()) {
            element = iterator.next();
            if (element.isEnabled() && GuiUtil.isHovered(element, mouseX, mouseY)) {
                clicked.add(element);
            }
        }
        clicked.sort(Comparator.comparing(element -> element.getZ()));
        if (!clicked.isEmpty()) {
            clicked.get(0).guiMouseClicked(mouseX, mouseY, mouseButton);
        }
        this.panel.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (element.isEnabled()) {
                element.guiMouseReleased(mouseX, mouseY, state);
            }
        }
        this.panel.mouseReleased(mouseX, mouseY, state);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (element.isEnabled()) {
                element.guiKeyPressed(typedChar, keyCode);
            }
        }
        this.panel.keyPressed(typedChar, keyCode);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (final SnapPoint point : this.snapPoints) {
            point.update(mouseX, mouseY, partialTicks);
            point.draw(mouseX, mouseY, partialTicks);
            Earthhack.getLogger().info(point.orientation.name() + " x: " + point.getX() + " y: " + point.getY() + " length: " + point.getLength());
        }
        for (final SnapPoint point : this.moduleSnapPoints.values()) {
            point.update(mouseX, mouseY, partialTicks);
        }
        for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
            if (element.isEnabled()) {
                element.guiUpdate(mouseX, mouseY, partialTicks);
                element.guiDraw(mouseX, mouseY, partialTicks);
            }
        }
        this.panel.draw(mouseX, mouseY, partialTicks);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public Set<SnapPoint> getSnapPoints() {
        return this.snapPoints;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
