// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol;

import java.nio.charset.*;
import java.io.*;
import java.nio.*;
import com.google.common.base.*;
import me.earth.earthhack.impl.modules.client.server.api.*;

public class ProtocolUtil
{
    public static void sendMessage(final IConnection connection, final int id, final String s) throws IOException {
        connection.send(writeString(id, s));
    }
    
    public static byte[] writeString(final int id, final String message) {
        final byte[] bytes = new byte[message.length() + 8];
        addInt(id, bytes);
        addInt(message.length(), bytes, 4);
        addAllBytes(message.getBytes(StandardCharsets.UTF_8), bytes, 8);
        return bytes;
    }
    
    public static void addAllBytes(final byte[] from, final byte[] to, final int start) {
        System.arraycopy(from, 0, to, start, from.length);
    }
    
    public static void addInt(final int integer, final byte[] bytes) {
        addInt(integer, bytes, 0);
    }
    
    public static void addInt(final int integer, final byte[] bytes, final int offset) {
        bytes[offset] = (byte)((integer & 0xFF000000) >>> 24);
        bytes[offset + 1] = (byte)((integer & 0xFF0000) >>> 16);
        bytes[offset + 2] = (byte)((integer & 0xFF00) >>> 8);
        bytes[offset + 3] = (byte)(integer & 0xFF);
    }
    
    public static void copy(final byte[] from, final byte[] to, final int offset) {
        if (to.length - offset < 0) {
            throw new IndexOutOfBoundsException(from.length + " : " + to.length + " : " + offset);
        }
        System.arraycopy(from, 0, to, offset, from.length);
    }
    
    public static IPacket readPacket(final DataInputStream in) throws IOException {
        final int id = in.readInt();
        final int size = in.readInt();
        final byte[] buffer = new byte[size];
        final int read = in.read(buffer);
        if (read != size) {
            throw new IOException("Expected " + size + " bytes, but found: " + read);
        }
        return new SimplePacket(id, buffer);
    }
    
    public static byte[] serializeServerList(final IConnectionManager manager) {
        final IConnectionEntry[] entries = manager.getConnections().toArray(new IConnection[0]);
        int size = 0;
        int count = 0;
        for (final IConnectionEntry entry : entries) {
            if (entry != null) {
                ++count;
                size += 8 + entry.getName().length();
            }
        }
        final ByteBuffer buffer = ByteBuffer.allocate(size + 12);
        buffer.putInt(8);
        buffer.putInt(size);
        buffer.putInt(count);
        for (final IConnectionEntry entry2 : entries) {
            if (entry2 != null) {
                buffer.putInt(entry2.getId());
                buffer.putInt(entry2.getName().length());
                buffer.put(entry2.getName().getBytes(StandardCharsets.UTF_8));
            }
        }
        return buffer.array();
    }
    
    public static IConnectionEntry[] deserializeServerList(final byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);
        final int count = buffer.getInt();
        final IConnectionEntry[] entries = new IConnectionEntry[count];
        for (int i = 0; i < entries.length; ++i) {
            final int id = buffer.getInt();
            final int size = buffer.getInt();
            final byte[] name = new byte[size];
            buffer.get(name);
            entries[i] = new SimpleEntry(new String(name, Charsets.UTF_8), id);
        }
        return entries;
    }
}
