// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.entity;

import java.util.*;
import javax.swing.text.html.parser.*;
import net.minecraft.entity.player.*;

public interface IEntityProvider
{
    List<Entity> getEntities();
    
    List<EntityPlayer> getPlayers();
}
