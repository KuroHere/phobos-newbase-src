// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.command;

public class PossibleInputs
{
    private String completion;
    private String rest;
    
    public PossibleInputs(final String completion, final String rest) {
        this.completion = completion;
        this.rest = rest;
    }
    
    public PossibleInputs setCompletion(final String completion) {
        this.completion = completion;
        return this;
    }
    
    public PossibleInputs setRest(final String rest) {
        this.rest = rest;
        return this;
    }
    
    public String getFullText() {
        return this.completion + this.rest;
    }
    
    public String getCompletion() {
        return this.completion;
    }
    
    public String getRest() {
        return this.rest;
    }
    
    public static PossibleInputs empty() {
        return new PossibleInputs("", "");
    }
}
