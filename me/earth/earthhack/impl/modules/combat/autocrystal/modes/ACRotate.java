// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.modes;

public enum ACRotate
{
    None {
        @Override
        public boolean noRotate(final ACRotate rotate) {
            return true;
        }
    }, 
    Break {
        @Override
        public boolean noRotate(final ACRotate rotate) {
            return rotate == ACRotate$2.Place || rotate == ACRotate$2.None;
        }
    }, 
    Place {
        @Override
        public boolean noRotate(final ACRotate rotate) {
            return rotate == ACRotate$3.Break || rotate == ACRotate$3.None;
        }
    }, 
    All {
        @Override
        public boolean noRotate(final ACRotate rotate) {
            return false;
        }
    };
    
    public abstract boolean noRotate(final ACRotate p0);
}
