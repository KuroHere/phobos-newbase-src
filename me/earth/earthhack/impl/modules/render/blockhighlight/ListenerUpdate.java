//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.blockhighlight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

final class ListenerUpdate extends ModuleListener<BlockHighlight, UpdateEvent>
{
    public ListenerUpdate(final BlockHighlight module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        if (ListenerUpdate.mc.objectMouseOver != null) {
            switch (ListenerUpdate.mc.objectMouseOver.typeOfHit) {
                case BLOCK: {
                    final BlockPos pos = ListenerUpdate.mc.objectMouseOver.getBlockPos();
                    if (!ListenerUpdate.mc.world.getWorldBorder().contains(pos)) {
                        break;
                    }
                    final IBlockState state = ListenerUpdate.mc.world.getBlockState(pos);
                    if (state.getMaterial() != Material.AIR) {
                        final ItemStack stack = state.getBlock().getItem((World)ListenerUpdate.mc.world, pos, state);
                        ((BlockHighlight)this.module).current = stack.getItem().getItemStackDisplayName(stack);
                        return;
                    }
                    break;
                }
                case ENTITY: {
                    final Entity entity = ListenerUpdate.mc.objectMouseOver.entityHit;
                    if (entity != null) {
                        ((BlockHighlight)this.module).current = EntityNames.getName(entity);
                        return;
                    }
                    break;
                }
            }
        }
        ((BlockHighlight)this.module).current = null;
    }
}
