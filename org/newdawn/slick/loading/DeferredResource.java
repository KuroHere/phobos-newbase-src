// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.newdawn.slick.loading;

import java.io.*;

public interface DeferredResource
{
    void load() throws IOException;
    
    String getDescription();
}
