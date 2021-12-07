// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.event;

public class SettingResult
{
    public static final SettingResult SUCCESSFUL;
    private final boolean successful;
    private final String message;
    
    public SettingResult(final boolean successful, final String message) {
        this.successful = successful;
        this.message = message;
    }
    
    public boolean wasSuccessful() {
        return this.successful;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    static {
        SUCCESSFUL = new SettingResult(true, "");
    }
}
