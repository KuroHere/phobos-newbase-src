// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.tools.obfuscation.mapping.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.*;

public interface IObfuscationManager
{
    void init();
    
    IObfuscationDataProvider getDataProvider();
    
    IReferenceManager getReferenceManager();
    
    IMappingConsumer createMappingConsumer();
    
    List<ObfuscationEnvironment> getEnvironments();
    
    void writeMappings();
    
    void writeReferences();
}
