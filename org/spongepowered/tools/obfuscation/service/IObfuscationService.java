// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.tools.obfuscation.service;

import java.util.*;

public interface IObfuscationService
{
    Set<String> getSupportedOptions();
    
    Collection<ObfuscationTypeDescriptor> getObfuscationTypes();
}
