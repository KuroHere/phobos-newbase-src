// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.srg2notch.remappers;

import org.objectweb.asm.tree.*;
import java.util.*;
import org.objectweb.asm.*;
import me.earth.earthhack.installer.srg2notch.*;

public class AnnotationRemapper implements Remapper
{
    @Override
    public void remap(final ClassNode cn, final Mapping mapping) {
        if (cn.invisibleAnnotations != null) {
            for (final AnnotationNode node : cn.invisibleAnnotations) {
                this.remapAnnotation(node, mapping);
            }
        }
        if (cn.visibleAnnotations != null) {
            for (final AnnotationNode node : cn.visibleAnnotations) {
                this.remapAnnotation(node, mapping);
            }
        }
    }
    
    private void remapAnnotation(final AnnotationNode node, final Mapping mapping) {
        if (node.values == null || node.values.isEmpty()) {
            return;
        }
        final List<Object> values = new ArrayList<Object>(node.values.size());
        this.remapList(node.values, values, mapping);
        node.values = values;
    }
    
    private void remapList(final List<Object> objects, final List<Object> collecting, final Mapping mapping) {
        for (Object t : objects) {
            if (t instanceof Type) {
                t = MappingUtil.map((Type)t, mapping);
            }
            else if (t instanceof List) {
                final List<Object> list = (List<Object>)t;
                t = new ArrayList(list.size());
                this.remapList(list, (List<Object>)t, mapping);
            }
            collecting.add(t);
        }
    }
}
