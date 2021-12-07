// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.modes;

public enum AntiFriendPop
{
    None {
        @Override
        public boolean shouldCalc(final AntiFriendPop type) {
            return false;
        }
    }, 
    Place {
        @Override
        public boolean shouldCalc(final AntiFriendPop type) {
            return type == AntiFriendPop$2.Place;
        }
    }, 
    Break {
        @Override
        public boolean shouldCalc(final AntiFriendPop type) {
            return type == AntiFriendPop$3.Break;
        }
    }, 
    All {
        @Override
        public boolean shouldCalc(final AntiFriendPop type) {
            return true;
        }
    };
    
    public abstract boolean shouldCalc(final AntiFriendPop p0);
}
