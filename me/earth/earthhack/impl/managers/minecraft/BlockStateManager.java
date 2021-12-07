//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import java.util.function.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import me.earth.earthhack.api.event.bus.*;
import java.util.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import me.earth.earthhack.impl.event.events.network.*;

public class BlockStateManager extends SubscriberImpl implements Globals
{
    private final Map<BlockPos, Queue<Consumer<IBlockState>>> callbacks;
    
    public BlockStateManager() {
        this.callbacks = new ConcurrentHashMap<BlockPos, Queue<Consumer<IBlockState>>>();
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketBlockChange.class, event -> {
            final SPacketBlockChange packet = (SPacketBlockChange)event.getPacket();
            this.process(packet.getBlockPosition(), packet.getBlockState());
            return;
        }));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketMultiBlockChange.class, event -> {
            final SPacketMultiBlockChange packet2 = (SPacketMultiBlockChange)event.getPacket();
            packet2.getChangedBlocks();
            final SPacketMultiBlockChange.BlockUpdateData[] array;
            int i = 0;
            for (int length = array.length; i < length; ++i) {
                final SPacketMultiBlockChange.BlockUpdateData data = array[i];
                this.process(data.getPos(), data.getBlockState());
            }
            return;
        }));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketExplosion.class, event -> {
            final SPacketExplosion packet3 = (SPacketExplosion)event.getPacket();
            packet3.getAffectedBlockPositions().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final BlockPos pos = iterator.next();
                this.process(pos, Blocks.AIR.getDefaultState());
            }
            return;
        }));
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                BlockStateManager.this.callbacks.clear();
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Unload>(WorldClientEvent.Unload.class) {
            @Override
            public void invoke(final WorldClientEvent.Unload event) {
                BlockStateManager.this.callbacks.clear();
            }
        });
    }
    
    public void addCallback(final BlockPos pos, final Consumer<IBlockState> callback) {
        this.callbacks.computeIfAbsent(pos.toImmutable(), v -> new ConcurrentLinkedQueue()).add(callback);
    }
    
    private void process(final BlockPos pos, final IBlockState state) {
        final Queue<Consumer<IBlockState>> cbs = this.callbacks.remove(pos);
        if (cbs != null) {
            CollectionUtil.emptyQueue(cbs, c -> c.accept(state));
        }
    }
}
