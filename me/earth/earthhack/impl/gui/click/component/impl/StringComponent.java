//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component.impl;

import me.earth.earthhack.impl.gui.click.component.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.util.*;

public class StringComponent extends Component
{
    private final StringSetting stringSetting;
    public boolean isListening;
    private CurrentString currentString;
    private boolean idling;
    private final StopWatch idleTimer;
    
    public StringComponent(final StringSetting stringSetting, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        super(stringSetting.getName(), posX, posY, offsetX, offsetY, width, height);
        this.currentString = new CurrentString("");
        this.idleTimer = new StopWatch();
        this.stringSetting = stringSetting;
    }
    
    @Override
    public void moved(final float posX, final float posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + 5.0f, this.getFinishedY() + 1.0f, this.getWidth() - 10.0f, this.getHeight() - 2.0f);
        Render2DUtil.drawBorderedRect(this.getFinishedX() + 4.5f, this.getFinishedY() + 1.0f, this.getFinishedX() + this.getWidth() - 4.5f, this.getFinishedY() + this.getHeight() - 0.5f, 0.5f, hovered ? 1714631475 : 0, -16777216);
        if (this.isListening) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.currentString.getString() + this.getIdleSign(), this.getFinishedX() + 6.5f, this.getFinishedY() + this.getHeight() - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1.0f, this.getState() ? -1 : -5592406);
        }
        else {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow((String)this.getStringSetting().getValue(), this.getFinishedX() + 6.5f, this.getFinishedY() + this.getHeight() - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1.0f, this.getState() ? -1 : -5592406);
        }
    }
    
    @Override
    public void keyTyped(final char character, final int keyCode) {
        super.keyTyped(character, keyCode);
        if (this.isListening) {
            if (keyCode == 1) {
                return;
            }
            if (keyCode == 28) {
                this.enterString();
                this.setListening(false);
            }
            else if (keyCode == 14) {
                this.setString(removeLastChar(this.currentString.getString()));
            }
            else {
                Label_0131: {
                    if (keyCode == 47) {
                        if (!Keyboard.isKeyDown(157)) {
                            if (!Keyboard.isKeyDown(29)) {
                                break Label_0131;
                            }
                        }
                        try {
                            this.setString(this.currentString.getString() + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                if (ChatAllowedCharacters.isAllowedCharacter(character)) {
                    this.setString(this.currentString.getString() + character);
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + 5.0f, this.getFinishedY() + 1.0f, this.getWidth() - 10.0f, this.getHeight() - 2.0f);
        if (hovered && mouseButton == 0) {
            this.toggle();
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }
    
    public String getIdleSign() {
        if (this.idleTimer.passed(500L)) {
            this.idling = !this.idling;
            this.idleTimer.reset();
        }
        if (this.idling) {
            return "_";
        }
        return "";
    }
    
    private void enterString() {
        if (this.currentString.getString().isEmpty()) {
            this.getStringSetting().setValue(this.getStringSetting().getInitial());
        }
        else {
            this.getStringSetting().setValue(this.currentString.getString());
        }
        this.setString("");
    }
    
    public StringSetting getStringSetting() {
        return this.stringSetting;
    }
    
    public void toggle() {
        this.isListening = !this.isListening;
    }
    
    public boolean getState() {
        return !this.isListening;
    }
    
    public void setListening(final boolean listening) {
        this.isListening = listening;
    }
    
    public void setString(final String newString) {
        this.currentString = new CurrentString(newString);
    }
    
    public static String removeLastChar(final String str) {
        String output = "";
        if (str != null && str.length() > 0) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }
    
    public static class CurrentString
    {
        private final String string;
        
        public CurrentString(final String string) {
            this.string = string;
        }
        
        public String getString() {
            return this.string;
        }
    }
}
