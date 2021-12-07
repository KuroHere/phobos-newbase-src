// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

import java.io.*;

public interface IConnection extends IConnectionEntry, ICloseable, ISender
{
    void setName(final String p0);
    
    InputStream getInputStream() throws IOException;
    
    OutputStream getOutputStream() throws IOException;
}
