// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.newdawn.slick.openal;

import java.io.*;

interface AudioInputStream
{
    int getChannels();
    
    int getRate();
    
    int read() throws IOException;
    
    int read(final byte[] p0) throws IOException;
    
    int read(final byte[] p0, final int p1, final int p2) throws IOException;
    
    boolean atEnd();
    
    void close() throws IOException;
}
